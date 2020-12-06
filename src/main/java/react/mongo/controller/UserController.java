package react.mongo.controller;

import org.springframework.web.bind.annotation.*;
import react.mongo.domain.User;
import react.mongo.repository.UserRepository;

@RestController
public class UserController {


    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/users")
    public void saveUser( @RequestBody User user) {
        repository.save(user);
    }


    @GetMapping("/users/{id}")
    @ResponseBody
    public User getUserById(@PathVariable String id) {
        return repository.findById(id).get();
    }
}
