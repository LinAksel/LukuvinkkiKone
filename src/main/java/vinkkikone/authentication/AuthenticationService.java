package vinkkikone.authentication;

import vinkkikone.domain.Vinkki;
import vinkkikone.util.CreationStatus;
import vinkkikone.data_access.VinkkiDao;

public class AuthenticationService {

    private VinkkiDao userDao;

    public AuthenticationService(VinkkiDao userDao) {
        this.userDao = userDao;
    }
}
// Vanha koodi vinkiksi. Poista!
//    public boolean logIn(String username, String password) {
//        for (Vinkki user : userDao.listAll()) {
//            if (user.getUsername().equals(username)
//                    && user.getPassword().equals(password)) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    public CreationStatus createUser(String username, String password, String passwordConfirmation) {
//        CreationStatus status = new CreationStatus();
//        
//        if (userDao.findByName(username) != null) {
//            status.addError("username is already taken");
//        }
//
//        if (username.length()<3 ) {
//            status.addError("username should have at least 3 characters");
//        }
//
//        if (status.isOk()) {
//            userDao.add(new Vinkki(username, password));
//        }
//        
//        return status;
//    }
//
//}
