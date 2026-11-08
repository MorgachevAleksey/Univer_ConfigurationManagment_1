package ru.fintech.aleksey.morgachev.commands;

import ru.fintech.aleksey.morgachev.core.ShellContext;

/** Общий интерфейс команд оболочки. */
public interface ShellCommand {

    /**
     * Выполняет команду.
     *
     * @param args аргументы без имени команды
     * @param context состояние оболочки
     * @return текст результата для GUI
     * @throws CommandException при ошибке выполнения
     */
    String execute(String[] args, ShellContext context)
            throws CommandException;
}