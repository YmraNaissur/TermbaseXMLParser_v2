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

        System.out.println(doc);

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
            // теперь из каждого conceptgrp выделяем все элементы languageGrp
            List<Element> languageGroups = e.getElementsByTag("languageGrp");

                // Элементов languageGrp получается два (для оригинального и целевого языков).
                // Но почему-то бывает, что иногда первым в XML идет оригинальный, язык, а следующим - целевой,
                // а иногда наоборот

                // Проверяем все элементы term в первом languageGrp
                List<Element> terms = languageGroups.get(0).getElementsByTag("term");

                // Определяем, являются ли эти термины терминами оригинального языка
                if (languageGroups.get(0).getElementsByAttributeValue("lang", sourceLanguage).size() > 0) {
                    // Если да, значит заносим их сразу в список оригинальных терминов
                    for (Element term: terms) {
                        entry.originalTerms.add(term.text());
                    }

                    // Затем ищем все элементы term во втором languageGrp (целевые термины)
                    if (!(languageGroups.size() < 2)) {
                        terms = languageGroups.get(1).getElementsByTag("term");
                    }


                    for (Element term: terms) {
                        entry.targerTerms.add(term.text());
                    }
                } else {
                    // Если нет, значит это целевой язык и мы сначала заносим эти термины в список targetTerms
                    for (Element term: terms) {
                        entry.targerTerms.add(term.text());
                    }

                    // А затем ищем все элементы во втором languageGrp, они будут оригинальными
                    if (!(languageGroups.size() < 2)) {
                        terms = languageGroups.get(1).getElementsByTag("term");
                        for (Element term: terms) {
                            entry.originalTerms.add(term.text());
                        }
                    }
                }

                // Извлекаем все примечания
                List<Element> notes = e.getElementsByAttributeValue("type", "Note");
                if (notes.size() > notesSize) {
                    notesSize = notes.size();
                }

                for (Element note: notes) {
                    if (!entry.notes.contains(note.text())) {
                        entry.notes.add(note.text());
                    }
                }

            // добавляем созданную запись в список записей
            entryList.add(entry);
            entry = new Entry();
        }
    }

    public List<Entry> getEntryList() {
        return entryList;
    }
}
