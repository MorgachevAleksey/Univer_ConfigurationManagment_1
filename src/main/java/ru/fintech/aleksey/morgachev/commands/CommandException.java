package ru.fintech.aleksey.morgachev.commands;

/** Ожидаемая ошибка выполнения команды. */
public class CommandException extends Exception {

    /**
     * Создает ошибку с пояснением для пользователя.
     *
     * @param message описание ошибки
     */
    public CommandException(String message) {
        super(message);
    }
}