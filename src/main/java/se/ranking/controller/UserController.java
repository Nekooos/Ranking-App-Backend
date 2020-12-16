package se.ranking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.ranking.exception.NotFoundException;
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
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) throws NotFoundException {
        User user;
        try {
            user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch(NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found", e);
        }
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

    @PatchMapping(value = "/patch/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> patchUser(@RequestBody JsonPatch jsonPatch, @PathVariable("id") Long id) {
        try {
            User user = userService.patchUser(jsonPatch, id);
            return ResponseEntity.ok().body(user);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
