package vinkkikone.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Tag {
    
    @Id
    private ObjectId id;
    private String name;
}
