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
                           
Usage:  online
prints username of online bots
```

To add a new client implement the interface `IStatefulLoginProvider` and modifiy the `LoginCommand.java` file to instaniate your new implemented provider.
An example impl of `IStatefulLoginProvider` is shown inside `MythicalPSLoginProvider`
It is recommended to extend `BaseLoginProvider` instead of implementing `IStatefulLoginProvider`

Simple usage of the login provider:
```java
var loginDetails = new LoginDetails("TestUser", "TestPassword", reconnecting);
var loginProvider = new MythicalPSLoginProvider(loginDetails, inetSocketAddr);
var rsClient = new RSClientConnection(loginProvider);
rsClient.login();
```
