package ua.taxistation.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.taxistation.dao.RequestDao;
import ua.taxistation.entity.Request;
import ua.taxistation.entity.User;
import ua.taxistation.entity.enums.CarCharacteristics;
import ua.taxistation.entity.enums.RequestStatus;
import ua.taxistation.exceptions.ServerAppException;

public class JdbcRequestDao implements RequestDao {

	private static String CREATE_REQUEST = "INSERT INTO requests(user_id, pickup, destination, status) values (?, ?, ?, ?)";

	private static String CREATE_LINK_BETWEEN_REQUEST_AND_CHARACTERISTICS = "INSERT INTO m2m_requests_characteristics"
			+ "(request_id, characteristic_id) values (?, ?)";

	private static String SELECT_REQUESTS_BY_STATUS = "SELECT requests.id, requests.user_id, requests.pickup, "
			+ "requests.destination, requests.date_of_request, requests.status, car_characteristics.value "
			+ "FROM requests, car_characteristics, m2m_requests_characteristics "
			+ "WHERE m2m_requests_characteristics.request_id = requests.id "
			+ "AND m2m_requests_characteristics.characteristic_id = car_characteristics.id AND status = ?";

	private static String SELECT_REQUEST_BY_ID = "SELECT requests.id, requests.user_id, requests.pickup, "
			+ "requests.destination, requests.date_of_request, requests.status, car_characteristics.value "
			+ "FROM requests, car_characteristics, m2m_requests_characteristics "
			+ "WHERE m2m_requests_characteristics.request_id = requests.id "
			+ "AND m2m_requests_characteristics.characteristic_id = car_characteristics.id AND requests.id = ?";

	private static String UPDATE_REQUEST = "UPDATE requests SET status = ? where id = ?";

	private static String ID = "requests.id";
	private static String USERID = "requests.user_id";
	private static String PICKUP = "requests.pickup";
	private static String DESTINATION = "requests.destination";
	private static String DATE_OF_REQUEST = "requests.date_of_request";
	private static String STATUS = "requests.status";

	private static String CHARACTERISTIC_VALUE = "car_characteristics.value";

	private static final Logger LOGGER = LogManager.getLogger(JdbcRequestDao.class);

	private Connection connection;
	private boolean connectionShouldBeClosed;

	public JdbcRequestDao(Connection connection) {
		this.connection = connection;
		connectionShouldBeClosed = false;
	}

	public JdbcRequestDao(Connection connection, boolean connectionShouldBeClosed) {
		this.connection = connection;
		this.connectionShouldBeClosed = connectionShouldBeClosed;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Optional<Request> getById(Long id) {
		Optional<Request> optionalRequest = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(SELECT_REQUEST_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				Request request = extractRequestFromResultSet(resultSet);
				request.setCarCharacteristics(extractCarCharacteristicsFromResultSet(resultSet));
				optionalRequest = Optional.of(request);
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcRequestDao getById SQL failed: " + id, e);
			throw new ServerAppException(e);
		}
		return optionalRequest;
	}

	@Override
	public List<Request> getAll() {
		throw new UnsupportedOperationException("JdbcRequestDao getAll error");
	}

	@Override
	public boolean create(Request request) {
		try (PreparedStatement query = connection.prepareStatement(CREATE_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
			query.setLong(1, request.getUser().getId());
			query.setString(2, request.getPickup());
			query.setString(3, request.getDestination());
			query.setString(4, request.getRequestStatus().name().toLowerCase());
			query.executeUpdate();
			ResultSet generatedKeys = query.getGeneratedKeys();
			if (generatedKeys.next()) {
				request.setId(generatedKeys.getLong(1));
				createCharacteristicsByRequest(request);
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcRequestDao createRequest SQL failed", e);
			throw new ServerAppException(e);
		}
		return true;
	}

	@Override
	public boolean update(Request request) {
		try (PreparedStatement query = connection.prepareStatement(UPDATE_REQUEST)) {
			query.setString(1, request.getRequestStatus().name().toLowerCase());
			query.setLong(2, request.getId());
			query.executeUpdate();
			return true;
		} catch (SQLException e) {
			LOGGER.error("JdbcRequestDao updateStatus SQL failed: " + request.getId(), e);
			throw new ServerAppException(e);
		}
	}

	@Override
	public boolean delete(Long id) {
		throw new UnsupportedOperationException("JdbcRequestDao getAll error");
	}

	@Override
	public void close() {
		if (connectionShouldBeClosed) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("JdbcRequestDao connection closing failed", e);
				throw new ServerAppException(e);
			}
		}
	}

	private void createCharacteristicsByRequest(Request request) {
		for (CarCharacteristics carCharacteristics : request.getCarCharacteristics()) {
			try (PreparedStatement query = connection
					.prepareStatement(CREATE_LINK_BETWEEN_REQUEST_AND_CHARACTERISTICS)) {
				query.setLong(1, request.getId());
				query.setLong(2, carCharacteristics.ordinal() + 1);
				query.executeUpdate();
			} catch (SQLException e) {
				LOGGER.error("JdbcRequestDao createCharacteristicsByRequest SQL failed: " + request.getId(), e);
				throw new ServerAppException(e);
			}
		}
	}

	@Override
	public List<Request> getRequestsByStatus(RequestStatus requestStatus) {
		List<Request> requests = new ArrayList<>();
		Long last_request_id = (long) -2;
		try (PreparedStatement query = connection.prepareStatement(SELECT_REQUESTS_BY_STATUS)) {
			query.setString(1, requestStatus.name().toLowerCase());
			ResultSet resultSet = query.executeQuery();

			Request request = new Request();
			boolean ifFirstRequest = true;
			while (resultSet.next()) {
				if (!last_request_id.equals(resultSet.getLong(ID))) {

					if (!ifFirstRequest) {
						requests.add(request);
					} else {
						ifFirstRequest = false;
					}

					request = extractRequestFromResultSet(resultSet);
					request.addCarCharacteristic(
							CarCharacteristics.valueOf(resultSet.getString(CHARACTERISTIC_VALUE).toUpperCase()));
					last_request_id = request.getId();

				} else {

					request.addCarCharacteristic(
							CarCharacteristics.valueOf(resultSet.getString(CHARACTERISTIC_VALUE).toUpperCase()));
				}
			}
			if (request.getId() != null)
				requests.add(request);
		} catch (SQLException e) {
			LOGGER.error("JdbcRequestDao getByStatus SQL failed", e);
			throw new ServerAppException(e);
		}
		return requests;
	}

	private Request extractRequestFromResultSet(ResultSet resultSet) throws SQLException {
		Request request = new Request.Builder().setId(resultSet.getLong(ID))
				.setUser(new User.Builder().setId(resultSet.getLong(USERID)).build())
				.setPickup(resultSet.getString(PICKUP)).setDestination(resultSet.getString(DESTINATION))
				.setRequestStatus(RequestStatus.valueOf(resultSet.getString(STATUS).toUpperCase()))
				.setDateOfRequest(resultSet.getTimestamp(DATE_OF_REQUEST).toLocalDateTime()).build();
		return request;
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
