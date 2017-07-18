package ua.taxistation.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import ua.taxistation.dao.CarDao;
import ua.taxistation.dao.DaoConnection;
import ua.taxistation.dao.DaoFactory;
import ua.taxistation.dao.UserDao;
import ua.taxistation.data.CarData;
import ua.taxistation.data.UserData;
import ua.taxistation.entity.Car;
import ua.taxistation.entity.User;
import ua.taxistation.entity.enums.CarCharacteristics;
import ua.taxistation.entity.enums.CarStatus;
import ua.taxistation.exceptions.ServerAppException;
import ua.taxistation.services.CarService;

public class CarServiceTest {
	private DaoFactory daoFactory;
	private DaoConnection daoConnection;
	private UserDao userDao;
	private CarDao carDao;
	private CarService carService;

	private void initMocking() {
		daoFactory = mock(DaoFactory.class);
		daoConnection = mock(DaoConnection.class);
		userDao = mock(UserDao.class);
		carDao = mock(CarDao.class);
		carService = new CarService(daoFactory);
	}

	private void initDaoCreationStubbing() {
		when(daoFactory.createCarDao()).thenReturn(carDao);
	}

	private void initDaoCreationsWithDaoConnectionStubbing() {
		when(daoFactory.getConnection()).thenReturn(daoConnection);
		when(daoFactory.createUserDao(daoConnection)).thenReturn(userDao);
		when(daoFactory.createCarDao(daoConnection)).thenReturn(carDao);
	}

	@Test
	public void testGetAllCars() {
		List<Car> cars = CarData.getAllCars();
		Optional<User> driver = UserData.getOptionalClient();
		initMocking();
		initDaoCreationsWithDaoConnectionStubbing();

		when(carDao.getAll()).thenReturn(cars);
		when(userDao.getById(Matchers.anyLong())).thenReturn(driver);

		List<Car> actualCars = carService.getAllCars();
		assertTrue(cars.containsAll(actualCars));

		verify(daoFactory).getConnection();
		verify(daoFactory).createCarDao(daoConnection);
		verify(daoFactory).createUserDao(daoConnection);
		verify(userDao, atLeastOnce()).getById(driver.get().getId());
		verify(carDao).getAll();
	}

	@Test
	public void testGetCarByValidDriverId() {
		Car car = CarData.getCar();
		User driver = UserData.getDriver();
		initMocking();
		initDaoCreationStubbing();

		when(carDao.getCarByDriver(Matchers.anyLong())).thenReturn(Optional.of(car));
		Car resultCar = carService.getCarByDriver(driver.getId());

		assertEquals(car, resultCar);
		verify(daoFactory).createCarDao();
		verify(carDao).getCarByDriver(resultCar.getDriver().getId());

	}

	@Test(expected = ServerAppException.class)
	public void testGetCarByInvalidDriverId() {
		Optional<Car> car = Optional.empty();
		User driver = UserData.getDriver();
		initMocking();
		initDaoCreationStubbing();

		when(carDao.getCarByDriver(Matchers.anyLong())).thenReturn(car);
		carService.getCarByDriver(driver.getId());

		verify(daoFactory).createCarDao();
		verify(carDao).getCarByDriver(driver.getId());
	}

	@Test
	public void testGetCarByCharacteristicsWithNoneCharacteristic() {
		List<Car> cars = CarData.getAllCars();
		List<Car> availableCars = CarData.getAvailableCars();
		initMocking();
		initDaoCreationStubbing();

		when(carDao.getAll()).thenReturn(cars);
		when(carDao.getCarsByStatus(Matchers.any(CarStatus.class))).thenReturn(availableCars);

		List<Car> actualCars = carService
				.getCarsByCharacteristics(Arrays.asList(new CarCharacteristics[] { CarCharacteristics.NONE }));

		actualCars.removeAll(availableCars);
		assertTrue(actualCars.isEmpty());

		verify(daoFactory).createCarDao();
		verify(carDao).getAll();
		verify(carDao).getCarsByStatus(CarStatus.AVAILABLE);
		verify(carDao, Mockito.never()).getCarsByCharacteristicAndStatus(Mockito.any(CarCharacteristics.class),
				Mockito.any(CarStatus.class));
	}

	@Test
	public void testGetCarByCharacteristicsWithCharacteristics() {
		List<Car> cars = CarData.getAllCars();
		List<Car> carsWithCharacteristics = CarData.getCarsWithCharacteristics();
		initMocking();
		initDaoCreationStubbing();

		when(carDao.getAll()).thenReturn(cars);
		when(carDao.getCarsByStatus(Matchers.any(CarStatus.class))).thenReturn(carsWithCharacteristics);

		List<Car> actualCars = carService
				.getCarsByCharacteristics(Arrays.asList(new CarCharacteristics[] { CarCharacteristics.PREMIUM }));

		actualCars.removeAll(carsWithCharacteristics);
		assertTrue(actualCars.isEmpty());

		verify(daoFactory).createCarDao();
		verify(carDao).getAll();
		verify(carDao, Mockito.never()).getCarsByStatus(Mockito.any(CarStatus.class));
		verify(carDao, Mockito.atLeastOnce()).getCarsByCharacteristicAndStatus(Mockito.any(CarCharacteristics.class),
				Mockito.any(CarStatus.class));
	}

}
