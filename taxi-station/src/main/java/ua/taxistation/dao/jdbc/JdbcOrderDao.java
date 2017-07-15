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

import ua.taxistation.dao.OrderDao;
import ua.taxistation.entity.Car;
import ua.taxistation.entity.Order;
import ua.taxistation.entity.Request;
import ua.taxistation.entity.enums.OrderStatus;
import ua.taxistation.exceptions.ServerAppException;

public class JdbcOrderDao implements OrderDao {

	private static String SELECT_ORDER_BY_ID = "SELECT orders.id, orders.request_id, orders.car_id, orders.status "
			+ "FROM orders WHERE id = ?";

	private static String CREATE_ORDER = "INSERT INTO orders(request_id, car_id, status) values (?, ?, ?)";

	private static String SELECT_ORDER_BY_STATUS = "SELECT orders.id, orders.request_id, orders.car_id, orders.status "
			+ "FROM orders WHERE status = ?";

	private static String SELECT_ORDER_BY_USER_ID = "SELECT orders.id, orders.request_id, orders.car_id, orders.status "
			+ "FROM orders,requests WHERE requests.id = orders.request_id AND requests.user_id = ?";

	private static String SELECT_LAST_ORDER_BY_CAR_ID = "SELECT orders.id, orders.request_id, orders.car_id, orders.status "
			+ "FROM orders WHERE car_id = ? limit 1";

	private static String UPDATE_ORDER = "UPDATE orders SET status = ? where id = ?";

	private static String ID = "orders.id";
	private static String REQUEST_ID = "orders.request_id";
	private static String CAR_ID = "orders.car_id";
	private static String STATUS = "orders.status";

	private static final Logger LOGGER = LogManager.getLogger(JdbcOrderDao.class);

	private Connection connection;
	private boolean connectionShouldBeClosed;

	public JdbcOrderDao(Connection connection) {
		this.connection = connection;
		connectionShouldBeClosed = false;
	}

	public JdbcOrderDao(Connection connection, boolean connectionShouldBeClosed) {
		this.connection = connection;
		this.connectionShouldBeClosed = connectionShouldBeClosed;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Optional<Order> getById(Long id) {
		Optional<Order> order = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(SELECT_ORDER_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				order = Optional.of(extractOrderFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcOrderDao getById SQL failed: " + id, e);
			throw new ServerAppException(e);
		}
		return order;
	}

	@Override
	public List<Order> getAll() {
		throw new UnsupportedOperationException("JdbcOrderDao getAll error");
	}

	@Override
	public boolean create(Order order) {
		try (PreparedStatement query = connection.prepareStatement(CREATE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
			query.setLong(1, order.getRequest().getId());
			query.setLong(2, order.getCar().getId());
			query.setString(3, order.getOrderStatus().name().toLowerCase());
			query.executeUpdate();
			ResultSet generatedKeys = query.getGeneratedKeys();
			if (generatedKeys.next())
				order.setId(generatedKeys.getLong(1));
		} catch (SQLException e) {
			LOGGER.error("JdbcOrderDao createOrder SQL failed", e);
			throw new ServerAppException(e);
		}
		return true;
	}

	@Override
	public boolean update(Order order) {
		try (PreparedStatement query = connection.prepareStatement(UPDATE_ORDER)) {
			query.setString(1, order.getOrderStatus().name().toLowerCase());
			query.setLong(2, order.getId());
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcOrderDao updateOrder SQL failed: " + order.getId(), e);
			throw new ServerAppException(e);
		}
		return true;
	}

	@Override
	public boolean delete(Long id) {
		throw new UnsupportedOperationException("JdbcOrderDao delete error");
	}

	@Override
	public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
		List<Order> orders = new ArrayList<>();
		try (PreparedStatement query = connection.prepareStatement(SELECT_ORDER_BY_STATUS)) {
			query.setString(1, orderStatus.name().toLowerCase());
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				orders.add(extractOrderFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcOrderDao getByStatus SQL failed", e);
			throw new ServerAppException(e);
		}
		return orders;
	}

	@Override
	public Optional<Order> getLastCarOrder(Long carId) {
		Optional<Order> order = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(SELECT_LAST_ORDER_BY_CAR_ID)) {
			query.setLong(1, carId);
			ResultSet resultSet = query.executeQuery();
			if (resultSet.next()) {
				order = Optional.of(extractOrderFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcOrderDao getLastCarOrder SQL failed: " + carId, e);
			throw new ServerAppException(e);
		}
		return order;
	}

	public List<Order> getOrdersByUserId(Long userId) {
		List<Order> orders = new ArrayList<>();
		try (PreparedStatement query = connection.prepareStatement(SELECT_ORDER_BY_USER_ID)) {
			query.setLong(1, userId);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				orders.add(extractOrderFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcOrderDao getByUserId SQL failed: " + userId, e);
			throw new ServerAppException(e);
		}
		return orders;
	}

	@Override
	public void close() {
		if (connectionShouldBeClosed) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("JdbcOrderDao connection closing failed", e);
				throw new ServerAppException(e);
			}
		}
	}

	private Order extractOrderFromResultSet(ResultSet resultSet) throws SQLException {
		return new Order.Builder().setId(resultSet.getLong(ID))
				.setCar(new Car.Builder().setId(resultSet.getLong(CAR_ID)).build())
				.setRequest(new Request.Builder().setId(resultSet.getLong(REQUEST_ID)).build())
				.setOrderStatus(OrderStatus.valueOf(resultSet.getString(STATUS).toUpperCase())).build();
	}
}
