import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by fajek on 8/10/17.
 */
public class FileManager {
    protected boolean isAlphaNumeric(String input) {
        String pattern = "^[a-zA-Z0-9]*$";
        return input.matches(pattern);
    }

    private void createAlbumDirectoryInFilesLocation(String filesPath) {
        int slashOccurrences = StringUtils.countMatches(filesPath, "/");
        String album = filesPath;
        String filesLocationDirectory = filesPath.substring(0, filesPath.lastIndexOf("/") + 1);

        for (int i = 0; i < slashOccurrences - 1; i++) {
            album = album.substring(album.indexOf("/") + 1);
        }
        album = album.substring(0, album.lastIndexOf("/"));
        File albumDirectory = new File(filesLocationDirectory + album);

        albumDirectory.mkdir();
    }

    private void createArtistAndAlbumDirectoriesInSetLocation(File artistDirectory, File albumDirectory) {
        artistDirectory.mkdir();
        albumDirectory.mkdir();
    }

    private String getArtistName(String sourcePath) {
        String artistName = sourcePath;
        int slashOccurrences = StringUtils.countMatches(sourcePath, "/");

        for (int i = 0; i < slashOccurrences - 2; i++) {
            artistName = artistName.substring(artistName.indexOf("/") + 1);
        }
        artistName = artistName.substring(0, artistName.indexOf("/"));

        return artistName;
    }

    private String getAlbumName(String sourcePath) {
        String albumName = sourcePath;
        int slashOccurrences = StringUtils.countMatches(sourcePath, "/");
        for (int i = 0; i < slashOccurrences - 1; i++) {
            albumName = albumName.substring(albumName.indexOf("/") + 1);
        }
        albumName = albumName.substring(0, albumName.lastIndexOf("/"));

        return albumName;
    }

    private String getSongName(String sourcePath) {
        return sourcePath.substring(sourcePath.lastIndexOf("/") + 1);
    }

    private String getArtistAndAlbumDirectoriesInSetLocation(String sourcePath, String upstreamDestinationPath) {
        String artistName = getArtistName(sourcePath);
        String albumName = getAlbumName(sourcePath);

        File artistDirectory = new File(upstreamDestinationPath + artistName);
        File albumDirectory = new File(upstreamDestinationPath + artistName + "/" + albumName);

        createArtistAndAlbumDirectoriesInSetLocation(artistDirectory, albumDirectory);

        return albumDirectory.toString();
    }

    private String getDestinationPath(String sourcePath, String upstreamDestinationPath) {
        String newArtistAndAlbumDirectory = getArtistAndAlbumDirectoriesInSetLocation(sourcePath, upstreamDestinationPath);
        String songName = getSongName(sourcePath);
        String destinationPath = newArtistAndAlbumDirectory + "/" + songName;

        return destinationPath;
    }

    protected void cutFile(String sourcePath, String upstreamDestinationPath) throws IOException {
        File sourceFile = new File(sourcePath);
        File destinationFile = new File(getDestinationPath(sourcePath, upstreamDestinationPath));

        if(destinationFile.exists())
            destinationFile.delete();

        FileUtils.moveFile(sourceFile, destinationFile);
    }

    protected void copyAndPasteFile(String sourcePath, String upstreamDestinationPath) throws IOException {
        File sourceFile = new File(sourcePath);
        File destinationFile = new File(getDestinationPath(sourcePath, upstreamDestinationPath));

        FileUtils.copyFile(sourceFile, destinationFile);
    }
}
