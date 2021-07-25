package io.github.ctlove0523.gotify;

import java.util.List;

import io.github.ctlove0523.gotify.plugin.PluginConf;

/**
 * @author chentong
 */
public interface PluginClient extends CloseableClient {
	/**
	 * Return all plugins.
	 *
	 * @return return a {@link Result} object,if success {@link Result#result()} return
	 * a list of {@link PluginConf} otherwise {@link Result#error()} return a {@link ResponseError}
	 */
	Result<List<PluginConf>, ResponseError> getPlugins();

	/**
	 * Update YAML configuration for Configurer plugin.
	 *
	 * @return return a {@link Result} object,if success {@link Result#result()} return
	 * {@link Boolean} otherwise {@link Result#error()} return a {@link ResponseError}
	 */
	Result<Boolean, ResponseError> updatePluginConfig(int id);

	/**
	 * Get display info for a Displayer plugin.
	 *
	 * @return return a {@link Result} object,if success {@link Result#result()} return
	 * {@link String} otherwise {@link Result#error()} return a {@link ResponseError}
	 */
	Result<String, ResponseError> getPluginDisplay(int id);

	/**
	 * Disable a plugin.
	 *
	 * @return return a {@link Result} object,if success {@link Result#result()} return
	 * {@link Boolean} otherwise {@link Result#error()} return a {@link ResponseError}
	 */
	Result<Boolean, ResponseError> disablePlugin(int id);

	/**
	 * Enable a plugin.
	 *
	 * @return return a {@link Result} object,if success {@link Result#result()} return
	 * {@link Boolean} otherwise {@link Result#error()} return a {@link ResponseError}
	 */
	Result<Boolean, ResponseError> enablePlugin(int id);
}
