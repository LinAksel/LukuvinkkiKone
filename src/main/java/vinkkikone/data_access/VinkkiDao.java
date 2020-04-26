package vinkkikone.data_access;

import java.util.List;
import org.bson.types.ObjectId;
import vinkkikone.domain.Vinkki;

public interface VinkkiDao {

    List<Vinkki> listAll();

    Vinkki findByTitle(String title);       //etsitään onko samaa otsikkoa jo käytetty

    Vinkki findById(ObjectId id);
    
    void add(Vinkki vinkki);

    void update(Vinkki vinkki);

    void delete(Vinkki vinkki);
    
    // ETSI toiminnallisuudet

    List<Vinkki> searchByTag(String findme);  //etsi pelkällä tagilla
    
    List<Vinkki> searchByTitle(String title); //etsi pelkällä otsikolla
    
    List<Vinkki> searchByTitleAndTag(String title, String tag); //etsi molemmilla
    
    // LUETTU merkkaukset

    void markAsRead(Vinkki vinkki);
    
    void markAsUnread(Vinkki vinkki);
    
    
}
