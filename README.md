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
```

To add a new client implement the interface `IStatefulLoginProvider` a example is shown inside `MythicalPSLoginProvider`



Simple usage of the login provider:
```java
			var loginDetails = new LoginDetails(username + i, password, false);
			var loginProvider = new MythicalPSLoginProvider(loginDetails, inetSocketAddr);
			var rsClient = new RSClientConnection(loginProvider);
			rsClient.login();
			try {
				Thread.sleep(msDelayBetweenEachLogin);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
```
