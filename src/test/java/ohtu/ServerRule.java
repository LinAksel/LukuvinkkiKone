package ohtu;

import vinkkikone.Main;
import vinkkikone.domain.Vinkki;
import org.junit.rules.ExternalResource;
import spark.Spark;
import vinkkikone.data_access.VinkkiDao;

public class ServerRule extends ExternalResource {
    
    private final int port;

    public ServerRule(int port) {
        this.port = port;
    }

    @Override
    protected void before() throws Throwable {
        Spark.port(port);
        VinkkiDao dao = new VinkkiDaoForTests();
        dao.add(new Vinkki("Paroni von Münchhausen", "http://www.gutenberg.org/ebooks/48623"));
        dao.add(new Vinkki("Seitsemän veljestä", "https://fi.wikipedia.org/wiki/Seitsem%C3%A4n_veljest%C3%A4"));
        Main.setDao(dao);
        Main.main(null);
    }

    @Override
    protected void after() {
        Spark.stop();
    }
    
}
