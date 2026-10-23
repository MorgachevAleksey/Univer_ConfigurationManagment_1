package ru.fintech.aleksey.morgachev.core;

/**
 * Класс контекста эмулятора.
 * Хранит состояние оболочки (например, текущую директорию).
 */
public class ShellContext {
    private String currentDirectory = "/";

    // Имитация домашней директории для красивого приветствия
    public ShellContext() {
        String home = System.getProperty("user.home");
        if (home != null) {
            this.currentDirectory = home.replace("\\", "/"); // Для Windows путей
        }
    }

    public String getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(String currentDirectory) {
        this.currentDirectory = currentDirectory;
    }
}
