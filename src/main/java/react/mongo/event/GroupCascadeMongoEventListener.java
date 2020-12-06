package react.mongo.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.util.ReflectionUtils;
import react.mongo.domain.Group;

import java.util.Objects;

public class GroupCascadeMongoEventListener extends AbstractMongoEventListener<Group> {

  private @Autowired MongoOperations mongoOperations;
  private Group deletedGroup;

  public @Override void onBeforeSave(BeforeSaveEvent<Group> event) {
    final Object source = event.getSource();
    ReflectionUtils.doWithFields(source.getClass(), new CascadeSaveCallback(source, mongoOperations));
  }

  public @Override void onBeforeDelete(BeforeDeleteEvent<Group> event) {
    final Object id = Objects.requireNonNull(event.getDocument()).get("_id");
    deletedGroup = mongoOperations.findById(id, Group.class);
  }

  public @Override void onAfterDelete(AfterDeleteEvent<Group> event) {
    ReflectionUtils.doWithFields(Group.class, new CascadeDeleteCallback(deletedGroup, mongoOperations));
  }
}
