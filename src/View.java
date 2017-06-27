import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Naissur on 05.04.2016.
 */

public class View {
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Сверхофигенное приложение для разбора словарей из XML"); // главное окно
        JPanel panel = new JPanel(); // на эту панель добавим все компоненты, а саму панель - на главное окно
        JLabel sourceFileNameFieldLabel = new JLabel("Путь к XML-файлу:");
        JTextField sourceFileNameField = new JTextField("g:/tmp/course.xml", 30); // поле для ввода пути к XML-файлу
        JLabel targetFileNameFieldLabel = new JLabel("Сохранить в:");
        JTextField targetFileNameField = new JTextField("g:/tmp/output.xls", 30); // поле для ввода пути к целевому файлу

        // создаем два списка для выбора исходного и целевого языков
        JComboBox<String> sourceLanguageSelector = initLanguageList();
        JComboBox<String> targetLanguageSelector = initLanguageList();
        sourceLanguageSelector.setSelectedIndex(2);
        targetLanguageSelector.setSelectedIndex(0);

        JButton executeButton = new JButton("Execute parsing"); // кнопка для запуска процесса парсинга

        // базовая операция нажатия на крестик - собственно, выход
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // добавляем слушателя событий для кнопки, и ее нажатие будет запускать парсинг
        // в метод execute() класса TermBaseXMLParser будут передаваться строки,
        // введенные в sourceFileNameField и targetFileNameField
        executeButton.addActionListener(e -> {
            String sourceLanguage = (String) sourceLanguageSelector.getSelectedItem();
            String targetLanguage = (String) targetLanguageSelector.getSelectedItem();
            try {
                new TermBaseXMLParser(sourceFileNameField.getText(), sourceLanguage, targetLanguage).execute(targetFileNameField.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        // добавляем компоненты на panel
        panel.add(sourceFileNameFieldLabel);
        panel.add(sourceFileNameField);
        panel.add(targetFileNameFieldLabel);
        panel.add(targetFileNameField);
        panel.add(sourceLanguageSelector);
        panel.add(targetLanguageSelector);
        panel.add(executeButton);

        // добавляем панель с компонентами на форму
        mainFrame.getContentPane().add(panel);
        mainFrame.pack();

        // отображаем форму
        mainFrame.setVisible(true);
    }

    /**
     * Инициализирует ComboBox'ы списком языковых обозначений
     * @return объект JComboBox, инициализированный списком доступных языков
     */
    private static JComboBox<String> initLanguageList() {
        String[] items = {"RU", "RU-RU", "EN", "EN-GB", "EN-US"};
        return new JComboBox<>(items);
    }
}
