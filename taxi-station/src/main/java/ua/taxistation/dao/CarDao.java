package ua.taxistation.dao;

import java.util.List;
import java.util.Optional;

import ua.taxistation.entity.Car;
import ua.taxistation.entity.enums.CarCharacteristics;
import ua.taxistation.entity.enums.CarStatus;

public interface CarDao extends GenericDao<Car>, AutoCloseable {

	public List<Car> getCarsByCharacteristicAndStatus(CarCharacteristics characteristics, CarStatus carStatus);

	public List<Car> getCarsByStatus(CarStatus carStatus);

	public Optional<Car> getCarByDriver(Long driverId);

	void close();
}
