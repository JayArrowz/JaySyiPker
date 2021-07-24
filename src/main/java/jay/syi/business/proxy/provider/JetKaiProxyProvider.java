package jay.syi.business.proxy.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import jay.syi.model.JetKaisProxyModel;
import jay.syi.model.ProxyDetails;
import jay.syi.model.ProxyType;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URL;

@Component("jet-kai-proxy-provider")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class JetKaiProxyProvider extends BaseProxyProvider {

    private final String JET_KAI_PROXY_REPO_URL = "https://raw.githubusercontent.com/KaiBurton/free-proxies-autoupdated/main/proxies-socks4%2B5-beautify.json";

    @Override
    public void load() throws IOException {
        var objectMapper = new ObjectMapper();
        JetKaisProxyModel jetKaiProxies =
                objectMapper.readValue(new URL(JET_KAI_PROXY_REPO_URL), objectMapper
                        .getTypeFactory()
                        .constructType(JetKaisProxyModel.class));
        for(var proxy : jetKaiProxies.getSocks4()) {
            ProxyDetails proxyDetails = getProxyDetails(ProxyType.SOCKS_4, proxy);
            add(proxyDetails);
        }

        for(var proxy : jetKaiProxies.getSocks5()) {
            ProxyDetails proxyDetails = getProxyDetails(ProxyType.SOCKS_5, proxy);
            add(proxyDetails);
        }

        LOGGER.info("Loaded {} proxies from JetKai's repo", get().size());
    }

    private ProxyDetails getProxyDetails(ProxyType type, String proxyData) {
        ProxyDetails proxyDetails = new ProxyDetails();
        String[] data = proxyData.split(":");
        proxyDetails.setProxyType(type);
        proxyDetails.setIp(data[0]);
        proxyDetails.setPort(Integer.parseInt(data[1]));
        return proxyDetails;
    }
}
