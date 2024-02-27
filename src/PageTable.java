import java.util.HashMap;
import java.util.Map;

public class PageTable {

    private Map<Integer, PageTableEntry> entries;

    public PageTable(){
        this.entries = new HashMap<>();
    }
}
