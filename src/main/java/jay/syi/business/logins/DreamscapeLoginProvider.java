package jay.syi.business.logins;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import jay.syi.interfaces.client.IChannelHandlerContextRegister;
import jay.syi.interfaces.client.IStatefulLoginProvider;
import jay.syi.model.LoginDetails;
import jay.syi.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component("Dreamscape")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DreamscapeLoginProvider extends BaseLoginProvider {

	public int[] CRCS = new int[]{
			-2147483551,
			-2147483540,
			-2147483547,
			-2147483528,
			-2147483546,
			-2147483532,
			-2147483529,
			-2147483540,
			-2147483537
	};

	private final static Logger LOGGER = LogManager.getLogger(DreamscapeLoginProvider.class);
	private DreamscapeStream stream;
	private DreamscapeStream rsaStream;
	private LoginState loginState;
	public DreamscapeLoginProvider(IChannelHandlerContextRegister channelHandlerContextRegister) {
		super(channelHandlerContextRegister);
	}

	private boolean isLoggedIn() {
		return loginState == LoginState.IN_GAME;
	}

	@Override
	public void channelActivated(ChannelHandlerContext ctx) {
		stream.b = 0;
		stream.b(99);
		stream.j(77);
		StreamUtil.queueBytes(ctx, 2, stream.a);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, ByteBuf buffer) {
		var loginDetails = getLoginDetails();
		if (!isLoggedIn()) {
			LOGGER.info("Attempting login with : {} State: {}", loginDetails.getUsername(), loginState);
		}

		switch (loginState) {
			case DEFAULT:
				buffer.skipBytes(8);
				int loginCode = buffer.readByte();
				LOGGER.info("[{}] - Initial Login response {}", loginDetails.getUsername(), loginCode);
				if (loginCode == 0) {
					loginState = LoginState.KEYS;
					channelRead(ctx, buffer);
				}
				break;
			case KEYS:
				long l = TextClass.longForName(getLoginDetails().getUsername());
				int i = (int) (l >> 16L & 0x1FL);
				var key = buffer.readLong();
				int[] ai = getKeys(key);
				stream.b = 0;
				stream.b(8);
				stream.f(ai[0]);
				stream.f(ai[1]);
				stream.f(ai[2]);
				stream.f(ai[3]);
				stream.f(94879439);
				stream.a(l);
				stream.a(getLoginDetails().getPassword());
				stream.a("74-D0-2B-9E-F6-13");
				stream.a("empty_or_unknown");
				stream.b(1);
				this.stream.a("client -184647613pdte");
				this.stream.a("30.1");
				this.stream.f(0);
				this.stream.t();

				this.rsaStream.b = 0;
				if (loginDetails.isReconnecting()) {
					this.rsaStream.k(86);
				} else {
					this.rsaStream.k(43);
				}
				this.rsaStream.b(this.stream.b + 37 + 1 + 1 + 2);
				this.rsaStream.b(i);
				this.rsaStream.c(35);
				this.rsaStream.c(5);
				byte b1;
				for (b1 = 0; b1 < 9; b1++)
					this.rsaStream.f(CRCS[b1]);
				this.rsaStream.a(this.stream.a, this.stream.b, 0);
				//this.kG.d = new DreamscapeCg(ai);
				for (b1 = 0; b1 < 4; b1++)
					ai[b1] = ai[b1] + 50;
				//this.hJ = new DreamscapeCg(ai);
				StreamUtil.queueBytes(ctx, this.rsaStream.b, this.rsaStream.a);
				loginState = LoginState.COMPLETE_LOGIN;
				resetStreams();
				break;
			case COMPLETE_LOGIN:
				var loginResponse = buffer.readByte();
				if(loginResponse == 5) {
					//Login success
					loginSuccess(ctx);
					loginState = LoginState.IN_GAME;
				}
				LOGGER.info("[{}] - Login response {}", loginDetails.getUsername(), loginResponse);
				break;
			case IN_GAME:
				var packetId = buffer.readUnsignedByte();
				LOGGER.trace("[{}] - Incoming packet ID: {}", loginDetails.getUsername(), packetId);
				break;
		}
	}


	private void resetStreams() {
		stream.b = 0;
		rsaStream.b = 0;
	}

	private int[] getKeys(long key) {
		var ai = new int[4];
		ai[0] = (int) (Math.random() * 9.9999999E7D);
		ai[1] = (int) (Math.random() * 9.9999999E7D);
		ai[2] = (int) (key >> 32L);
		ai[3] = (int) key;
		return ai;
	}

	@Override
	public IStatefulLoginProvider set(InetSocketAddress address, LoginDetails loginDetails) {
		super.set(address, loginDetails);
		this.stream = DreamscapeStream.createNewDreamscapeStream();
		this.rsaStream = DreamscapeStream.createNewDreamscapeStream();
		loginState = LoginState.DEFAULT;
		return this;
	}

	private enum LoginState {
		DEFAULT,
		KEYS,
		COMPLETE_LOGIN,
		IN_GAME
	}
}
