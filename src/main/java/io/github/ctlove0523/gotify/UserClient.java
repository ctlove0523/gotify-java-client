package io.github.ctlove0523.gotify;

import java.util.List;

import io.github.ctlove0523.gotify.user.UpdateCurrentUserPasswordRequest;
import io.github.ctlove0523.gotify.user.User;

/**
 * @author chentong
 */
public interface UserClient extends CloseableClient {

	/**
	 * Return the current user.
	 *
	 * @return return a {@link Result} object,if success {@link Result#result()} return
	 * {@link User} otherwise {@link Result#error()} return a {@link ResponseError}
	 */
	Result<User, ResponseError> currentUser();

	/**
	 * Update the password of the current user.
	 *
	 * @param updateCurrentUserPasswordRequest new password
	 * @return return a {@link Result} object,if success {@link Result#result()} return
	 * {@link Boolean} otherwise {@link Result#error()} return a {@link ResponseError}
	 */
	Result<Boolean, ResponseError> updateCurrentUserPassword(UpdateCurrentUserPasswordRequest updateCurrentUserPasswordRequest);

	/**
	 * Query all users.
	 *
	 * @return return a {@link Result} object,if success {@link Result#result()} return a list of
	 * {@link User} otherwise {@link Result#error()} return a {@link ResponseError}
	 */
	Result<List<User>, ResponseError> getUsers();

	/**
	 * Create new user.
	 *
	 * @param user user you want to create.
	 * @return return a {@link Result} object,if success {@link Result#result()} return
	 * {@link User},otherwise {@link Result#error()} return a {@link ResponseError}
	 */
	Result<User, ResponseError> createUser(User user);

	/**
	 * Query user with id
	 *
	 * @param id the user's id
	 * @return return a {@link Result} object,if success {@link Result#result()} return
	 * {@link User} with the id,otherwise {@link Result#error()} return a {@link ResponseError}
	 */
	Result<User, ResponseError> getUser(Integer id);

	/**
	 * Update user info.
	 *
	 * @param user new user.
	 * @return return a {@link Result} object,if success {@link Result#result()} return new
	 * {@link User} otherwise {@link Result#error()} return a {@link ResponseError}
	 */
	Result<User, ResponseError> updateUser(User user);

	/**
	 * Delete user with id
	 *
	 * @param id the user's id
	 * @return return a {@link Result} object,if success {@link Result#result()} return
	 * {@link Boolean} otherwise {@link Result#error()} return a {@link ResponseError}
	 */
	Result<Boolean, ResponseError> deleteUser(Integer id);
}
