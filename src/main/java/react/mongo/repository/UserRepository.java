package react.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import react.mongo.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByKey(String key);
}
