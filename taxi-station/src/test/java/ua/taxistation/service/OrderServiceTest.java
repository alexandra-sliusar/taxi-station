package ua.taxistation.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.anyLong;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

import ua.taxistation.dao.CarDao;
import ua.taxistation.dao.DaoConnection;
import ua.taxistation.dao.DaoFactory;
import ua.taxistation.dao.OrderDao;
import ua.taxistation.dao.RequestDao;
import ua.taxistation.dao.UserDao;
import ua.taxistation.data.CarData;
import ua.taxistation.data.RequestData;
import ua.taxistation.entity.Car;
import ua.taxistation.entity.Order;
import ua.taxistation.entity.Request;
import ua.taxistation.entity.enums.OrderStatus;
import ua.taxistation.exceptions.ServerAppException;
import ua.taxistation.services.CarService;
import ua.taxistation.services.OrderService;

public class OrderServiceTest {
	private DaoFactory daoFactory;
	private DaoConnection daoConnection;
	private UserDao userDao;
	private CarDao carDao;
	private RequestDao requestDao;
	private OrderDao orderDao;
	private OrderService orderService;

	private void initMocking() {
		daoFactory = mock(DaoFactory.class);
		daoConnection = mock(DaoConnection.class);
		userDao = mock(UserDao.class);
		carDao = mock(CarDao.class);
		requestDao = mock(RequestDao.class);
		orderDao = mock(OrderDao.class);
		orderService = new OrderService(daoFactory);
	}

	private void initDaoCreationStubbing() {
		when(daoFactory.createOrderDao()).thenReturn(orderDao);
	}

	private void initDaoCreationsWithDaoConnectionStubbing() {
		when(daoFactory.getConnection()).thenReturn(daoConnection);
		when(daoFactory.createUserDao(daoConnection)).thenReturn(userDao);
		when(daoFactory.createCarDao(daoConnection)).thenReturn(carDao);
		when(daoFactory.createRequestDao(daoConnection)).thenReturn(requestDao);
		when(daoFactory.createOrderDao(daoConnection)).thenReturn(orderDao);
	}

	@Test(expected = ServerAppException.class)
	public void testCreateOrderWhenCarIsUnavailable() {
		initMocking();
		initDaoCreationsWithDaoConnectionStubbing();
		when(carDao.getById(anyLong())).thenReturn(Optional.of(CarData.getUnavailableCar()));

		orderService.createOrder(new Long(1), new Long(1));
		verify(daoConnection).begin();
		verify(daoConnection).rollback();
		verify(daoConnection).close();
		verify(carDao).getById(anyLong());
	}

	@Test(expected = ServerAppException.class)
	public void testCreateOrderWhenRequestIsProcessed() {
		initMocking();
		initDaoCreationsWithDaoConnectionStubbing();
		when(carDao.getById(anyLong())).thenReturn(Optional.of(CarData.getCar()));
		when(requestDao.getById(anyLong())).thenReturn(Optional.of(RequestData.getProcessedRequest()));

		orderService.createOrder(new Long(1), new Long(1));
		verify(daoConnection).begin();
		verify(daoConnection).rollback();
		verify(daoConnection).close();
		verify(carDao).getById(anyLong());
		verify(requestDao).getById(anyLong());
	}

	@Test
	public void testCreateOrderSuccess() {
		Car car = CarData.getCar();
		Request request = RequestData.getRequest();
		initMocking();
		initDaoCreationsWithDaoConnectionStubbing();
		when(carDao.getById(anyLong())).thenReturn(Optional.of(car));
		when(requestDao.getById(anyLong())).thenReturn(Optional.of(request));
		orderService.createOrder(new Long(1), new Long(1));
		verify(daoConnection).begin();
		verify(carDao).getById(anyLong());
		verify(requestDao).getById(anyLong());
		verify(orderDao).create(any(Order.class));
		verify(requestDao).update(any(Request.class));
		verify(carDao).update(any(Car.class));
		verify(daoConnection).commit();
		verify(daoConnection).close();
	}
}
