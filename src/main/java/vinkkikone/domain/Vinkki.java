package vinkkikone.domain;

import org.bson.types.ObjectId;

public class Vinkki {

    private String title;
    private String link;
    private ObjectId mongoId;
 
    public Vinkki(String title, String link) {
        this.title = title;
        this.link = link;
    }
    
    public Vinkki(ObjectId mongoId, String title, String link) {
        this.mongoId = mongoId;
        this.title = title;
        this.link = link;
    }

    public Vinkki() {
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        return this.title + ", " + this.link;
    }
    
    public ObjectId getMongoId() {
        return this.mongoId;
    }

    public void setMongoId(ObjectId mongoId) {
        this.mongoId = mongoId;
    }
}
