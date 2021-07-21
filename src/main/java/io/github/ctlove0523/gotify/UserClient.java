package io.github.ctlove0523.gotify;

import java.util.List;

import io.github.ctlove0523.gotify.user.UpdateCurrentUserPasswordRequest;
import io.github.ctlove0523.gotify.user.User;

public interface UserClient extends CloseableClient {

	Result<User, ResponseError> currentUser();

	Result<Boolean, ResponseError> updateCurrentUserPassword(UpdateCurrentUserPasswordRequest updateCurrentUserPasswordRequest);

	Result<List<User>, ResponseError> getUsers();

	Result<User, ResponseError> createUser(User user);

	Result<User, ResponseError> getUser(Integer id);

	Result<User, ResponseError> updateUser(User user);

	Result<Boolean, ResponseError> deleteUser(Integer id);
}
