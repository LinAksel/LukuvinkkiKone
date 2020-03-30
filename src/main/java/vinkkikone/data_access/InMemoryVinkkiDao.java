package vinkkikone.data_access;

import vinkkikone.domain.Vinkki;
import java.util.ArrayList;
import java.util.List;

public class InMemoryVinkkiDao implements VinkkiDao {

    private List<Vinkki> vinkit;

    public InMemoryVinkkiDao() {
        vinkit = new ArrayList<Vinkki>();
        Vinkki u = new Vinkki();
        
        vinkit.add(new Vinkki("Paroni von Münchhausen", "http://www.gutenberg.org/ebooks/48623"));
    }        

    @Override
    public List<Vinkki> listAll() {
        return vinkit;
    }

    @Override
    public Vinkki findByTitle(String name) {
        for (Vinkki user : vinkit) {
            if (user.getTitle().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void add(Vinkki user) {
        vinkit.add(user);
    }

    public void setUsers(List<Vinkki> users) {
        this.vinkit = users;
    }

    public List<Vinkki> getUsers() {
        return vinkit;
    }
}
