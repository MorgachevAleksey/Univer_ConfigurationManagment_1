package ru.fintech.aleksey.morgachev.core;

import org.junit.jupiter.api.Test;

import ru.fintech.aleksey.morgachev.commands.impl.CdCommand;
import ru.fintech.aleksey.morgachev.commands.impl.LsCommand;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Проверяет парсер и выполнение команд первого этапа. */
public class Test1 {

    /** Проверяет пустой ввод и разделение по пробельным символам. */
    @Test
    void parsesWhitespace() {
        Map<String, String> environment = Map.of();

        assertArrayEquals(
                new String[0],
                CommandParser.parse("   ", environment));

        assertArrayEquals(
                new String[]{"ls", "-la", "/tmp"},
                CommandParser.parse("  ls   -la\t/tmp  ", environment));
    }

    /** Проверяет переменные внутри аргумента. */
    @Test
    void expandsVariablesInsideArgument() {
        Map<String, String> environment = Map.of(
                "HOME", "/home/alex",
                "USER", "alex");

        assertArrayEquals(
                new String[]{"ls", "/home/alex/docs", "user=alex"},
                CommandParser.parse("ls $HOME/docs user=$USER", environment));
    }

    /** Проверяет буквальную подстановку обратных слешей и символа $. */
    @Test
    void preservesWindowsPaths() {
        Map<String, String> environment = Map.of(
                "USERPROFILE", "C:\\Users\\Alex$test");

        assertArrayEquals(
                new String[]{"ls", "C:\\Users\\Alex$test/docs"},
                CommandParser.parse("ls $USERPROFILE/docs", environment));
    }

    /** Проверяет выбранное поведение для отсутствующей переменной. */
    @Test
    void replacesMissingVariableWithEmptyString() {
        assertArrayEquals(
                new String[]{"ls", ""},
                CommandParser.parse("ls $MISSING", Map.of()));
    }

    /** Проверяет заглушку cd без аргументов. */
    @Test
    void acceptsCdWithoutArguments() {
        CdCommand command = new CdCommand();

        assertEquals(
                "cd: name=cd, args=[]\n",
                command.execute(new String[0], new ShellContext()));
    }

    /** Проверяет продолжение работы после неизвестной команды. */
    @Test
    void continuesAfterUnknownCommand() {
        ShellEngine engine = new ShellEngine(new ShellContext());
        engine.registerCommand("ls", new LsCommand());

        assertEquals(
                "Command not found: unknown_cmd\n",
                engine.processInput("unknown_cmd"));

        assertEquals(
                "ls: name=ls, args=[-la]\n",
                engine.processInput("ls -la"));
    }
}