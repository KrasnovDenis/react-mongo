package react.mongo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import react.mongo.event.GroupCascadeMongoEventListener;

@EnableMongoRepositories(MongoConfiguration.REPOSITORY_PACKAGE)
public @Configuration class MongoConfiguration {

  static final String REPOSITORY_PACKAGE = "react.mongo.repository";

  public @Bean
  GroupCascadeMongoEventListener cascadeMongoEventListener() {
    return new GroupCascadeMongoEventListener();
  }

  public @Bean MongoCustomConversions customConversions() {
    return new MongoCustomConversions(ZonedDateTimeConverters.getConvertersToRegister());
  }
}
