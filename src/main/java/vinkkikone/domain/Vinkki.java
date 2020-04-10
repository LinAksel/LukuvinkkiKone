package vinkkikone.domain;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;

public class Vinkki {

    private String title;
    private String link;
    private String description;
    private List<String>tagsList; 
    private String readDate;
    private ObjectId mongoId;

    //tähän muutettava tagsList
    public Vinkki(String title, String link, String description, List<String>tagsList, String readDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.tagsList = tagsList;
        this.readDate = readDate;
    }

    public Vinkki(String title, String link, String description, String tagsList, String readDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.tagsList = Arrays.asList(tagsList.split(","));
        this.readDate = readDate;
    }

    public Vinkki(ObjectId mongoId, String title, String link) {
        this.mongoId = mongoId;
        this.title = title;
        this.link = link;
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

    public List<String> getTags() {
        return tagsList;
    }

    public String getReadDate() {
        return readDate;
    }
    public void setTags(String tags){

        tagsList=Arrays.asList(tags.split(","));
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
