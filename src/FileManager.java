import javafx.scene.control.TextField;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fajek on 8/10/17.
 */
public class FileManager {
    protected boolean isAlphaNumeric(String input) {
        String pattern = "^[a-zA-Z0-9]*$";
        return input.matches(pattern);
    }

    protected void cutFile(String sourcePath, String destinationPath) throws IOException {
        File sourceFile = new File(sourcePath);
        File destinationFile = new File(destinationPath);

        FileUtils.moveFileToDirectory(sourceFile, destinationFile, false);
    }

    protected void copyAndPasteFile(String sourcePath, String destinationPath) throws IOException {
        File sourceFile = new File(sourcePath);
        File destinationFile = new File(destinationPath);

        FileUtils.copyFile(sourceFile, destinationFile);
    }

    protected void createAlbumDirectoryInFilesLocation(String filesLocation) {
        int slashOccurrences = StringUtils.countMatches(filesLocation, "/");
        String albumDirectoryName = filesLocation;
        String filesLocationDirectory = filesLocation.substring(0, filesLocation.lastIndexOf("/") + 1);
        System.out.println(filesLocationDirectory);
        for (int i = 0; i < slashOccurrences - 1; i++) {
            albumDirectoryName = albumDirectoryName.substring(albumDirectoryName.indexOf("/") + 1);
        }
        albumDirectoryName = albumDirectoryName.substring(0, albumDirectoryName.lastIndexOf("/"));
        File albumDirectory = new File(filesLocationDirectory + albumDirectoryName);

        albumDirectory.mkdir();
    }
}
