package jay.syi.interfaces;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.Closeable;
import java.net.InetSocketAddress;

public interface IStatefulLoginProvider extends Closeable {
	void initLogin(ChannelHandlerContext ctx);

	void incomingResponse(ChannelHandlerContext ctx, ByteBuf buffer);

	InetSocketAddress getSocketAddress();
}
