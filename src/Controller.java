import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Controller extends FileManager {
//    private String extension;

    @FXML
    private TextField extension;
    @FXML
    private Button lookForFiles;
    @FXML
    private ToggleGroup outputDirectoryButtonsGroup;
    @FXML
    private RadioButton outputDirectoryPathButton;
    @FXML
    private TextField outputDirectoryPath;
    @FXML
    private Button outputDirectoryPathBrowse;
    @FXML
    private RadioButton outputDirectoryCreateFolderButton;
    @FXML
    private Button outputDirectoryCreateFolderHelp;
    @FXML
    private RadioButton outputDirectoryCreateTwoFoldersButton;
    @FXML
    private TextField outputDirectoryCreateTwoFoldersPath;
    @FXML
    private Button outputDirectoryCreateTwoFoldersBrowse;
    @FXML
    private Button outputDirectoryCreateTwoFoldersHelp;
    @FXML
    private RadioButton cut;
    @FXML
    private RadioButton copyAndPaste;
    @FXML
    private ChoiceBox<String> doIfFileExists;
    @FXML
    private ListView<String> filesFound;
    @FXML
    private TextField overallSizeField;
    @FXML
    private Button performOperation;
    @FXML
    private Alert alert;

    private FileManager fileManager = new FileManager();

    @FXML
    private void getOverallSize() {
        File file;
        double overallSize = 0;
        for (String filePath : filesFound.getItems()) {
            file = new File(filePath);
            overallSize += file.length() / (1024*1024);
        }
        overallSizeField.setText(String.valueOf(overallSize));
    }

    @FXML
    private String getExtension() {
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
    protected void loadFilesList() throws IOException {
        String formattedExtension = getExtension();

        if (formattedExtension == null) {
            return;
        }

        String[] extensions = new String[]{formattedExtension};
        extensions[0] = formattedExtension;

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);

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
}
