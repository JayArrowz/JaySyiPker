package jay.syi.business;

import jay.syi.interfaces.IStatefulLoginProvider;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RunescapeInboundHandler extends ChannelInboundHandlerAdapter {

	private final IStatefulLoginProvider loginProvider;
	private final static Logger LOGGER = LogManager.getLogger(RunescapeInboundHandler.class);

	public RunescapeInboundHandler(IStatefulLoginProvider loginProvider) {
		this.loginProvider = loginProvider;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		LOGGER.trace("Channel Active {}", ctx.channel().remoteAddress());
		loginProvider.initLogin(ctx);
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf buf = (ByteBuf) msg;
		LOGGER.trace("Incoming message readable bytes {}", buf.readableBytes());
		loginProvider.incomingResponse(ctx, buf);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}
}
