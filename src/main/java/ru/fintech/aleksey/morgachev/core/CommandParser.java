package ru.fintech.aleksey.morgachev.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс-парсер для обработки ввода пользователя.
 * Отвечает за разбиение строки на токены и раскрытие переменных окружения.
 */
public class CommandParser {
    /**
     * Разбивает входную строку на массив токенов (команда и аргументы),
     * а также подставляет значения переменных окружения (например, $HOME).
     *
     * @param inputLine Сырая строка, введенная пользователем.
     * @return Массив строк (токенов). Если строка пустая, возвращает пустой массив.
     */
    public static String[] parse(String inputLine) {
        // Проверка на пустой ввод или строку из одних пробелов
        if (inputLine == null || inputLine.trim().isEmpty()) {
            return new String[0];
        }

        // Разбиваем строку по одному или нескольким пробелам
        String[] rawTokens = inputLine.trim().split("\\s+");
        List<String> processedTokens = new ArrayList<>();

        for (String token : rawTokens) {
            // Если токен начинается с $ и имеет длину больше 1 (то есть это не просто символ $)
            if (token.startsWith("$") && token.length() > 1) {
                String envVarName = token.substring(1); // Отрезаем символ $
                String envValue = System.getenv(envVarName); // Получаем значение из реальной ОС

                if (envValue != null) {
                    // Если переменная существует, подставляем её значение
                    processedTokens.add(envValue);
                } else {
                    // Если переменной нет, по аналогии с UNIX-подобными ОС заменяем её на пустую строку.
                    // (Или можно оставить как есть, но пустая строка безопаснее для эмулятора)
                    processedTokens.add("");
                }
            } else {
                // Если это обычный токен, просто добавляем его
                processedTokens.add(token);
            }
        }

        return processedTokens.toArray(new String[0]);
    }
}
