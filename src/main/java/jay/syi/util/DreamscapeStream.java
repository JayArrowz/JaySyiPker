package jay.syi.util;

import java.math.BigInteger;

public final class DreamscapeStream extends Node {
	private static final BigInteger e = new BigInteger("a7cf3a0c734263cfafd23d66e67b49a1689f5548ff2e495aac11309254c3695dc938233b6f4f2b9ac84e0076aaf33d419ad729841090d639dd31bf465244498b93fd59098564929f4afb9506ad48edaf595cd84b4cbac96bf38a9a0ed7d37f389b3911352b3d761597325e17d0a055edce43fc9581843f43665274c4d03b393b", 16);

	private static final BigInteger f = new BigInteger("65537");

	public byte[] a;

	public int b;

	public int c;

	private static final int[] g = new int[] {
			0, 1, 3, 7, 15, 31, 63, 127, 255, 511,
			1023, 2047, 4095, 8191, 16383, 32767, 65535, 131071, 262143, 524287,
			1048575, 2097151, 4194303, 8388607, 16777215, 33554431, 67108863, 134217727, 268435455, 536870911,
			1073741823, Integer.MAX_VALUE, -1 };

	private static int h;

	private static final Deque i = new Deque();

	public int a() {
		return g();
	}

	public static DreamscapeStream createNewDreamscapeStream() {
		synchronized (i) {
			DreamscapeStream j2 = null;
			if (h > 0) {
				h--;
				j2 = (DreamscapeStream)i.popFront();
			}
			if (j2 != null) {
				j2.b = 0;
				return j2;
			}
		}
		DreamscapeStream j1 = new DreamscapeStream();
		j1.b = 0;
		j1.a = new byte[100000];
		return j1;
	}

	private DreamscapeStream() {}

	public DreamscapeStream(byte[] paramArrayOfbyte) {
		this.a = paramArrayOfbyte;
		this.b = 0;
	}

	public void b(int paramInt) {
		this.a[this.b++] = (byte)paramInt;
	}

	public void c(int paramInt) {
		this.a[this.b++] = (byte)(paramInt >> 8);
		this.a[this.b++] = (byte)paramInt;
	}

	public void d(int paramInt) {
		this.a[this.b++] = (byte)paramInt;
		this.a[this.b++] = (byte)(paramInt >> 8);
	}

	public void e(int paramInt) {
		this.a[this.b++] = (byte)(paramInt >> 16);
		this.a[this.b++] = (byte)(paramInt >> 8);
		this.a[this.b++] = (byte)paramInt;
	}

	public void f(int paramInt) {
		this.a[this.b++] = (byte)(paramInt >> 24);
		this.a[this.b++] = (byte)(paramInt >> 16);
		this.a[this.b++] = (byte)(paramInt >> 8);
		this.a[this.b++] = (byte)paramInt;
	}

	public void g(int paramInt) {
		this.a[this.b++] = (byte)paramInt;
		this.a[this.b++] = (byte)(paramInt >> 8);
		this.a[this.b++] = (byte)(paramInt >> 16);
		this.a[this.b++] = (byte)(paramInt >> 24);
	}

	public void a(long paramLong) {
		this.a[this.b++] = (byte)(int)(paramLong >> 56L);
		this.a[this.b++] = (byte)(int)(paramLong >> 48L);
		this.a[this.b++] = (byte)(int)(paramLong >> 40L);
		this.a[this.b++] = (byte)(int)(paramLong >> 32L);
		this.a[this.b++] = (byte)(int)(paramLong >> 24L);
		this.a[this.b++] = (byte)(int)(paramLong >> 16L);
		this.a[this.b++] = (byte)(int)(paramLong >> 8L);
		this.a[this.b++] = (byte)(int)paramLong;
	}

	public void a(String paramString) {
		System.arraycopy(paramString.getBytes(), 0, this.a, this.b, paramString.length());
		this.b += paramString.length();
		this.a[this.b++] = 10;
	}

	public void a(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
		for (int i = paramInt2; i < paramInt2 + paramInt1; i++)
			this.a[this.b++] = paramArrayOfbyte[i];
	}

	public void h(int paramInt) {
		this.a[this.b - paramInt - 1] = (byte)paramInt;
	}

	public int d() {
		return this.a[this.b++] & 0xFF;
	}

	public byte e() {
		return this.a[this.b++];
	}

	public int f() {
		this.b += 2;
		return ((this.a[this.b - 2] & 0xFF) << 8) + (this.a[this.b - 1] & 0xFF);
	}

	public int g() {
		this.b += 2;
		int i = ((this.a[this.b - 2] & 0xFF) << 8) + (this.a[this.b - 1] & 0xFF);
		if (i > 32767)
			i -= 65536;
		return i;
	}

	public int h() {
		this.b += 3;
		return ((this.a[this.b - 3] & 0xFF) << 16) + ((this.a[this.b - 2] & 0xFF) << 8) + (this.a[this.b - 1] & 0xFF);
	}

	public int i() {
		this.b += 4;
		return ((this.a[this.b - 4] & 0xFF) << 24) + ((this.a[this.b - 3] & 0xFF) << 16) + ((this.a[this.b - 2] & 0xFF) << 8) + (this.a[this.b - 1] & 0xFF);
	}

	public int j() {
		this.b += 4;
		return ((this.a[this.b - 2] & 0xFF) << 24) + ((this.a[this.b - 1] & 0xFF) << 16) + ((this.a[this.b - 4] & 0xFF) << 8) + (this.a[this.b - 3] & 0xFF);
	}

	public int k() {
		this.b += 4;
		return ((this.a[this.b - 3] & 0xFF) << 24) + ((this.a[this.b - 4] & 0xFF) << 16) + ((this.a[this.b - 1] & 0xFF) << 8) + (this.a[this.b - 2] & 0xFF);
	}

