# JaySyiPker
Headless RS2 Client Base

Command Usage:
```
Commands:
  login      login to a server
  proxylist  view proxy list
  online     prints username of online bots
  quit       quit cli
  
Usage:  login [-d=<msDelayBetweenEachLogin>] [-i=<ip>] [-n=<number>]
              [-p=<password>] [-po=<port>] -u=<username>
login to a server

Options:
  -u, --username=<username>
                           The username
  -p, --password=<password>
                           The password
  -n, --num=<number>       Number of robots
  -i, --ip=<ip>            ip
      -po, --port=<port>   port
  -d, --delay=<msDelayBetweenEachLogin>
                           ms delay between each login
      -im, --impl=<impl>   login provider implementation bean name
      --proxy-dist=<proxyDistributionStrategyName>
                           Defines the proxy distribution strategy (copied-proxy-dist, random-proxy-dist)
      --proxy-ip=<specificProxy>
                           Uses a specific proxy
```

To add a new client implement the interface `IStatefulLoginProvider` then use the `--impl` switch
An example impl of `IStatefulLoginProvider` is shown inside `MythicalPSLoginProvider`
It is recommended to extend `BaseLoginProvider` instead of implementing `IStatefulLoginProvider`

Simple usage of the login provider:
```java
var loginDetails = new LoginDetails("TestUser", "TestPassword", reconnecting);
var loginRegister = new ChannelHandlerContextRegister();
var loginProvider = new MythicalPSLoginProvider(loginRegister);
loginProvider.set(socketAddress, loginDetails);
var rsClient = new RSClientConnection(loginProvider);
rsClient.login();
```

For the `--impl` argument to work ensure your LoginProvider Component is named. .e.g.

```
@Component("MyLoginProvider")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MythicalPSLoginProvider extends BaseLoginProvider {
```

Would allow the command:
`login -u Test --impl MyLoginProvider`

The Headless client will support Socks4/5 proxies. To enable this feature add the proxy details inside `socks.json`
```
  {
    "ip": "127.0.0.1",
    "port": 4165,
    "proxyType": "SOCKS_4",
    "username": "optional",
    "password": "optional"
  }
```
The switch `--proxy-ip=127.0.0.1` can be used with the `login` command to pick a specific proxy for login. However if you have multiple logins and want to distribute different proxies to those logins, then use `--proxy-dist=random-proxy-dist`. If no `--proxy-ip` or `--proxy-dist` is defined then a proxy will NOT be used for the login. Create different distribution patterns by implementing `IProxyDistributionStrategy` for a example see `RandomProxyDistributionStrategy`.
