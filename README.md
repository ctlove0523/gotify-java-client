# gotify-java-client

`gotify-java-client` is sync client of [Gotify](https://gotify.net/) which written in Java.`gotify-java-client` can be used in Java or Android application.

## Building from Source

You don't need to build from source to use `gotify-java-client` (binaries in release page), but if you want to try out the latest and greatest, `gotify-java-client` can be easily built with the [maven](https://maven.apache.org/). You also need JDK 1.8.

`gotify-java-client` requires Java 8 or + to run.

~~~shell
$ git clone https://github.com/ctlove0523/gotify-java-client.git
$ cd gotify-java-client
$ mvn clean package
~~~

If you want to publish the artifacts to your local `Maven` repository use:

~~~shell
$ mvn clean install
~~~

Add `gotify-java-client` to your project dependencies like this：

~~~xml
<dependency>
	<groupId>io.github.ctlove0523.gotify</groupId>
	<artifactId>gotify-java-client</artifactId>
	<version>{version}</version>
</dependency>
~~~



## Getting Started

New to `Gotify`? Check this [Gotify](https://gotify.net/) and the [Gotify API](https://gotify.net/api-docs). Here is a very simple example about get applications from gotify server.

```java
// credential about gotify server,like user name and user password
Credential credential = new BasicCredential.Builder()
		.userName("admin")
		.password("admin")
		.build();

// config gotify server listen address and port.
GotifyClientConfig config = new GotifyClientConfig.Builder()
		.scheme("http")
		.host("localhost")
		.port(80)
		.credential(credential)
		.build();

GotifyClient gotifyClient = GotifyClient.build(config);

AppClient appClient = gotifyClient.getAppClient();

Result<List<Application>, GotifyResponseError> result = appClient.getApplications();
```

All public method return one `Resutl<T,E>` object,when method success `isSuccessful()`method return true otherwise return false and get use `error()` to get server error response. Usual usage like below：

~~~java
Result<List<Application>, GotifyResponseError> result = appClient.getApplications();
if (result.isSuccessful()) {
	// do something
} else {
	// process error/IOException
}
~~~

## Getting help

Having trouble with `gotify-java-client`? We'd like to help!

* If you are upgrading, read the [release notes](https://github.com/ctlove0523/gotify-java-client/releases) for upgrade instructions and *new and noteworthy* features.
* Report bugs with `gotify-java-client` at [https://github.com/ctlove0523/gotify-java-client/issues](https://github.com/ctlove0523/gotify-java-client/issues).

## Reporting Issues

`gotify-java-client` uses `GitHub’s` integrated issue tracking system to record bugs and feature requests.
If you want to raise an issue, please follow the recommendations below:

* Before you log a bug, please [search the issue tracker](https://github.com/ctlove0523/gotify-java-client/search?type=Issues) to see if someone has already reported the problem.
* If the issue doesn't already exist, [create a new issue](https://github.com/ctlove0523/gotify-java-client/issues/new/choose).
* Please provide as much information as possible with the issue report, we like to know
  the version of `gotify-java-client` that you are using, as well as your `Operating System` and
  `JVM` version.

## License

Reactor Netty is Open Source Software released under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
