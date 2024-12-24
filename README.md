# Как запустить JAR файл

Для запуска `jar` файла необходимо собрать проект командой:

```bash
mvn clean package
```

После выполнения этой команды в папке `target` будет находиться файл `FilteringFileContents.jar`, который можно скопировать и использовать для фильтрации данных.

## Тестовые файлы

В корне проекта также есть 4 тестовых файла: `in1.txt`, `in2.txt`, `in3.txt`, и `in4.txt`. Эти файлы содержат случайные данные типов `Long`, `Double` и `String`.

## Запуск JAR файла

Запуск `jar` файла можно осуществить командой:

```bash
java -jar FilteringFileContents.jar <options> <test-files.txt>
```

Порядок записи опций не влияет, так как для обработки команды есть класс `ParserOptionsCmd`.

## Обработка ошибок

Все возможные виды ошибок обработаны и имеют соответствующий уровень логирования.

### Пример запуска:

```bash
java -jar FilteringFileContents.jar in1.txt in2.txt in3.txt in4.txt -p test_ -s -a
```

**Пример вывода:**

```plaintext
2024-12-24 17:07:54 WARN  - Файла не существует по указанному пути: D:\Java-projects\test-filtering\test_integers.txt будет создан новый
2024-12-24 17:07:54 WARN  - Файла не существует по указанному пути: D:\Java-projects\test-filtering\test_floats.txt будет создан новый
2024-12-24 17:07:54 WARN  - Файла не существует по указанному пути: D:\Java-projects\test-filtering\test_strings.txt будет создан новый
2024-12-24 17:07:54 INFO  -

Количество целочисленных элементов в файле in1.txt:
2

Количество вещественных элементов в файле in1.txt:
2

Количество строк в файле in1.txt:
5

Количество целочисленных элементов в файле in2.txt:
1

Количество вещественных элементов в файле in2.txt:
1

Количество строк в файле in2.txt:
3

Количество целочисленных элементов в файле in3.txt:
343

Количество вещественных элементов в файле in3.txt:
315

Количество строк в файле in3.txt:
342

Количество целочисленных элементов в файле in4.txt:
329

Количество вещественных элементов в файле in4.txt:
342

Количество строк в файле in4.txt:
329
```

## Требования к программному обеспечению

- **Версия Java:**
  17

- **Версия Maven:**
  3.9.2

## Библиотеки

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.7.0</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.7.0</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>commons-cli</groupId>
    <artifactId>commons-cli</artifactId>
    <version>1.8.0</version>
</dependency>

<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

## Тестирование

Проект покрыт тестами, но при полном запуске теста с проверками логов происходит ошибка, так как при повторном перехвате вывода данные не получаются. Если запустить каждый тест вручную (раскомментировав `Assert` с проверкой лога), то тесты будут проходить.
