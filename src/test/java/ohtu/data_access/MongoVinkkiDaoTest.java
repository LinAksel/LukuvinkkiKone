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
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("mongo.config"));

            String mongoUser = properties.getProperty("user");
            String mongoPW = properties.getProperty("password");
            String mongoURL = properties.getProperty("url");
            this.url = "mongodb+srv://" + mongoUser + ":" + mongoPW + "@" + mongoURL + "/";
            this.md = new MongoVinkkiDao(url, collection);
        } catch (Exception e) {
            System.out.println("Error reading config file:" + e.getMessage());
        }
    }

    @Test
    public void canClearCollection() {
        this.md.clearCollection();
        assertEquals(0, md.listAll().size());
    }

    @Test
    public void canGetEmptyListFromDbWithNoEntries() {
        this.md.clearCollection();
        List<Vinkki> lista = md.listAll();
        assertEquals(0, lista.size());
    }

    @Test
    public void canInsertOneEntryAndListGrows() {
        this.md.clearCollection();
        md.add(new Vinkki("titteli", "linkki", "some kind of description", new ArrayList<>()));
        assertEquals(1, md.listAll().size());
        this.md.clearCollection();
    }

    @Test
    public void canInsertOneEntryAndFindItWithRightInfo() {
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
    public void canInsertOneEntryWithCustomConstructorAndItGetsObjectId() {
        this.md.clearCollection();
        md.add(new Vinkki("titteli", "linkki", "some kind of description", new ArrayList<>()));
        assertEquals(ObjectId.class, md.listAll().get(0).getMongoId().getClass());
        this.md.clearCollection();
    }

    @Test
    public void canInsertMultipleEntriesWithCustomConstructorAndListGrows() {
        this.md.clearCollection();
        md.add(new Vinkki("titteli", "linkki", "some kind of description", new ArrayList<>()));
        md.add(new Vinkki("titteli2", "linkki2", "some kind of description", new ArrayList<>()));
        md.add(new Vinkki("titteli3", "linkki3", "some kind of description", new ArrayList<>()));
        md.add(new Vinkki("titteli4", "linkki4", "some kind of description", new ArrayList<>()));
        assertEquals(4, md.listAll().size());
        this.md.clearCollection();
    }

    @Test
    public void readEntryGetsValidReaddate() {
        this.md.clearCollection();
        md.add(new Vinkki("titteli", "linkki", "some kind of description", new ArrayList<>()));
        Vinkki vinkki = md.listAll().get(0);
        md.markAsRead(vinkki);
        vinkki = md.listAll().get(0);
        assertNotEquals("Ei luettu", vinkki.getReadDate());
    }

    @Test
    public void canInsertOneEntryAndFindItByTitle() {
        this.md.clearCollection();
        md.add(new Vinkki("titteli", "linkki", "some kind of description", new ArrayList<>()));
        assertEquals("titteli", md.findByTitle("titteli").getTitle());
        this.md.clearCollection();
    }

    @Test
    public void canInsertOneEntryAndFindItByTitleWithReadDate() {
        this.md.clearCollection();
        md.add(new Vinkki("titteli", "linkki", "some kind of description", new ArrayList<>()));
        md.markAsRead(md.findByTitle("titteli"));
        assertNotEquals("Ei luettu", md.findByTitle("titteli").getReadDate());
        this.md.clearCollection();
    }

    @Test
    public void canFindById() {
        md.add(new Vinkki("Title", "Link", "Joku kuvaus", new ArrayList<>()));
        Vinkki vinkki = md.listAll().get(0);
        assertEquals(vinkki, md.findById(vinkki.getMongoId()));
    }

    @Test
    public void canInsertOneEntryAndNotFindItByWrongTitle() {
        this.md.clearCollection();
        md.add(new Vinkki("titteli", "linkki", "some kind of description", new ArrayList<>()));
        assertEquals(null, md.findByTitle("linkki"));
        this.md.clearCollection();
    }
}
