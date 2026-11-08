package ru.fintech.aleksey.morgachev.core;

/** Хранит общее состояние оболочки. */
public class ShellContext {

    private String currentDirectory = "/";

    /** Задает начальный каталог на основе данных Java. */
    public ShellContext() {
        String home = System.getProperty("user.home");

        if (home != null && !home.isBlank()) {
            currentDirectory = home.replace('\\', '/');
        }
    }

    /**
     * Возвращает текущий каталог.
     *
     * @return каталог для приглашения
     */
    public String getCurrentDirectory() {
        return currentDirectory;
    }

    /**
     * Изменяет текущий каталог.
     *
     * @param currentDirectory новый каталог
     */
    public void setCurrentDirectory(String currentDirectory) {
        this.currentDirectory = currentDirectory;
    }
}