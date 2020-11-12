# JaySyiPker
Headless RS2 Client Base

Command Usage:
```
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
```

To add a new client implement the interface `IStatefulLoginProvider` then use the `--impl` switch
An example impl of `IStatefulLoginProvider` is shown inside `MythicalPSLoginProvider`
It is recommended to extend `BaseLoginProvider` instead of implementing `IStatefulLoginProvider`

Simple usage of the login provider:
```java
var loginDetails = new LoginDetails("TestUser", "TestPassword", reconnecting);
var loginProvider = new MythicalPSLoginProvider(loginDetails, inetSocketAddr);
var rsClient = new RSClientConnection(loginProvider);
rsClient.login();
```

For the `--impl` argument to work ensure your LoginProvider Component is named. .e.g.

```
@Component("*MyLoginProvider*")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MythicalPSLoginProvider extends BaseLoginProvider {
```

Would allow the command:
`login -u Test --impl MyLoginProvider`
