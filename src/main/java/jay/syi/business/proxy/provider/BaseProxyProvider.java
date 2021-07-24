package jay.syi.business.proxy.provider;

import jay.syi.interfaces.proxy.IProxyListProvider;
import jay.syi.model.ProxyDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseProxyProvider implements IProxyListProvider, InitializingBean  {

    protected static Logger LOGGER = LogManager.getLogger(BaseProxyProvider.class);
    private List<ProxyDetails> proxyDetails = new ArrayList<>();

    @Override
    public void add(ProxyDetails details) {
        proxyDetails.add(details);
    }

    @Override
    public List<ProxyDetails> get() {
        return proxyDetails;
    }

    @Override
    public void afterPropertiesSet() {
        try {
            load();
        } catch(Exception e) {
            LOGGER.error(e);
        }
    }
}
