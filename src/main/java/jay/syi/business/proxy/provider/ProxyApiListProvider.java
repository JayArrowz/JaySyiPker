package jay.syi.business.proxy.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import jay.syi.interfaces.proxy.IProxyListProvider;
import jay.syi.model.ProxyDetails;
import jay.syi.model.ProxyDetailsApi;
import jay.syi.model.ProxyType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("api-proxy")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ProxyApiListProvider implements IProxyListProvider, InitializingBean {

	private List<ProxyDetails> proxyDetails = new ArrayList<>();
	private static final Logger LOGGER = LogManager.getLogger(ProxyApiListProvider.class);
	private static final String API_URL = "https://www.proxyscan.io/api/proxy?type=socks4,socks5&limit=1000&lastCheck=3600";

	@Override
	public void add(ProxyDetails details) {
		proxyDetails.add(details);
	}

	@Override
	public List<ProxyDetails> get() {
		return proxyDetails;
	}

	@Override
	public void load() {
		var objectMapper = new ObjectMapper();
		try {
			List<ProxyDetailsApi> loadedProxyDetails =
					objectMapper.readValue(new URL(API_URL), objectMapper
							.getTypeFactory()
							.constructCollectionType(List.class, ProxyDetailsApi.class));

			var proxyDetails = loadedProxyDetails.stream().map(t -> {
				var details = new ProxyDetails();
				details.setIp(t.getIp());
				details.setPort(t.getPort());
				details.setProxyType(t.getType().stream().findFirst().orElse(null).equalsIgnoreCase("SOCKS4") ? ProxyType.SOCKS_4 : ProxyType.SOCKS_5);
				return details;
			}).collect(Collectors.toList());
			this.proxyDetails.addAll(proxyDetails);

			LOGGER.info("Loaded {} proxies", loadedProxyDetails.size());
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}

	@Override
	public void afterPropertiesSet() {
		load();
	}
}
