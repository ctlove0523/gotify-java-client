package io.github.ctlove0523.gotify;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chentong
 */
class UriBuilder {
	private String scheme;
	private String host;
	private int port;
	private String path;
	private Map<String, Object> pathParams;
	private Map<String, Object> queryParams;

	public static UriBuilder builder() {
		return new UriBuilder();
	}

	public UriBuilder scheme(String scheme) {
		this.scheme = scheme;
		return this;
	}

	public UriBuilder host(String host) {
		this.host = host;
		return this;
	}

	public UriBuilder port(int port) {
		this.port = port;
		return this;
	}

	public UriBuilder path(String path) {
		this.path = path;
		return this;
	}

	public UriBuilder pathParams(Map<String, Object> pathParams) {
		this.pathParams = pathParams;
		return this;
	}

	public UriBuilder queryParams(Map<String, Object> queryParams) {
		this.queryParams = queryParams;
		return this;
	}

	public UriBuilder config(GotifyClientConfig config) {
		this.scheme = config.getScheme();
		this.host = config.getHost();
		this.port = config.getPort();
		return this;
	}

	public String build() {
		StringBuilder sb = new StringBuilder();
		sb.append(scheme);
		sb.append("://");
		sb.append(host);
		sb.append(":");
		sb.append(port);

		if (pathParams == null || pathParams.isEmpty()) {
			sb.append(path);
		}
		else {
			for (Map.Entry<String, Object> entry : pathParams.entrySet()) {
				path = path.replace("{" + entry.getKey() + "}", entry.getValue().toString());
			}
			sb.append(path);
		}

		if (queryParams != null && !queryParams.isEmpty()) {
			List<String> queryString = new ArrayList<>(queryParams.size());
			queryParams.forEach((s, o) -> queryString.add(s + "=" + o.toString()));
			sb.append("?");
			sb.append(String.join("&", queryString));
		}

		return sb.toString();
	}
}
