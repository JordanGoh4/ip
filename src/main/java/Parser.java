package ip.src.main.java;

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
        if (input == null || input.trim().isEmpty()) {
            throw new Bark("Please enter a command.");
        }

        String[] parts = input.split(" ", 2);
        String command = parts[0];
        String rest = parts.length > 1 ? parts[1] : "";

        switch (command) {
        case "bye":
            return new ParsedCommand(CommandType.BYE, -1, null, null, null, null);
        case "list":
            return new ParsedCommand(CommandType.LIST, -1, null, null, null, null);
        case "find":
            if (rest.trim().isEmpty()) {
                throw new Bark("Please provide a search keyword.");
            }
            return new ParsedCommand(CommandType.FIND, -1, rest.trim(), null, null, null);
        case "mark":
            return new ParsedCommand(CommandType.MARK, parseIndex(rest), null, null, null, null);
        case "unmark":
            return new ParsedCommand(CommandType.UNMARK, parseIndex(rest), null, null, null, null);
        case "delete":
            return new ParsedCommand(CommandType.DELETE, parseIndex(rest), null, null, null, null);
        case "todo":
            if (rest.trim().isEmpty()) {
                throw new Bark("Borf! No empty.");
            }
            return new ParsedCommand(CommandType.TODO, -1, rest, null, null, null);
        case "deadline": {
            String[] deadlineParts = rest.split(" /by ", 2);
            if (deadlineParts.length < 2) {
                throw new Bark("Please use: deadline <description> /by <d/M/yyyy HHmm>");
            }
            String description = deadlineParts[0];
            String byText = deadlineParts[1];
            LocalDateTime by = LocalDateTime.parse(byText, formatter);
            return new ParsedCommand(CommandType.DEADLINE, -1, description, by, null, null);
        }
        case "event": {
            String[] eventParts = rest.split(" /from | /to ");
            if (eventParts.length < 3) {
                throw new Bark("Please use: event <description> /from <d/M/yyyy HHmm> /to <d/M/yyyy HHmm>");
            }
            String description = eventParts[0];
            LocalDateTime start = LocalDateTime.parse(eventParts[1], formatter);
            LocalDateTime end = LocalDateTime.parse(eventParts[2], formatter);
            return new ParsedCommand(CommandType.EVENT, -1, description, null, start, end);
        }
        default:
            throw new Bark("Bark Bark intruder alert!");
        }
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

