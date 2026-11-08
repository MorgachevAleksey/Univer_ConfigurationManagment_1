package ru.fintech.aleksey.morgachev.commands.impl;

import ru.fintech.aleksey.morgachev.commands.ShellCommand;
import ru.fintech.aleksey.morgachev.core.ShellContext;

import java.util.Arrays;

/** Заглушка cd: выводит имя и переданные аргументы. */
public class CdCommand implements ShellCommand {

    /** {@inheritDoc} */
    @Override
    public String execute(String[] args, ShellContext context) {
        return "cd: name=cd, args=" + Arrays.toString(args) + "\n";
    }
}