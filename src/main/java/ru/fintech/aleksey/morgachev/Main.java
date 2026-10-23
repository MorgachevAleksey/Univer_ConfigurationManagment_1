package ru.fintech.aleksey.morgachev;

import ru.fintech.aleksey.morgachev.ui.MainWindow;

import javax.swing.*;

/**
 * Главный класс приложения. Точка входа.
 */
public class Main {

    /**
     * Запускает приложение.
     *
     * @param args Аргументы командной строки (пока не используются).
     */
    public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
            window.requestFocusInWindow(); // Устанавливаем фокус на окно
        });
    }
}