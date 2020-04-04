package vinkkikone.domain;

public class Vinkki {

    private String title;
    private String link;
    private String mongoId;
    private Integer id;

    public Vinkki(String title, String link) {
        this.title = title;
        this.link = link;
    }
    
    public Vinkki(String jsoni) {
        String[] patkat = jsoni.split(", ");
        this.mongoId = patkat[0].substring(18, patkat[0].length() - 2);
        this.title = patkat[1].substring(10, patkat[1].length() - 1);
        this.link = patkat[2].substring(9, patkat[2].length() - 2);
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
    
    public String getMongoId() {
        return this.mongoId;
    }
    
    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }
    
    public String toString() {
        return this.title + ", " + this.link;
    }
}
