import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Program {

    public static void main(String[] args) throws IOException {
        FileWalker fw = new FileWalker();
        Path startDir = Paths.get("D:\\Public\\Music");
        Path fv = Files.walkFileTree(startDir, fw);

        fw.gc.summariseStats(startDir);
    }

}
