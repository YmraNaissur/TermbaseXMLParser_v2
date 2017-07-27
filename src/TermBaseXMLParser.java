import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Naissur on 04.04.2016.
 */

public class TermBaseXMLParser {
    private int notesSize = 0; // записываем сюда нужно кол-во столбцов под примечания
    private String sourceLanguage; // исходный язык

    private List<Entry> entryList = new ArrayList<>();

    private Document doc; // здесь будет содержаться xml-структура файла xmlToBeParsed

    TermBaseXMLParser(String filePath, String sourceLanguage, String targetLanguage) {
        File xmlToBeParsed = new File(filePath); // xml-файл с экспортированным словарем

        try {
            doc = Jsoup.parse(xmlToBeParsed, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.sourceLanguage = sourceLanguage;
        setEntryList();

        // круто делает отступы в xml, а то стандартный экспорт мультитерма выдает просто сплошняком текст
        // можно будет потом удалить, когда все будет парситься ровно
        //System.out.println(doc);
    }

    void execute(String targetFile) throws IOException {
        int termSize = 5; // Максимальное количество терминов одного языка

        // уравниваем количество элементов в полях объектов Entry
        Entry.adjustList(entryList, termSize);

        // вызываем метод writeToExcel, чтобы записать все элементы Entry из списка в Excel-файл
        OutputHelper.writeToExcel(entryList, targetFile, termSize, notesSize);
    }

    /**
     * Метод, инициализирующий список словарных записей
     */
    private void setEntryList() {
        // создаем новую словарную статью
        Entry entry = new Entry();

        // извлекаем из XML-ки все теги <conceptgrp>
        List<Element> elements = doc.getElementsByTag("conceptGrp");

        for (Element e: elements) {
            // ищем все элементы, отвечающие за язык
            List<Element> langElements = e.getElementsByTag("languageGrp");

            // Извлекаем все термины
            for (Element languageGrp : langElements) {
                // Если текущий элемент отвечает за исходный язык, то добавляем термины из него в originalTerms
                if (!languageGrp.getElementsByAttributeValue("lang", sourceLanguage).isEmpty()) {
                    extractTerms(entry.originalTerms, languageGrp);
                } else {    // иначе добавляем их в targetTerms
                    extractTerms(entry.targerTerms, languageGrp);
                }
            }

            // Извлекаем все примечания
            List<Element> notes = e.getElementsByAttributeValue("type", "Note");
            if (notes.size() > notesSize) {
                notesSize = notes.size();
            }

            for (Element note : notes) {
                if (!entry.notes.contains(note.text())) {
                    entry.notes.add(note.text());
                }
            }

            // добавляем созданную запись в список записей
            entryList.add(entry);
            entry = new Entry();
        }
    }

    /**
     * Возвращает список всех извлеченных словарных записей
     * @return список объектов Entry
     */
    List<Entry> getEntryList() {
        return entryList;
    }

    /**
     * Извлекает все элементы, находящиеся между тегами term внутри указанного родительского элемента
     * @param list список, в который нужно будет добавить термины
     * @param element - родительский элемент, внутри которого нужно найти элементы term
     */
    private void extractTerms(List<String> list, Element element) {
        for (Element term : element.select("term")) {
            list.add(term.text());
        }
    }
}
