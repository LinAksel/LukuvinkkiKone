package vinkkikone;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;
import vinkkikone.authentication.AuthenticationService;
import vinkkikone.data_access.FileVinkkiDao;
import vinkkikone.util.CreationStatus;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.velocity.VelocityTemplateEngine;
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
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/list.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        // post("/list", (request, response) -> {
        // HashMap<String, String> model = new HashMap<>();
        // String title = request.queryParams("title");
        // String link = request.queryParams("link");
        //
        // if (!authenticationService().add(title, link)) {
        // model.put("error", "Jotain meni pieleen. :(");
        // model.put("template", "templates/addnew.html");
        // return new ModelAndView(model, LAYOUT);
        // }
        //
        // response.redirect("/");
        // return new ModelAndView(model, LAYOUT);
        // }, new VelocityTemplateEngine());
        // addnew
        get("/addnew", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/addnew.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());

        post("/addnew", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            String title = request.queryParams("title");
            String link = request.queryParams("link");

            CreationStatus status = authenticationService().createNew(title, link);

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
            dao = new FileVinkkiDao("vinkit.txt");
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
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("mongo.config"));

            String mongoUser = properties.getProperty("user");
            String mongoPW = properties.getProperty("password");
            String mongoURL = properties.getProperty("url");
            System.out.println("palautetaan url: mongodb+srv://" + mongoUser + ":" + mongoPW + "@" + mongoURL + "/");
            return "mongodb+srv://" + mongoUser + ":" + mongoPW + "@" + mongoURL + "/";

        } catch (Exception e) {
            System.out.println("Error reading config file:" + e.getMessage());
            return "";
        }
    }

}
