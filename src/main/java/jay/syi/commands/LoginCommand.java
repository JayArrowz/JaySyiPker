package jay.syi.commands;

import jay.syi.business.RSClientConnection;
import jay.syi.business.logins.MythicalPSLoginProvider;
import jay.syi.model.LoginDetails;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.net.InetSocketAddress;

@Component
@CommandLine.Command(name = "login", sortOptions = false, header = {}, description = { "login to a server" },
		optionListHeading = "@|bold %nOptions|@:%n", footer = {})
public class LoginCommand implements Runnable {
	@CommandLine.Option(names = { "-u", "--username" }, description = "The username", required = true)
	String username;

	@CommandLine.Option(names = { "-p", "--password" }, description = "The password", required = false, defaultValue = "defaultpw12")
	String password;

	@CommandLine.Option(names = { "-n", "--num" }, description = "Number of robots", required = false, defaultValue = "1")
	int number;

	@CommandLine.Option(names = { "-i", "--ip" }, description = "ip", required = false, defaultValue = "hydrixps.ddns.net")
	private String ip;

	@CommandLine.Option(names = { "-po", "--port" }, description = "port", required = false, defaultValue = "43594")
	private int port;

	@CommandLine.Option(names = { "-d", "--delay" }, description = "ms delay between each login", required = false, defaultValue = "100")
	private long msDelayBetweenEachLogin;

	@Override
	public void run() {
		var inetSocketAddr = new InetSocketAddress(ip, port);
		for (int i = 0; i < number; i++) {
			var loginDetails = new LoginDetails(username + i, password, false);
			var loginProvider = new MythicalPSLoginProvider(loginDetails, inetSocketAddr);
			var rsClient = new RSClientConnection(loginProvider);
			rsClient.login();
			try {
				Thread.sleep(msDelayBetweenEachLogin);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
