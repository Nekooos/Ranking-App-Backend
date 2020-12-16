package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import se.ranking.exception.NotFoundException;
import se.ranking.model.User;
import se.ranking.model.UserDto;

import java.util.List;

public interface UserService {
    User findById(Long id) throws NotFoundException;
    User save(UserDto user);
    List<User> findAll();
    User edit(Long id, User user) throws Exception;
    User delete(User user);
    User patchUser(JsonPatch jsonPatch, Long id) throws JsonPatchException, JsonProcessingException;
}
