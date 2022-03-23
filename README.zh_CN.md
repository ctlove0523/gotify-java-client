# gotify-java-client

`gotify-java-client` 是使用Java编写的 [Gotify](https://gotify.net/) 同步Client.`gotify-java-client` 可以在Java或者Android应用中使用。

## 从源码构建

使用`gotify-java-client`时无需从源码构建，可以从release页面直接下载二进制jar。如果你想提前体验新功能`gotify-java-client`
可以很容易使用 [maven](https://maven.apache.org/) 进行构建，当然这需要你已经安装JDK 1.8或者更高版本。

`gotify-java-client` 运行需要 Java 8 及其以上版本。

~~~shell
$ git clone https://github.com/ctlove0523/gotify-java-client.git
$ cd gotify-java-client
$ mvn clean package
~~~

如果你想将`gotify-java-client` 的jar包发布到本地maven仓库，下面的命令可以轻松完成这项工作：

~~~shell
$ mvn clean install
~~~

将 `gotify-java-client` 添加到项目的依赖中：

~~~xml
<dependency>
	<groupId>io.github.ctlove0523.gotify</groupId>
	<artifactId>gotify-java-client</artifactId>
	<version>{version}</version>
</dependency>
~~~

## 开始使用

如果对`Gotify` 还不熟悉，可以参考 [Gotify](https://gotify.net/) 和 [Gotify API](https://gotify.net/api-docs) 进行学习。下面是一个非常简单的获取应用的样例：

```java
Credential credential=new BasicCredential.Builder()
		.userName("admin")
		.password("admin")
		.build();

GotifyClientConfig config=new GotifyClientConfig.Builder()
		.endpoint("http://localhost")
		.credential(credential)
		.build();

GotifyClient gotifyClient=GotifyClient.build(config);

MessageClient messageClient = gotifyClient.getMessageClient();
messageClient.registerMessageHandler(message -> System.out.println(message.getMessage()));
```

`gotify-java-client` 所有公开的方法都会返回一个`Result<T,E>` 对象，如果方法调用成功，那么`isSuccessful()` 方法返回`true`，否则返回`false` ，可以调用`error()`
方法获取Server返回的错误信息。下面是管用的方法：

~~~java
Result<List<Application>, GotifyResponseError> result = appClient.getApplications();
if (result.isSuccessful()) {
	// do something
} else {
	// process error/IOException
}
~~~

## 获取帮助

在使用`gotify-java-client` 的过程中遇到任何困难，我们都非常乐意提供帮助！

* 如果你要升级版本，请阅读 [release notes](https://github.com/ctlove0523/gotify-java-client/releases) 以获取升级帮助以及新功能介绍。
* 可以在 [https://github.com/ctlove0523/gotify-java-client/issues](https://github.com/ctlove0523/gotify-java-client/issues)
  上报`gotify-java-client` 的bug。

## 报告问题

`gotify-java-client` 使用`Github` 的issue 跟踪系统来记录bugs和功能需求。如果你想提一个issue，请参考下面的建议：

* 在提bug之前，请从[search the issue tracker](https://github.com/ctlove0523/gotify-java-client/search?type=Issues)
  查看是否已经有其他人报告相同的bug。
* 如果不存在相同的issue，那么请你[创建一个新的issue]((https://github.com/ctlove0523/gotify-java-client/issues/new/choose))
* 报告bug时请尽量提供足够多的信息，以方便我们定位并解决bug，因此我们需要知道`gotify-java-client` 的版本以及你使用的操作系统和JDK版本。

## License

gotify-java-client 使用 MIT License.

