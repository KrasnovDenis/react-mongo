package react.mongo.controller;

import org.springframework.web.bind.annotation.*;
import react.mongo.domain.Group;
import react.mongo.domain.User;
import react.mongo.repository.GroupRepository;
import react.mongo.repository.UserRepository;
import react.mongo.utils.TransformUtils;

import java.util.*;

@RestController
public class GroupController {
    public static final String DEFAULT_GROUP_ID = "0bd01274-8a22-40bd-8131-a5b37a2be82f";
    private static boolean isDefaultGroupExists = false;
    private final GroupRepository repository;
    private final UserRepository studentRepository;
    private final TransformUtils transformUtils;

    public GroupController(GroupRepository repository, UserRepository studentRepository, TransformUtils transformUtils) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.transformUtils = transformUtils;

        if (!this.repository.existsById(DEFAULT_GROUP_ID)) {
            Group defaultGroup = Group.builder()
                    .key(DEFAULT_GROUP_ID)
                    .name("Вне группы")
                    .courseLevel("отсутствует")
                    .direction("Оставшиеся юзеры")
                    .users(new ArrayList<>())
                    .build();

            this.repository.save(defaultGroup);

        }

        isDefaultGroupExists = true;
    }

    @PostMapping("/groups")
    public void saveGroup(@RequestBody LinkedHashMap<String, Object> entity) {
        Group group = transformUtils.JSON2GroupTransform(entity, false);
        repository.save(group);
    }

    @PutMapping("/groups")
    public void updateGroup(@RequestBody LinkedHashMap<String, Object> entity) {
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
        List<Group> groupList = repository.findAll();
        for(Group g : groupList){
            if(g.getUsers().contains(student)){
                Set<User> listUsers = g.getUsers();
                listUsers.remove(student);
                g.setUsers(listUsers);
                repository.save(g);
            }
        }
        repository.save(group);

    }

    @DeleteMapping("/groups/{key}")
    public void deleteGroup(@PathVariable String key) {
        if(key.equals(DEFAULT_GROUP_ID)){
            return ;
        }
        repository.deleteByKey(key);
    }


    @GetMapping("/groups/{key}")
    @ResponseBody
    public Group getGroupById(@PathVariable String key) {
        return repository.findById(key).get();
    }

    @DeleteMapping("/groups/{groupId}&{studentId}")
    public void deleteStudentFromGroup(@PathVariable String groupId, @PathVariable String studentId) {
        Group group = repository.findByKey(groupId);
        User student = studentRepository.findByKey(studentId);
        Set<User> set = group.getUsers();
        set.remove(student);
        group.setUsers(set);
        addStudentToGroup(DEFAULT_GROUP_ID,student.getKey());
        repository.save(group);
    }

    @GetMapping("/groups")
    @ResponseBody
    public List<Group> getAllGroups() {
        return repository.findAll();
    }
}
