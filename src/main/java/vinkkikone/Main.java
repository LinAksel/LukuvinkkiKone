package vinkkikone;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import vinkkikone.domain.*;
import java.util.Properties;

import org.bson.types.ObjectId;

import vinkkikone.authentication.AuthenticationService;
//import vinkkikone.data_access.FileVinkkiDao;
import vinkkikone.util.CreationStatus;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.velocity.VelocityTemplateEngine;
import vinkkikone.data_access.MongoVinkkiDao;
import vinkkikone.data_access.VinkkiDao;

public class Main {

    static String LAYOUT = "templates/layout.html";
    static VinkkiDao dao;
    static AuthenticationService authService;

    public static void main(String[] args) {
        port(findOutPort());

        // index
        get("/", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/index.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        get("/lukuvinkkikone", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/index.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        // list
        get("/list", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Vinkki> lista = authenticationService().getList();
            model.put("list", lista);
            model.put("template", "templates/list.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        // merkitään luetuksi
        post("/list", (request, response) -> {
            System.out.println("tulin lisäämään lukumerkinnän "+ request.queryParams("readId"));
            ObjectId id = new ObjectId(request.queryParams("readId"));
            //System.out.println("title: " + id);
            Vinkki v = authenticationService().getById(id);
            dao.markAsRead(v);
            HashMap<String, Object> model = new HashMap<>();
            List<Vinkki> lista = authenticationService().getList();
            model.put("list", lista);
            model.put("template", "templates/list.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        // Poista luettu-merkintä
        post("/list1", (request, response) -> {
            System.out.println("tulin poistamaan lukumerkintää");
            HashMap<String, Object> model = new HashMap<>();
            ObjectId id = new ObjectId(request.queryParams("readId"));
            //System.out.println("title: " + title);
            Vinkki v = authenticationService().getById(id);
            dao.markAsUnread(v);
            List<Vinkki> lista = authenticationService().getList();
            model.put("list", lista);
            model.put("template", "templates/list.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        post("/listTag", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            String tag = request.queryParams("tagSearchField");
            List<Vinkki> lista = authenticationService().getByTag(tag);
            model.put("list", lista);
            model.put("searchTag", tag);
            model.put("template", "templates/list.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        // add
        get("/addnew", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/addnew.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        post("/addnew", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            String title = request.queryParams("title");
            String link = request.queryParams("link");
            String description = request.queryParams("description");
            String tags = request.queryParams("tags");
            String readDate = request.queryParams("readDate");

            CreationStatus status = authenticationService().createNew(title, link, description, tags);

            if (!status.isOk()) {
                model.put("error", String.join(",  ", status.errors()));
                model.put("template", "templates/addnew.html");
                return new ModelAndView(model, LAYOUT);
            } else {
                model.put("note", String.join(",  ", status.notes()));
                model.put("template", "templates/addnew.html");
                return new ModelAndView(model, LAYOUT);
            }
        }, new VelocityTemplateEngine());
    }

    public static void setDao(VinkkiDao dao) {
        Main.dao = dao;
    }

    public static AuthenticationService authenticationService() {
        if (dao == null) {
            // dao = new FileVinkkiDao("vinkit.txt");
            String url = System.getenv("MONGODB_URI");
            if (url == null) {
                dao = new MongoVinkkiDao(mongoUrl());
            } else {
                dao = new MongoVinkkiDao(url);
            }
        }
        if (authService == null) {
            authService = new AuthenticationService(dao);
        }

        return authService;
    }

    static int findOutPort() {
        if (portFromEnv != null) {
            return Integer.parseInt(portFromEnv);
        }
        return 4567;
    }

    static String portFromEnv = new ProcessBuilder().environment().get("PORT");

    static void setEnvPort(String port) {
        portFromEnv = port;
    }

    static String mongoUrl() {

        // FIXME paikallisen config tiedoston lukija kehityksen tarpeisiin
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("mongo.config"));

            String mongoUser = properties.getProperty("user");
            String mongoPW = properties.getProperty("password");
            String mongoURL = properties.getProperty("url");

            return "mongodb+srv://" + mongoUser + ":" + mongoPW + "@" + mongoURL + "/";

        } catch (Exception e) {
            System.out.println("Error reading config file:" + e.getMessage());
            return "";
        }
    }

}
