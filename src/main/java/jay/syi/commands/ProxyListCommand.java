package jay.syi.commands;

import jay.syi.interfaces.proxy.IProxyListProvider;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@CommandLine.Command(name = "proxylist", sortOptions = false, header = {}, description = {"view proxy list"},
		optionListHeading = "@|bold %nOptions|@:%n", footer = {})
public class ProxyListCommand implements Runnable {

	private final IProxyListProvider proxyListProvider;

	public ProxyListCommand(IProxyListProvider proxyListProvider) {
		this.proxyListProvider = proxyListProvider;
	}

	@Override
	public void run() {
		System.out.println("Proxy List");
		proxyListProvider.get().stream()
				.map(t -> String.format("IP: %s Port: %s Username: %s Pw: %s Proxy Type: %s", t.getIp(), t.getPort(), t.getUsername(), t.getPassword(), t.getProxyType()))
				.forEach(System.out::println);
	}
}
