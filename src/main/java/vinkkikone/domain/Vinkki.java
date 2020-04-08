package vinkkikone.domain;

import org.bson.types.ObjectId;

public class Vinkki {

    private String title;
    private String link;
    private String description;
    private String tags;
    private String readDate;
    private ObjectId mongoId;

    //Vähin pakollinen tietomäärä, otsikko ja linkki
    public Vinkki(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public Vinkki(String title, String link, String description, String tags, String readDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.tags = tags;
        this.readDate = readDate;
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

    public String getDescription() {
        return description;
    }

    public String getTags() {
        return tags;
    }

    public String getReadDate() {
        return readDate;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setReadDate(String rDate) {
        this.readDate = rDate;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
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
