import java.util.ArrayList;
import java.util.List;

/**
 * Класс, хранящий записи из словаря.
 * Сюда будут сохраняться все строки в результате парсинга XML-файла.
 * На выходе будем получать список элементов класса Entry.
 * Created by Naissur on 04.04.2016.
 */

public class Entry {
    List<String> originalTerms = new ArrayList<>(); // список терминов на языке оригинала
    List<String> targerTerms = new ArrayList<>();   // список терминов на целевом языке
    List<String> notes = new ArrayList<>();         // список примечаний (может быть и пустым)
    String createdAt = "";  // дата создания термина
    String changedAt = "";  // дата последнего изменения термина
    // любой список может содержать не менее 1 элемента

    /**
     * Метод будет заполнять элементы записи пустыми строками,
     * если количество элементов одной записи не соответствует количеству элементов другой. Например:
     * originalTerm1    originalTerm2   targetTemr1 targetTerm2
     * стол             столешница      table       little table
     * автомобиль       <пустая строка> car         <пустая строка>
     * Это нужно делать для того, чтобы столбцы в результирующем файле не сдвигались влево
     */
    static void adjustList(List<Entry> list, int size) {

        for (Entry entry: list) {
            if ((entry.originalTerms.size() < size)) {
                for (int i = entry.originalTerms.size(); i < size; i++) {
                    entry.originalTerms.add("");
                }
            }

            if (entry.targerTerms.size() < size) {
                for (int i = entry.targerTerms.size(); i < size; i++) {
                    entry.targerTerms.add("");
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Original Terms: ");
        for (String s: originalTerms) {
            result.append(s).append("; ");
        }

        result.append("Target Terms: ");
        for (String s: targerTerms) {
            result.append(s).append("; ");
        }

        result.append("Notes: ");
        for (String s : notes) {
            result.append(s).append("; ");
        }

        result.delete(result.length() - 2, result.length());

        return result.toString();
    }
}