package forger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Parses raw user input into structured commands.
 *
 * @author Jordan
 */
public class Parser {
    /**
     * Types of supported commands.
     */
    public enum CommandType {
        BYE, LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, FIND
    }

    /**
     * Represents a parsed command with associated data.
     */
    public static class ParsedCommand {
        public final CommandType type;
        public final int index; // for mark/unmark/delete (0-based), otherwise -1
        public final String description; // for todo/deadline/event
        public final LocalDateTime by; // for deadline
        public final LocalDateTime start; // for event
        public final LocalDateTime end; // for event

        private ParsedCommand(CommandType type, int index, String description,
                              LocalDateTime by, LocalDateTime start, LocalDateTime end) {
            this.type = type;
            this.index = index;
            this.description = description;
            this.by = by;
            this.start = start;
            this.end = end;
        }
    }

    /**
     * Parses a single line of user input.
     *
     * @param input Raw user input.
     * @param formatter Date/time formatter to parse deadline/event date-time strings.
     * @return ParsedCommand representing the user intent.
     * @throws Bark If the input is invalid or unsupported.
     */
    public static ParsedCommand parse(String input, DateTimeFormatter formatter) throws Bark {
        validateInput(input);

        String[] parts = input.split(" ", 2);
        String command = parts[0];
        String rest = parts.length > 1 ? parts[1] : "";

        switch (command) {
        case "bye":
            return createSimpleCommand(CommandType.BYE);
        case "list":
            return createSimpleCommand(CommandType.LIST);
        case "find":
            return parseFindCommand(rest);
        case "mark":
            return createIndexCommand(CommandType.MARK, rest);
        case "unmark":
            return createIndexCommand(CommandType.UNMARK, rest);
        case "delete":
            return createIndexCommand(CommandType.DELETE, rest);
        case "todo":
            return parseTodoCommand(rest);
        case "deadline":
            return parseDeadlineCommand(rest, formatter);
        case "event":
            return parseEventCommand(rest, formatter);
        default:
            throw new Bark("Bark Bark intruder alert!");
        }
    }

    /**
     * Validates that the input is not null or empty.
     */
    private static void validateInput(String input) throws Bark {
        if (input == null || input.trim().isEmpty()) {
            throw new Bark("Please enter a command.");
        }
    }

    /**
     * Creates a simple command with no additional parameters.
     */
    private static ParsedCommand createSimpleCommand(CommandType type) {
        return new ParsedCommand(type, -1, null, null, null, null);
    }

    /**
     * Creates a command that requires an index parameter.
     */
    private static ParsedCommand createIndexCommand(CommandType type, String rest) throws Bark {
        return new ParsedCommand(type, parseIndex(rest), null, null, null, null);
    }

    /**
     * Parses a find command.
     */
    private static ParsedCommand parseFindCommand(String rest) throws Bark {
        if (rest.trim().isEmpty()) {
            throw new Bark("Please provide a search keyword.");
        }
        return new ParsedCommand(CommandType.FIND, -1, rest.trim(), null, null, null);
    }

    /**
     * Parses a todo command.
     */
    private static ParsedCommand parseTodoCommand(String rest) throws Bark {
        if (rest.trim().isEmpty()) {
            throw new Bark("Borf! No empty.");
        }
        return new ParsedCommand(CommandType.TODO, -1, rest, null, null, null);
    }

    /**
     * Parses a deadline command with description and due date.
     */
    private static ParsedCommand parseDeadlineCommand(String rest, DateTimeFormatter formatter) throws Bark {
        String[] parts = rest.split(" /by ", 2);
        if (parts.length < 2) {
            throw new Bark("Please use: deadline <description> /by <d/M/yyyy HHmm>");
        }

        String description = parts[0];
        LocalDateTime by = LocalDateTime.parse(parts[1], formatter);
        return new ParsedCommand(CommandType.DEADLINE, -1, description, by, null, null);
    }

    /**
     * Parses an event command with description, start time, and end time.
     */
    private static ParsedCommand parseEventCommand(String rest, DateTimeFormatter formatter) throws Bark {
        String[] parts = rest.split(" /from | /to ");
        if (parts.length < 3) {
            throw new Bark("Please use: event <description> /from <d/M/yyyy HHmm> /to <d/M/yyyy HHmm>");
        }

        String description = parts[0];
        LocalDateTime start = LocalDateTime.parse(parts[1], formatter);
        LocalDateTime end = LocalDateTime.parse(parts[2], formatter);
        return new ParsedCommand(CommandType.EVENT, -1, description, null, start, end);
    }

    private static int parseIndex(String text) throws Bark {
        if (text == null || text.trim().isEmpty()) {
            throw new Bark("Please specify a task number.");
        }
        try {
            return Integer.parseInt(text.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new Bark("Please provide a valid task number.");
        }
    }
}

