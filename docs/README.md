# Bond Forger User Guide

Bond Forger is a task-management chatbot that helps you track to-dos, deadlines, and events. You can use it from the **terminal** (text-based) or the **GUI** (JavaFX). Tasks are saved to a data file so they persist between sessions.

---

## Quick start

1. **Run the application**
   - **GUI:** From the project root (`ip`), run `.\gradlew run` (Windows) or `./gradlew run` (Mac/Linux).
   - **Terminal:** Run the main class `forger.BondForger` (e.g. from your IDE or `java -cp ... forger.BondForger`).

2. Type a command and press Enter (terminal) or type in the chat box and press Send (GUI).

3. Type **`bye`** when you are done; your tasks are saved automatically.

---

## Commands

All commands are **case-insensitive** (e.g. `LIST`, `list`, and `List` all work).

| Command   | Description |
|----------|-------------|
| `list`   | Show all tasks. |
| `todo`   | Add a simple task with no date. |
| `deadline` | Add a task with a due date and time. |
| `event`  | Add a task with a start and end time. |
| `mark`   | Mark a task as done. |
| `unmark` | Mark a task as not done. |
| `delete` | Remove a task from the list. |
| `find`   | Search tasks by keyword. |
| `bye`    | Save tasks and exit. |

---

## Adding tasks

### Todo (no date)

Add a task with just a description.

**Format:** `todo <description>`

**Examples:**
- `todo read book`
- `todo buy groceries`

**Outcome:** The task is added and shown with a `[T]` tag. You see a confirmation and the new total number of tasks.

---

### Deadline (due date and time)

Add a task that must be done by a specific date and time.

**Format:** `deadline <description> /by <date and time>`

**Date/time format:** `d/M/yyyy HHmm`  
Examples: `3/2/2026 1430` (3 Feb 2026, 2:30 PM), `25/12/2025 0900` (25 Dec 2025, 9:00 AM).

**Examples:**
- `deadline return book /by 11/4/2026 2200`
- `deadline submit report /by 1/3/2026 1700`

**Outcome:** The task is added with a `[D]` tag and the due time. If that time clashes with an existing deadline or event, Bond Forger will show an error and not add the task.

---

### Event (start and end time)

Add a task that happens during a time range.

**Format:** `event <description> /from <date and time> /to <date and time>`

**Date/time format:** Same as deadline, `d/M/yyyy HHmm`.

**Examples:**
- `event team meeting /from 8/12/2025 1820 /to 9/12/2025 1900`
- `event lunch /from 3/2/2026 1200 /to 3/2/2026 1300`

**Outcome:** The task is added with an `[E]` tag and the time range. The end time must be after the start time. If the range overlaps with an existing deadline or event, Bond Forger will report a schedule clash and not add the task.

---

## Managing tasks

### List all tasks

**Command:** `list`

**Outcome:** All tasks are shown with their type (`[T]`, `[D]`, or `[E]`), status (`[X]` done or `[ ]` not done), description, and for deadlines/events the date/time.

---

### Mark as done / not done

**Format:** `mark <number>` or `unmark <number>`

The number is the one shown in the list (1-based).

**Examples:**
- `mark 1`
- `unmark 2`

**Outcome:** The task is marked as done (`[X]`) or not done (`[ ]`). If the number is invalid, you get an error message.

---

### Delete a task

**Format:** `delete <number>`

**Example:** `delete 3`

**Outcome:** That task is removed and you see the new total number of tasks. Invalid numbers produce an error.

---

### Find tasks by keyword

**Format:** `find <keyword>`

**Example:** `find book`

**Outcome:** All tasks whose description contains the keyword (e.g. “read book”, “return book”) are listed. The search is case-sensitive in the current implementation.

---

## Exiting and saving

**Command:** `bye`

**Outcome:** All tasks are saved to the data file, a farewell message is shown, and the application exits. You do not need to run a separate “save” command; saving happens when you type `bye`.

---

## Schedule clashes

When you add a **deadline** or **event**, Bond Forger checks for clashes:

- Two **deadlines** at the same time.
- A **deadline** at a time that falls inside an **event**.
- Two **events** whose time ranges overlap.

If there is a clash, the new task is **not** added and you see which existing task(s) clash. You can change the time or remove the clashing task first, then try again.

---

## Errors and invalid input

- **Empty or unknown command:** You are asked to enter a valid command.
- **Missing arguments:** For example, `todo` with no description or `mark` with no number — you get a short message explaining what is needed.
- **Invalid task number:** If the number for `mark`, `unmark`, or `delete` is out of range, you see “That task number does not exist.”
- **Wrong date/time format:** For `deadline` and `event`, use `d/M/yyyy HHmm` (e.g. `3/2/2026 1430`). Otherwise you get a parse error.
- **Event end before start:** You get a message that the end time must be after the start time.

---

## Summary of formats

| Item        | Format / example |
|------------|-------------------|
| Date & time | `d/M/yyyy HHmm` (e.g. `3/2/2026 1430`) |
| Todo       | `todo <description>` |
| Deadline   | `deadline <description> /by <date time>` |
| Event      | `event <description> /from <date time> /to <date time>` |
| Mark/Unmark/Delete | `mark <number>`, `unmark <number>`, `delete <number>` |
| Find       | `find <keyword>` |

Enjoy using Bond Forger.
