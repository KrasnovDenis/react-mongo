package react.mongo.domain;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Singular;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import react.mongo.event.Cascade;

import java.util.Set;
import java.util.UUID;

@Data @Builder
public class Group {

  private  @Id @Default String key = UUID.randomUUID().toString();
  private String name;
  private String courseLevel;
  private String direction;
  private @DBRef @Cascade
  @Singular Set<User> users;
}
