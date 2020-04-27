package ohtu;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

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

    @Override
    public Vinkki findById(ObjectId id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Vinkki vinkki) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(Vinkki vinkki) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Vinkki> searchByTag(String findme) {
        if (findme.isEmpty()) {
            return vinkit;
        }
        List<Vinkki> lista = new ArrayList<>();
        for (Vinkki v : vinkit) {
            for (String tag : v.getTagsList()) {
                if (tag.equals(findme)) {
                    lista.add(v);
                }
            }
        }
        return lista;
    }

    @Override
    public void markAsRead(Vinkki vinkki) {
        // TODO Auto-generated method stub

    }

    @Override
    public void markAsUnread(Vinkki vinkki) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Vinkki> searchByTitleAndTag(String title, String tag) {
        return null;
    }

    @Override
    public List<Vinkki> searchByTitle(String title) {
        return null;
    }
}
