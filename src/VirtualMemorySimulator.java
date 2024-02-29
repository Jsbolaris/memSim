import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class VirtualMemorySimulator {
    // Constants for memory sizes, page sizes, etc.
    private static final int PAGE_SIZE = 256; // Example page size
    private static final int NUM_PAGES = 256; // Total number of pages
    private static final int PHYSICAL_MEMORY_SIZE = 128 * PAGE_SIZE; // Example physical memory size
    private static final int TLB_SIZE = 16; // TLB entries
    
    // Data structures for TLB, page table, physical memory, and disk
    private TLB tlb = new TLB(TLB_SIZE);
    private PageTable pageTable = new PageTable(NUM_PAGES);
    
    // Main method to run the simulation
    
    // Simulation method
    /*
     * 
     */
    public void simulate(ArrayList<Integer> referenceSequence, String backingStorePath, TLB tlb, PageTable pageTable, FrameTable frameTable, String pra) throws IOException, Exception {
        int tlbHits = 0, tlbMisses = 0, pageFaults = 0, totalAccess = referenceSequence.size();
        for(Integer logicalAddress : referenceSequence){
            int pageNumber = logicalAddress / PAGE_SIZE;
            int frameNumber = -1;
            try{
                frameNumber = tlb.lookup(pageNumber);
                tlbHits++;
            } catch (Exception e){
                tlbMisses++;
                try{
                    frameNumber = pageTable.lookup(pageNumber);
                } catch (Exception ex){
                    pageFaults++;
                    frameNumber = loadPageFromBackingStore(pageNumber, backingStorePath, frameTable, pageTable, tlb, pra);
                }
            }
            byte[] data = frameTable.retrieveDataFromFrame(frameNumber);
            if(data == null){
                System.out.println("Error: data Not found for address " + logicalAddress);
                continue;
            }
            int offset = logicalAddress % 256;
            int value = data[offset] & 0xFF;
            StringBuilder dataHex = new StringBuilder();
            for(byte b : data){
                dataHex.append(String.format("%02x", b));
            }
        }
        double pageFaultRate = (double) pageFaults / totalAccess * 100;
        double tblMissRate = (double) tlbMisses / totalAccess * 100;
        System.out.println("Total page faults: " + pageFaults + ", Page Fault Rate: " + String.format("%.2f", pageFaultRate));
    }

    private int loadPageFromBackingStore(int pageNumber, String backingStorePath, FrameTable frameTable,
            PageTable pageTable, TLB tlb, String pra) throws IOException, Exception {
        // TODO Auto-generated method stub
        int offset = pageNumber * PAGE_SIZE;
        byte[] pageData = new byte[PAGE_SIZE];
        try(FileInputStream fis = new FileInputStream(backingStorePath)){
            fis.skip(offset);
            fis.read(pageData);
        }
        int frameNumber = frameTable.updateFrame(pageNumber, backingStorePath);
        pageTable.update(pageNumber, frameNumber);
        tlb.update(pageNumber, frameNumber);
        frameTable.storeDataInFrame(frameNumber, pageData);
        return frameNumber;
    }
}
