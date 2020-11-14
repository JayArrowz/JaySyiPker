package jay.syi.business.client;

import jay.syi.interfaces.proxy.IProxyHandlerFactory;
import jay.syi.interfaces.client.IStatefulLoginProvider;
import jay.syi.interfaces.client.IRunescapeChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import jay.syi.model.ProxyDetails;

import java.io.Closeable;

public class RSClientConnection implements IRunescapeChannel, Closeable {

	private final NioEventLoopGroup eventLoop;
	private final IProxyHandlerFactory proxyHandlerFactory;
	private ChannelFuture channelFuture;
	private final IStatefulLoginProvider loginProvider;

	public RSClientConnection(IStatefulLoginProvider loginProvider) {
		this(loginProvider, null);
	}

	public RSClientConnection(IStatefulLoginProvider loginProvider, IProxyHandlerFactory proxyHandlerFactory) {
		this.proxyHandlerFactory = proxyHandlerFactory;
		eventLoop = new NioEventLoopGroup(1);
		this.loginProvider = loginProvider;
	}

	@Override
	public void login(ProxyDetails proxyDetails) {
		var bootstrap = new Bootstrap().group(eventLoop)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true);
		channelFuture = bootstrap
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) {
						ChannelPipeline p = ch.pipeline();
						if(proxyDetails != null && proxyHandlerFactory != null) {
							proxyHandlerFactory.create(proxyDetails);
						}
						p.addLast(new RunescapeInboundHandler(loginProvider, RSClientConnection.this));
					}
				}).connect(this.loginProvider.getSocketAddress());
	}

	@Override
	public void logout() {
		try {
			Channel channel = channelFuture.sync().channel();
			if (channel.isActive()) {
				channel.disconnect();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		logout();
		eventLoop.shutdownGracefully();
		channelFuture = null;
	}
}
