import java.util.HashMap;
import java.util.Map;

public class PageTable {

    private HashMap<Integer, Integer> table; 

    public PageTable(int Size){
        this.table = new HashMap<>();
    }

    public PageTable() {
        this.table = new HashMap<>();
    }

    public int lookup(int pageNumber) throws Exception {
        if (table.containsKey(pageNumber)) {
            return table.get(pageNumber);
        } else {
            throw new Exception("Page " + pageNumber + " not found in Page Table.");
        }
    }

    public void update(int pageNumber, int frameNumber) {
        table.put(pageNumber, frameNumber);
    }

}
