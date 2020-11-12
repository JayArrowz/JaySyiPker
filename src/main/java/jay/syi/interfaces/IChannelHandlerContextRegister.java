package jay.syi.interfaces;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import jay.syi.model.LoginDetails;

import java.util.HashMap;

public interface IChannelHandlerContextRegister {
	void addLogin(LoginDetails details, ChannelHandlerContext ctx);
	void broadcast(ByteBuf byteBuf);
	void logout(LoginDetails details);
	HashMap<LoginDetails, ChannelHandlerContext> getRegister();
}
