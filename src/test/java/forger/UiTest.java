package forger;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UiTest {

    @Test
    public void readCommand_withInput_returnsSameInput() {
        Scanner scanner = new Scanner("list");
        Ui ui = new Ui(scanner);
        assertEquals("list", ui.readCommand());
    }

    @Test
    public void showError_anyMessage_outputContainsMessageAndOops() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        Ui ui = new Ui(new Scanner(""));
        ui.showError("something went wrong");

        System.setOut(originalOut);
        String output = out.toString();
        assertTrue(output.contains("OOPS!!!"));
        assertTrue(output.contains("something went wrong"));
    }
}
