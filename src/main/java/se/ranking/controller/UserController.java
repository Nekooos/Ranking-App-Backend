package se.ranking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.ranking.exception.EntityNotFoundException;
import se.ranking.model.*;
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

    @PostMapping("/save-not-registered")
    public ResponseEntity<?> saveNotRegisteredUser(@Valid @RequestBody NotRegisteredUserDto user) {
        RegisteredUser registeredUser = userService.saveNotRegisteredUserDto(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user-results/{id}")
    public ResponseEntity<?> getUserResults(@PathVariable("id") Long id) {
        List<UserResultsDto> userResults = userService.getUserResults(id);
        return ResponseEntity.ok(userResults);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDto user) {
        RegisteredUser savedRegisteredUser = userService.save(user);
        return ResponseEntity.ok(savedRegisteredUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) throws EntityNotFoundException {
        RegisteredUser registeredUser = userService.findById(id);
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<RegisteredUser> registeredUsers = userService.findAll();
        return ResponseEntity.ok(registeredUsers);
    }

    @PutMapping("/put")
    public ResponseEntity<?> editUser(@RequestBody RegisteredUser registeredUser, @PathVariable("id") Long id) throws Exception {
        RegisteredUser editedRegisteredUser = userService.edit(id, registeredUser);
        return ResponseEntity.ok().body(editedRegisteredUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody RegisteredUser registeredUser) {
        userService.delete(registeredUser);
        return ResponseEntity.ok().body(registeredUser.getFirstName() + " " + registeredUser.getLastName() + " was deleted");
    }

    @PatchMapping(value = "/patch/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> patchUser(@RequestBody JsonPatch jsonPatch, @PathVariable("id") Long id) throws JsonPatchException, JsonProcessingException {
        RegisteredUser registeredUser = userService.patchUser(jsonPatch, id);
        return ResponseEntity.ok().body(registeredUser);

    }
}
