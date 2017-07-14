package ua.taxistation.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ua.taxistation.controller.dto.RequestDto;
import ua.taxistation.dao.DaoConnection;
import ua.taxistation.dao.DaoFactory;
import ua.taxistation.dao.RequestDao;
import ua.taxistation.entity.Request;
import ua.taxistation.entity.User;
import ua.taxistation.entity.enums.CarCharacteristics;
import ua.taxistation.entity.enums.RequestStatus;

public class RequestService {

	private DaoFactory daoFactory;

	RequestService(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private static class Holder {
		static final RequestService INSTANCE = new RequestService(DaoFactory.getDaoFactory());
	}

	public static RequestService getInstance() {
		return Holder.INSTANCE;
	}

	public List<Request> getUnprocessedRequests() {
		List<Request> requests = new ArrayList<>();
		try (DaoConnection connection = daoFactory.getConnection()) {
			connection.begin();
			RequestDao requestDao = daoFactory.createRequestDao(connection);
			requests.addAll(requestDao.getRequestsByStatus(RequestStatus.UNPROCESSED));
			connection.commit();
		}
		return requests;
	}

	public void updateRequestStatus(Request request) {
		try (RequestDao requestDao = daoFactory.createRequestDao()) {
			requestDao.update(request);
		}
	}

	public Request createRequest(RequestDto requestDto, User client) {
		String pickup = requestDto.getPickup();
		String destination = requestDto.getDestination();
		Set<CarCharacteristics> carCharacteristics = requestDto.getCarCharacteristics();

		try (RequestDao requestDao = daoFactory.createRequestDao()) {
			Request request = new Request.Builder().setUser(client).setDateOfRequest(LocalDateTime.now())
					.setPickup(pickup).setDestination(destination).setCarCharacteristics(carCharacteristics)
					.setRequestStatus(RequestStatus.UNPROCESSED).build();
			requestDao.create(request);
			return request;
		}
	}
}
