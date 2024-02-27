import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TLB {
    private Map<Integer, Integer> entries;
    private final int maxSize;
    public TLB(int maxSize){
        this.maxSize = maxSize;
        this.entries = new LinkedHashMap<>(maxSize, 0.75f, true){
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > maxSize;
            }
        };
    }
    public void addEntry(int pageNumber, int frameNumber){
        entries.put(pageNumber, frameNumber);
    }

    public Integer getFrameNumber(int pageNumber){
        return entries.get(pageNumber);
    }
    // Implement TLB with a fixed size, store page number and frame number
}