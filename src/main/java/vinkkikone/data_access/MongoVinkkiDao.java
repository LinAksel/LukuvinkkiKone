package vinkkikone.data_access;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.currentDate;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import org.bson.Document;
import org.bson.types.ObjectId;
import vinkkikone.domain.Vinkki;

public class MongoVinkkiDao implements VinkkiDao {

    // date muunnokset:
    // https://www.baeldung.com/java-string-to-date
    // https://www.baeldung.com/java-date-to-localdate-and-localdatetime <---!!!!
    // ja teinkin stringinä =D
    private final String url;

    public MongoVinkkiDao(String url) {
        this.url = url;
    }

    @Override
    public void add(Vinkki vinkki) {
        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
            MongoCollection<Document> haetut = database.getCollection("vinkit");

            Document document = new Document("title", vinkki.getTitle()).append("link", vinkki.getLink())
                    .append("description", vinkki.getDescription()).append("tags", vinkki.getTagsList());

            haetut.insertOne(document);
            haetut.updateOne(eq("title", vinkki.getTitle()), currentDate("creationDate"));

            mongoClient.close();
        } catch (Exception e) {
            System.out.println("Error creating Vinkki: " + e.getMessage());
        }

    }

    @Override
    public List<Vinkki> listAll() {
        List<Vinkki> palautettava = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
            MongoCollection<Document> haetut = database.getCollection("vinkit");

            haetut.find().forEach((Consumer<Document>) doc -> {
                String paivays = "Ei luettu";
                if (doc.get("readdate") != null) {
                    paivays = doc.get("readdate", Date.class).toString();
                }
                palautettava.add(new Vinkki(doc.get("_id", ObjectId.class), doc.get("title", String.class),
                        doc.get("link", String.class), doc.get("description", String.class),
                        doc.getList("tags", String.class), paivays, doc.get("creationDate", Date.class)));
                // LocalDateTime.ofInstant(doc.get("readdate", Date.class).toInstant(),
                // ZoneId.systemDefault())));
            });
            mongoClient.close();
        } catch (Exception e) {
            System.out.println("Error listing all Vinkki: " + e.getMessage());
        }
        Collections.sort(palautettava);
        return palautettava;
    }

    @Override
    public Vinkki findByTitle(String title) {

        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
            MongoCollection<Document> haetut = database.getCollection("vinkit");
            Document document = haetut.find(eq("title", title)).first();
            if (document == null) {
                mongoClient.close();
                return null;
            }
            String paivays = "Ei luettu";
            if (document.get("readdate") != null) {
                paivays = document.get("readdate", Date.class).toString();
            }
            return new Vinkki(document.get("_id", ObjectId.class), document.get("title", String.class),
                    document.get("link", String.class), document.get("description", String.class),
                    document.getList("tags", String.class), paivays, document.get("creationDate", Date.class));
            // LocalDateTime.ofInstant(document.get("readdate", Date.class).toInstant(),
            // ZoneId.systemDefault()));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;

    }

    @Override
    public Vinkki findById(ObjectId id) {
        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
            MongoCollection<Document> haetut = database.getCollection("vinkit");
            Document document = haetut.find(eq("_id", id)).first();

            if (document == null) {
                // System.out.println("The Vinkki you searched for could not be found."); // tää
                // laukee jo uniikkiuden tarkistuksessa
                mongoClient.close();
                return null;
            }
            mongoClient.close();
            String paivays = "Ei luettu";
            if (document.get("readdate") != null) {
                paivays = document.get("readdate", Date.class).toString();
            }
            Vinkki palautettava = new Vinkki(document.get("_id", ObjectId.class), document.get("title", String.class),
                    document.get("link", String.class), document.get("description", String.class),
                    document.getList("tags", String.class), paivays, document.get("creationDate", Date.class));
            // LocalDateTime.ofInstant(document.get("readdate", Date.class).toInstant(),
            // ZoneId.systemDefault()));
            return palautettava;
        } catch (Exception e) {
            System.out.println("Error in findById: " + e.getMessage());
        }
        return null;

    }

    // UPDATE jättää creationDaten koskematta

    @Override
    public void update(Vinkki vinkki) {
        if (vinkki.getMongoId() == null) {
            return;
        }
        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
            MongoCollection<Document> haetut = database.getCollection("vinkit");

            haetut.updateOne(eq("_id", vinkki.getMongoId()),
                    combine(set("title", vinkki.getTitle()), set("link", vinkki.getLink()),
                            set("description", vinkki.getDescription()), set("tags", vinkki.getTagsList())));
        } catch (Exception e) {
            System.out.println("Error updating: " + e.getMessage());
        }
    }

    @Override
    public void delete(Vinkki vinkki) {
        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
            MongoCollection<Document> haetut = database.getCollection("vinkit");

            haetut.deleteOne(eq("_id", vinkki.getMongoId()));

            mongoClient.close();
        } catch (Exception e) {
            System.out.println("Error deleting vinkki :" + e.getMessage());
        }

    }

    @Override
    public List<Vinkki> findByTag(String findme) {
        List<Vinkki> palautettava = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
            MongoCollection<Document> haetut = database.getCollection("vinkit");

            haetut.find(eq("tags", findme)).forEach((Consumer<Document>) doc -> {
                String paivays = "Ei luettu";
                if (doc.get("readdate") != null) {
                    paivays = doc.get("readdate", Date.class).toString();
                }
                palautettava.add(new Vinkki(doc.get("_id", ObjectId.class), doc.get("title", String.class),
                        doc.get("link", String.class), doc.get("description", String.class),
                        doc.getList("tags", String.class), paivays, doc.get("creationDate", Date.class)
                // ,LocalDateTime.ofInstant(doc.get("readdate", Date.class).toInstant(),
                // ZoneId.systemDefault())));
                ));
            });
            mongoClient.close();
        } catch (Exception e) {
            System.out.println("Error in findByTag: " + e.getMessage());
        }
        return palautettava;
    }

    @Override
    public void markAsRead(Vinkki vinkki) {
        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
            MongoCollection<Document> haetut = database.getCollection("vinkit");

            haetut.updateOne(eq("_id", vinkki.getMongoId()), currentDate("readdate"));

        } catch (Exception e) {
            System.out.println("Error in markAsRead: " + e.getMessage());
        }
    }

    // https://mongodb.github.io/mongo-java-driver/4.0/driver/tutorials/perform-read-operations/
    // tällä muokattuna etsitään sitten tägit (kts categories):
    // collection.find(
    // new Document("stars", new Document("$gte", 2)
    // .append("$lt", 5))
    // .append("categories", "Bakery")).forEach(printBlock);
    // sama toisin kirjoitettuna:
    // collection.find(and(gte("stars", 2), lt("stars", 5), eq("categories",
    // "Bakery")))
    // .forEach(printBlock);
}
