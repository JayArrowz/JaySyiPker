package jay.syi.util;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

public class StreamUtil {

	public static void queueBytes(ChannelHandlerContext ctx, int bytes, byte[] buffer) {
		var byteBuff = Unpooled.buffer(bytes);
		for(int i = 0; i < bytes; i++) {
			byteBuff.writeByte(buffer[i]);
		}
		ctx.write(byteBuff);
	}
}
