package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ranking.exception.EntityNotFoundException;
import se.ranking.model.*;
import se.ranking.repository.NotRegisteredUserRepository;
import se.ranking.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    NotRegisteredUserRepository notRegisteredUserRepository;

    @Override
    public RegisteredUser findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User was not found"));
    }

    @Override
    public RegisteredUser save(UserDto userDto) {
        RegisteredUser registeredUser = createUserFromUserDto(userDto);
        return userRepository.save(registeredUser);
    }

    public NotRegisteredUser save(NotRegisteredUser user) {
        boolean userExists = notRegisteredUserRepository.existsById(user.getId());
        if(!userExists) {
            return notRegisteredUserRepository.save(user);
        }
        return user;
    }

    @Override
    public List<RegisteredUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public RegisteredUser edit(Long id, RegisteredUser registeredUser) throws Exception {
        RegisteredUser targetRegisteredUser = this.findById(id);
        BeanUtils.copyProperties(registeredUser, targetRegisteredUser, String.valueOf(id));
        userRepository.save(targetRegisteredUser);
        return registeredUser;
    }

    @Override
    public RegisteredUser delete(RegisteredUser registeredUser) {
        userRepository.delete(registeredUser);
        return registeredUser;
    }

    @Override
    public RegisteredUser patchUser(JsonPatch jsonPatch, Long id) throws JsonPatchException, JsonProcessingException {
        RegisteredUser registeredUser = this.findById(id);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(registeredUser, JsonNode.class));
        RegisteredUser patchedRegisteredUser = objectMapper.treeToValue(patched, RegisteredUser.class);

        return userRepository.save(patchedRegisteredUser);
    }

    @Override
    public List<UserResultsDto> getUserResults(Long id) {
        return userRepository.getUserResults(id);
    }

    @Override
    public NotRegisteredUser saveNotRegisteredUser(NotRegisteredUser user) {
        return notRegisteredUserRepository.save(user);
    }

    private RegisteredUser createUserFromUserDto(UserDto userdto) {
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setEmail(userdto.getEmail());
        registeredUser.setPassword(userdto.getPassword());
        registeredUser.setGender(userdto.getGender());
        registeredUser.setFirstName(userdto.getFirstName());
        registeredUser.setLastName(userdto.getLastName());
        return registeredUser;
    }
}
