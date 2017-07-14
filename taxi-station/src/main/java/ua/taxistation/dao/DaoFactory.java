package ua.taxistation.dao;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.taxistation.exceptions.ServerAppException;

public abstract class DaoFactory {
	private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class);

	public static final String DB_FILE = "/db.properties";
	private static final String DB_FACTORY_CLASS = "factory.class";

	private static DaoFactory daoFactory;

	public abstract DaoConnection getConnection();

	public abstract UserDao createUserDao();

	public abstract UserDao createUserDao(DaoConnection connection);

	public abstract CarDao createCarDao();

	public abstract CarDao createCarDao(DaoConnection connection);

	public abstract OrderDao createOrderDao();

	public abstract OrderDao createOrderDao(DaoConnection connection);

	public abstract RequestDao createRequestDao();

	public abstract RequestDao createRequestDao(DaoConnection connection);

	public static DaoFactory getDaoFactory() {
		if (daoFactory == null) {
			try {
				InputStream inputStream = DaoFactory.class.getResourceAsStream(DB_FILE);
				Properties dbProps = new Properties();
				dbProps.load(inputStream);
				String factoryClass = dbProps.getProperty(DB_FACTORY_CLASS);
				daoFactory = (DaoFactory) Class.forName(factoryClass).newInstance();

			} catch (Exception e) {
				LOGGER.error("Can't load inputStream db config file to properties object", e);
				throw new ServerAppException(e);
			}
		}

		return daoFactory;
	}
}
