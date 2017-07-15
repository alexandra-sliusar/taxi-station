package ua.taxistation.dao;

import java.util.List;

import ua.taxistation.entity.Request;
import ua.taxistation.entity.enums.CarCharacteristics;
import ua.taxistation.entity.enums.RequestStatus;

public interface RequestDao extends GenericDao<Request>, AutoCloseable {

	public List<Request> getRequestsByStatus(RequestStatus requestStatus);

	void close();
}
