
public class PageTableEntry {
    private int frameNumber; // Physical frame number
    private boolean isPresent; // Present/Absent bit
    private boolean isModified; // Modified (Dirty) bit
    private boolean isAccessed; // Accessed bit

    
    private PageTableEntry(int mFrameNumber, boolean mIsPresent, boolean mIsModified, boolean mIsAccessed){
        frameNumber = mFrameNumber;
        isPresent = mIsPresent;
        isModified = mIsModified;
    }

    

}
