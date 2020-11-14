package jay.syi.interfaces.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import jay.syi.model.LoginDetails;

import java.util.HashMap;

public interface IChannelHandlerContextRegister {
	void login(LoginDetails details, ChannelHandlerContext ctx);
	void broadcast(ByteBuf byteBuf);
	void logout(LoginDetails details);
	HashMap<LoginDetails, ChannelHandlerContext> get();
}
