package io.github.ctlove0523.gotify;

import java.util.List;

import io.github.ctlove0523.gotify.user.UpdateCurrentUserPasswordRequest;
import io.github.ctlove0523.gotify.user.User;

public interface UserClient extends CloseableClient {

	Result<User, GotifyResponseError> currentUser();

	Result<Boolean, GotifyResponseError> updateCurrentUserPassword(UpdateCurrentUserPasswordRequest updateCurrentUserPasswordRequest);

	Result<List<User>, GotifyResponseError> getUsers();

	Result<User, GotifyResponseError> createUser(User user);

	Result<User, GotifyResponseError> getUser(Integer id);

	Result<User, GotifyResponseError> updateUser(User user);

	Result<Boolean, GotifyResponseError> deleteUser(Integer id);
}
