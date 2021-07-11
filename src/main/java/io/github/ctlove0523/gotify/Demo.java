package io.github.ctlove0523.gotify;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ctlove0523.gotify.app.Application;
import io.github.ctlove0523.gotify.app.CreateApplicationRequest;
import io.github.ctlove0523.gotify.app.UpdateApplictionRequest;

public class Demo {
	public static void main(String[] args) throws Exception {
		GotifyClientConfig config = new GotifyClientConfig.Builder()
				.scheme("http")
				.host("localhost")
				.port(6875)
				.userName("admin")
				.password("admin")
				.build();

		GotifyClient gotifyClient = GotifyClient.build(config);

		AppClient appClient = gotifyClient.getAppClient();

		File file = new File("D:\\迅雷下载\\御姐很哀伤\\111.御姐很哀伤[54P]\\1.jpg");



		byte[] imge = Files.readAllBytes(file.toPath());


		appClient.listApplication().forEach(new Consumer<Application>() {
			@Override
			public void accept(Application application) {
				System.out.println(application.getName());
			}
		});
	}
}
