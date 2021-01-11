package jay.syi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProxyDetailsApi {
	@JsonProperty("Ip")
	private String ip;
	@JsonProperty("Port")
	private int port;
	@JsonProperty("Ping")
	private int ping;
	@JsonProperty("Time")
	private int time;
	@JsonProperty("Type")
	private List<String> type;
	@JsonProperty("Failed")
	private boolean failed;
	@JsonProperty("Anonymity")
	private String anonymity;
	@JsonProperty("WorkingCount")
	private int workingCount;
	@JsonProperty("Uptime")
	private double uptime;
	@JsonProperty("RecheckCount")
	private int recheckCount;

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public int getPing() {
		return ping;
	}

	public List<String> getType() {
		return type;
	}
}