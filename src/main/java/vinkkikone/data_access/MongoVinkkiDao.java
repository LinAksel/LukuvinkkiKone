package vinkkikone.data_access;

import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import vinkkikone.domain.Vinkki;

/**
 *
 * @author karpo
 */
public class MongoVinkkiDao implements VinkkiDao {

    private String url;

    public MongoVinkkiDao(String url) {
        this.url = url;
    }

    public void add(Vinkki vinkki) {

    }

    public List<Vinkki> listAll() {
        List<Vinkki> palautettava = new ArrayList<>();

        MongoClient mongoClient = MongoClients.create(url);
        MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
        MongoCollection<Document> haetut = database.getCollection("vinkit");

        Block<Document> saveBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                palautettava.add(new Vinkki(document.toJson()));
            }
        };
        haetut.find().forEach(saveBlock);
        
        mongoClient.close();
//        for(Vinkki vinkki : palautettava) {
//            System.out.println(vinkki);
//        }

        return palautettava;
    }

    public Vinkki findByTitle(String title) {
        return null;
    }

    public void testi() {

        MongoClient mongoClient = MongoClients.create(url);
        MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
        MongoCollection<Document> haetut = database.getCollection("vinkit");

        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
                System.out.println("seuraava");
            }
        };
        haetut.find().forEach(printBlock);
        mongoClient.close();

    }
}
