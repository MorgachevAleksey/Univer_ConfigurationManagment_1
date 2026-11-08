package ru.fintech.aleksey.morgachev.ui;

import ru.fintech.aleksey.morgachev.commands.impl.CdCommand;
import ru.fintech.aleksey.morgachev.commands.impl.ExitCommand;
import ru.fintech.aleksey.morgachev.commands.impl.LsCommand;
import ru.fintech.aleksey.morgachev.core.ShellContext;
import ru.fintech.aleksey.morgachev.core.ShellEngine;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import java.net.InetAddress;
import java.net.UnknownHostException;

/** Графический интерфейс эмулятора оболочки. */
public class MainWindow extends JFrame {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 500;
    private static final int FONT_SIZE = 14;
    private static final int INPUT_PADDING = 5;

    private final ShellEngine engine;
    private JTextArea outputArea;
    private JTextField inputField;

    /** Создает движок и графический интерфейс. */
    public MainWindow() {
        super(generateWindowTitle());

        engine = initEngine();
        setupUI();
        printWelcomeMessage();

        SwingUtilities.invokeLater(() -> inputField.requestFocusInWindow());
    }

    /**
     * Создает движок и регистрирует команды.
     *
     * @return настроенный движок
     */
    private ShellEngine initEngine() {
        ShellEngine shellEngine = new ShellEngine(new ShellContext());

        shellEngine.registerCommand("ls", new LsCommand());
        shellEngine.registerCommand("cd", new CdCommand());
        shellEngine.registerCommand("exit", new ExitCommand());

        return shellEngine;
    }

    /**
     * Формирует заголовок на основе реальных данных ОС.
     *
     * @return заголовок окна
     */
    private static String generateWindowTitle() {
        String user = System.getProperty("user.name");
        return String.format("Эмулятор - [%s@%s]", user, getHostName());
    }

    /**
     * Получает имя компьютера.
     *
     * @return имя компьютера либо unknown
     */
    private static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            String host = System.getenv("COMPUTERNAME");

            if (host == null || host.isBlank()) {
                host = System.getenv("HOSTNAME");
            }

            if (host == null || host.isBlank()) {
                return "unknown";
            }

            return host;
        }
    }

    /** Настраивает окно и расположение компонентов. */
    private void setupUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        outputArea = createOutputArea();
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        inputField = createInputField();
        add(inputField, BorderLayout.SOUTH);
    }

    /**
     * Создает область истории диалога.
     *
     * @return область вывода
     */
    private JTextArea createOutputArea() {
        JTextArea area = new JTextArea();

        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, FONT_SIZE));
        area.setBackground(Color.BLACK);
        area.setForeground(Color.GREEN);

        return area;
    }

    /**
     * Создает поле ввода с обработкой Enter.
     *
     * @return поле ввода
     */
    private JTextField createInputField() {
        JTextField field = new JTextField();

        field.setFont(new Font("Monospaced", Font.PLAIN, FONT_SIZE));
        field.setBackground(Color.BLACK);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);

        field.setBorder(BorderFactory.createEmptyBorder(
                INPUT_PADDING, INPUT_PADDING,
                INPUT_PADDING, INPUT_PADDING));

        field.addActionListener(e -> handleUserInput());

        return field;
    }

    /** Передает введенную строку движку и показывает результат. */
    private void handleUserInput() {
        String rawInput = inputField.getText();
        inputField.setText("");

        appendToOutput(rawInput + "\n");
        appendToOutput(engine.processInput(rawInput));

        printPrompt();
    }

    /**
     * Добавляет текст и прокручивает историю вниз.
     *
     * @param text добавляемый текст
     */
    private void appendToOutput(String text) {
        outputArea.append(text);
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    /** Выводит приглашение к вводу. */
    private void printPrompt() {
        appendToOutput(engine.getContext().getCurrentDirectory() + " $ ");
    }

    /** Выводит приветствие и примеры для текущей ОС. */
    private void printWelcomeMessage() {
        appendToOutput("Java Shell Emulator - Stage 1\n");
        appendToOutput("Commands: ls, cd, exit\n");

        if (System.getenv("HOME") != null) {
            appendToOutput("Try: ls $HOME, ls $HOME/docs\n");
        } else if (System.getenv("USERPROFILE") != null) {
            appendToOutput("Try: ls $USERPROFILE, ls $USERPROFILE/docs\n");
        }

        appendToOutput("Try unknown_cmd to see error handling.\n\n");
        printPrompt();
    }
}