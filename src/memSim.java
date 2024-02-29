import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;






public class memSim{
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java MemorySimulation <reference-sequence-file.txt> <FRAMES> <PRA>");
            System.exit(1);
        }
        VirtualMemorySimulator memSim = new VirtualMemorySimulator();
        String referenceFilePath = args[0];
        int frames = args.length > 1 ? Integer.parseInt(args[1]) : 256;
        String pra = args.length > 2 ? args[2] : "fifo";

        try (BufferedReader reader = new BufferedReader(new FileReader(referenceFilePath))) {
            ArrayList<Integer> referenceSequence = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                referenceSequence.add(Integer.parseInt(line.trim()));
            }
            TLB tlb = new TLB();
            PageTable pageTable = new PageTable();
            FrameTable frameTable = new FrameTable(frames);

            memSim.simulate(referenceSequence, "BACKING_STORE.bin", tlb, pageTable, frameTable, pra);
        } catch (IOException e) {
            System.out.println("Error: File " + referenceFilePath + " not found.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}  