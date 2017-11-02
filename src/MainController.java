import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController extends FileManager {

    @FXML
    private TextField extension;
    @FXML
    private Button lookForFiles;
    @FXML
    private ToggleGroup destinationDirectoryButtonsGroup;
    @FXML
    private RadioButton setDestinationDirectoryButton;
    @FXML
    private TextField setDestinationDirectory;
    @FXML
    private Button destinationDirectoryBrowser;
    @FXML
    private RadioButton createDirectoryInCurrentLocationButton;
    @FXML
    private Button createDirectoryInCurrentLocationHelp;
    @FXML
    private RadioButton createTwoDirectoriesInSetLocationButton;
    @FXML
    private TextField locationToCreateTwoDirectories;
    @FXML
    private Button createTwoDirectoriesInSetLocationBrowser;
    @FXML
    private Button createTwoDirectoriesInSetLocationHelp;
    @FXML
    private RadioButton cut;
    @FXML
    private RadioButton copyAndPaste;
    @FXML
    private ComboBox<String> doIfFileExists;
    @FXML
    private ListView<String> filesFound;
    @FXML
    private TextField overallSizeField;
    @FXML
    private Button performOperation;

    private ProgressController progressController;

    @FXML
    private String getExtension() {
        Alert alert;
        if (extension.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.NONE, "Please enter filename extension", ButtonType.OK);
            alert.showAndWait();
            return null;
        } else {
            if (isAlphaNumeric(extension.getText())) {
                return extension.getText();
            } else {
                alert = new Alert(Alert.AlertType.NONE, "Filename extension has to be alphanumeric", ButtonType.OK);
                alert.showAndWait();
                return null;
            }
        }
    }

    @FXML
    private String getDestinationDirectory(TextField destinationDirectory) {
        Alert alert;
        if (destinationDirectory.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.NONE, "Please enter filename extension", ButtonType.OK);
            alert.showAndWait();
            return null;
        }
        return null;
    }

    @FXML
    private void getOverallSize() {
        File file;
        double overallSize = 0;
        for (String filePath : filesFound.getItems()) {
            file = new File(filePath);
            overallSize += file.length() / (1024 * 1024);
        }
        overallSizeField.setText(String.valueOf(overallSize));
    }

    @FXML
    private void loadFilesList() throws IOException {
        String formattedExtension = getExtension();

        if (formattedExtension == null) {
            return;
        }

        String[] extensions = new String[]{formattedExtension};
        extensions[0] = formattedExtension;

        DirectoryChooser sourceDirectory = new DirectoryChooser();
        sourceDirectory.setInitialDirectory(new File("/media/fajek/Gry, filmy i reszta dysku/Muzyka/"));
        File selectedDirectory = sourceDirectory.showDialog(null);

        if (selectedDirectory != null) {
            List<File> files = (List<File>) FileUtils.listFiles(selectedDirectory, extensions, true);
            for (File file : files) {
                filesFound.getItems().add(file.getPath());
            }
            getOverallSize();
        } else {
            return;
        }
    }

    @FXML
    private void initializeComboBox() {
        List<String> operations = new ArrayList<>();
        operations.add("Replace file");
        operations.add("Skip file");
        operations.add("Keep both files");
        ObservableList operationsIfFileExists = FXCollections.observableList(operations);
        doIfFileExists.setItems(operationsIfFileExists);
    }

    @FXML
    private void cutFiles() throws IOException {
        for (String sourceFile : filesFound.getItems()) {
            cutFile(sourceFile, "/home/fajek/Desktop/");
        }
//        String source = filesFound.getItems().get(0);
//        DirectoryChooser destinationDirectory = new DirectoryChooser();
//        String destination = "/home/fajek/Desktop/";
//        cutFile(source, destination);
//        createAlbumDirectoryInFilesLocation(source);
    }

    @FXML
    private void setOutputDirectoryPath() {

    }
}
