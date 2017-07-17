package ua.taxistation.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.taxistation.dao.CarDao;
import ua.taxistation.dao.DaoConnection;
import ua.taxistation.dao.DaoFactory;
import ua.taxistation.dao.UserDao;
import ua.taxistation.entity.Car;
import ua.taxistation.entity.enums.CarCharacteristics;
import ua.taxistation.entity.enums.CarStatus;
import ua.taxistation.exceptions.ServerAppException;

public class CarService {
	private static final Logger LOGGER = LogManager.getLogger(CarService.class);

	private DaoFactory daoFactory;

	CarService(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static class Holder {
		static final CarService INSTANCE = new CarService(DaoFactory.getDaoFactory());
	}

	public static CarService getInstance() {
		return Holder.INSTANCE;
	}

	public List<Car> getCarsByCharacteristics(List<CarCharacteristics> characteristics) {
		Set<Car> cars = new HashSet<>();
		try (CarDao carDao = daoFactory.createCarDao()) {
			cars.addAll(carDao.getAll());
			if (!characteristics.contains(CarCharacteristics.NONE)) {
				for (CarCharacteristics carCharacteristic : characteristics) {
					cars.retainAll(carDao.getCarsByCharacteristicAndStatus(carCharacteristic, CarStatus.AVAILABLE));
				}
			}

		}
		return new ArrayList<Car>(cars);
	}

	public Car updateCarStatus(Car car) {
		try (CarDao carDao = daoFactory.createCarDao()) {
			carDao.update(car);
			Optional<Car> optionalCar = carDao.getById(car.getId());
			isCarExist(optionalCar);
			return optionalCar.get();
		} catch (Exception e) {
			LOGGER.error("Updating car status has failed " + car.getId(), e);
			throw new ServerAppException("Updating car status has failed " + car.getId());
		}

	}

	public Car getCarByDriver(Long driverId) {
		try (CarDao carDao = daoFactory.createCarDao()) {
			Optional<Car> car = carDao.getCarByDriver(driverId);
			isCarExist(car);
			return car.get();
		} catch (Exception e) {
			LOGGER.error("Getting car by driver id " + driverId + " has failed", e);
			throw new ServerAppException("Getting car by driver id " + driverId + " has failed");
		}
	}

	public List<Car> getAllCars() {
		try (DaoConnection connection = daoFactory.getConnection()) {
			CarDao carDao = daoFactory.createCarDao(connection);
			UserDao userDao = daoFactory.createUserDao(connection);
			Set<Car> cars = new HashSet<>();
			cars.addAll(carDao.getAll());
			for (Car car : cars) {
				car.setDriver(userDao.getById(car.getDriver().getId()).get());
			}
			return new ArrayList<Car>(cars);
		} catch (Exception e) {
			LOGGER.error("Getting all cars has failed", e);
			throw new ServerAppException("Getting all cars has failed");
		}
	}

	private void isCarExist(Optional<Car> car) throws ServerAppException {
		if (!car.isPresent())
			throw new ServerAppException("Car doesn`t exist");
	}
}
