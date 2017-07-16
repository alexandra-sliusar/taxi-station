package ua.taxistation.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.taxistation.dao.CarDao;
import ua.taxistation.dao.DaoConnection;
import ua.taxistation.dao.DaoFactory;
import ua.taxistation.dao.OrderDao;
import ua.taxistation.dao.RequestDao;
import ua.taxistation.dao.UserDao;
import ua.taxistation.entity.Car;
import ua.taxistation.entity.Order;
import ua.taxistation.entity.Request;
import ua.taxistation.entity.User;
import ua.taxistation.entity.enums.CarStatus;
import ua.taxistation.entity.enums.OrderStatus;
import ua.taxistation.entity.enums.RequestStatus;
import ua.taxistation.exceptions.ServerAppException;

public class OrderService {
	private static final Logger LOGGER = LogManager.getLogger(OrderService.class);

	private DaoFactory daoFactory;

	OrderService(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static class Holder {
		static final OrderService INSTANCE = new OrderService(DaoFactory.getDaoFactory());
	}

	public static OrderService getInstance() {
		return Holder.INSTANCE;
	}

	public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
		List<Order> orders = new ArrayList<>();
		try (OrderDao orderDao = daoFactory.createOrderDao()) {
			orders.addAll(orderDao.getOrdersByStatus(orderStatus));
		}
		return orders;
	}

	public List<Order> getOrdersByUser(User user) {
		List<Order> orders = new ArrayList<>();
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			OrderDao orderDao = daoFactory.createOrderDao(connection);
			RequestDao requestDao = daoFactory.createRequestDao(connection);
			UserDao userDao = daoFactory.createUserDao(connection);
			CarDao carDao = daoFactory.createCarDao(connection);
			orders.addAll(orderDao.getOrdersByUserId(user.getId()));
			for (Order order : orders) {
				order.setRequest(requestDao.getById(order.getRequest().getId()).get());
				order.getRequest().setUser(userDao.getById(user.getId()).get());
				order.setCar(carDao.getById(order.getCar().getId()).get());
			}
		}
		return orders;
	}

	public List<Order> getOrdersByDriver(User user) {
		List<Order> orders = new ArrayList<>();
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			OrderDao orderDao = daoFactory.createOrderDao(connection);
			RequestDao requestDao = daoFactory.createRequestDao(connection);
			UserDao userDao = daoFactory.createUserDao(connection);
			CarDao carDao = daoFactory.createCarDao(connection);

			orders.addAll(orderDao.getOrdersByDriverId(user.getId()));
			for (Order order : orders) {
				order.setRequest(requestDao.getById(order.getRequest().getId()).get());
				order.getRequest().setUser(userDao.getById(order.getRequest().getUser().getId()).get());
				order.setCar(carDao.getById(order.getCar().getId()).get());
			}
		}
		return orders;
	}

	public Order createOrder(Long requestId, Long carId) {
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			OrderDao orderDao = daoFactory.createOrderDao(connection);
			RequestDao requestDao = daoFactory.createRequestDao(connection);
			CarDao carDao = daoFactory.createCarDao(connection);
			Car car = carDao.getById(carId).get();
			isCarNotAvailable(car);
			Request request = requestDao.getById(requestId).get();
			isRequestNotProcessed(request);
			Order order = new Order.Builder().setCar(car).setRequest(request).setOrderStatus(OrderStatus.INCOMPLETE)
					.build();
			orderDao.create(order);
			request.setRequestStatus(RequestStatus.PROCESSED);
			car.setCarStatus(CarStatus.UNAVAILABLE);
			requestDao.update(request);
			carDao.update(car);
			connection.commit();
			return order;
		} catch (Exception e) {
			LOGGER.error("Order addition has been failed", e);
			throw new ServerAppException("Order addition has been failed");
		}
	}

	public void updateOrder(Order order) {
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			OrderDao orderDao = daoFactory.createOrderDao(connection);
			CarDao carDao = daoFactory.createCarDao(connection);

			order.getCar().setCarStatus(CarStatus.AVAILABLE);
			orderDao.update(order);
			carDao.update(order.getCar());

			connection.commit();
		}
	}

	public Optional<Order> getLastCarOrder(Car car) {
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			OrderDao orderDao = daoFactory.createOrderDao(connection);
			RequestDao requestDao = daoFactory.createRequestDao(connection);
			Optional<Order> order = orderDao.getLastCarOrder(car.getId());
			if (order.isPresent()) {
				order.get().setRequest(requestDao.getById(order.get().getRequest().getId()).get());
			}
			connection.commit();
			return order;
		} catch (Exception e) {
			LOGGER.error("Getting last order of car " + car.getId() + " has failed", e);
			throw new ServerAppException("Getting last order of car " + car.getId() + " has failed");
		}
	}

	private void isCarNotAvailable(Car car) throws ServerAppException {
		if (car.getCarStatus() != CarStatus.AVAILABLE)
			throw new ServerAppException("Car is not available");
	}

	private void isRequestNotProcessed(Request request) throws ServerAppException {
		if (request.getRequestStatus() != RequestStatus.UNPROCESSED)
			throw new ServerAppException("Request is processed already");
	}

}
