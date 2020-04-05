
package vinkkikone.data_access;

import java.util.List;
import org.bson.types.ObjectId;
import vinkkikone.domain.Vinkki;

public interface VinkkiDao {
    List<Vinkki> listAll();
    Vinkki findByTitle(String title);
    Vinkki findById(ObjectId id);
    void add(Vinkki vinkki);
    void update(Vinkki vinkki);
    void delete(Vinkki vinkki);
    
}
