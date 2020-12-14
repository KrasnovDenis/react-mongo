package react.mongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import react.mongo.domain.Group;
import react.mongo.domain.User;
import react.mongo.repository.GroupRepository;
import react.mongo.repository.UserRepository;
import react.mongo.utils.TransformUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

@RestController
public class GroupController {

    private final GroupRepository repository;
    private final UserRepository studentRepository;
    private final TransformUtils transformUtils;

    public GroupController(GroupRepository repository, UserRepository studentRepository, TransformUtils transformUtils) {
        this.repository = repository;
        this.studentRepository = studentRepository;
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

    @PutMapping("/groups/{groupId}&{studentId}")
    public void addStudentToGroup(@PathVariable String groupId, @PathVariable String studentId) {
        Group group = repository.findByKey(groupId);
        User student = studentRepository.findByKey(studentId);
        Set<User> set = group.getUsers();
        set.add(student);
        group.setUsers(set);
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

    @DeleteMapping("/groups/{groupId}&{studentId}")
    public void deleteStudentFromGroup(@PathVariable String groupId, @PathVariable String studentId){
        Group group = repository.findByKey(groupId);
        User student = studentRepository.findByKey(studentId);
        Set<User> set = group.getUsers();
        set.remove(student);
        group.setUsers(set);
        repository.save(group);
    }

    @GetMapping("/groups")
    @ResponseBody
    public List<Group> getAllGroups() {
        return repository.findAll();
    }
}
