import java.io.IOException;
import java.util.List;

/**
 * Класс для тестирования работы приложения
 * Created by Naissur on 11.04.2016.
 */

public class ConsoleTesterClass {
    public static void main(String[] args) throws IOException {
        // создаем объект TermBaseXMLParser, в нем уже будет храниться список разобранных словарных записей
        TermBaseXMLParser parser = new TermBaseXMLParser("G:\\tmp\\tank\\tank_ru_en.xml", "RU", "EN-US");

        // получаем список словарных записей из созданного выше объекта
        List<Entry> entryList = parser.getEntryList();
        for (Entry e: entryList) {
            System.out.println(e);
        }
    }
}