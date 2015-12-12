package de.oio.service;

import de.oio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

/**
 * @author Roland Krüger
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Secured("ROLE_ADMIN")
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
