package ru.fintech.aleksey.morgachev.commands;

import ru.fintech.aleksey.morgachev.core.ShellContext;

/**
 * Интерфейс для всех команд эмулятора (паттерн "Команда").
 */
public interface ShellCommand {

    /**
     * Выполняет команду с заданными аргументами.
     *
     * @param args    Массив аргументов (без имени самой команды).
     * @param context Контекст эмулятора (содержит состояние, например, текущую директорию).
     * @return Строка с результатом работы команды для вывода в GUI.
     * @throws CommandException Если команда выполнена с ошибкой (неверные аргументы и т.д.).
     */
    String execute(String[] args, ShellContext context) throws CommandException;
}
