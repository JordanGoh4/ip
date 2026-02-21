package forger;

import java.io.IOException;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) throws IOException {
        Application.launch(BondForger.class, args);
    }
}
