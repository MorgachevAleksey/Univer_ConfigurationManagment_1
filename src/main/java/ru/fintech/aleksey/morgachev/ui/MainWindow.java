package ru.fintech.aleksey.morgachev.ui;

import ru.fintech.aleksey.morgachev.commands.impl.CdCommand;
import ru.fintech.aleksey.morgachev.commands.impl.ExitCommand;
import ru.fintech.aleksey.morgachev.commands.impl.LsCommand;
import ru.fintech.aleksey.morgachev.core.ShellContext;
import ru.fintech.aleksey.morgachev.core.ShellEngine;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainWindow extends JFrame {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 500;
    private static final int FONT_SIZE = 14;

    private JTextArea outputArea;
    private JTextField inputField;
    private final ShellEngine engine;

    /**
     * Создает и настраивает главное окно приложения.
     */
    public MainWindow() {
        super(generateWindowTitle()); // ТЗ Этап 1, п.2: Заголовок окна из реальных данных ОС

        this.engine = initEngine();

        setupUI();
        printWelcomeMessage();
    }

    /**
     * Инициализирует ядро эмулятора и регистрирует команды.
     *
     * @return Настроенный экземпляр ShellEngine.
     */
    private ShellEngine initEngine() {
        ShellContext context = new ShellContext();
        ShellEngine shellEngine = new ShellEngine(context);

        shellEngine.registerCommand("ls", new LsCommand());
        shellEngine.registerCommand("cd", new CdCommand());
        shellEngine.registerCommand("exit", new ExitCommand());

        return shellEngine;
    }

    /**
     * Формирует заголовок окна на основе данных реальной ОС.
     *
     * @return Строка вида "Эмулятор - [username@hostname]".
     */
    private static String generateWindowTitle() {
        String user = System.getProperty("user.name");
        String host = "unknown";
        try {
            host = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            // Игнорируем, оставляем unknown
        }
        return String.format("Эмулятор - [%s@%s]", user, host);
    }

    /**
     * Настраивает компоненты графического интерфейса.
     */
    private void setupUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(new BorderLayout());

        outputArea = createOutputArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        inputField = createInputField();
        add(inputField, BorderLayout.SOUTH);
    }

    /**
     * Создает и настраивает текстовую область для вывода.
     *
     * @return Настроенный JTextArea.
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
     * Создает и настраивает поле ввода.
     *
     * @return Настроенный JTextField.
     */
    private JTextField createInputField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Monospaced", Font.PLAIN, FONT_SIZE));
        field.setBackground(Color.BLACK);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // ТЗ Этап 1, п.6: Интерактивный режим (обработка нажатия Enter)
        field.addActionListener(e -> handleUserInput());
        return field;
    }

    /**
     * Обрабатывает ввод пользователя, отправляя команду в ядро эмулятора.
     */
    private void handleUserInput() {
        String rawInput = inputField.getText();
        inputField.setText(""); // Очищаем поле

        // Имитация диалога: выводим введенную строку
        appendToOutput(rawInput + "\n");

        if (!rawInput.trim().isEmpty()) {
            // ТЗ Этап 1, п.6: Обработка ошибок выполняется внутри engine.processInput
            String result = engine.processInput(rawInput);
            appendToOutput(result);
        }

        printPrompt();
    }

    /**
     * Добавляет текст в область вывода и прокручивает вниз.
     *
     * @param text Текст для добавления.
     */
    private void appendToOutput(String text) {
        outputArea.append(text);
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    /**
     * Выводит приглашение к вводу (текущую директорию).
     */
    private void printPrompt() {
        appendToOutput(engine.getContext().getCurrentDirectory() + " $ ");
    }

    /**
     * Выводит приветственное сообщение при старте.
     */
    private void printWelcomeMessage() {
        appendToOutput("Welcome to Java Shell Emulator (Stage 1)\n");
        appendToOutput("Try: ls -la, cd /tmp, $HOME, or exit\n\n");
        printPrompt();
    }
}