	public long l() {
		long l1 = i() & 0xFFFFFFFFL;
		long l2 = i() & 0xFFFFFFFFL;
		return (l1 << 32L) + l2;
	}

	public String m() {
		int i = this.b;
		while (this.a[this.b++] != 10);
		return new String(this.a, i, this.b - i - 1);
	}

	public String n() {
		int i = this.b;
		while (this.a[this.b++] != 0);
		return new String(this.a, i, this.b - i - 1);
	}

	public byte[] o() {
		int i = this.b;
		while (this.a[this.b++] != 10);
		byte[] arrayOfByte = new byte[this.b - i - 1];
		System.arraycopy(this.a, i, arrayOfByte, i - i, this.b - 1 - i);
		return arrayOfByte;
	}

	public void a(int paramInt1, int paramInt2, byte[] paramArrayOfbyte) {
		for (int i = paramInt2; i < paramInt2 + paramInt1; i++)
			paramArrayOfbyte[i] = this.a[this.b++];
	}

	public void p() {
		this.c = this.b * 8;
	}

	public int i(int paramInt) {
		int i = this.c >> 3;
		int k = 8 - (this.c & 0x7);
		int m = 0;
		this.c += paramInt;
		while (paramInt > k) {
			m += (this.a[i++] & g[k]) << paramInt - k;
			paramInt -= k;
			k = 8;
		}
		if (paramInt == k) {
			m += this.a[i] & g[k];
		} else {
			m += this.a[i] >> k - paramInt & g[paramInt];
		}
		return m;
	}

	public void q() {
		this.b = (this.c + 7) / 8;
	}

	public int r() {
		int i = this.a[this.b] & 0xFF;
		return (i < 128) ? (d() - 64) : (f() - 49152);
	}

	public int s() {
		int i = this.a[this.b] & 0xFF;
		return (i < 128) ? d() : (f() - 32768);
	}

	public void t() {
		int i = this.b;
		this.b = 0;
		byte[] arrayOfByte1 = new byte[i];
		a(i, 0, arrayOfByte1);
		BigInteger bigInteger1 = new BigInteger(arrayOfByte1);
		BigInteger bigInteger2 = bigInteger1.modPow(f, e);
		byte[] arrayOfByte2 = bigInteger2.toByteArray();
		this.b = 0;
		b(arrayOfByte2.length);
		a(arrayOfByte2, arrayOfByte2.length, 0);
	}

	public void j(int paramInt) {
		this.a[this.b++] = (byte)-paramInt;
	}

	public void k(int paramInt) {
		this.a[this.b++] = (byte)(128 - paramInt);
	}

	public int u() {
		return this.a[this.b++] - 128 & 0xFF;
	}

	public int v() {
		return -this.a[this.b++] & 0xFF;
	}

	public int w() {
		return 128 - this.a[this.b++] & 0xFF;
	}

	public byte x() {
		return (byte)-this.a[this.b++];
	}

	public byte y() {
		return (byte)(128 - this.a[this.b++]);
	}

	public void l(int paramInt) {
		this.a[this.b++] = (byte)(paramInt >> 8);
		this.a[this.b++] = (byte)(paramInt + 128);
	}

	public void m(int paramInt) {
		this.a[this.b++] = (byte)(paramInt + 128);
		this.a[this.b++] = (byte)(paramInt >> 8);
	}

	public int z() {
		this.b += 2;
		return ((this.a[this.b - 1] & 0xFF) << 8) + (this.a[this.b - 2] & 0xFF);
	}

	public int A() {
		this.b += 2;
		return ((this.a[this.b - 2] & 0xFF) << 8) + (this.a[this.b - 1] - 128 & 0xFF);
	}

	public int B() {
		this.b += 2;
		return ((this.a[this.b - 1] & 0xFF) << 8) + (this.a[this.b - 2] - 128 & 0xFF);
	}

	public int C() {
		this.b += 2;
		int i = ((this.a[this.b - 1] & 0xFF) << 8) + (this.a[this.b - 2] & 0xFF);
		if (i > 32767)
			i -= 65536;
		return i;
	}

	public int D() {
		this.b += 2;
		int i = ((this.a[this.b - 1] & 0xFF) << 8) + (this.a[this.b - 2] - 128 & 0xFF);
		if (i > 32767)
			i -= 65536;
		return i;
	}

	public void a(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
		for (int i = paramInt1 + paramInt2 - 1; i >= paramInt1; i--)
			this.a[this.b++] = (byte)(paramArrayOfbyte[i] + 128);
	}

	public void b(int paramInt1, int paramInt2, byte[] paramArrayOfbyte) {
		for (int i = paramInt2 + paramInt1 - 1; i >= paramInt2; i--)
			paramArrayOfbyte[i] = this.a[this.b++];
	}

	public double E() {
		return Float.intBitsToFloat(i());
	}

	public byte F() {
		return this.a[this.b++];
	}

	public int G() {
		return this.a[this.b++] & 0xFF;
	}

	public int H() {
		this.b += 2;
		return (this.a[this.b - 2] & 0xFF) << 8 | this.a[this.b - 1] & 0xFF;
	}

	public int I() {
		this.b += 4;
		return (this.a[this.b - 4] & 0xFF) << 24 | (this.a[this.b - 3] & 0xFF) << 16 | (this.a[this.b - 2] & 0xFF) << 8 | this.a[this.b - 1] & 0xFF;
	}

	public int J() {
		int i = this.a[this.b] & 0xFF;
		return (i < 128) ? G() : (H() - 32768);
	}

	public int K() {
		int i = this.a[this.b] & 0xFF;
		return (i < 128) ? (G() - 64) : (H() - 49152);
	}
}
