package ohtu.data_access;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.bson.types.ObjectId;
import org.junit.Test;
import static org.junit.Assert.*;
import vinkkikone.data_access.MongoVinkkiDao;
import vinkkikone.domain.Vinkki;

public class MongoVinkkiDaoTest {

    private String collection = "vinkit-test";
    private String url;
    private MongoVinkkiDao md;

    public MongoVinkkiDaoTest() throws Exception {
        url = System.getenv("MONGODB_URI");
        if (url == null) {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream("mongo.config"));

                String mongoUser = properties.getProperty("user");
                String mongoPW = properties.getProperty("password");
                String mongoURL = properties.getProperty("url");

                this.url = "mongodb+srv://" + mongoUser + ":" + mongoPW + "@" + mongoURL + "/";
            } catch (Exception e) {
                System.out.println("Error reading config file:" + e.getMessage());
            }
        }
        this.md = new MongoVinkkiDao(url, collection);

    }

    @Test
    public void mongoCanClearCollection() {
        this.md.clearCollection();
        assertEquals(0, md.listAll().size());
        this.md.clearCollection();
    }

    @Test
    public void mongoCanGetEmptyListFromDbWithNoEntries() {
        this.md.clearCollection();
        List<Vinkki> lista = md.listAll();
        assertEquals(0, lista.size());
        this.md.clearCollection();
    }

    @Test
    public void mongoCanInsertOneEntryAndListGrows() {
        this.md.clearCollection();
        md.add(new Vinkki("titteli", "linkki", "some kind of description", new ArrayList<>()));
        assertEquals(1, md.listAll().size());
        this.md.clearCollection();
    }

    @Test
    public void mongoCanInsertOneEntryAndFindItWithRightInfo() {
        this.md.clearCollection();
        md.add(new Vinkki("titteli", "linkki", "some kind of description", new ArrayList<>()));
        List<Vinkki> lista = md.listAll();
        Vinkki tarkasteltava = lista.get(0);
        assertEquals("titteli", tarkasteltava.getTitle());
        assertEquals("linkki", tarkasteltava.getLink());
        assertEquals("some kind of description", tarkasteltava.getDescription());
        assertEquals(0, tarkasteltava.getTagsList().size());
        this.md.clearCollection();
    }

    @Test
    public void mongoCanInsertOneEntryWithCustomConstructorAndItGetsObjectId() {
        this.md.clearCollection();
        md.add(new Vinkki("titteli", "linkki", "some kind of description", new ArrayList<>()));
        assertEquals(ObjectId.class, md.listAll().get(0).getMongoId().getClass());
        this.md.clearCollection();
    }

    @Test
    public void mongoCanInsertMultipleEntriesWithCustomConstructorAndListGrows() {
        this.md.clearCollection();
        md.add(new Vinkki("titteli", "linkki", "some kind of description", new ArrayList<>()));
        md.add(new Vinkki("titteli2", "linkki2", "some kind of description", new ArrayList<>()));
        md.add(new Vinkki("titteli3", "linkki3", "some kind of description", new ArrayList<>()));
        md.add(new Vinkki("titteli4", "linkki4", "some kind of description", new ArrayList<>()));
        assertEquals(4, md.listAll().size());
        this.md.clearCollection();
    }

    @Test
    public void mongoReadEntryGetsValidReaddate() {
        this.md.clearCollection();
        md.add(new Vinkki("titteli", "linkki", "some kind of description", new ArrayList<>()));
        Vinkki vinkki = md.listAll().get(0);
        md.markAsRead(vinkki);
        vinkki = md.listAll().get(0);
        assertNotEquals("Ei luettu", vinkki.getReadDate());
        this.md.clearCollection();
    }

    @Test
    public void mongoCanInsertOneEntryAndFindItByTitle() {
        this.md.clearCollection();
        md.add(new Vinkki("titteli", "linkki", "some kind of description", new ArrayList<>()));
        assertEquals("titteli", md.findByTitle("titteli").getTitle());
        this.md.clearCollection();
    }

    @Test
    public void mongoCanInsertOneEntryAndFindItByTitleWithReadDate() {
        this.md.clearCollection();
        md.add(new Vinkki("titteli", "linkki", "some kind of description", new ArrayList<>()));
        md.markAsRead(md.findByTitle("titteli"));
        assertNotEquals("Ei luettu", md.findByTitle("titteli").getReadDate());
        this.md.clearCollection();
    }

    @Test
    public void mongoCanFindById() {
        this.md.clearCollection();
        md.add(new Vinkki("Title", "Link", "Joku kuvaus", new ArrayList<>()));
        Vinkki vinkki = md.listAll().get(0);
        assertEquals(vinkki, md.findById(vinkki.getMongoId()));
        this.md.clearCollection();
    }
    @Test
    public void mongoFindByIdReturnsNullIfNotFound() {
        this.md.clearCollection();
        md.add(new Vinkki("Title", "Link", "Joku kuvaus", new ArrayList<>()));
        assertEquals(null, md.findById(new ObjectId("507f191e810c19729de860ea")));
        this.md.clearCollection();
    }

    @Test
    public void mongoCanInsertOneEntryAndNotFindItByWrongTitle() {
        this.md.clearCollection();
        md.add(new Vinkki("titteli", "linkki", "some kind of description", new ArrayList<>()));
        assertEquals(null, md.findByTitle("linkki"));
        this.md.clearCollection();
    }

    @Test
    public void mongoUpdateChangesValues() {
        this.md.clearCollection();
        md.add(new Vinkki("titteli", "linkki", "some kind of description", new ArrayList<>()));
        Vinkki haettu = md.findByTitle("titteli");
        haettu.setLink("uusilinkki");
        haettu.setTitle("uusititteli");

        // update vaatii id:n!!
        md.update(haettu);
        haettu = md.findByTitle("uusititteli");

        assertEquals("uusititteli", haettu.getTitle());
        assertEquals("uusilinkki", haettu.getLink());
        assertEquals(null, md.findByTitle("titteli"));
        this.md.clearCollection();
    }

    @Test
    public void mongoUpdateReturnsWithMissingId() {
        this.md.clearCollection();
        Vinkki v = new Vinkki("titteli", "linkki", "some kind of description", new ArrayList<>());
        md.add(v);
        v.setTitle("new title");
        md.update(v);
        assertEquals(null, md.findByTitle("new title"));
        this.md.clearCollection();
    }

    @Test
    public void mongoSearchByTitleAndTagReturnsEmptyListIfNotFound() {
        this.md.clearCollection();
        md.add(new Vinkki("joku titteli", "linkki", "some kind of description", new ArrayList<>()));
        //System.out.println(this.md.searchByTitleAndTag("ei", "loydy"));
        List<Vinkki> vertailtava = new ArrayList<>();
        assertEquals(vertailtava, this.md.searchByTitleAndTag("ei", "loydy"));
        this.md.clearCollection();
    }

    @Test
    public void mongoSearchByTitleAndTagReturnsRightVinkkiIfFound() {
        this.md.clearCollection();
        List<String> lista = new ArrayList<>();
        lista.add("tagi");

        md.add(new Vinkki("joku titteli", "linkki", "some kind of description", new ArrayList<>()));
        md.add(new Vinkki("joku titteli2", "linkki2", "some kind of description", lista));
        md.add(new Vinkki("joku titteli3", "linkki3", "some kind of description", lista));
        lista.add("etsi");
        Vinkki v = new Vinkki("joku tittelein", "linkein", "some kind of description", lista);
        md.add(v);
        assertNotNull(this.md.searchByTitleAndTag("joku", "etsi"));
        this.md.clearCollection();
    }
    @Test
    public void mongoSearchByTagReturnsEmptyListIfNotFound() {
        this.md.clearCollection();
        md.add(new Vinkki("joku titteli", "linkki", "some kind of description", new ArrayList<>()));
        List<Vinkki> vertailtava = new ArrayList<>();
        assertEquals(vertailtava, this.md.searchByTag("eiloydy"));
        this.md.clearCollection();
    }

    @Test
    public void mongoSearchByTagReturnsRightVinkkiIfFound() {
        this.md.clearCollection();
        List<String> lista = new ArrayList<>();
        lista.add("tagi");

        md.add(new Vinkki("joku titteli", "linkki", "some kind of description", new ArrayList<>()));
        md.add(new Vinkki("joku titteli2", "linkki2", "some kind of description", lista));
        md.add(new Vinkki("joku titteli3", "linkki3", "some kind of description", lista));
        lista.add("etsi");
        Vinkki v = new Vinkki("joku tittelein", "linkein", "some kind of description", lista);
        md.add(v);
        List<Vinkki> vertailtava = new ArrayList<>();
        assertNotEquals(vertailtava, this.md.searchByTag("etsi"));
        this.md.clearCollection();
    }
    @Test
    public void mongoSearchByTagReturnsMultipleMatches() {
        this.md.clearCollection();
        List<String> lista = new ArrayList<>();
        lista.add("tagi");

        md.add(new Vinkki("joku titteli", "linkki", "some kind of description", new ArrayList<>()));
        md.add(new Vinkki("joku titteli2", "linkki2", "some kind of description", lista));
        lista.add("etsi");
        md.add(new Vinkki("joku titteli3", "linkki3", "some kind of description", lista));
        Vinkki v = new Vinkki("joku tittelein", "linkein", "some kind of description", lista);
        md.add(v);
        List<Vinkki> vertailtava = new ArrayList<>();
        assertEquals(2, this.md.searchByTag("etsi").size());
        this.md.clearCollection();
    }
    @Test
    public void mongoSearchByTitleReturnsEmptyListIfNotFound() {
        this.md.clearCollection();
        md.add(new Vinkki("joku titteli", "linkki", "some kind of description", new ArrayList<>()));
        List<Vinkki> vertailtava = new ArrayList<>();
        assertEquals(vertailtava, this.md.searchByTitle("eiloydy"));
        this.md.clearCollection();
    }

    @Test
    public void mongoSearchByTitleReturnsRightVinkkiIfFound() {
        this.md.clearCollection();
        List<String> lista = new ArrayList<>();
        lista.add("tagi");

        md.add(new Vinkki("joku titteli", "linkki", "some kind of description", new ArrayList<>()));
        md.add(new Vinkki("joku titteli2", "linkki2", "some kind of description", lista));
        md.add(new Vinkki("joku titteli3", "linkki3", "some kind of description", lista));
        lista.add("etsi");
        Vinkki v = new Vinkki("joku tittelein", "linkein", "some kind of description", lista);
        md.add(v);
        List<Vinkki> vertailtava = new ArrayList<>();
        assertNotEquals(vertailtava, this.md.searchByTitle("tittelein"));
        this.md.clearCollection();
    }
    @Test
    public void mongoSearchByTitleReturnsMultipleMatches() {
        this.md.clearCollection();
        List<String> lista = new ArrayList<>();
        lista.add("tagi");

        md.add(new Vinkki("joku titteli", "linkki", "some kind of description", new ArrayList<>()));
        md.add(new Vinkki("joku titteli2", "linkki2", "some kind of description", lista));
        lista.add("etsi");
        md.add(new Vinkki("joku titteli3", "linkki3", "some kind of description", lista));
        Vinkki v = new Vinkki("joku tittelein", "linkein", "some kind of description", lista);
        md.add(v);
        //System.out.println(this.md.searchByTitle("joku"));
        assertEquals(4, this.md.searchByTitle("joku").size());
        this.md.clearCollection();
    }
    
    @Test
    public void mongoMarkAsUnreadWorksWhenDocumentExists() {
        this.md.clearCollection();
        List<String> lista = new ArrayList<>();
        lista.add("tagi");
        md.add(new Vinkki("joku titteli", "linkki", "some kind of description", lista));
        assertEquals("Ei luettu", md.findByTitle("joku titteli").getReadDate());
        md.markAsRead(md.findByTitle("joku titteli"));
        assertNotEquals("Ei luettu", md.findByTitle("joku titteli").getReadDate());
        md.markAsUnread(md.findByTitle("joku titteli"));
        assertEquals("Ei luettu", md.findByTitle("joku titteli").getReadDate());
    }

}
