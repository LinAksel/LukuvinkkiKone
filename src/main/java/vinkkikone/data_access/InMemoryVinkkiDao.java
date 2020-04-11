//package vinkkikone.data_access;
//
//import vinkkikone.domain.Vinkki;
//import java.util.ArrayList;
//import java.util.List;
//import org.bson.types.ObjectId;
//
//public class InMemoryVinkkiDao implements VinkkiDao {
//
//    private List<Vinkki> vinkit;
//
//    public InMemoryVinkkiDao() {
//        vinkit = new ArrayList<Vinkki>();
//        vinkit.add(new Vinkki("Paroni von Münchhausen", "http://www.gutenberg.org/ebooks/48623", "Kommentti", "tagi,tagi,tagi", "Ei luettu"));
//    }
//
//    @Override
//    public List<Vinkki> listAll() {
//        return vinkit;
//    }
//
//    @Override
//    public Vinkki findByTitle(String name) {
//        for (Vinkki v : vinkit) {
//            if (v.getTitle().equals(name)) {
//                return v;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public void add(Vinkki v) {
//        vinkit.add(v);
//    }
//
//    public void setAll(List<Vinkki> all) {
//        this.vinkit = all;
//    }
//
//    public List<Vinkki> getAll() {
//        return vinkit;
//    }
//    
//    public Vinkki findById(ObjectId id) {
//        return null;
//    }
//    
//    public void update(Vinkki vinkki) {
//        return;
//    }
//    
//    public void delete(Vinkki vinkki) {
//        vinkit.remove(vinkki);
//    }
//}
