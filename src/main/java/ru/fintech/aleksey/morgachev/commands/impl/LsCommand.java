package ru.fintech.aleksey.morgachev.commands.impl;

import ru.fintech.aleksey.morgachev.commands.ShellCommand;
import ru.fintech.aleksey.morgachev.core.ShellContext;

import java.util.Arrays;

public class LsCommand implements ShellCommand {

    @Override
    public String execute(String[] args, ShellContext context) {
        return "ls: name=ls, args=" + Arrays.toString(args) + "\n";
    }
}
