package se.ranking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ranking.exception.NotFoundException;
import se.ranking.model.User;
import se.ranking.model.UserDto;
import se.ranking.model.UserResultsDto;
import se.ranking.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public User save(UserDto userDto) {
        User user = createUserFromUserDto(userDto);
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User edit(Long id, User user) throws Exception {
        User targetUser = this.findById(id);
        BeanUtils.copyProperties(user, targetUser, String.valueOf(id));
        userRepository.save(targetUser);
        return user;
    }

    @Override
    public User delete(User user) {
        userRepository.delete(user);
        return user;
    }

    @Override
    public User patchUser(JsonPatch jsonPatch, Long id) throws JsonPatchException, JsonProcessingException {
        User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(user, JsonNode.class));
        User patchedUser = objectMapper.treeToValue(patched, User.class);

        return userRepository.save(patchedUser);
    }

    @Override
    public List<UserResultsDto> getUserResults(Long id) {
        return userRepository.getUserResults(id);
    }

    private User createUserFromUserDto(UserDto userdto) {
        User user = new User();
        user.setEmail(userdto.getEmail());
        user.setPassword(userdto.getPassword());
        user.setGender(userdto.getGender());
        user.setFirstName(userdto.getFirstName());
        user.setLastName(userdto.getLastName());
        return user;
    }
}
