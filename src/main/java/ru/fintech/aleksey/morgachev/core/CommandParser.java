package ru.fintech.aleksey.morgachev.core;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Разбивает строку по пробельным символам и раскрывает переменные $VAR.
 * Кавычки и остальные конструкции языка оболочки пока не поддерживаются.
 */
public class CommandParser {

    private static final Pattern ENV_PATTERN =
            Pattern.compile("\\$([A-Za-z_][A-Za-z0-9_]*)");

    /** Запрещает создание объектов служебного класса. */
    private CommandParser() {
    }

    /**
     * Разбирает ввод с использованием окружения реальной ОС.
     *
     * @param inputLine строка пользователя
     * @return имя команды и аргументы либо пустой массив
     */
    public static String[] parse(String inputLine) {
        return parse(inputLine, System.getenv());
    }

    /**
     * Разбирает ввод с заданным окружением для воспроизводимых тестов.
     *
     * @param inputLine строка пользователя
     * @param environment таблица переменных окружения
     * @return имя команды и аргументы
     */
    static String[] parse(String inputLine, Map<String, String> environment) {
        if (inputLine == null || inputLine.trim().isEmpty()) {
            return new String[0];
        }

        String[] tokens = inputLine.trim().split("\\s+");

        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = expandVariables(tokens[i], environment);
        }

        return tokens;
    }

    /**
     * Подставляет переменные, сохраняя окружающий текст.
     *
     * @param token обрабатываемый элемент строки
     * @param environment таблица переменных окружения
     * @return текст после подстановки
     */
    private static String expandVariables(
            String token, Map<String, String> environment) {
        Matcher matcher = ENV_PATTERN.matcher(token);
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String value = environment.get(matcher.group(1));

            if (value == null) {
                value = "";
            }

            matcher.appendReplacement(
                    result, Matcher.quoteReplacement(value));
        }

        matcher.appendTail(result);
        return result.toString();
    }
}