import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import jxl.*;
import jxl.write.*;

/**
 * Содержит статические методы для вывода результирующей информации в файл
 * Created by Naissur on 04.04.2016.
 */

public class OutputHelper {
    /**
     * Выводит список записей в TXT-файл, разделяя термины табуляцией, а записи - новой строкой.
     * @param entryList - спискок словарных статей (элементов Entry)
     * @param fileName - имя файла, в которое нам следует записать данные
     */
    public static void writeToTXT(List<Entry> entryList, String fileName) {
        StringBuffer resultString = new StringBuffer(); // очередную строку для записи в файл будем собирать StringBuffer'ом

        try (FileWriter fout = new FileWriter(new File(fileName))) {
            // проходим по всем словарным статьям
            for (Entry e: entryList) {
                // сначала добавляем в результирующую строку термины на оригинальном языке
                for (String s: e.originalTerms) {
                    resultString.append(s).append("\t");
                }

                // затем добавляем в результирующую строку термины на целевом языке
                for (String s: e.targerTerms) {
                    resultString.append(s).append("\t");
                }

                // пишем в файл результирующую строку, символ переноса строки, и очищаем StringBuffer для следующей итерации
                fout.write(resultString.toString().trim());
                fout.write("\r\n");
                resultString = new StringBuffer();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeToExcel(List<Entry> entryList, String fileName, int termSize, int notesSize) throws IOException {
        // создаем книгу и лист Excel
        WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
        WritableSheet sheet = workbook.createSheet("Export", 0);

        for (int i = 0; i < termSize; i++) {
            Label label = new Label(i, 0, "Original Term");
            writeCell(sheet, label);
        }

        for (int i = termSize; i < termSize * 2; i++) {
            Label label = new Label(i, 0, "Target Term");
            writeCell(sheet, label);
        }

        for (int i = termSize*2; i < termSize*2 + 1 + notesSize; i++) {
            Label label = new Label(i, 0, "Note");
            writeCell(sheet, label);
        }

        // проходим по всем элементам списка entryList
        for (int i = 0; i < entryList.size(); i++) {
            int columnNumber = 0;

            // пишем в Excel оригинальные термины
            for (String s: entryList.get(i).originalTerms) {
                Label label = new Label(columnNumber++, i + 1, s);
                writeCell(sheet, label);
            }

            // пишем в Excel целевые термины
            for (String s: entryList.get(i).targerTerms) {
                Label label = new Label(columnNumber++, i + 1, s);
                writeCell(sheet, label);
            }

            // пишем в Excel примечания
            for (String s: entryList.get(i).notes) {
                Label label = new Label(columnNumber++, i + 1, s);
                writeCell(sheet, label);
            }
        }

        System.out.println("Пытаемся закрыть книгу excel");

        workbook.write();
        try {
            workbook.close();
        } catch (WriteException e) {
            e.printStackTrace();
        }

        System.out.println("Книга excel закрыта. Экспорт успешно завершен.");
    }

    private static void writeCell(WritableSheet sheet, Label label) {
        try {
            sheet.addCell(label);
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
}
