/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinkkikone.data_access;

import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.List;
import org.bson.Document;
import vinkkikone.domain.Vinkki;

/**
 *
 * @author karpo
 */
public class MongoVinkkiDao implements VinkkiDao {

    public void add(Vinkki vinkki) {

    }

    public List<Vinkki> listAll() {
        return null;
    }

    public Vinkki findByTitle(String title) {
        return null;
    }

    public void testi() {

        MongoClient mongoClient = MongoClients.create("mongodb+srv://lukuvinkkikone:ayvvIMF9K74gjoHV@einekluster-y4sht.mongodb.net/");
        //System.out.println("yhteys muodostettu");
        MongoDatabase database = mongoClient.getDatabase("lukuvinkkikone");
        //System.out.println("vinkit haettu");
//        System.out.println("tässä collectionit:");
//        for (String name : database.listCollectionNames()) {
//            System.out.println(name);
//        }
        MongoCollection<Document> haetut = database.getCollection("vinkit");
        System.out.println("KOKO :" + haetut.countDocuments());
        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.toJson());
                System.out.println("seuraava");
            }
        };
        haetut.find().forEach(printBlock);

    }
}
