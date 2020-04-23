package vinkkikone.data_access;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.currentDate;
import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Updates.unset;

import java.util.ArrayList;
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
    private final String url;
    private final String collection;

    public MongoVinkkiDao(String url) {
        this(url, "vinkit");
    }

    public MongoVinkkiDao(String url, String collection) {
        this.url = url;
        this.collection = collection;
    }

    @Override
    public void add(Vinkki vinkki) {
        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
            MongoCollection<Document> haetut = database.getCollection(collection);

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
            MongoCollection<Document> haetut = database.getCollection(collection);

            haetut.find().sort(new Document("creationDate", -1)).forEach((Consumer<Document>) doc -> {
                String paivays = "Ei luettu";
                if (doc.get("readdate") != null) {
                    paivays = doc.get("readdate", Date.class).toString();
                }
                palautettava.add(new Vinkki(doc.get("_id", ObjectId.class), doc.get("title", String.class),
                        doc.get("link", String.class), doc.get("description", String.class),
                        doc.getList("tags", String.class), paivays, doc.get("creationDate", Date.class)));
            });
            mongoClient.close();
        } catch (Exception e) {
            System.out.println("Error listing all Vinkki: " + e.getMessage());
        }
        return palautettava;
    }

    @Override
    public Vinkki findByTitle(String title) {

        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
            MongoCollection<Document> haetut = database.getCollection(collection);
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
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;

    }

    public List<Vinkki> searchByTitle(String title) {
        List<Vinkki> palautettava = new ArrayList<>();
//        try (MongoClient mongoClient = MongoClients.create(url)) {
//            MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
//            MongoCollection<Document> haetut = database.getCollection(collection);
//            Document document = haetut.find(eq("title", title)).first();
//            if (document == null) {
//                mongoClient.close();
//                return null;
//            }
//            String paivays = "Ei luettu";
//            if (document.get("readdate") != null) {
//                paivays = document.get("readdate", Date.class).toString();
//            }
//            return new Vinkki(document.get("_id", ObjectId.class), document.get("title", String.class),
//                    document.get("link", String.class), document.get("description", String.class),
//                    document.getList("tags", String.class), paivays, document.get("creationDate", Date.class));
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//        }
        return palautettava;

    }

    public List<Vinkki> searchByTitleAndTag(String title, String tag) {
        List<Vinkki> palautettava = new ArrayList<>();
//        try (MongoClient mongoClient = MongoClients.create(url)) {
//            MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
//            MongoCollection<Document> haetut = database.getCollection(collection);
//            Document document = haetut.find(eq("title", title)).first();
//            if (document == null) {
//                mongoClient.close();
//                return null;
//            }
//            String paivays = "Ei luettu";
//            if (document.get("readdate") != null) {
//                paivays = document.get("readdate", Date.class).toString();
//            }
//            return new Vinkki(document.get("_id", ObjectId.class), document.get("title", String.class),
//                    document.get("link", String.class), document.get("description", String.class),
//                    document.getList("tags", String.class), paivays, document.get("creationDate", Date.class));
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//        }
        return palautettava;

    }

    @Override
    public Vinkki findById(ObjectId id) {
        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
            MongoCollection<Document> haetut = database.getCollection(collection);
            Document document = haetut.find(eq("_id", id)).first();

            if (document == null) {
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
            MongoCollection<Document> haetut = database.getCollection(collection);

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
            MongoCollection<Document> haetut = database.getCollection(collection);

            haetut.deleteOne(eq("_id", vinkki.getMongoId()));

            mongoClient.close();
        } catch (Exception e) {
            System.out.println("Error deleting vinkki :" + e.getMessage());
        }

    }

    @Override
    public List<Vinkki> searchByTag(String findme) {
        List<Vinkki> palautettava = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
            MongoCollection<Document> haetut = database.getCollection(collection);

            haetut.find(eq("tags", findme)).forEach((Consumer<Document>) doc -> {
                String paivays = "Ei luettu";
                if (doc.get("readdate") != null) {
                    paivays = doc.get("readdate", Date.class).toString();
                }
                palautettava.add(new Vinkki(doc.get("_id", ObjectId.class), doc.get("title", String.class),
                        doc.get("link", String.class), doc.get("description", String.class),
                        doc.getList("tags", String.class), paivays, doc.get("creationDate", Date.class)));
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
            MongoCollection<Document> haetut = database.getCollection(collection);

            haetut.updateOne(eq("_id", vinkki.getMongoId()), currentDate("readdate"));

        } catch (Exception e) {
            System.out.println("Error in markAsRead: " + e.getMessage() + " with title: " + vinkki.getTitle());
        }
    }

    @Override
    public void markAsUnread(Vinkki vinkki) {
        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
            MongoCollection<Document> haetut = database.getCollection(collection);

            haetut.updateOne(eq("_id", vinkki.getMongoId()), unset("readdate"));

        } catch (Exception e) {
            System.out.println("Error in markAsUnread: " + e.getMessage() + " with title: " + vinkki.getTitle());
        }
    }

    // erityisesti testaukseen, poistaa kaikki, ei vinkkiDao kautta vaan suoraan
    public void clearCollection() {
        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
            MongoCollection<Document> haetut = database.getCollection(collection);
            haetut.deleteMany(new Document());
        }
    }

    // hakee ja palauttaa kokonaisen vinkin nimen perusteella
    @Override
    public Vinkki getByTitle(String title) {
        Vinkki v = new Vinkki();
        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
            MongoCollection<Document> haetut = database.getCollection(collection);
            Document document = haetut.find(eq("title", title)).first();
            if (document == null) {
                mongoClient.close();
                return null;
            }
            String paivays = null;
            if (document.get("readdate") != null) {
                paivays = document.get("readdate", Date.class).toString();
            }
            v = new Vinkki(document.get("_id", ObjectId.class), document.get("title", String.class),
                    document.get("link", String.class), document.get("description", String.class),
                    document.getList("tags", String.class), paivays, document.get("creationDate", Date.class));
            v.setReadDate(paivays);
            mongoClient.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return v;
    }
}
