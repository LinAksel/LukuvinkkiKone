package vinkkikone.authentication;

import vinkkikone.domain.Vinkki;
import java.util.List;
import java.util.ArrayList;
import vinkkikone.util.CreationStatus;
import vinkkikone.data_access.VinkkiDao;

public class AuthenticationService {

    private VinkkiDao vinkkiDao;

    public AuthenticationService(VinkkiDao vD) {
        this.vinkkiDao = vD;
    }

    // Tää metodi kaipaa vielä statuserror-käsittelyä createNew-tyylisesti!
    public List<Vinkki> getList() {
        return vinkkiDao.listAll();
    }

    public CreationStatus createNew(String title, String link, String description, String tags, String readDate) {
        if (description == null || description.isEmpty()) {
            description = "Kuvausta ei ole annettu ";
        }
        if (tags == null) {
            tags = "Tägejä ei ole annettu.";
        }
        if (readDate == null) {
            readDate = "Ei luettu";
        }
        CreationStatus status = new CreationStatus();

        if (vinkkiDao.findByTitle(title) != null) {
            status.addError("Teokselle on jo luotu vinkki!");
            return status;
        }

        if (title.length() < 3) {
            status.addError("Nimen pitää olla vähintään kolme merkkiä pitkä!");
            return status;
        }

        if (link == null) {
            status.addError("Vinkillä pitää olla myös linkki!");
            return status;
        }

        if (!link.contains("www.") && !link.contains("https://")) {
            status.addError("Anna linkki oikeassa muodossa! https://... tai www...");
            return status;
        }

        if (!tags.contains("Tägejä ei ole annettu.") && (tags.contains(" ") && (!tags.contains(",")))) {
            status.addError("Tägit pitää erottaa pilkulla! Käytä vain yksisanaisia tägejä.");
            return status;
        }

        if (status.isOk()) {
            status.addNote("Lisääminen onnistui!");
            Vinkki v = new Vinkki(title, link, description, tags, readDate);
            vinkkiDao.add(v);
        }

        return status;
    }
}
