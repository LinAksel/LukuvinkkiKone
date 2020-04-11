package vinkkikone.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vinkki implements Serializable {

    @Id
    private ObjectId mongoid;
    private String title;
    private String link;
    private String description;
    private List<String> tagsList;
    //tämä arvo voi olla null tietokannassa
    private String readDate;
    //tämä arvo ei voi olla koskaan null edes tietokannassa
    private String creationDate;

    //ylimääränen konstruktori authservicelle
    public Vinkki(String title, String link, String description, List<String> tagsList) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.tagsList = tagsList;
    }
}
