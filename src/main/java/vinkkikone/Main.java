
package vinkkikone;

import java.util.HashMap;
import vinkkikone.authentication.AuthenticationService;
import vinkkikone.data_access.FileVinkkiDao;
import vinkkikone.util.CreationStatus;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.velocity.VelocityTemplateEngine;
import vinkkikone.data_access.VinkkiDao;

public class Main {
    
    static String LAYOUT = "templates/layout.html";
  
    // Daoa voi käyttää vinkkien tallennuksessa tarkastamaan, onko samannimistä olemassa 
    // ks. myös metodit alla ja vinkkikone.authentication
    static VinkkiDao dao;
    static AuthenticationService authService;
    
    public static void main(String[] args) {
        port(findOutPort());
              
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
        
        get("/addnew", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/addnew.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());            
        
        get("/list", (request, response) -> {
            HashMap<String, String> model = new HashMap<>();
            model.put("template", "templates/list.html");
            return new ModelAndView(model, LAYOUT);
        }, new VelocityTemplateEngine());     

        //AVUKSI koodatessa........... POISTA!!!
//        post("/login", (request, response) -> {
//            HashMap<String, String> model = new HashMap<>();
//            String username = request.queryParams("username");
//            String password = request.queryParams("password");
//            
//            if ( !authenticationService().logIn(username, password) ) {
//                model.put("error", "invalid username or password");
//                model.put("template", "templates/login.html");
//                return new ModelAndView(model, LAYOUT);
//            }
//                
//           response.redirect("/ohtu");
//           return new ModelAndView(model, LAYOUT);
//        }, new VelocityTemplateEngine());
//        
//        post("/user", (request, response) -> {
//            HashMap<String, String> model = new HashMap<>();
//            String username = request.queryParams("username");
//            String password = request.queryParams("password");
//            String passwordConf = request.queryParams("passwordConfirmation");
//            
//            CreationStatus status = authenticationService().createUser(username, password, passwordConf);
//            
//            if ( !status.isOk()) {
//                model.put("error", String.join(",  ", status.errors()));
//                model.put("template", "templates/user.html");
//                return new ModelAndView(model, LAYOUT);
//            }
//                
//           response.redirect("/welcome");
//           return new ModelAndView(model, LAYOUT);
//        }, new VelocityTemplateEngine());           
    }

    public static void setDao(VinkkiDao dao) {
        Main.dao = dao;
    }
    
    public static AuthenticationService authenticationService(){
        if ( dao==null ) {
          dao = new FileVinkkiDao("vinkit.txt");  
        } if (authService==null) {
           authService = new AuthenticationService(dao); 
        }

        return authService;
    }    
      
    static int findOutPort() {
        if ( portFromEnv!=null ) {
            return Integer.parseInt(portFromEnv);
        }
        
        return 4567;
    }
    
    static String portFromEnv = new ProcessBuilder().environment().get("PORT");
    
    static void setEnvPort(String port){
        portFromEnv = port;
    }
}
