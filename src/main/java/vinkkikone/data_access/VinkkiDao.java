
package vinkkikone.data_access;

import java.util.List;
import vinkkikone.domain.Vinkki;

public interface VinkkiDao {
    List<Vinkki> listAll();
    Vinkki findByTitle(String title);
    void add(Vinkki vinkki);
}
