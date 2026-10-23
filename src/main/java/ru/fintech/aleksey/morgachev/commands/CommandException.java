package ru.fintech.aleksey.morgachev.commands;

/**
 * Специализированное исключение для ошибок выполнения команд.
 * Позволяет отделить ошибки пользователя (неверный ввод) от системных сбоев.
 */
public class CommandException extends Exception {

    /**
     * Создает новое исключение с указанным сообщением.
     *
     * @param message Текст ошибки для отображения пользователю.
     */
    public CommandException(String message) {
        super(message);
    }
}
