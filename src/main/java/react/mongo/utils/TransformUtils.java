package react.mongo.utils;

import org.springframework.stereotype.Service;
import react.mongo.domain.Group;
import react.mongo.domain.User;
import react.mongo.repository.GroupRepository;
import react.mongo.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class TransformUtils {
    private final UserRepository userRepo;

    private final GroupRepository groupRepo;

    public TransformUtils(UserRepository userRepo, GroupRepository groupRepo) {
        this.userRepo = userRepo;
        this.groupRepo = groupRepo;
    }

    public Group JSON2GroupTransform(LinkedHashMap<String, Object> jsonGroup, boolean isUpdate) {
        String groupName = (String) jsonGroup.get("name");
        String direction = (String) jsonGroup.get("direction");
        String courseLevel = (String) jsonGroup.get("courseLevel");
        Object users = jsonGroup.get("users");
        String key =  (String) jsonGroup.get("key");

        List<User> userList = new ArrayList<>();
        try {
            if (!isCollection(users)) {
                throw new RuntimeException("Is not collection");
            }
            List<Object> list = (ArrayList<Object>) users;
            for (Object i : list) {
                userList.add(JSON2UserTransform(i));
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        Group group = groupRepo.findByKey(key);

        if(group!= null && isUpdate){
            return Group.builder().key(key).name(groupName)
                    .courseLevel(courseLevel)
                    .direction(direction)
                    .users(userList)
                    .build();
        }

        return Group.builder().name(groupName)
                .courseLevel(courseLevel)
                .direction(direction)
                .users(userList)
                .build();

    }

    private User JSON2UserTransform(Object obj) {
        LinkedHashMap<String, Object> user = new LinkedHashMap<>();
        try {
            user = (LinkedHashMap<String, Object>) obj;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return JSON2UserTransform(user);
    }

    private User JSON2UserTransform(LinkedHashMap<String, Object> jsonGroup) {
        String id = (String) jsonGroup.get("key");
        User user = userRepo.findByKey(id);

        if(user == null){
            throw new RuntimeException("User not found");
        }

        return user;
    }

    private static boolean isCollection(Object obj) {
        return obj.getClass().isArray() || obj instanceof Collection;
    }


}
