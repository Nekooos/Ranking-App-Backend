package se.ranking.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ranking.model.User;
import se.ranking.model.UserDto;
import se.ranking.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(Exception::new);
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
        return user;
    }

    @Override
    public User delete(User user) {
        userRepository.delete(user);
        return user;
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
