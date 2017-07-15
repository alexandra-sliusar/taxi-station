package ua.taxistation.entity;

import java.io.Serializable;

import ua.taxistation.entity.enums.OrderStatus;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Request request;
	private Car car;
	private OrderStatus orderStatus = OrderStatus.INCOMPLETE;

	public Order() {

	}

	public static class Builder {
		protected Order order = new Order();

		public Builder() {
		}

		public Builder setId(Long id) {
			order.id = id;
			return this;
		}

		public Builder setRequest(Request request) {
			order.request = request;
			return this;
		}

		public Builder setCar(Car car) {
			order.car = car;
			return this;
		}

		public Builder setOrderStatus(OrderStatus orderStatus) {
			order.orderStatus = orderStatus;
			return this;
		}

		public Order build() {
			return order;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result += prime * result + ((car == null) ? 0 : car.hashCode());
		result += prime * result + ((request == null) ? 0 : request.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (car == null) {
			if (other.car != null)
				return false;
		} else if (!car.equals(other.car))
			return false;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", request=" + request + ", car=" + car + ", orderStatus=" + orderStatus + "]";
	}
}
