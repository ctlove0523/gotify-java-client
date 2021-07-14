package io.github.ctlove0523.gotify;

import io.github.ctlove0523.gotify.user.UpdateCurrentUserPasswordRequest;
import io.github.ctlove0523.gotify.user.User;

public interface UserClient extends CloseableClient {

	User currentUser();

	Boolean updateCurrentUserPassword(UpdateCurrentUserPasswordRequest updateCurrentUserPasswordRequest);

	Iterable<User> getUsers();

	User createUser(User user);

	User getUser(Integer id);

	User updateUser(User user);

	Boolean deleteUser(Integer id);
}
