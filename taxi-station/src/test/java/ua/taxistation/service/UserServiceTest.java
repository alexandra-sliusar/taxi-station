package ua.taxistation.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.Test;
import org.mockito.Matchers;

import ua.taxistation.controller.dto.CredentialsDto;
import ua.taxistation.dao.DaoConnection;
import ua.taxistation.dao.DaoFactory;
import ua.taxistation.dao.UserDao;
import ua.taxistation.data.UserData;
import ua.taxistation.entity.User;
import ua.taxistation.exceptions.ServerAppException;
import ua.taxistation.services.UserService;

public class UserServiceTest {
	private DaoFactory daoFactory;
	private DaoConnection daoConnection;
	private UserDao userDao;
	private UserService userService;

	private void initMocking() {
		daoFactory = mock(DaoFactory.class);
		daoConnection = mock(DaoConnection.class);
		userDao = mock(UserDao.class);
		userService = new UserService(daoFactory);
		when(daoFactory.getConnection()).thenReturn(daoConnection);
		when(daoFactory.createUserDao()).thenReturn(userDao);
	}

	@Test
	public void testSignInUserWithValidPassword() {
		Optional<User> user = UserData.getOptionalClient();
		CredentialsDto credentialsDto = UserData.getCredentialsDtoWithValidPassword();
		initMocking();
		when(userDao.getUserByLogin(anyString())).thenReturn(user);

		User actualUser = userService.signInUser(credentialsDto);
		assertEquals(user.get(), actualUser);
		verify(daoFactory).createUserDao();
		verify(userDao).getUserByLogin(credentialsDto.getLogin());
	}

	@Test(expected = ServerAppException.class)
	public void testSignInUserWithInvalidPassword() {
		Optional<User> user = UserData.getOptionalClient();
		CredentialsDto credentialsDto = UserData.getCredentialsDtoWithInvalidPassword();
		initMocking();
		when(userDao.getUserByLogin(anyString())).thenReturn(user);

		userService.signInUser(credentialsDto);
		verify(daoFactory).createUserDao();
		verify(userDao).getUserByLogin(credentialsDto.getLogin());
	}

	@Test(expected = ServerAppException.class)
	public void testSignInUserWhichNotExist() {
		Optional<User> user = Optional.empty();
		CredentialsDto credentialsDto = UserData.getCredentialsDtoForNotSignedUpUser();
		initMocking();
		when(userDao.getUserByLogin(anyString())).thenReturn(user);
		userService.signInUser(credentialsDto);
		verify(daoFactory).createUserDao();
		verify(userDao).getUserByLogin(credentialsDto.getLogin());
	}

	@Test
	public void testSignUpUserWhichNotExist() {
		Optional<User> signedUpUser = Optional.empty();
		CredentialsDto dto = UserData.getCredentialsDtoForNotSignedUpUser();
		User user = UserData.getUserFromCredentialsDto(dto);
		initMocking();
		when(userDao.getUserByLogin(anyString())).thenReturn(signedUpUser);
		when(userDao.create(user)).thenReturn(true);
		userService.signUpUser(dto);
		
		verify(daoFactory).createUserDao();
		verify(userDao).getUserByLogin(dto.getLogin());
		verify(userDao).create(Matchers.any(User.class));
	}
	
	@Test(expected = ServerAppException.class)
	public void testSignUpUserWhichExist() {
		Optional<User> signedUpUser = UserData.getOptionalClient();
		CredentialsDto dto = UserData.getCredentialsDtoWithValidPassword();
		initMocking();
		
		when(userDao.getUserByLogin(anyString())).thenReturn(signedUpUser);
		userService.signUpUser(dto);
		verify(daoFactory).createUserDao();
		verify(userDao).getUserByLogin(dto.getLogin());
	}


}
