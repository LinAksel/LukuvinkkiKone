import static org.junit.Assert.assertEquals;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import vinkkikone.domain.Vinkki;



public class VinkkiTest{

    Vinkki vinkki;

    @Before
    public void setup(){
        vinkki=new Vinkki();
    }

    @Test
    public void newVinkkiWithMongoId(){
        Vinkki vinkki = new Vinkki(new ObjectId("111111111111111111111111"), "Ronja ryövärintytär", "www.helsinki.fi");
        assertEquals(vinkki.getMongoId(),new ObjectId("111111111111111111111111"));
        assertEquals(vinkki.getTitle(),"Ronja ryövärintytär");
        assertEquals(vinkki.getLink(), "www.helsinki.fi");
    }

    @Test
    public void setMongoIdSetsMongoId(){
        vinkki.setMongoId(new ObjectId("111111111111111111111111"));

        assertEquals(vinkki.getMongoId(), new ObjectId("111111111111111111111111"));
    }


    @Test
    public void setLinkSetsLink(){
        vinkki.setLink("www.helsinki.fi");

        assertEquals("www.helsinki.fi", vinkki.getLink());
    }


    @Test
    public void setTitleSetsTitle(){
        vinkki.setTitle("Ronja ryövärintytär");

        assertEquals("Ronja ryövärintytär", vinkki.getTitle());
    }

    @Test 
    public void toStringIsGivingTheRightString(){
        vinkki.setTitle("Ronja ryövärintytär");
        vinkki.setLink("www.helsinki.fi");

        assertEquals("Ronja ryövärintytär, www.helsinki.fi", vinkki.toString());
    }

    @Test
    public void setDescriptionSetsDescription(){
        vinkki.setDescription("Kirjan avulla voit löytää sisäisen ryövärisi");

        assertEquals("Kirjan avulla voit löytää sisäisen ryövärisi", vinkki.getDescription());

    }
    
    //Tämä testi on nyt String muotoisille tageille. Oletettavasti muutetaan kun tagit muuttavat muotoaan
    @Test
    public void setTagsSetsTags(){
        vinkki.setTags("testi, kokeilu, mitä, tapahtuu");

        assertEquals("testi, kokeilu, mitä, tapahtuu", vinkki.getTags());

    }
//testi string muotoiselle päivämäärälle, muokattaneen?
    @Test
    public void setReadDateSetsReadDate(){
        vinkki.setReadDate("1.3.2019");

        assertEquals("1.3.2019",vinkki.getReadDate());

    }




}



