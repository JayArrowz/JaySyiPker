package jay.syi.util;

import java.math.BigInteger;

public final class Stream extends Node {
	public static Stream create() {
		synchronized (nodeList) {
			Stream stream = null;
			if (anInt1412 > 0) {
				anInt1412--;
				stream = (Stream)nodeList.popFront();
			}
			if (stream != null) {
				stream.currentOffset = 0;
				return stream;
			}
		}
		Stream stream_1 = new Stream();
		stream_1.currentOffset = 0;
		stream_1.buffer = new byte[30000];
		return stream_1;
	}

	public int getMedium() {
		this.currentOffset += 3;
		return (this.buffer[this.currentOffset - 3] & 0xFF) << 16 | (
				this.buffer[this.currentOffset - 2] & 0xFF) << 8 |
				this.buffer[this.currentOffset - 1] & 0xFF;
	}

	public String readNewString() {
		int i = this.currentOffset;
		do {

		} while (this.buffer[this.currentOffset++] != 0);
		return new String(this.buffer, i, this.currentOffset - i - 1);
	}

	public int readUSmart2() {
		int baseVal = 0;
		int lastVal = 0;
		while ((lastVal = method422()) == 42000)
			baseVal += 32767;
		return baseVal + lastVal;
	}

	public int readInt() {
		return (readUnsignedByte() << 24) + (readUnsignedByte() << 16) + (
				readUnsignedByte() << 8) + readUnsignedByte();
	}

	public int readShort2() {
		this.currentOffset += 2;
		int i = ((this.buffer[this.currentOffset - 2] & 0xFF) << 8) + (
				this.buffer[this.currentOffset - 1] & 0xFF);
		if (i > 60000)
			i += -65535;
		return i;
	}

	private Stream() {}

	public Stream(byte[] abyte0) {
		this.buffer = abyte0;
		this.currentOffset = 0;
	}

	final int v(int i) {
		this.currentOffset += 3;
		return (0xFF & this.buffer[this.currentOffset - 3] << 16) + (
				0xFF & this.buffer[this.currentOffset - 2] << 8) + (
				0xFF & this.buffer[this.currentOffset - 1]);
	}

	public void createFrame(int i) {
		this.buffer[this.currentOffset++] = (byte)(i + this.encryption.getNextKey());
	}

	public void writeWordBigEndian(int i) {
		this.buffer[this.currentOffset++] = (byte)i;
	}

	public void writeWord(int i) {
		this.buffer[this.currentOffset++] = (byte)(i >> 8);
		this.buffer[this.currentOffset++] = (byte)i;
	}

	public void method400(int i) {
		this.buffer[this.currentOffset++] = (byte)i;
		this.buffer[this.currentOffset++] = (byte)(i >> 8);
	}

	public void writeDWordBigEndian(int i) {
		this.buffer[this.currentOffset++] = (byte)(i >> 16);
		this.buffer[this.currentOffset++] = (byte)(i >> 8);
		this.buffer[this.currentOffset++] = (byte)i;
	}

	public void writeDWord(int i) {
		this.buffer[this.currentOffset++] = (byte)(i >> 24);
		this.buffer[this.currentOffset++] = (byte)(i >> 16);
		this.buffer[this.currentOffset++] = (byte)(i >> 8);
		this.buffer[this.currentOffset++] = (byte)i;
	}

	public void method403(int j) {
		this.buffer[this.currentOffset++] = (byte)j;
		this.buffer[this.currentOffset++] = (byte)(j >> 8);
		this.buffer[this.currentOffset++] = (byte)(j >> 16);
		this.buffer[this.currentOffset++] = (byte)(j >> 24);
	}

