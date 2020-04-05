package vinkkikone.domain;

import org.bson.types.ObjectId;

public class Vinkki {

    private String title;
    private String link;
    private ObjectId mongoId;
    //private Integer id;

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
    
    // FIXME jätin nämä tähän jos tarvitaan jotain näistä vielä
    
    public ObjectId getMongoId() {
        return this.mongoId;
    }

    public void setMongoId(ObjectId mongoId) {
        this.mongoId = mongoId;
    }


//    public Vinkki(String jsoni) {
//        String[] patkat = jsoni.split(", ");
//        this.mongoId = patkat[0].substring(18, patkat[0].length() - 2);
//        this.title = patkat[1].substring(10, patkat[1].length() - 1);
//        this.link = patkat[2].substring(9, patkat[2].length() - 2);
//    }


}
