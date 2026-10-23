package ru.fintech.aleksey.morgachev.commands;

public class CommandException extends Throwable {

    /**
     * Создает новое исключение с указанным сообщением.
     *
     * @param message Текст ошибки для отображения пользователю.
     */
    public CommandException(String message) {
        super(message);
    }
}
