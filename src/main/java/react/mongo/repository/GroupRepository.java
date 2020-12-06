package react.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import react.mongo.domain.Group;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {
    Group findByKey(String key);
    Group deleteByKey(String key);
}
