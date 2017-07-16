package ua.taxistation.dao;

import java.util.List;
import java.util.Optional;

import ua.taxistation.entity.Order;
import ua.taxistation.entity.enums.OrderStatus;

public interface OrderDao extends GenericDao<Order>, AutoCloseable {

	public List<Order> getOrdersByStatus(OrderStatus orderStatus);

	public List<Order> getOrdersByUserId(Long userId);
	
	public List<Order> getOrdersByDriverId(Long userId);

	public Optional<Order> getLastCarOrder(Long carId);

	void close();
}
