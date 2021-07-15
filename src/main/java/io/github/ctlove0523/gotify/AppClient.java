package io.github.ctlove0523.gotify;

import java.util.List;

import io.github.ctlove0523.gotify.app.Application;
import io.github.ctlove0523.gotify.app.CreateApplicationRequest;
import io.github.ctlove0523.gotify.app.UpdateApplictionRequest;

public interface AppClient extends CloseableClient {

	Result<List<Application>, GotifyResponseError> getApplications();

	Result<Application, GotifyResponseError> createApplication(CreateApplicationRequest createApplicationRequest);

	Result<Application, GotifyResponseError> updateApplication(int id, UpdateApplictionRequest updateApplictionRequest);

	Result<Boolean, GotifyResponseError> deleteApplication(int id);

	Result<Application, GotifyResponseError> uploadApplicationImage(int id, byte[] image);

}
