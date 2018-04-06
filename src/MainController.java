import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MainController extends FileManager {
    @FXML
    private Button listCleaner;
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
    private ToggleGroup operationButtonsGroup;
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

    @FXML
    private void clearList() {
        filesFound.getItems().clear();
    }

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
        // TO DO: REMOVE THE HARDCODE !!!
        sourceDirectory.setInitialDirectory(new File("/media/fajek/Gry, filmy i reszta dysku/Muzyka/"));
        File selectedDirectory = sourceDirectory.showDialog(null);

        Stage progressStage = ProgressStagesSetter.progressInSearching();

        Task<Void> searchForFiles = new Task<Void>() {
            @Override
            public Void call() {
                if (selectedDirectory != null) {
                    List<File> files = (List<File>) FileUtils.listFiles(selectedDirectory, extensions, true);
                    for (File file : files) {
                        filesFound.getItems().add(file.getPath());
                    }
                    getOverallSize();
                } else {
                    return null;
                }
//                Platform.runLater(
//                        progressStage::close
//                );
                return null;
            }
        };

        searchForFiles.setOnSucceeded(event -> progressStage.close());
        Thread searchingForFiles = new Thread(searchForFiles, "Searching for files");

        searchingForFiles.start();
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
            // TO DO: REMOVE THE HARDCODE!!!
            cutFile(sourceFile, "/home/fajek/Desktop/");
        }
    }

    @FXML
    private void copyAndPasteFiles() throws IOException {
        for (String sourceFile : filesFound.getItems()) {
            // TO DO: REMOVE THE HARDCODE!!!
            copyAndPasteFile(sourceFile, "/home/fajek/Desktop/");
        }
    }

    @FXML
    private void setOutputDirectoryPath() {

    }

    @FXML
    private void performOperation() throws IOException {
//        RadioButton operation = (RadioButton) operationButtonsGroup.getSelectedToggle();
//        if (operation == null) {
//            Alert alert = new Alert(Alert.AlertType.NONE, "Please select an operation", ButtonType.OK);
//            alert.showAndWait();
//            return;
//        }
//        String selectedOperation = operation.getText();
//        if (selectedOperation.equals("Cut")) {
//            cutFiles();
//        } else if (selectedOperation.equals("Copy and paste")) {
//            copyAndPasteFiles();
//        }

        Task performOperation = new Task<Void>() {
            @Override
            public Void call() throws InterruptedException, IOException {
                int i = 1;
                for (String sourceFile : filesFound.getItems()) {
                    // TO DO: REMOVE THE HARDCODE!!!
                    copyAndPasteFile(sourceFile, "/home/fajek/Desktop/");
                    updateProgress(i, filesFound.getItems().size());
                    updateMessage(sourceFile.substring(sourceFile.lastIndexOf("/") + 1));
                    i++;
                }
                return null;
            }
        };

        Stage progressStage = ProgressStagesSetter.progressInManagingFiles(performOperation);

        performOperation.setOnSucceeded(event -> progressStage.close());

        Thread managingFiles = new Thread(performOperation, "Managing files");
        managingFiles.start();
    }
}
