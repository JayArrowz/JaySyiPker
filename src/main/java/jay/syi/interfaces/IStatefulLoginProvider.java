package jay.syi.interfaces;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import jay.syi.model.LoginDetails;

import java.net.InetSocketAddress;

public interface IStatefulLoginProvider {
	void channelActivated(ChannelHandlerContext ctx);

	void channelRead(ChannelHandlerContext ctx, ByteBuf buffer);

	IStatefulLoginProvider set(InetSocketAddress address, LoginDetails loginDetails);

	InetSocketAddress getSocketAddress();

	void channelInactive(ChannelHandlerContext ctx);

	LoginDetails getLoginDetails();
}
