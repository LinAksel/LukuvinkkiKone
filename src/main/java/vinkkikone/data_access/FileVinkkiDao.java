//package vinkkikone.data_access;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.util.List;
//import vinkkikone.domain.Vinkki;
//import java.util.*;
//import org.bson.types.ObjectId;
//
//public class FileVinkkiDao implements VinkkiDao {
//
//    private String file;
//    private List<Vinkki> vinkit;
//
//    public FileVinkkiDao(String file) {
//        this.file = file;
//        vinkit = new ArrayList<>();
//
//        try {
//            Scanner scanner = new Scanner(new File(file));
//            while (scanner.hasNextLine()) {
//                String[] parts = scanner.nextLine().split(";");
//                Vinkki v = new Vinkki(parts[0], parts[1], parts[2], parts[3], parts[4]);
//                vinkit.add(v);
//            }
//        } catch (Exception e) {
//        }
//    }
//
//    @Override
//    public List<Vinkki> listAll() {
//        return vinkit;
//    }
//
//    @Override
//    public Vinkki findByTitle(String title) {
//        for (Vinkki v : vinkit) {
//            if (v.getTitle().equals(title)) {
//                return v;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public void add(Vinkki newVinkki) {
//        vinkit.add(newVinkki);
//
//        try {
//            FileWriter author = new FileWriter(file);
//            for (Vinkki v : vinkit) {
//                String rivi = v.getTitle() + ";" + v.getLink() + ";" + v.getDescription() + ";" + v.getTags() + ";" + v.getReadDate() + "\n";
//                author.write(rivi);
//            }
//
//            author.close();
//        } catch (Exception e) {
//        }
//    }
//
//    @Override
//    public void delete(Vinkki vinkki) {
//        vinkit.remove(vinkki);
//    }
//
//    @Override
//    public void update(Vinkki vinkki) {
//    }
//
//    @Override
//    public Vinkki findById(ObjectId id) {
//        return null;
//    }
//}
