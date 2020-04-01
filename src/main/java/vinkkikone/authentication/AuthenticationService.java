package vinkkikone.authentication;

import vinkkikone.domain.Vinkki;
import vinkkikone.util.CreationStatus;
import vinkkikone.data_access.VinkkiDao;

public class AuthenticationService {

    private VinkkiDao vinkkiDao;

    public AuthenticationService(VinkkiDao vD) {
        this.vinkkiDao = vD;
    }

    public boolean add(String title, String link) {
        for (Vinkki v : vinkkiDao.listAll()) {
            if (v.getTitle().equals(title)
                    && v.getLink().equals(link)) {
                return true;
            }
        }

        return false;
    }

    public CreationStatus createNew(String title, String link) {
        CreationStatus status = new CreationStatus();        
        
        if (vinkkiDao.findByTitle(title) != null) {
            status.addError("Teokselle on jo luotu vinkki.");
        }

        if (title.length()<3 ) {
            status.addError("Nimikkeen pitää olla vähintään kolme merkkiä pitkä.");
        }

        if (status.isOk()) {
            vinkkiDao.add(new Vinkki(title, link));
        }
        
        return status;
    }

}
