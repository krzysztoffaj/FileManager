import javafx.scene.control.TextField;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fajek on 8/10/17.
 */
public class FileManager {
    protected boolean isAlphaNumeric(String string) {
        String pattern = "^[a-zA-Z0-9]*$";
        return string.matches(pattern);
    }
}
