package jay.syi.model;

public class LoginDetails {
	private final String username;
	private final String password;
	private final boolean reconnecting;
	private final int[] crcs;

	public LoginDetails(String username, String password, boolean reconnecting, int[] crcs) {
		this.username = username;
		this.password = password;
		this.reconnecting = reconnecting;
		this.crcs = crcs;
	}

	public LoginDetails(String username, String password, boolean reconnecting) {
		this(username, password, reconnecting, new int[9]);
	}
	
	public int[] getCrcs() {
		return crcs;
	}

	public boolean isReconnecting() {
		return reconnecting;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public int hashCode() {
		return this.username.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LoginDetails) {
			var other = (LoginDetails) obj;
			return obj == this ||
					(other.username.equalsIgnoreCase(username));
		}
		return false;
	}
}
