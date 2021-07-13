package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.app.Application;
import io.github.ctlove0523.gotify.app.CreateApplicationRequest;
import io.github.ctlove0523.gotify.app.UpdateApplictionRequest;

public interface AppClient extends CloseableClient {
	Iterable<Application> listApplication();

	Application createApplication(CreateApplicationRequest createApplicationRequest);

	Application updateApplication(int id, UpdateApplictionRequest updateApplictionRequest);

	boolean deleteApplication(int id);

	Application uploadApplicationImage(int id, byte[] image);

}
