package se.ranking.service;

import se.ranking.exception.NotFoundException;
import se.ranking.model.Qualifier;
import se.ranking.model.User;
import se.ranking.model.UserDto;

import java.util.List;

public interface UserService {
    User findById(Long id) throws NotFoundException;
    User save(UserDto user);
    List<User> findAll();
    User edit(Long id, User user) throws Exception;
    User delete(User user);
}
