package forger;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ParserTest {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    @Test
    public void parse_listInput_returnsListCommand() throws Bark {
        Parser.ParsedCommand cmd = Parser.parse("list", FORMAT);
        assertEquals(Parser.CommandType.LIST, cmd.type);
        assertEquals(-1, cmd.index);
        assertNull(cmd.description);
    }

    @Test
    public void parse_todoWithDescription_returnsTodoWithDescription() throws Bark {
        Parser.ParsedCommand cmd = Parser.parse("todo read book", FORMAT);
        assertEquals(Parser.CommandType.TODO, cmd.type);
        assertEquals("read book", cmd.description);
        assertEquals(-1, cmd.index);
    }
}
