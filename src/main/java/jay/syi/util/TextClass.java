package jay.syi.util;

public final class TextClass {
	public static long longForName(String s) {
		long l = 0L;
		for (int i = 0; i < s.length() && i < 12; i++) {
			char c = s.charAt(i);
			l *= 37L;
			if (c >= 'A' && c <= 'Z') {
				l += (1 + c - 65);
			} else if (c >= 'a' && c <= 'z') {
				l += (1 + c - 97);
			} else if (c >= '0' && c <= '9') {
				l += (27 + c - 48);
			}
		}
		for (; l % 37L == 0L && l != 0L; l /= 37L) ;
		return l;
	}
}