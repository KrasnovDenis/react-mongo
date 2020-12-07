package react.mongo.domain;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data @Builder
public class User {

  private @Id @Default String key = UUID.randomUUID().toString();
  private String name;
  private String doubt;
  private String timing;
  private Double averageGrade;
}
