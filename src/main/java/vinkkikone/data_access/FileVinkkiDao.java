
package vinkkikone.data_access;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import vinkkikone.domain.Vinkki;
import java.util.*;

public class FileVinkkiDao implements VinkkiDao {
    private String file;
    private List<Vinkki> vinkit;
    
    public FileVinkkiDao(String file) {
        this.file = file;
        vinkit = new ArrayList<>();
        
        try{
            Scanner scanner = new Scanner(new File(file));
            while( scanner.hasNextLine() ){
                String[] parts = scanner.nextLine().split(";");
                vinkit.add(new Vinkki(parts[0], parts[1]));
            }
        } catch(Exception e) {
        }
    }
        
    @Override
    public List<Vinkki> listAll() {
        return vinkit;
    }

    @Override
    public Vinkki findByTitle(String name) {
        for (Vinkki user : vinkit) {
            if ( user.getTitle().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void add(Vinkki newUser) {
        vinkit.add(newUser);
        
        try{
            FileWriter author = new FileWriter(file);
            for (Vinkki v : vinkit) {
                String rivi = v.getTitle()+";"+v.getLink()+"\n";
                author.write(rivi);
            }
            
            author.close();
        } catch(Exception e) {
        }
    }
    
}
