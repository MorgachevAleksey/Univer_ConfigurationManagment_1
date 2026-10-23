package ru.fintech.aleksey.morgachev.commands.impl;

import ru.fintech.aleksey.morgachev.commands.CommandException;
import ru.fintech.aleksey.morgachev.commands.ShellCommand;
import ru.fintech.aleksey.morgachev.core.ShellContext;

import java.util.Arrays;

/**
 * Заглушка команды cd. Выводит свое имя и аргументы.
 * Проверяет наличие аргументов для имитации обработки ошибок.
 */
public class CdCommand implements ShellCommand {

    @Override
    public String execute(String[] args, ShellContext context) throws CommandException {
        // Обработка ошибки: если пользователь ввел просто "cd" без пути
        if (args.length == 0) {
            throw new CommandException("cd: missing operand");
        }

        return "cd: name=cd, args=" + Arrays.toString(args) + "\n";
    }
}
