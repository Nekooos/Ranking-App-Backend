package se.ranking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.ranking.model.Qualifier;
import se.ranking.model.User;
import se.ranking.model.UserDto;
import se.ranking.service.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody UserDto user) {
        User savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) throws Exception {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/put")
    public ResponseEntity<?> editResult(@RequestBody User user, @PathVariable("id") Long id) throws Exception {
        User editedUser = userService.edit(id, user);
        return ResponseEntity.ok().body(editedUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody User user) {
        userService.delete(user);
        return ResponseEntity.ok().body(user.getFirstName() + " " + user.getLastName() + " was deleted");
    }
}
