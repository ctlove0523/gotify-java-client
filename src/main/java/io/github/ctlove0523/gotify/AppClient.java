package io.github.ctlove0523.gotify;

import java.util.List;

import io.github.ctlove0523.gotify.app.Application;
import io.github.ctlove0523.gotify.app.CreateApplicationRequest;
import io.github.ctlove0523.gotify.app.UpdateApplicationRequest;

/**
 * client talk to gotify server to manage applications.
 */
public interface AppClient extends CloseableClient {

	/**
	 * query all applications
	 *
	 * @return return a {@link Result} object,if success {@link Result#result()} return a list of
	 * {@link Application} otherwise {@link Result#error()} return a {@link GotifyResponseError}
	 */
	Result<List<Application>, GotifyResponseError> getApplications();

	/**
	 * Create an application.
	 *
	 * @param createApplicationRequest {@link CreateApplicationRequest}
	 * @return return a {@link Result} object,if success {@link Result#result()} return the created
	 * {@link Application} otherwise {@link Result#error()} return a {@link GotifyResponseError}
	 */
	Result<Application, GotifyResponseError> createApplication(CreateApplicationRequest createApplicationRequest);

	/**
	 * Update an application
	 *
	 * @param id the id of an application
	 * @param updateApplicationRequest {@link UpdateApplicationRequest}
	 * @return return a {@link Result} object,if success {@link Result#result()} return the updated
	 * {@link Application} otherwise {@link Result#error()} return a {@link GotifyResponseError}
	 */
	Result<Application, GotifyResponseError> updateApplication(int id, UpdateApplicationRequest updateApplicationRequest);

	/**
	 * Delete an application
	 * @param id the id of an application
	 * @return return a {@link Result} object,if success {@link Result#result()} return true
	 * otherwise {@link Result#error()} return a {@link GotifyResponseError}
	 */
	Result<Boolean, GotifyResponseError> deleteApplication(int id);

	/**
	 * Upload an image for an application.
	 *
	 * @param id the id of an application
	 * @param image the image for uploaded
	 * @return return a {@link Result} object,if success {@link Result#result()} return the new
	 * {@link Application} with new image,otherwise {@link Result#error()} return a {@link GotifyResponseError}
	 */
	Result<Application, GotifyResponseError> uploadApplicationImage(int id, byte[] image);

}
