package ua.taxistation.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.taxistation.dao.CarDao;
import ua.taxistation.dao.DaoConnection;
import ua.taxistation.dao.DaoFactory;
import ua.taxistation.dao.OrderDao;
import ua.taxistation.dao.RequestDao;
import ua.taxistation.dao.UserDao;
import ua.taxistation.exceptions.ServerAppException;

public class JdbcDaoFactory extends DaoFactory {
	public DataSource dataSource;
	private static final Logger LOGGER = LogManager.getLogger(JdbcDaoFactory.class);

	public JdbcDaoFactory() {
		try {
			InitialContext ic = new InitialContext();
			dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/taxistation");
		} catch (Exception e) {
			LOGGER.error("Can't load pool connection from initial context", e);
			throw new ServerAppException(e);
		}
	}

	public DaoConnection getConnection() {
		try {
			return new JdbcDaoConnection(dataSource.getConnection());
		} catch (SQLException e) {
			LOGGER.error("Can't get DB connection from data source", e);
			throw new ServerAppException(e);
		}
	}

	@Override
	public UserDao createUserDao() {
		try {
			return new JdbcUserDao(dataSource.getConnection(), true);
		} catch (SQLException e) {
			LOGGER.error("Can't get connection for user dao creation", e);
			throw new ServerAppException(e);
		}
	}

	@Override
	public UserDao createUserDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcUserDao(sqlConnection);
	}

	@Override
	public CarDao createCarDao() {
		try {
			return new JdbcCarDao(dataSource.getConnection(), true);
		} catch (SQLException e) {
			LOGGER.error("Can't get connection for car dao creation", e);
			throw new ServerAppException(e);
		}
	}

	@Override
	public CarDao createCarDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcCarDao(sqlConnection);
	}

	@Override
	public OrderDao createOrderDao() {
		try {
			return new JdbcOrderDao(dataSource.getConnection(), true);
		} catch (SQLException e) {
			LOGGER.error("Can't get connection for order dao creation", e);
			throw new ServerAppException(e);
		}
	}

	@Override
	public OrderDao createOrderDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcOrderDao(sqlConnection);
	}

	@Override
	public RequestDao createRequestDao() {
		try {
			return new JdbcRequestDao(dataSource.getConnection(), true);
		} catch (SQLException e) {
			LOGGER.error("Can't get connection for request dao creation", e);
			throw new ServerAppException(e);
		}
	}

	@Override
	public RequestDao createRequestDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcRequestDao(sqlConnection);
	}

}
