package se.ranking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.ranking.exception.EntityNotFoundException;
import se.ranking.model.User;
import se.ranking.model.UserDto;
import se.ranking.model.UserResultsDto;
import se.ranking.service.UserService;
import se.ranking.service.UtilService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UtilService utilService;

    @GetMapping("/user-results/{id}")
    public ResponseEntity<?> getUserResults(@PathVariable("id") Long id) {
        List<UserResultsDto> userResults = userService.getUserResults(id);
        return ResponseEntity.ok(userResults);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDto user) {
        User savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) throws EntityNotFoundException {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/put")
    public ResponseEntity<?> editUser(@RequestBody User user, @PathVariable("id") Long id) throws Exception {
        User editedUser = userService.edit(id, user);
        return ResponseEntity.ok().body(editedUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody User user) {
        userService.delete(user);
        return ResponseEntity.ok().body(user.getFirstName() + " " + user.getLastName() + " was deleted");
    }

    @PatchMapping(value = "/patch/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> patchUser(@RequestBody JsonPatch jsonPatch, @PathVariable("id") Long id) throws JsonPatchException, JsonProcessingException {
        User user = userService.patchUser(jsonPatch, id);
        return ResponseEntity.ok().body(user);

    }
}
