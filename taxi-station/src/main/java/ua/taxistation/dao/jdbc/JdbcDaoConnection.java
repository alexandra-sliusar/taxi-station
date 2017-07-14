package ua.taxistation.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.taxistation.dao.DaoConnection;
import ua.taxistation.exceptions.ServerAppException;

public class JdbcDaoConnection implements DaoConnection {

	private Connection connection;
	private boolean inTransaction = false;
	private static final Logger LOGGER = LogManager.getLogger(JdbcDaoConnection.class);

	public JdbcDaoConnection(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}

	public void begin() {
		try {
			connection.setAutoCommit(false);
			inTransaction = true;
			LOGGER.info("Transaction begun");
		} catch (SQLException e) {
			LOGGER.error("JdbcDaoConnection begin failed", e);
			throw new ServerAppException(e);
		}
	}

	@Override
	public void commit() {
		try {
			connection.commit();
			inTransaction = false;
			LOGGER.info("Transaction commited");
		} catch (SQLException e) {
			LOGGER.error("JdbcDaoConnection commit failed", e);
			throw new ServerAppException(e);
		}
	}

	@Override
	public void rollback() {
		try {
			connection.rollback();
			inTransaction = false;
			LOGGER.info("Transaction rollbacked");
		} catch (SQLException e) {
			LOGGER.error("JdbcDaoConnection rollback failed", e);
			throw new ServerAppException(e);
		}
	}

	@Override
	public void close() {
		if (inTransaction) {
			rollback();
		}
		try {
			connection.close();
			LOGGER.info("Transaction closed");
		} catch (SQLException e) {
			LOGGER.error("JdbcDaoConnection close failed", e);
			throw new ServerAppException(e);
		}
	}
}
