package jay.syi.business.logins;

import io.netty.channel.ChannelHandlerContext;
import jay.syi.interfaces.client.IChannelHandlerContextRegister;
import jay.syi.interfaces.client.IStatefulLoginProvider;
import jay.syi.model.LoginDetails;

import java.net.InetSocketAddress;


public abstract class BaseLoginProvider implements IStatefulLoginProvider {
	private LoginDetails loginDetails;
	private InetSocketAddress socketAddress;

	private IChannelHandlerContextRegister channelHandlerContextRegister;
	public BaseLoginProvider(IChannelHandlerContextRegister channelHandlerContextRegister) {
		this.channelHandlerContextRegister = channelHandlerContextRegister;
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		channelHandlerContextRegister.logout(getLoginDetails());
	}

	@Override
	public IStatefulLoginProvider set(InetSocketAddress address, LoginDetails loginDetails) {
		this.loginDetails = loginDetails;
		this.socketAddress = address;
		return this;
	}

	protected void loginSuccess(ChannelHandlerContext ctx) {
		channelHandlerContextRegister.login(getLoginDetails(), ctx);

	}

	@Override
	public LoginDetails getLoginDetails() {
		return loginDetails;
	}

	@Override
	public InetSocketAddress getSocketAddress() {
		return this.socketAddress;
	}
}
