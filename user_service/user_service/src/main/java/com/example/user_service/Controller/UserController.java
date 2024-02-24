package com.example.user_service.Controller;

import com.example.user_service.Model.User;
import com.example.user_service.Repository.UserRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class UserController {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/createUser")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User"));
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        User userToBeUpdated =  userRepository.findById(id).orElseThrow(() -> new NotFoundException("user"));
        userToBeUpdated.setName(user.getName() != null ? user.getName() :  userToBeUpdated.getName());
        userToBeUpdated.setEmail(user.getEmail() != null ? user.getEmail() : userToBeUpdated.getEmail());
        userToBeUpdated.setPnr(user.getPnr()!= null ? user.getPnr(): userToBeUpdated.getPnr());
        userToBeUpdated.set_kyc_done(user.is_kyc_done());
        return userRepository.save(userToBeUpdated);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }
}