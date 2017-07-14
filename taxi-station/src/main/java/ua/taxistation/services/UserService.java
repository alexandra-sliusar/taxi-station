package ua.taxistation.services;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.taxistation.controller.dto.CredentialsDto;
import ua.taxistation.dao.DaoConnection;
import ua.taxistation.dao.DaoFactory;
import ua.taxistation.dao.UserDao;
import ua.taxistation.entity.User;
import ua.taxistation.entity.enums.Role;
import ua.taxistation.exceptions.ServerAppException;
import ua.taxistation.utilities.PasswordHashing;

public class UserService {
	private static final Logger LOGGER = LogManager.getLogger(UserService.class);

	private DaoFactory daoFactory;

	UserService(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static class Holder {
		static final UserService INSTANCE = new UserService(DaoFactory.getDaoFactory());
	}

	public static UserService getInstance() {
		return Holder.INSTANCE;
	}

	public User signUpUser(CredentialsDto credentials, String passwordConfirm) throws ServerAppException {
		String login = credentials.getLogin();
		String password = credentials.getPassword();
		String phonenumber = credentials.getPhonenumber();

		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			UserDao userDao = daoFactory.createUserDao(connection);

			Optional<User> optionalUser = userDao.getUserByLogin(login);
			isNotUserSignedUp(optionalUser);

			User user = new User.Builder().setLogin(login).setRole(Role.CLIENT).setPhonenumber(phonenumber).build();
			hashUserPassword(user, password);
			userDao.create(user);
			connection.commit();

			return user;
		} catch (Exception e) {
			LOGGER.error("Signing up user " + login + " has failed", e);
			throw new ServerAppException("Signing up user " + login + " has failed");
		}
	}

	public User signInUser(CredentialsDto credentials) throws ServerAppException {
		String login = credentials.getLogin();
		String password = credentials.getPassword();

		try (UserDao userDao = daoFactory.createUserDao()) {
			Optional<User> user = userDao.getUserByLogin(login);
			isUserSignedUp(user);
			isPasswordValid(password, user.get().getSalt(), user.get().getPassword());
			return user.get();
		} catch (Exception e) {
			LOGGER.error("Signing in user " + login + " has failed", e);
			throw new ServerAppException("Signing in user " + login + " has failed");
		}
	}

	private void isUserSignedUp(Optional<User> user) throws ServerAppException {
		if (!user.isPresent()) {
			throw new ServerAppException("User isn`t signed up");
		}
	}

	private void isNotUserSignedUp(Optional<User> user) throws ServerAppException {
		if (user.isPresent()) {
			throw new ServerAppException("User is signed up");
		}
	}

	private void isPasswordValid(String passwordToCheck, byte[] salt, String hashedPassword) throws ServerAppException {
		if (!PasswordHashing.generateHash(passwordToCheck, salt).equals(hashedPassword)) {
			throw new ServerAppException("Wrong password");
		}
	}

	private void hashUserPassword(User user, String passwordToHash) {
		byte[] salt = PasswordHashing.generateSalt();
		String password = PasswordHashing.generateHash(passwordToHash, salt);
		user.setSalt(salt);
		user.setPassword(password);
	}
}
