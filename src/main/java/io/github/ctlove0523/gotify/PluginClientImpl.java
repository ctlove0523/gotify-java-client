package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.plugin.PluginConf;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PluginClientImpl implements PluginClient {

    private final GotifyClientConfig clientConfig;

    PluginClientImpl(GotifyClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    @Override
    public Result<List<PluginConf>, ResponseError> getPlugins() {
        String url = UriBuilder.builder()
                .config(clientConfig)
                .path("/plugin")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        return new GotifyRequest.Builder(clientConfig)
                .request(request)
                .clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
                .build()
                .executeReturnList(PluginConf.class);
    }

    @Override
    public Result<Boolean, ResponseError> updatePluginConfig(int id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);

        String url = UriBuilder.builder()
                .config(clientConfig)
                .path("/plugin/{id}/config")
                .pathParams(params)
                .build();

        RequestBody requestBody = RequestBody.create(new byte[]{}, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        return new GotifyRequest.Builder(clientConfig)
                .request(request)
                .clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
                .build()
                .execute(Boolean.class);
    }

    @Override
    public Result<String, ResponseError> getPluginDisplay(int id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);

        String url = UriBuilder.builder()
                .config(clientConfig)
                .path("/plugin/{id}/display")
                .pathParams(params)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        return new GotifyRequest.Builder(clientConfig)
                .request(request)
                .clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
                .build()
                .execute(String.class);
    }

    @Override
    public Result<Boolean, ResponseError> disablePlugin(int id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);

        String url = UriBuilder.builder()
                .config(clientConfig)
                .path("/plugin/{id}/disable")
                .pathParams(params)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        return new GotifyRequest.Builder(clientConfig)
                .request(request)
                .clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
                .build()
                .execute(Boolean.class);
    }

    @Override
    public Result<Boolean, ResponseError> enablePlugin(int id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);

        String url = UriBuilder.builder()
                .config(clientConfig)
                .path("/plugin/{id}/enable")
                .pathParams(params)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        return new GotifyRequest.Builder(clientConfig)
                .request(request)
                .clientAuthInfoWriter(ClientAuthInfoWriterFactory.writer(clientConfig.getCredential()))
                .build()
                .execute(Boolean.class);
    }
}
