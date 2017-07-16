package ua.taxistation.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.taxistation.dao.CarDao;
import ua.taxistation.entity.Car;
import ua.taxistation.entity.User;
import ua.taxistation.entity.enums.CarCharacteristics;
import ua.taxistation.entity.enums.CarStatus;
import ua.taxistation.exceptions.ServerAppException;

public class JdbcCarDao implements CarDao {

	private static String SELECT_ALL_CARS = "SELECT cars.id, cars.driver_id, cars.number, cars.model, "
			+ "cars.color, cars.status, car_characteristics.value FROM cars, m2m_cars_characteristics, car_characteristics "
			+ "WHERE m2m_cars_characteristics.car_id = cars.id "
			+ "AND m2m_cars_characteristics.characteristic_id = car_characteristics.id";

	private static String SELECT_CAR_BY_ID = "SELECT cars.id, cars.driver_id, cars.number, cars.model, "
			+ "cars.color, cars.status, car_characteristics.value FROM cars, m2m_cars_characteristics, car_characteristics "
			+ "WHERE m2m_cars_characteristics.car_id = cars.id "
			+ "AND m2m_cars_characteristics.characteristic_id = car_characteristics.id AND cars.id = ?";

	private static String SELECT_CAR_BY_DRIVER = "SELECT cars.id, cars.driver_id, cars.number, cars.model, "
			+ "cars.color, cars.status, car_characteristics.value FROM cars, m2m_cars_characteristics, car_characteristics "
			+ "WHERE m2m_cars_characteristics.car_id = cars.id "
			+ "AND m2m_cars_characteristics.characteristic_id = car_characteristics.id AND cars.driver_id = ?";

	private static String SELECT_CARS_BY_CHARACTERISTIC_AND_STATUS = "SELECT cars.id, cars.driver_id, cars.number, cars.model, "
			+ "cars.color, cars.status FROM cars, m2m_cars_characteristics, car_characteristics "
			+ "WHERE m2m_cars_characteristics.car_id = cars.id "
			+ "AND m2m_cars_characteristics.characteristic_id = car_characteristics.id AND cars.status = ? "
			+ "AND car_characteristics.value = ?";

	private static String UPDATE_CAR = "UPDATE cars SET status = ? where id = ?";

	private static String ID = "cars.id";
	private static String DRIVERID = "cars.driver_id";
	private static String NUMBER = "cars.number";
	private static String MODEL = "cars.model";
	private static String COLOR = "cars.color";
	private static String STATUS = "cars.status";

	private static String CHARACTERISTIC_VALUE = "car_characteristics.value";

	private static final Logger LOGGER = LogManager.getLogger(JdbcCarDao.class);

	private Connection connection;
	private boolean connectionShouldBeClosed;

	public JdbcCarDao(Connection connection) {
		this.connection = connection;
		connectionShouldBeClosed = false;
	}

	public JdbcCarDao(Connection connection, boolean connectionShouldBeClosed) {
		this.connection = connection;
		this.connectionShouldBeClosed = connectionShouldBeClosed;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Optional<Car> getById(Long id) {
		Optional<Car> optionalCar = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(SELECT_CAR_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				Car car = extractCarFromResultSet(resultSet);
				car.setCarCharacteristics(extractCarCharacteristicsFromResultSet(resultSet));
				optionalCar = Optional.of(car);
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcCarDao getById SQL failed: " + id, e);
			throw new ServerAppException(e);
		}
		return optionalCar;
	}

	@Override
	public List<Car> getAll() {
		List<Car> cars = new ArrayList<>();
		String last_car_number = "";
		try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_CARS)) {
			ResultSet resultSet = query.executeQuery();
			Car car = new Car();
			boolean ifFirstCar = true;
			while (resultSet.next()) {
				if (!last_car_number.equals(resultSet.getString(NUMBER))) {
					if (!ifFirstCar)
						cars.add(car);
					else
						ifFirstCar = false;
					car = extractCarFromResultSet(resultSet);
					car.addCarCharacteristic(
							CarCharacteristics.valueOf(resultSet.getString(CHARACTERISTIC_VALUE).toUpperCase()));
					last_car_number = car.getNumber();
				} else {
					car.addCarCharacteristic(
							CarCharacteristics.valueOf(resultSet.getString(CHARACTERISTIC_VALUE).toUpperCase()));
				}
				if (car.getId() != null)
					cars.add(car);
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcCarDao getAll SQL failed", e);
			throw new ServerAppException(e);
		}
		return cars;
	}

	@Override
	public boolean create(Car e) {
		throw new UnsupportedOperationException("JdbcCarDao create error");
	}

	@Override
	public boolean update(Car car) {
		try (PreparedStatement query = connection.prepareStatement(UPDATE_CAR)) {
			query.setString(1, car.getCarStatus().name().toLowerCase());
			query.setLong(2, car.getId());
			query.executeUpdate();
			return true;
		} catch (SQLException e) {
			LOGGER.error("JdbcCarDao updateCar SQL failed: " + car.getId(), e);
			throw new ServerAppException(e);
		}
	}

	@Override
	public boolean delete(Long id) {
		throw new UnsupportedOperationException("JdbcCarDao delete error");
	}

	public List<Car> getCarsByCharacteristicAndStatus(CarCharacteristics characteristic, CarStatus carStatus) {
		List<Car> cars = new ArrayList<>();
		try (PreparedStatement query = connection.prepareStatement(SELECT_CARS_BY_CHARACTERISTIC_AND_STATUS)) {
			query.setString(1, carStatus.name().toLowerCase());
			query.setString(2, characteristic.name().toLowerCase());
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				Car car = extractCarFromResultSet(resultSet);
				car.addCarCharacteristic(characteristic);
				cars.add(car);
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcCarDao getByCharacteristicsAndStatus SQL failed", e);
			throw new ServerAppException(e);
		}
		return cars;
	}

	@Override
	public Optional<Car> getCarByDriver(Long driverId) {
		Optional<Car> optionalCar = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(SELECT_CAR_BY_DRIVER)) {
			query.setLong(1, driverId);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				Car car = extractCarFromResultSet(resultSet);
				car.setCarCharacteristics(extractCarCharacteristicsFromResultSet(resultSet));
				optionalCar = Optional.of(car);
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcCarDao getByDriver SQL failed: " + driverId, e);
			throw new ServerAppException(e);
		}
		return optionalCar;
	}

	@Override
	public void close() {
		if (connectionShouldBeClosed) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("JdbcCarDao connection closing failed", e);
				throw new ServerAppException(e);
			}
		}
	}

	private Car extractCarFromResultSet(ResultSet resultSet) throws SQLException {
		Car car = new Car.Builder().setId(resultSet.getLong(ID)).setModel(resultSet.getString(MODEL))
				.setNumber(resultSet.getString(NUMBER)).setColor(resultSet.getString(COLOR))
				.setCarStatus(CarStatus.valueOf(resultSet.getString(STATUS).toUpperCase()))
				.setDriver(new User.Builder().setId(resultSet.getLong(DRIVERID)).build()).build();
		return car;
	}

	private List<CarCharacteristics> extractCarCharacteristicsFromResultSet(ResultSet resultSet) throws SQLException {
		List<CarCharacteristics> carCharacteristics = new ArrayList<>();
		resultSet.beforeFirst();
		while (resultSet.next()) {
			carCharacteristics.add(CarCharacteristics.valueOf(resultSet.getString(CHARACTERISTIC_VALUE).toUpperCase()));
		}
		return carCharacteristics;

	}
}
