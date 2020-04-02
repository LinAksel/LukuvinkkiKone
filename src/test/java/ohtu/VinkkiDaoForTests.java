
package ohtu;

import java.util.ArrayList;
import java.util.List;
import vinkkikone.domain.Vinkki;
import vinkkikone.data_access.VinkkiDao;

public class VinkkiDaoForTests implements VinkkiDao {

    private List<Vinkki> vinkit;

    public VinkkiDaoForTests() {
        this.vinkit = new ArrayList<>();
    }

    @Override
    public List<Vinkki> listAll() {
        return vinkit;
    }

    @Override
    public Vinkki findByTitle(String title) {
        for (Vinkki v : vinkit) {
            if (v.getTitle().equals(title)) {
                return v;
            }
        }
        return null;
    }

    @Override
    public void add(Vinkki vinkki) {
        vinkit.add(vinkki);
    }

    public void setUsers(List<Vinkki> vinkit) {
        this.vinkit = vinkit;
    }

    public List<Vinkki> getVinkit() {
        return vinkit;
    }
}