package jay.syi.business.logins;

import jay.syi.buffers.Stream;
import jay.syi.interfaces.IChannelHandlerContextRegister;
import jay.syi.interfaces.IStatefulLoginProvider;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import jay.syi.model.LoginDetails;
import jay.syi.util.ISAACRandomGen;
import jay.syi.util.StreamUtil;
import jay.syi.util.TextClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.net.InetSocketAddress;

@Component("MythicalPSLoginProvider")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MythicalPSLoginProvider extends BaseLoginProvider {
	private final static Logger LOGGER = LogManager.getLogger(MythicalPSLoginProvider.class);
	private static final BigInteger RSA_MODULUS = new BigInteger("95612669133916791964792058475561565423343689389615267339371722935515946359123272976676521317381550800766531334654289372355646166864266851779594805775091231014752375925208789397867354222876544309042689750172369546015666149456955593475643704259059963473988694852180997990665355463548404131660805506414722086279");
	private static final BigInteger RSA_EXPONENT = new BigInteger("65537");

	private Stream stream;
	private Stream rsaStream;
	private LoginState loginState;
	private ISAACRandomGen encryption;

	public MythicalPSLoginProvider(IChannelHandlerContextRegister channelHandlerContextRegister) {
		super(channelHandlerContextRegister);
	}

	private boolean isLoggedIn() {
		return loginState == LoginState.IN_GAME;
	}

	@Override
	public void channelActivated(ChannelHandlerContext ctx) {
		long l = TextClass.longForName(getLoginDetails().getUsername());
		int i = (int) (l >> 16L & 0x1FL);
		stream.currentOffset = 0;
		stream.writeWordBigEndian(14);
		stream.writeWordBigEndian(i);
		StreamUtil.queueBytes(ctx, 2, stream.buffer);
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
				if (loginCode == 0) {
					loginState = LoginState.KEYS;
					channelRead(ctx, buffer);
				}
				LOGGER.info("[{}] - Initial Login response {}", loginDetails.getUsername(), loginCode);
				break;
			case KEYS:
				var key = buffer.readLong();
				int[] ai = getKeys(key);
				stream.currentOffset = 0;
				stream.writeWordBigEndian(10);
				stream.writeDWord(ai[0]);
				stream.writeDWord(ai[1]);
				stream.writeDWord(ai[2]);
				stream.writeDWord(ai[3]);
				stream.writeDWord(350);
				stream.writeString("NO MAC FOUND");
				stream.writeString(loginDetails.getUsername());
				stream.writeString(loginDetails.getPassword());
				stream.writeWord(222);
				stream.writeWordBigEndian(0);
				stream.doKeys(RSA_MODULUS, RSA_EXPONENT);

				this.rsaStream.currentOffset = 0;
				if (loginDetails.isReconnecting()) {
					this.rsaStream.writeWordBigEndian(18);
				} else {
					this.rsaStream.writeWordBigEndian(16);
				}
				this.rsaStream.writeWordBigEndian(stream.currentOffset + 36 + 1 + 1 + 2);
				this.rsaStream.writeWordBigEndian(255);
				this.rsaStream.writeWord(13);
				this.rsaStream.writeWordBigEndian(0); //Low mem

				for (int l1 = 0; l1 < loginDetails.getCrcs().length; l1++) {
					this.rsaStream.writeDWord(loginDetails.getCrcs()[l1]); //Todo fake CRC?
				}

				this.rsaStream.writeBytes(stream.buffer, stream.currentOffset, 0);
				stream.encryption = new ISAACRandomGen(ai);
				for (int j2 = 0; j2 < 4; j2++)
					ai[j2] = ai[j2] + 50;
				this.encryption = new ISAACRandomGen(ai);
				StreamUtil.queueBytes(ctx, rsaStream.currentOffset, rsaStream.buffer);
				loginState = LoginState.COMPLETE_LOGIN;

				resetStreams();
				break;
			case COMPLETE_LOGIN:
				var loginResponse = buffer.readByte();
				if(loginResponse == 2) {
					//Login success
					loginSuccess(ctx);
					loginState = LoginState.IN_GAME;
				}
				LOGGER.info("[{}] - Login response {}", loginDetails.getUsername(), loginResponse);
				break;
			case IN_GAME:
				var packetId = buffer.readUnsignedByte() - encryption.getNextKey() & 0xFF;
				LOGGER.trace("[{}] - Incoming packet ID: {}", loginDetails.getUsername(), packetId);
				break;
		}
	}

	@Override
	public IStatefulLoginProvider set(InetSocketAddress address, LoginDetails loginDetails) {
		super.set(address, loginDetails);
		this.stream = Stream.create();
		this.rsaStream = Stream.create();
		loginState = LoginState.DEFAULT;
		return this;
	}

	private void resetStreams() {
		stream.currentOffset = 0;
		rsaStream.currentOffset = 0;
	}

	private int[] getKeys(long key) {
		var ai = new int[4];
		ai[0] = (int) (Math.random() * 9.9999999E7D);
		ai[1] = (int) (Math.random() * 9.9999999E7D);
		ai[2] = (int) (key >> 32L);
		ai[3] = (int) key;
		return ai;
	}

	public enum LoginState {
		DEFAULT,
		KEYS,
		COMPLETE_LOGIN,
		IN_GAME
	}
}
