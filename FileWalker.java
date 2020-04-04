import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.*;

public class FileWalker
        extends SimpleFileVisitor<Path> {
    public GapChecker gc = new GapChecker();

    @Override
    public FileVisitResult visitFile(Path file,
                                     BasicFileAttributes attr) throws IOException {

        if (attr.isRegularFile()) {

            System.out.format("Regular file: %s \n", file.getFileName());
            if (file.getFileName().toString().endsWith("mp3") || file.getFileName().toString().endsWith("m4a")) {
                gc.getStats(file);
            }
            System.out.println(" (" + attr.size() + "bytes)");
            System.out.println();
        }

        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file,
                                           IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }

}

