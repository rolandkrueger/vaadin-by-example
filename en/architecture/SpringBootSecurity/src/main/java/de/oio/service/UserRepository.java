package de.oio.service;

import de.oio.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Roland Krüger
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);
}
