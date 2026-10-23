package ru.fintech.aleksey.morgachev.commands.impl;

import ru.fintech.aleksey.morgachev.commands.ShellCommand;
import ru.fintech.aleksey.morgachev.core.ShellContext;

/**
 * Команда выхода из эмулятора.
 */
public class ExitCommand implements ShellCommand {

    @Override
    public String execute(String[] args, ShellContext context) {
        // Завершаем работу виртуальной машины Java
        System.exit(0);
        return ""; // Недостижимый код, нужен для компиляции
    }
}
