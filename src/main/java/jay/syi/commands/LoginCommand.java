package jay.syi.commands;

import jay.syi.business.client.RSClientConnection;
import jay.syi.business.proxy.CopiedProxyDistributionStrategy;
import jay.syi.interfaces.client.IProxyDistributionStrategy;
import jay.syi.interfaces.client.IStatefulLoginProvider;
import jay.syi.interfaces.proxy.IProxyHandlerFactory;
import jay.syi.model.LoginDetails;
import jay.syi.model.ProxyDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "login", sortOptions = false, header = {}, description = {"login to a server"},
		optionListHeading = "@|bold %nOptions|@:%n", footer = {})
public class LoginCommand implements Runnable, ApplicationContextAware {

	private final static Logger LOGGER = LogManager.getLogger(LoginCommand.class);
	private final IProxyHandlerFactory proxyHandlerFactory;

	@CommandLine.Option(names = {"-u", "--username"}, description = "The username", required = true)
	String username;

	@CommandLine.Option(names = {"-p", "--password"}, description = "The password", required = false, defaultValue = "defaultpw12")
	String password;

	@CommandLine.Option(names = {"-n", "--num"}, description = "Number of robots", required = false, defaultValue = "1")
	int number;

	@CommandLine.Option(names = {"-i", "--ip"}, description = "ip", required = false, defaultValue = "93.158.238.74")
	private String ip;

	@CommandLine.Option(names = {"-po", "--port"}, description = "port", required = false, defaultValue = "43594")
	private int port;

	@CommandLine.Option(names = {"-d", "--delay"}, description = "ms delay between each login", required = false, defaultValue = "100")
	private long msDelayBetweenEachLogin;

	@CommandLine.Option(names = {"-im", "--impl"}, description = "login provider implementation bean name", required = false, defaultValue = "Dreamscape")
	private String impl;

	@CommandLine.Option(names = {"--proxy-dist"}, description = "Defines the proxy distribution strategy", required = false)
	private String proxyDistributionStrategyName;

	@CommandLine.Option(names = {"--proxy-ip"}, description = "Uses a specific proxy", required = false)
	private String specificProxy;

	private ApplicationContext applicationContext;

	public LoginCommand(IProxyHandlerFactory proxyHandlerFactory) {
		this.proxyHandlerFactory = proxyHandlerFactory;
	}

	@Override
	public void run() {
		try {
			ProxyDetails specifiedProxy = null;
			if (specificProxy != null) {
				//Define constant?
				proxyDistributionStrategyName = "copied-proxy-dist";
			}
			var inetSocketAddr = new InetSocketAddress(ip, port);
			List<ProxyDetails> proxyList = new ArrayList<>();

			if(proxyDistributionStrategyName != null) {
				var proxyDistributionStrategy = (IProxyDistributionStrategy)
						applicationContext.getBean(proxyDistributionStrategyName);
				proxyList = proxyDistributionStrategy.getNext(number);
				if (specificProxy != null) {
					specifiedProxy = proxyList.stream()
							.filter(t -> t.getIp().equalsIgnoreCase(specificProxy))
							.findFirst()
							.orElse(null);
					if (specifiedProxy == null) {
						LOGGER.warn("Specified proxy not detected in IProxyListProvider.getNext defaulting to no proxy");
					}
				}
			}

			for (int i = 0; i < number; i++) {
				var loginDetails = new LoginDetails(username + i, password, false);
				var statefulLoginProvider = (IStatefulLoginProvider) applicationContext.getBean(impl);
				statefulLoginProvider.set(inetSocketAddr, loginDetails);
				var rsClient = new RSClientConnection(statefulLoginProvider, proxyHandlerFactory);
				if (specifiedProxy != null) {
					rsClient.login(specifiedProxy);
				} else {
					var proxyDetails = !proxyList.isEmpty() ? proxyList.get(i) : null;
					rsClient.login(proxyDetails);
				}
				Thread.sleep(msDelayBetweenEachLogin);
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
