package vinkkikone.util;

import java.util.ArrayList;
import java.util.List;

public class CreationStatus {
    private List<String> errors;
    private List<String> notes;

    public CreationStatus() {
        errors = new ArrayList<>();
        notes = new ArrayList<>();
    }

    public void addError(String error) {
        errors.add(error);
    }

    public void addNote(String note) {
        notes.add(note);
    }

    public List<String> errors() {
        return errors;
    }

    public List<String> notes() {
        return notes;
    }

    public boolean isOk() {
        return errors.isEmpty();
    }
}
