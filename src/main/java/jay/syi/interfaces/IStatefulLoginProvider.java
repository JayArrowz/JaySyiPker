package jay.syi.interfaces;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.net.InetSocketAddress;

public interface IStatefulLoginProvider {
	void initLogin(ChannelHandlerContext ctx);

	void incomingResponse(ChannelHandlerContext ctx, ByteBuf buffer);

	InetSocketAddress getSocketAddress();
}
