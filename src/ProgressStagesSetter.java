import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgressStagesSetter {

    public static Stage progressInSearching() {
        final Stage progressStage = new Stage();
        final Text searchingInFiles = new Text();
        final ProgressIndicator operatingOnFilesProgress = new ProgressIndicator();

        progressStage.initStyle(StageStyle.UTILITY);
        progressStage.setHeight(140);
        progressStage.setWidth(250);
        progressStage.setResizable(false);
        progressStage.initModality(Modality.APPLICATION_MODAL);

        searchingInFiles.setText("Looking for files...");

        final VBox verticalBox = new VBox();
        verticalBox.setSpacing(20);
        verticalBox.setAlignment(Pos.CENTER);
        verticalBox.getChildren().addAll(searchingInFiles, operatingOnFilesProgress);

        Scene progressScene = new Scene(verticalBox);
        progressStage.setScene(progressScene);
        progressStage.show();

        return progressStage;
    }

    public static Stage progressInManagingFiles(final Task<?> performOperation) {
        final Stage progressStage = new Stage();
        final Text performingOperation = new Text();
        final Label currentFileOperated = new Label();
        final ProgressBar operatingOnFilesProgress = new ProgressBar();

        progressStage.initStyle(StageStyle.UTILITY);
        progressStage.setHeight(160);
        progressStage.setWidth(600);
        progressStage.setResizable(false);
        progressStage.initModality(Modality.APPLICATION_MODAL);

        performingOperation.setText("Operating on files...");
        operatingOnFilesProgress.setMinWidth(500);
        operatingOnFilesProgress.setProgress(-1F);

        final VBox verticalBox = new VBox();
        verticalBox.setSpacing(20);
        verticalBox.setAlignment(Pos.CENTER);
        verticalBox.getChildren().addAll(performingOperation, currentFileOperated, operatingOnFilesProgress);

        Scene progressScene = new Scene(verticalBox);
        progressStage.setScene(progressScene);

        currentFileOperated.textProperty().bind(performOperation.messageProperty());
        operatingOnFilesProgress.progressProperty().bind(performOperation.progressProperty());

        progressStage.show();

        return progressStage;
    }
}
