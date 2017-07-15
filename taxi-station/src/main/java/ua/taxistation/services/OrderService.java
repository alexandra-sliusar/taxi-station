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
			CarDao carDao = daoFactory.createCarDao(connection);
			orders.addAll(orderDao.getOrdersByUserId(user.getId()));
			for (Order order: orders)
			{
				order.setRequest(requestDao.getById(order.getRequest().getId()).get());
				order.setCar(carDao.getById(order.getCar().getId()).get());
			}
		}
		return orders;
	}

	public Order createOrder(Request request, Car car) {
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			OrderDao orderDao = daoFactory.createOrderDao(connection);
			RequestDao requestDao = daoFactory.createRequestDao(connection);
			CarDao carDao = daoFactory.createCarDao(connection);
			Order order = new Order.Builder().setCar(car).setRequest(request).setOrderStatus(OrderStatus.INCOMPLETE)
					.build();
			orderDao.create(order);
			request.setRequestStatus(RequestStatus.PROCESSED);
			car.setCarStatus(CarStatus.UNAVAILABLE);
			requestDao.update(request);
			carDao.update(car);
			connection.commit();
			return order;
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

	public Order getLastCarOrder(Car car) {
		try (OrderDao orderDao = daoFactory.createOrderDao()) {
			Optional<Order> order = orderDao.getLastCarOrder(car.getId());
			isOrderExist(order);
			return order.get();
		} catch (Exception e) {
			LOGGER.error("Getting last order of car " + car.getId() + " has failed", e);
			throw new ServerAppException("Getting last order of car " + car.getId() + " has failed");
		}
	}

	private void isOrderExist(Optional<Order> order) throws ServerAppException {
		if (!order.isPresent())
			throw new ServerAppException("Order doesn`t exist");
	}
}
