package vinkkikone.authentication;

import vinkkikone.domain.Vinkki;
import java.util.List;
import java.util.Arrays;
import vinkkikone.util.CreationStatus;
import vinkkikone.data_access.VinkkiDao;

public class AuthenticationService {

    private final VinkkiDao vinkkiDao;

    public AuthenticationService(VinkkiDao vD) {
        this.vinkkiDao = vD;
    }

    // Tää metodi kaipaa vielä statuserror-käsittelyä createNew-tyylisesti!
    public List<Vinkki> getList() {
        return vinkkiDao.listAll();
    }

    public CreationStatus createNew(String title, String link, String description, String tags) {
//        if (readDate == null) {
//            readDate = "Ei luettu";
//        }

        CreationStatus status = new CreationStatus();

        if (description == null || description.isEmpty()) {
            status.addError("Kuvausta ei ole annettu.");
            return status;
        }

        if (tags == null || tags.isEmpty()) {
            status.addError("Tägejä ei ole annettu.");
            return status;
        }

        if (vinkkiDao.findByTitle(title) != null) {
            status.addError("Teokselle on jo luotu vinkki!");
            return status;
        }

        if (title.length() < 3) {
            status.addError("Nimen pitää olla vähintään kolme merkkiä pitkä!");
            return status;
        }

        if (link == null || link.isEmpty()) {
            status.addError("Vinkillä pitää olla myös linkki!");
            return status;
        }

        if (!link.contains("www.") && !link.contains("https://")) {
            status.addError("Anna linkki oikeassa muodossa! https://... tai www...");
            return status;
        }

        if (!tags.contains("Tägejä ei ole annettu.") && (tags.contains(" ") && (!tags.contains(",")))) {
            status.addError("Anna tägejä enemmän kuin yksi. Tägit pitää erottaa pilkulla! Käytä vain yksisanaisia tägejä.");
            return status;
        }

        if (status.isOk()) {
            status.addNote("Lisääminen onnistui!");
            Vinkki v = new Vinkki(title, link, description, Arrays.asList(tags.split(",")));
            vinkkiDao.add(v);
        }

        return status;
    }
}
