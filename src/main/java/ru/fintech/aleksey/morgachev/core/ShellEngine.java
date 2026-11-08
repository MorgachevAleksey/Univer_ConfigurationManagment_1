package ru.fintech.aleksey.morgachev.core;

import ru.fintech.aleksey.morgachev.commands.CommandException;
import ru.fintech.aleksey.morgachev.commands.ShellCommand;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/** Находит и выполняет команды, обрабатывает ошибки. */
public class ShellEngine {

    private final ShellContext context;
    private final Map<String, ShellCommand> commands = new HashMap<>();

    /**
     * Создает движок оболочки.
     *
     * @param context общее состояние
     */
    public ShellEngine(ShellContext context) {
        this.context = context;
    }

    /**
     * Добавляет команду в реестр.
     *
     * @param name имя команды
     * @param command объект команды
     */
    public void registerCommand(String name, ShellCommand command) {
        commands.put(name, command);
    }

    /**
     * Разбирает строку и выполняет найденную команду.
     *
     * @param inputLine строка пользователя
     * @return результат или сообщение об ошибке
     */
    public String processInput(String inputLine) {
        try {
            String[] tokens = CommandParser.parse(inputLine);

            if (tokens.length == 0) {
                return "";
            }

            String commandName = tokens[0];
            ShellCommand command = commands.get(commandName);

            if (command == null) {
                return "Command not found: " + commandName + "\n";
            }

            String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);
            return command.execute(args, context);

        } catch (CommandException e) {
            return "Error: " + e.getMessage() + "\n";
        } catch (Exception e) {
            return "Unexpected error: " + e.getMessage() + "\n";
        }
    }

    /**
     * Возвращает состояние оболочки.
     *
     * @return общее состояние
     */
    public ShellContext getContext() {
        return context;
    }
}