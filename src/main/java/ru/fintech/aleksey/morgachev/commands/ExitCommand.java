package ru.fintech.aleksey.morgachev.commands.impl;

import ru.fintech.aleksey.morgachev.commands.ShellCommand;
import ru.fintech.aleksey.morgachev.core.ShellContext;

/** Завершает работу приложения. */
public class ExitCommand implements ShellCommand {

    /** {@inheritDoc} */
    @Override
    public String execute(String[] args, ShellContext context) {
        System.exit(0);
        return "";
    }
}