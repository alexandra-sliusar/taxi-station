package ua.taxistation.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;

import ua.taxistation.dao.DaoConnection;
import ua.taxistation.dao.DaoFactory;
import ua.taxistation.dao.RequestDao;
import ua.taxistation.data.RequestData;
import ua.taxistation.entity.Request;
import ua.taxistation.services.RequestService;

public class RequestServiceTest {
	private DaoFactory daoFactory;
	private DaoConnection daoConnection;
	private RequestDao requestDao;
	private RequestService requestService;

	private void initMocking() {
		daoFactory = mock(DaoFactory.class);
		daoConnection = mock(DaoConnection.class);
		requestDao = mock(RequestDao.class);
		requestService = new RequestService(daoFactory);
	}

	private void initDaoCreationStubbing() {
		when(daoFactory.createRequestDao()).thenReturn(requestDao);
	}

	@Test
	public void testGetByIdWithValidId() {
		initMocking();
		initDaoCreationStubbing();
		Optional<Request> request = Optional.of(RequestData.getRequest());

		when(requestDao.getById(anyLong())).thenReturn(request);

		Optional<Request> actualRequest = requestService.getRequestById(new Long(1));
		assertEquals(actualRequest.get(), request.get());

		verify(daoFactory).createRequestDao();
		verify(requestDao).getById(anyLong());

	}
	
	@Test
	public void testGetByIdWithInvalidId() {
		initMocking();
		initDaoCreationStubbing();
		Optional<Request> request = Optional.empty();

		when(requestDao.getById(anyLong())).thenReturn(request);

		Optional<Request> actualRequest = requestService.getRequestById(new Long(1));
		assertEquals(actualRequest, request);

		verify(daoFactory).createRequestDao();
		verify(requestDao).getById(anyLong());

	}

}
