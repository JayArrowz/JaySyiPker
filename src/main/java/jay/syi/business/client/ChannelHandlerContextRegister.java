package jay.syi.business.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import jay.syi.interfaces.client.IChannelHandlerContextRegister;
import jay.syi.model.LoginDetails;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ChannelHandlerContextRegister implements IChannelHandlerContextRegister {
	private static Object lock = new Object();
	private HashMap<LoginDetails, ChannelHandlerContext> loginDetails
			= new HashMap<>();

	@Override
	public void login(LoginDetails details, ChannelHandlerContext ctx) {
		synchronized (lock) {
			loginDetails.put(details, ctx);
		}
	}

	@Override
	public void broadcast(ByteBuf byteBuf) {
		loginDetails.values().forEach(channelHandlerContext -> channelHandlerContext.writeAndFlush(byteBuf));
	}

	@Override
	public void logout(LoginDetails details) {
		loginDetails.remove(details);
	}

	@Override
	public HashMap<LoginDetails, ChannelHandlerContext> get() {
		return loginDetails;
	}

}
