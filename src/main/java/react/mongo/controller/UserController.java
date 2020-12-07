package react.mongo.controller;

import org.springframework.web.bind.annotation.*;
import react.mongo.domain.User;
import react.mongo.repository.UserRepository;

import java.util.UUID;

@RestController
public class UserController {


    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/users")
    public void saveUser( @RequestBody User user) {
        if(user.getKey()== null){
            user.setKey(UUID.randomUUID().toString());
        }
        repository.save(user);
    }


    @GetMapping("/users/{id}")
    @ResponseBody
    public User getUserById(@PathVariable String id) {
        return repository.findById(id).get();
    }
}
