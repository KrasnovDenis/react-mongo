package react.mongo.controller;

import org.springframework.web.bind.annotation.*;
import react.mongo.domain.User;
import react.mongo.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {


    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/students")
    public void saveUser( @RequestBody User user) {
        if(user.getKey()== null){
            user.setKey(UUID.randomUUID().toString());
        }
        repository.save(user);
    }


    @GetMapping("/students/{id}")
    @ResponseBody
    public User getUserById(@PathVariable String id) {
        return repository.findById(id).get();
    }

    @GetMapping("/students")
    @ResponseBody
    public List<User> getUserById() {
        return repository.findAll();
    }
    
    @DeleteMapping("/students/{id}")
    public void deleteUserById(@PathVariable String id){
        repository.deleteById(id);
    }
}
