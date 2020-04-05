package vinkkikone.data_access;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import vinkkikone.domain.Vinkki;

/**
 *
 * @author Samuli Nikkilä
 */
public class MongoVinkkiDao implements VinkkiDao {

    private final String url;

    public MongoVinkkiDao(String url) {
        this.url = url;
    }

    @Override
    public void add(Vinkki vinkki) {
        MongoClient mongoClient = MongoClients.create(url);
        MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
        MongoCollection<Document> haetut = database.getCollection("vinkit");

        Document document = new Document("title", vinkki.getTitle())
                .append("link", vinkki.getLink()); //.append("kategoria", vinkki.getKategoria())
        haetut.insertOne(document);

        mongoClient.close();

    }

    @Override
    public List<Vinkki> listAll() {
        List<Vinkki> palautettava = new ArrayList<>();

        MongoClient mongoClient = MongoClients.create(url);
        MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
        MongoCollection<Document> haetut = database.getCollection("vinkit");

        Block<Document> saveBlock = (final Document document) -> {
            palautettava.add(new Vinkki(document.get("_id", ObjectId.class), document.get("title", String.class), document.get("link", String.class)));
        };
        haetut.find().forEach(saveBlock);
        mongoClient.close();

        return palautettava;
    }

    
    //find by x
    // jos ei löydy palautetaanko null vai tyhjä vinkki ?
    // vai vinkki jolla on error field jossa virheet niin voi näyttää käyttäjälle?

    @Override
    public Vinkki findByTitle(String title) {
        MongoClient mongoClient = MongoClients.create(url);
        MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
        MongoCollection<Document> haetut = database.getCollection("vinkit");
        Document tulos = haetut.find(eq("title", title)).first();
        if (tulos == null) {
            System.out.println("The Vinkki you searched for could not be found.");
            mongoClient.close();
            return null;
        }
        mongoClient.close();
        return new Vinkki(tulos.get("_id", ObjectId.class), tulos.get("title", String.class), tulos.get("link", String.class));
    }
    
    public Vinkki findById(ObjectId id) {
        MongoClient mongoClient = MongoClients.create(url);
        MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
        MongoCollection<Document> haetut = database.getCollection("vinkit");
        Document tulos = haetut.find(eq("_id", id)).first();
        
        if (tulos == null) {
            System.out.println("The Vinkki you searched for could not be found.");
            mongoClient.close();
            return null;
        }
        Vinkki palautettava = new Vinkki(tulos.get("_id", ObjectId.class), tulos.get("title", String.class), tulos.get("link", String.class));
        mongoClient.close();
        return palautettava;
    }
    
    public void update(Vinkki vinkki) {
        if(vinkki.getMongoId() == null) {
            return;
        }
        MongoClient mongoClient = MongoClients.create(url);
        MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
        MongoCollection<Document> haetut = database.getCollection("vinkit");
       
        // tämä on vähän outo BasicDBObjecteineen, mutta toimii
        // TODO upsert (update/insert)
        BasicDBObject paivitys = new BasicDBObject();
        paivitys.append("$set", new BasicDBObject("title", vinkki.getTitle()).append("link", vinkki.getLink()));
        haetut.updateOne(eq("_id",vinkki.getMongoId()), paivitys);

        mongoClient.close();
    }
    
    public void delete(Vinkki vinkki) {
        MongoClient mongoClient = MongoClients.create(url);
        MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
        MongoCollection<Document> haetut = database.getCollection("vinkit");
        
        haetut.deleteOne(eq("_id", vinkki.getMongoId()));
        
        mongoClient.close();
    }

    // https://mongodb.github.io/mongo-java-driver/4.0/driver/tutorials/perform-read-operations/
//    tällä muokattuna etsitään sitten tägit (kts categories):
//    collection.find(
//    new Document("stars", new Document("$gte", 2)
//          .append("$lt", 5))
//          .append("categories", "Bakery")).forEach(printBlock);
    //sama toisin kirjoitettuna:
    //collection.find(and(gte("stars", 2), lt("stars", 5), eq("categories", "Bakery")))
    //        .forEach(printBlock);
}
