package ru.fintech.aleksey.morgachev.core;

import ru.fintech.aleksey.morgachev.commands.CommandException;
import ru.fintech.aleksey.morgachev.commands.ShellCommand;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ShellEngine {
    /** Контекст эмулятора (состояние). */
    private final ShellContext context;

    /** Реестр доступных команд (имя команды -> объект команды). */
    private final Map<String, ShellCommand> commands;

    /**
     * Создает новый экземпляр движка.
     *
     * @param context Контекст эмулятора, который будет передаваться командам.
     */
    public ShellEngine(ShellContext context) {
        this.context = context;
        this.commands = new HashMap<>();
    }

    /**
     * Регистрирует новую команду в реестре.
     *
     * @param name    Имя команды (например, "ls", "cd").
     * @param command Экземпляр класса, реализующий интерфейс ShellCommand.
     */
    public void registerCommand(String name, ShellCommand command) {
        commands.put(name, command);
    }

    /**
     * Обрабатывает сырую строку ввода от пользователя.
     * Разбивает её на токены, находит нужную команду и выполняет её.
     *
     * @param inputLine Сырая строка ввода (например, "ls -la").
     * @return Результат выполнения команды или текст ошибки для вывода в GUI.
     */
    public String processInput(String inputLine) {
        // 1. Отдаем строку парсеру (он разобьет по пробелам и раскроет $HOME)
        String[] tokens = CommandParser.parse(inputLine);

        // Если ввели пустую строку или одни пробелы — ничего не делаем
        if (tokens.length == 0) {
            return "";
        }

        // 2. Отделяем имя команды от аргументов
        String commandName = tokens[0];
        String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);

        // 3. Ищем команду в реестре
        ShellCommand command = commands.get(commandName);

        // Обработка ошибки: неизвестная команда (ТЗ Этап 1, п.4)
        if (command == null) {
            return "Command not found: " + commandName + "\n";
        }

        // 4. Выполняем команду
        try {
            return command.execute(args, context);
        } catch (CommandException e) {
            // Обработка ожидаемых ошибок (например, неверные аргументы)
            return "Error: " + e.getMessage() + "\n";
        } catch (Exception e) {
            // Обработка непредвиденных ошибок (чтобы GUI не зависал)
            return "Unexpected error: " + e.getMessage() + "\n";
        }
    }

    /**
     * Возвращает текущий контекст эмулятора.
     * Используется GUI для отображения приглашения (например, текущей папки).
     *
     * @return Объект ShellContext.
     */
    public ShellContext getContext() {
        return context;
    }
}
