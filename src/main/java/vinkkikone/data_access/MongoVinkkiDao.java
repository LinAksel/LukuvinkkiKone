package vinkkikone.data_access;

import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
 import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import vinkkikone.domain.Vinkki;

/**
 *
 * @author Samuli Nikkilä
 */
public class MongoVinkkiDao implements VinkkiDao {

    private String url;

    public MongoVinkkiDao(String url) {
        this.url = url;
    }
    
    // Valmiiksi tehtynä parametritön konstruktori joka ottaa herokussa (jo siellä olevan) env arvon
    public MongoVinkkiDao() {
        this.url = System.getenv("MONGODB_URI");
    }

    public void add(Vinkki vinkki) {
        MongoClient mongoClient = MongoClients.create(url);
        MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
        MongoCollection<Document> haetut = database.getCollection("vinkit");
        
        Document document = new Document("title", vinkki.getTitle())
                .append("link", vinkki.getLink()); //.append("kategoria", vinkki.getKategoria())
        haetut.insertOne(document);
        
        mongoClient.close();

    }

    public List<Vinkki> listAll() {
        List<Vinkki> palautettava = new ArrayList<>();

        MongoClient mongoClient = MongoClients.create(url);
        MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
        MongoCollection<Document> haetut = database.getCollection("vinkit");

        Block<Document> saveBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                palautettava.add(jsonToVinkki(document.toJson()));
            }
        };
        haetut.find().forEach(saveBlock);
        mongoClient.close();

        return palautettava;
    }

    public Vinkki findByTitle(String title) {
        MongoClient mongoClient = MongoClients.create(url);
        MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
        MongoCollection<Document> haetut = database.getCollection("vinkit");
        
        
        Vinkki palautettava = jsonToVinkki(haetut.find(eq("title", title)).first().toJson()); //otetaan eka, pitäisi olla uniikki title
        
        mongoClient.close();
        return palautettava;
    }

    public Vinkki jsonToVinkki(String jsoni) {
        String[] patkat = jsoni.split(", ");
        return new Vinkki(patkat[1].substring(10, patkat[1].length() - 1), patkat[2].substring(9, patkat[2].length() - 2));
        
        // Tässä on mongon sisäinen id tälle vinkille:
//        this.mongoId = patkat[0].substring(18, patkat[0].length() - 2);

    }
    
    // https://mongodb.github.io/mongo-java-driver/4.0/driver/tutorials/perform-read-operations/
//    tällä muokattuna etsitään sitten tägit:
//    collection.find(
//    new Document("stars", new Document("$gte", 2)
//          .append("$lt", 5))
//          .append("categories", "Bakery")).forEach(printBlock);
    
    //sama toisin kirjoitettuna:
    //collection.find(and(gte("stars", 2), lt("stars", 5), eq("categories", "Bakery")))
    //        .forEach(printBlock);
}
