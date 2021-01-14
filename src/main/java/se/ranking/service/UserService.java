package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import se.ranking.exception.EntityNotFoundException;
import se.ranking.model.*;

import java.util.List;

public interface UserService {
    RegisteredUser findById(Long id) throws EntityNotFoundException;
    RegisteredUser save(UserDto user);
    List<RegisteredUser> findAll();
    RegisteredUser edit(Long id, RegisteredUser registeredUser) throws Exception;
    RegisteredUser delete(RegisteredUser registeredUser);
    RegisteredUser patchUser(JsonPatch jsonPatch, Long id) throws JsonPatchException, JsonProcessingException;
    List<UserResultsDto> getUserResults(Long id);
    RegisteredUser saveNotRegisteredUserDto(NotRegisteredUserDto user);
}
