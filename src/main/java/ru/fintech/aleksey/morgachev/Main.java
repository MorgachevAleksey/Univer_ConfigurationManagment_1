package ru.fintech.aleksey.morgachev;

import ru.fintech.aleksey.morgachev.ui.MainWindow;

import javax.swing.SwingUtilities;

/** Точка входа в приложение. */
public class Main {

    /**
     * Создает окно в потоке обработки событий Swing.
     *
     * @param args аргументы запуска, пока не используются
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}