	public void writeQWord(long l) {
		try {
			this.buffer[this.currentOffset++] = (byte)(int)(l >> 56L);
			this.buffer[this.currentOffset++] = (byte)(int)(l >> 48L);
			this.buffer[this.currentOffset++] = (byte)(int)(l >> 40L);
			this.buffer[this.currentOffset++] = (byte)(int)(l >> 32L);
			this.buffer[this.currentOffset++] = (byte)(int)(l >> 24L);
			this.buffer[this.currentOffset++] = (byte)(int)(l >> 16L);
			this.buffer[this.currentOffset++] = (byte)(int)(l >> 8L);
			this.buffer[this.currentOffset++] = (byte)(int)l;
		} catch (RuntimeException runtimeexception) {
			System.out.println("14395, 5, " + l + ", " +
					runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void writeString(String s) {
		System.arraycopy(s.getBytes(), 0, this.buffer, this.currentOffset, s.length());
		this.currentOffset += s.length();
		this.buffer[this.currentOffset++] = 10;
	}

	public void writeBytes(byte[] abyte0, int i, int j) {
		for (int k = j; k < j + i; k++)
			this.buffer[this.currentOffset++] = abyte0[k];
	}

	public void writeBytes(int i) {
		this.buffer[this.currentOffset - i - 1] = (byte)i;
	}

	public int readUByte() {
		return this.buffer[this.currentOffset++] & 0xFF;
	}

	public byte readByte() {
		return this.buffer[this.currentOffset++];
	}

	public void writeByte(int i) {
		this.buffer[this.currentOffset++] = (byte)i;
	}

	public int readShort() {
		this.currentOffset += 2;
		return ((this.buffer[this.currentOffset - 2] & 0xFF) << 8) + (
				this.buffer[this.currentOffset - 1] & 0xFF);
	}

	public byte readSignedByte() {
		return this.buffer[this.currentOffset++];
	}

	public int readUnsignedByte() {
		return this.buffer[this.currentOffset++] & 0xFF;
	}

	public int readUnsignedWord() {
		this.currentOffset += 2;
		return ((this.buffer[this.currentOffset - 2] & 0xFF) << 8) + (
				this.buffer[this.currentOffset - 1] & 0xFF);
	}

	public int readSignedWord() {
		this.currentOffset += 2;
		int i = ((this.buffer[this.currentOffset - 2] & 0xFF) << 8) + (
				this.buffer[this.currentOffset - 1] & 0xFF);
		if (i > 32767)
			i -= 65536;
		return i;
	}

	public int method434() {
		this.currentOffset += 2;
		return ((this.buffer[this.currentOffset - 1] & 0xFF) << 8) + (this.buffer[this.currentOffset - 2] & 0xFF);
	}

	public int read3Bytes() {
		this.currentOffset += 3;
		return ((this.buffer[this.currentOffset - 3] & 0xFF) << 16) + ((
				this.buffer[this.currentOffset - 2] & 0xFF) << 8) + (
				this.buffer[this.currentOffset - 1] & 0xFF);
	}

	public int readDWord() {
		this.currentOffset += 4;
		return ((this.buffer[this.currentOffset - 4] & 0xFF) << 24) + ((
				this.buffer[this.currentOffset - 3] & 0xFF) << 16) + ((
				this.buffer[this.currentOffset - 2] & 0xFF) << 8) + (
				this.buffer[this.currentOffset - 1] & 0xFF);
	}

	public long readQWord() {
		long l = readDWord() & 0xFFFFFFFFL;
		long l1 = readDWord() & 0xFFFFFFFFL;
		return (l << 32L) + l1;
	}

	public String readString() {
		int i = this.currentOffset;
		do {

		} while (this.buffer[this.currentOffset++] != 10);
		return new String(this.buffer, i, this.currentOffset - i - 1);
	}

	public byte[] readBytes() {
		int i = this.currentOffset;
		do {

		} while (this.buffer[this.currentOffset++] != 10);
		byte[] abyte0 = new byte[this.currentOffset - i - 1];
		System.arraycopy(this.buffer, i, abyte0, i - i, this.currentOffset - 1 - i);
		return abyte0;
	}

	public void readBytes(int i, int j, byte[] abyte0) {
		for (int l = j; l < j + i; l++)
			abyte0[l] = this.buffer[this.currentOffset++];
	}

	public void initBitAccess() {
		this.bitPosition = this.currentOffset * 8;
	}

	public int readBits(int i) {
		int k = this.bitPosition >> 3;
		int l = 8 - (this.bitPosition & 0x7);
		int i1 = 0;
		this.bitPosition += i;
		for (; i > l; l = 8) {
			i1 += (this.buffer[k++] & bitPositionTable[l]) << i - l;
			i -= l;
		}
		if (i == l) {
			i1 += this.buffer[k] & bitPositionTable[l];
		} else {
			i1 += this.buffer[k] >> l - i & bitPositionTable[i];
		}
		return i1;
	}

	public void finishBitAccess() {
		this.currentOffset = (this.bitPosition + 7) / 8;
	}

	public int method421() {
		int i = this.buffer[this.currentOffset] & 0xFF;
		if (i < 128)
			return readUnsignedByte() - 64;
		return readUnsignedWord() - 49152;
	}

	public int method422() {
		int i = this.buffer[this.currentOffset] & 0xFF;
		if (i < 128)
			return readUnsignedByte();
		return readUnsignedWord() - 32768;
	}

	public byte[] buffer;

	public int currentOffset;

	public int bitPosition;

	public void doKeys(BigInteger rsaMod, BigInteger rsaExp) {
		int i = this.currentOffset;
		this.currentOffset = 0;
		byte[] abyte0 = new byte[i];
		readBytes(i, 0, abyte0);
		BigInteger biginteger2 = new BigInteger(abyte0);
		BigInteger biginteger3 = biginteger2.modPow(rsaExp, rsaMod);
		byte[] abyte1 = biginteger3.toByteArray();
		this.currentOffset = 0;
		writeWordBigEndian(abyte1.length);
		writeBytes(abyte1, abyte1.length, 0);
	}

	public void method424(int i) {
		this.buffer[this.currentOffset++] = (byte)-i;
	}

	public void method425(int j) {
		this.buffer[this.currentOffset++] = (byte)(128 - j);
	}

	public int method426() {
		return this.buffer[this.currentOffset++] - 128 & 0xFF;
	}

	public int nglb() {
		return -this.buffer[this.currentOffset++] & 0xFF;
	}

	public int readByteS() {
		return 128 - this.buffer[this.currentOffset++] & 0xFF;
	}

	public byte method429() {
		return (byte)-this.buffer[this.currentOffset++];
	}

	public byte method430() {
		return (byte)(128 - this.buffer[this.currentOffset++]);
	}

	public void writeUnsignedWordBigEndian(int i) {
		this.buffer[this.currentOffset++] = (byte)i;
		this.buffer[this.currentOffset++] = (byte)(i >> 8);
	}

	public void writeUnsignedWordA(int j) {
		this.buffer[this.currentOffset++] = (byte)(j >> 8);
		this.buffer[this.currentOffset++] = (byte)(j + 128);
	}

	public void writeSignedBigEndian(int j) {
		this.buffer[this.currentOffset++] = (byte)(j + 128);
		this.buffer[this.currentOffset++] = (byte)(j >> 8);
	}

	public int ig2() {
		this.currentOffset += 2;
		return ((this.buffer[this.currentOffset - 1] & 0xFF) << 8) + (
				this.buffer[this.currentOffset - 2] & 0xFF);
	}

	public int readByteA() {
		this.currentOffset += 2;
		return ((this.buffer[this.currentOffset - 2] & 0xFF) << 8) + (
				this.buffer[this.currentOffset - 1] - 128 & 0xFF);
	}

	public int readWordBigEndian() {
		this.currentOffset += 2;
		return ((this.buffer[this.currentOffset - 1] & 0xFF) << 8) + (this.buffer[this.currentOffset - 2] - 128 & 0xFF);
	}

	public int method437() {
		this.currentOffset += 2;
		int j = ((this.buffer[this.currentOffset - 1] & 0xFF) << 8) + (
				this.buffer[this.currentOffset - 2] & 0xFF);
		if (j > 32767)
			j -= 65536;
		return j;
	}

	public int method438() {
		this.currentOffset += 2;
		int j = ((this.buffer[this.currentOffset - 1] & 0xFF) << 8) + (
				this.buffer[this.currentOffset - 2] - 128 & 0xFF);
		if (j > 32767)
			j -= 65536;
		return j;
	}

	public int method439() {
		this.currentOffset += 4;
		return ((this.buffer[this.currentOffset - 2] & 0xFF) << 24) + ((
				this.buffer[this.currentOffset - 1] & 0xFF) << 16) + ((
				this.buffer[this.currentOffset - 4] & 0xFF) << 8) + (
				this.buffer[this.currentOffset - 3] & 0xFF);
	}

	public int method440() {
		this.currentOffset += 4;
		return ((this.buffer[this.currentOffset - 3] & 0xFF) << 24) + ((
				this.buffer[this.currentOffset - 4] & 0xFF) << 16) + ((
				this.buffer[this.currentOffset - 1] & 0xFF) << 8) + (
				this.buffer[this.currentOffset - 2] & 0xFF);
	}

	public void method441(int i, byte[] abyte0, int j) {
		for (int k = i + j - 1; k >= i; k--)
			this.buffer[this.currentOffset++] = (byte)(abyte0[k] + 128);
	}

	public void readBytes_reverse(int i, int j, byte[] abyte0) {
		for (int k = j + i - 1; k >= j; k--)
			abyte0[k] = this.buffer[this.currentOffset++];
	}

	private static final int[] bitPositionTable = new int[] {
			0, 1, 3, 7, 15, 31, 63,
			127, 255, 511,
			1023, 2047, 4095, 8191, 16383, 32767, 65535,
			131071, 262143, 524287,
			1048575, 2097151, 4194303, 8388607,
			16777215, 33554431, 67108863, 134217727, 268435455, 536870911,
			1073741823, Integer.MAX_VALUE, -1 };

	public ISAACRandomGen encryption;

	private static int anInt1412;

	private static final Deque nodeList = new Deque();

	public int method435() {
		this.currentOffset += 2;
		return ((this.buffer[this.currentOffset - 2] & 0xFF) << 8) + (this.buffer[this.currentOffset - 1] - 128 & 0xFF);
	}

	public void method431(int i) {
		this.buffer[this.currentOffset++] = (byte)i;
		this.buffer[this.currentOffset++] = (byte)(i >> 8);
	}

	public int method428() {
		return 128 - this.buffer[this.currentOffset++] & 0xFF;
	}

	public int method427() {
		return -this.buffer[this.currentOffset++] & 0xFF;
	}

	public void method433(int j) {
		this.buffer[this.currentOffset++] = (byte)(j + 128);
		this.buffer[this.currentOffset++] = (byte)(j >> 8);
	}

	public void method432(int i) {
		this.buffer[this.currentOffset++] = (byte)(i >> 8);
		this.buffer[this.currentOffset++] = (byte)(i + 128);
	}

	public int readLEShort() {
		this.currentOffset += 2;
		return ((this.buffer[this.currentOffset - 1] & 0xFF) << 8) + (
				this.buffer[this.currentOffset - 2] - 128 & 0xFF);
	}
}