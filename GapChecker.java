import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class GapChecker {
    ArrayList<Integer> gapSizes = new ArrayList<>();
    Integer average = 0;
    Integer total = 0;
    Integer validFilesSeen = 0;
    Integer filesWithGaps = 0;

    public void getStats(Path filePath) throws IOException {

        byte[] musicBytes = Files.readAllBytes(filePath);
        validFilesSeen++;
        int consecEmpty = 0;
        int start = 0;
        int end = 0;
        boolean gap = false;
        for (int i = 0; i < musicBytes.length - 1; i++) {

            if (musicBytes[i] == 0) {
                if (start == 0) {
                    start = i;
                }
                consecEmpty++;
            } else {
                end = i;
                if (consecEmpty >= 1000) {
                    gap = true;
                    gapSizes.add(consecEmpty);
                    System.out.println("has " + consecEmpty + " bytes available from " + start + " to " + (end - 1));
                }
                start = 0;
                consecEmpty = 0;
            }
        }
        if (gap) {
            filesWithGaps++;
        }
    }

    public void summariseStats(Path sd) throws IOException {

        for (int g : gapSizes) {
            total = total + g;
        }

        if (gapSizes.size() != 0) {
            average = total / gapSizes.size();
        }else{
            System.out.println("No gaps larger than 1000 bytes found");
        }
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("averageAll.txt"), "utf-8"))) {
            writer.append(sd.toString() + ": \t");
            writer.append("Average size of gaps is " + average);
            writer.append("\t Valid files seen : " + validFilesSeen);
            writer.append("\t Valid files with gaps : " + filesWithGaps);
            writer.append("\t Total Gaps : " + gapSizes.size());
            writer.append("\n");

        }
    }

}
