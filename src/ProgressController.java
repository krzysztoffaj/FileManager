import com.sun.javaws.progress.Progress;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by fajek on 8/13/17.
 */
public class ProgressController {

    @FXML
    private ProgressBar lookingForFilesProgress;
    @FXML
    private ProgressBar managingFilesProgress;

    @FXML
    protected static void addProgressStage(String process) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(process));
        Pane showProgress = loader.load();

        Stage progressStage = new Stage();
        progressStage.setTitle("");
        progressStage.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(showProgress);
        progressStage.setScene(scene);
        progressStage.show();

        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(100);

                return null;
            }
        };

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                progressStage.hide();
            }
        });

        progressStage.show();
        new Thread(task).start();
    }
}
