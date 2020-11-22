package jay.syi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.netty.handler.codec.socks.SocksAddressType;

public class ProxyDetails {
	private ProxyType proxyType = ProxyType.SOCKS_5;
	private String username;
	private String password;
	private String ip;
	private int port;

	public ProxyDetails() {
	}

	@Override
	public int hashCode() {
		return ip.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ProxyDetails)) {
			return false;
		}

		ProxyDetails proxyDetails = (ProxyDetails) obj;
		return proxyDetails.ip.equalsIgnoreCase(ip);
	}

	public String getUsername() {
		return username;
	}

	public ProxyType getProxyType() {
		return proxyType;
	}
	public void setProxyType(ProxyType proxyType) {
		this.proxyType = proxyType;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

	public String getIp() {
		return ip;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
