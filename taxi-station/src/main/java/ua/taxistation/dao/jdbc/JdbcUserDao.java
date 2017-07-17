package ua.taxistation.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.taxistation.dao.UserDao;
import ua.taxistation.entity.User;
import ua.taxistation.entity.enums.Role;
import ua.taxistation.exceptions.ServerAppException;

public class JdbcUserDao implements UserDao {

	private static final Logger LOGGER = LogManager.getLogger(JdbcUserDao.class);

	private static String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";

	private static String SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";

	private static String CREATE_USER = "INSERT INTO users(login, password, phone_number, role, salt) values (?, ?, ?, ?, ?)";

	//user fields
	private static String ID = "id";
	private static String LOGIN = "login";
	private static String PASSWORD = "password";
	private static String PHONENUMBER = "phone_number";
	private static String ROLE = "role";
	private static String SALT = "salt";

	private Connection connection;
	private boolean connectionShouldBeClosed;

	public JdbcUserDao(Connection connection) {
		this.connection = connection;
		connectionShouldBeClosed = false;
	}

	public JdbcUserDao(Connection connection, boolean connectionShouldBeClosed) {
		this.connection = connection;
		this.connectionShouldBeClosed = connectionShouldBeClosed;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Optional<User> getById(Long id) {
		Optional<User> user = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(SELECT_USER_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				user = Optional.of(extractUserFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao getById SQL failed: " + id, e);
			throw new ServerAppException(e);
		}
		return user;
	}

	@Override
	public List<User> getAll() {
		throw new UnsupportedOperationException("JdbcUserDao getAll error");
	}

	@Override
	public boolean create(User user) {
		try (PreparedStatement query = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
			query.setString(1, user.getLogin());
			query.setString(2, user.getPassword());
			query.setString(3, user.getPhonenumber());
			query.setString(4, user.getRole().name().toLowerCase());
			query.setBytes(5, user.getSalt());
			query.executeUpdate();
			ResultSet generatedKeys = query.getGeneratedKeys();
			if (generatedKeys.next())
				user.setId(generatedKeys.getLong(1));

		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao createUser SQL failed: " + user.getLogin(), e);
			throw new ServerAppException(e);
		}
		return true;
	}

	@Override
	public boolean update(User e) {
		throw new UnsupportedOperationException("JdbcUserDao update error");
	}

	@Override
	public boolean delete(Long id) {
		throw new UnsupportedOperationException("JdbcUserDao delete error");
	}

	@Override
	public Optional<User> getUserByLogin(String login) {
		Optional<User> user = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
			query.setString(1, login);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				user = Optional.of(extractUserFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao getByLogin SQL failed: " + login, e);
			throw new ServerAppException(e);
		}
		return user;
	}

	@Override
	public void close() {
		if (connectionShouldBeClosed) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("JdbcUserDao connection closing failed", e);
				throw new ServerAppException(e);
			}
		}
	}

	private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
		return new User.Builder().setId(resultSet.getLong(ID)).setLogin(resultSet.getString(LOGIN))
				.setPassword(resultSet.getString(PASSWORD)).setPhonenumber(resultSet.getString(PHONENUMBER))
				.setRole(Role.valueOf(resultSet.getString(ROLE).toUpperCase())).setSalt(resultSet.getBytes(SALT)).build();
	}
}
