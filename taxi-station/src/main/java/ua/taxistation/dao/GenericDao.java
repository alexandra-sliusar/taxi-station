package ua.taxistation.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<E> {

	Optional<E> getById(Long id);

	List<E> getAll();

	boolean create(E e);

	boolean update(E e);

	boolean delete(Long id);
}
