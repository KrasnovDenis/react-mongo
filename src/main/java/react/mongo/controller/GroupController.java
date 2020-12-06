package react.mongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import react.mongo.domain.Group;
import react.mongo.repository.GroupRepository;
import react.mongo.utils.TransformUtils;

import java.util.LinkedHashMap;

@RestController
public class GroupController {

    @Autowired
    private final GroupRepository repository;
    private final TransformUtils transformUtils;

    public GroupController(GroupRepository repository, TransformUtils transformUtils) {
        this.repository = repository;
        this.transformUtils = transformUtils;
    }

    @PostMapping("/groups")
    public void saveGroup( @RequestBody LinkedHashMap<String, Object> entity) {
        Group group = transformUtils.JSON2GroupTransform(entity, false);
        repository.save(group);
    }

    @PutMapping("/groups")
    public void updateGroup( @RequestBody LinkedHashMap<String, Object> entity) {
        Group group = transformUtils.JSON2GroupTransform(entity, true);
        repository.save(group);
    }

    @DeleteMapping("/groups/{key}")
    public void deleteGroup( @PathVariable String key ) {
        repository.deleteByKey(key);
    }


    @GetMapping("/groups/{key}")
    @ResponseBody
    public Group getGroupById(@PathVariable String key) {
        return repository.findById(key).get();
    }


}
