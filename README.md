# TermbaseXMLParser_v2
An updated version of the Multiterm XML parser to Excel

* * *

ENGLISH

Hi, my name is Max and I have been working in a translation agency as an administrator of SDL Trados software for 5 years.
This software is one of the most used CAT tools and is intended for translators' job automatization. It consists of three main components:

1) SDL GroupShare - a server-side software for managing users, roles, resources etc..
2) SDL Trados Studio - a client-side workspace for the translation process, which is able to work with a different file types, maintains translation memories and project managaing.
3) SDL MultiTerm - a software for managing termbases (glossaries).

Sometimes we need to convert entries from some termbase into other file type to review it or to send it to customer.
SDL MultiTerm allows to perform some kinds of export tasks, including an export into XML format.

My project have been started when I had had a necessity to convert a termbase into .xls file according to some formatting rules, and there where no third party applications to perform this task.
It takes an XML file, exported from SDL Multiterm, and converts it to .xls. The structure is:

Original Term | Target Term   | Note
--------------|---------------|----------------
Terms         | Translations  | Descriptions

* * *

РУССКИЙ

Здравствуйте, меня зовут Макс, и я работаю в бюро переводов в качестве администратора ПО SDL Trados в течение 5 лет.
Это ПО является одним из наиболее используемых CAT-инструментов и предназначено для автоматизации работы переводчиков. Оно состоит из трех основных компонентов:

1) SDL GroupShare - серверная часть для управления пользователями, ролями, ресурсами и т. д..
2) SDL Trados Studio - клиентская часть, которая работает с различными типами файлов, поддерживает технологию памятей переводов и позволяет управлять проектами.
3) SDL MultiTerm - программное обеспечение для управления терминологическими базами (глоссариями).

Иногда нам нужно преобразовать записи из некоторой базы терминов в другой тип файла, чтобы редактировать его или отправить клиенту.
SDL MultiTerm позволяет выполнять некоторые виды экспорта, включая экспорт в формат XML.

Данный проект был начат мною, когда у меня возникла необходимость конвертировать терминологическую базу в файл .xls в соответствии с некоторыми правилами форматирования, и я не нашел сторонних приложений, которые могли бы удовлетворить моим потребностям.
Приложение принимает XML-файл, экспортированный из SDL Multiterm, и преобразует его в .xls. Структура конечного файла следующая:

Оригинал    | Перевод   | Note
------------|-----------|-----------
Термины     | Переводы  | Описания
