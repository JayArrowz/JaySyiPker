package jay.syi.util;

public final class ISAACRandomGen {
	private int count;

	private final int[] results;

	private final int[] memory;

	private int accumulator;

	private int lastResult;

	private int counter;

	public ISAACRandomGen(int[] seed) {
		this.results = new int[256];
		this.memory = new int[256];
		System.arraycopy(seed, 0, this.results, 0, seed.length);
		initializeKeySet();
	}

	public int getNextKey() {
		if (this.count-- == 0) {
			isaac();
			this.count = 255;
		}
		return this.results[this.count];
	}

	private void isaac() {
		this.lastResult += ++this.counter;
		for (int i = 0; i < 256; i++) {
			int j = this.memory[i];
			if ((i & 0x3) == 0) {
				this.accumulator ^= this.accumulator << 13;
			} else if ((i & 0x3) == 1) {
				this.accumulator ^= this.accumulator >>> 6;
			} else if ((i & 0x3) == 2) {
				this.accumulator ^= this.accumulator << 2;
			} else if ((i & 0x3) == 3) {
				this.accumulator ^= this.accumulator >>> 16;
			}
			this.accumulator += this.memory[i + 128 & 0xFF];
			int k = this.memory[(j & 0x3FC) >> 2] + this.accumulator + this.lastResult;
			this.results[i] = this.lastResult = this.memory[(k >> 8 & 0x3FC) >> 2] + j;
		}
	}

	private void initializeKeySet() {
		int k2 = -1640531527, j2 = k2, i2 = j2, l1 = i2, k1 = l1, j1 = k1, i1 = j1, l = i1;
		for (int i = 0; i < 4; i++) {
			l ^= i1 << 11;
			k1 += l;
			i1 += j1;
			i1 ^= j1 >>> 2;
			l1 += i1;
			j1 += k1;
			j1 ^= k1 << 8;
			i2 += j1;
			k1 += l1;
			k1 ^= l1 >>> 16;
			j2 += k1;
			l1 += i2;
			l1 ^= i2 << 10;
			k2 += l1;
			i2 += j2;
			i2 ^= j2 >>> 4;
			l += i2;
			j2 += k2;
			j2 ^= k2 << 8;
			i1 += j2;
			k2 += l;
			k2 ^= l >>> 9;
			j1 += k2;
			l += i1;
		}
		for (int j = 0; j < 256; j += 8) {
			l += this.results[j];
			i1 += this.results[j + 1];
			j1 += this.results[j + 2];
			k1 += this.results[j + 3];
			l1 += this.results[j + 4];
			i2 += this.results[j + 5];
			j2 += this.results[j + 6];
			k2 += this.results[j + 7];
			l ^= i1 << 11;
			k1 += l;
			i1 += j1;
			i1 ^= j1 >>> 2;
			l1 += i1;
			j1 += k1;
			j1 ^= k1 << 8;
			i2 += j1;
			k1 += l1;
			k1 ^= l1 >>> 16;
			j2 += k1;
			l1 += i2;
			l1 ^= i2 << 10;
			k2 += l1;
			i2 += j2;
			i2 ^= j2 >>> 4;
			l += i2;
			j2 += k2;
			j2 ^= k2 << 8;
			i1 += j2;
			k2 += l;
			k2 ^= l >>> 9;
			j1 += k2;
			l += i1;
			this.memory[j] = l;
			this.memory[j + 1] = i1;
			this.memory[j + 2] = j1;
			this.memory[j + 3] = k1;
			this.memory[j + 4] = l1;
			this.memory[j + 5] = i2;
			this.memory[j + 6] = j2;
			this.memory[j + 7] = k2;
		}
		for (int k = 0; k < 256; k += 8) {
			l += this.memory[k];
			i1 += this.memory[k + 1];
			j1 += this.memory[k + 2];
			k1 += this.memory[k + 3];
			l1 += this.memory[k + 4];
			i2 += this.memory[k + 5];
			j2 += this.memory[k + 6];
			k2 += this.memory[k + 7];
			l ^= i1 << 11;
			k1 += l;
			i1 += j1;
			i1 ^= j1 >>> 2;
			l1 += i1;
			j1 += k1;
			j1 ^= k1 << 8;
			i2 += j1;
			k1 += l1;
			k1 ^= l1 >>> 16;
			j2 += k1;
			l1 += i2;
			l1 ^= i2 << 10;
			k2 += l1;
			i2 += j2;
			i2 ^= j2 >>> 4;
			l += i2;
			j2 += k2;
			j2 ^= k2 << 8;
			i1 += j2;
			k2 += l;
			k2 ^= l >>> 9;
			j1 += k2;
			l += i1;
			this.memory[k] = l;
			this.memory[k + 1] = i1;
			this.memory[k + 2] = j1;
			this.memory[k + 3] = k1;
			this.memory[k + 4] = l1;
			this.memory[k + 5] = i2;
			this.memory[k + 6] = j2;
			this.memory[k + 7] = k2;
		}
		isaac();
		this.count = 256;
	}
}