import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;

public class TLB {
    private int size;
    private ArrayList<HashMap<Integer, Integer>> entries;

    public TLB(int size) {
        this.size = size;
        this.entries = new ArrayList<>();
    }

    public TLB() {
        this(16); // Default size is 16
    }

    public int lookup(int pageNumber) throws Exception {
        for (HashMap<Integer, Integer> entry : entries) {
            if (entry.containsKey(pageNumber)) {
                return entry.get(pageNumber);
            }
        }
        throw new Exception("Page " + pageNumber + " not found in TLB.");
    }

    public void update(int pageNumber, int frameNumber) {
        for (HashMap<Integer, Integer> entry : entries) {
            if (entry.containsKey(pageNumber)) {
                entry.put(pageNumber, frameNumber);
                return;
            }
        }
        if (entries.size() >= size) {
            entries.remove(0);
        }
        HashMap<Integer, Integer> newEntry = new HashMap<>();
        newEntry.put(pageNumber, frameNumber);
        entries.add(newEntry);
    }
}