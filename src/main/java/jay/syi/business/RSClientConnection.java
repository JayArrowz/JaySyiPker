package jay.syi.business;

import jay.syi.interfaces.IStatefulLoginProvider;
import jay.syi.interfaces.IRunescapeChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.Closeable;

public class RSClientConnection implements IRunescapeChannel, Closeable {

	private final NioEventLoopGroup eventLoop;
	private ChannelFuture channelFuture;
	private final IStatefulLoginProvider loginProvider;

	public RSClientConnection(IStatefulLoginProvider loginProvider) {
		eventLoop = new NioEventLoopGroup(1);
		this.loginProvider = loginProvider;
	}

	@Override
	public void login() {
		var bootstrap = new Bootstrap().group(eventLoop)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true);
		channelFuture = bootstrap
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) {
						ChannelPipeline p = ch.pipeline();
						p.addLast(new RunescapeInboundHandler(loginProvider));
					}
				}).connect(this.loginProvider.getSocketAddress());
	}

	@Override
	public void logout() {
		Channel channel = null;
		try {
			channel = channelFuture.sync().channel();
			if (channel.isActive()) {
				channel.disconnect();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		eventLoop.shutdownGracefully();
		channelFuture = null;
	}
}
