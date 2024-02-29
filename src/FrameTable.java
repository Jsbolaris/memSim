import java.util.HashMap;
import java.util.ArrayList;


public class FrameTable {
    private int numFrames;
    private int[] frameList;
    private HashMap<Integer, Integer> pageToFrameMap;
    private ArrayList<Integer> frameUsage;
    private HashMap<Integer, byte[]> frameData;

    public FrameTable(int numFrames){
        this.numFrames = numFrames;
        this.frameList = new int[numFrames];
        this.pageToFrameMap = new HashMap<>();
        this.frameUsage = new ArrayList<>();
        this.frameData = new HashMap<>();
        for(int i = 0; i < numFrames; i++){
            frameList[i] = -1;
        }
    }

    public int findFreeFrame(){
        for(int i = 0; i < numFrames; i++){
            if(frameList[i] == -1){
                return i;
            }
        }
        return -1;
    }

    public int updateFrame(int pageNumber, String algorithm) throws Exception {
        int freeFrame = findFreeFrame();
        if (freeFrame != -1) {
            frameList[freeFrame] = pageNumber;
            pageToFrameMap.put(pageNumber, freeFrame);
            frameUsage.add(freeFrame);
            return freeFrame;
        } else {
            if (algorithm.equalsIgnoreCase("FIFO") || algorithm.equalsIgnoreCase("LRU")) {
                int frameToReplace = algorithm.equalsIgnoreCase("FIFO") ? frameUsage.remove(0) : frameUsage.remove(frameUsage.size() - 1);
                int oldPage = frameList[frameToReplace];
                frameList[frameToReplace] = pageNumber;
                pageToFrameMap.put(pageNumber, frameToReplace);
                pageToFrameMap.remove(oldPage);
                frameUsage.add(frameToReplace);
                return frameToReplace;
            } else {
                throw new Exception("Unsupported page replacement algorithm");
            }
        }
    }

    public int getFrameNumber(int pageNumber) throws Exception {
        if (pageToFrameMap.containsKey(pageNumber)) {
            return pageToFrameMap.get(pageNumber);
        } else {
            throw new Exception("Page " + pageNumber + " not found in Frame Table.");
        }
    }

    public void storeDataInFrame(int frameNumber, byte[] data){
        frameData.put(frameNumber, data);
    }

    public byte[] retrieveDataFromFrame(int frameNumber){
        return frameData.getOrDefault(frameNumber, null);
    }

}
