package by.sharftim.parser;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserFileTest {

    private final String filepath = "D:\\Java-projects\\FilteringFileContents\\src\\main\\resources\\test_files";

    @Test
    public void parseData_FromFiles_ToLists_Test() {
        List<Long> expectedIntValues1 = List.of(45L, 100500L);
        List<Double> expectedFloatValues1 = List.of(3.1415, -0.001);
        List<String> expectedStringValues1 = List.of(
                "Пример входного файла in1.txt",
                "Lorem ipsum dolor sit amet",
                "Пример", "consectetur adipiscing", "тестовое задание");

        List<Long> expectedIntValues2 = List.of(1234567890123456789L);
        List<Double> expectedFloatValues2 = List.of(1.528535047E-25);
        List<String> expectedStringValues2 = List.of(
                "Пример входного файла in2.txt",
                "Нормальная форма числа с плавающей запятой", "Long");

        String txtFile1 = filepath + "\\in1.txt";
        ParserFile parserFile1 = new ParserFile(txtFile1);

        String txtFile2 = filepath + "\\in2.txt";
        ParserFile parserFile2 = new ParserFile(txtFile2);

        assertIterableEquals(expectedIntValues1, parserFile1.getIntValues());
        assertIterableEquals(expectedFloatValues1, parserFile1.getFloatValues());
        assertIterableEquals(expectedStringValues1, parserFile1.getStringValues());
        assertIterableEquals(expectedIntValues2, parserFile2.getIntValues());
        assertIterableEquals(expectedFloatValues2, parserFile2.getFloatValues());
        assertIterableEquals(expectedStringValues2, parserFile2.getStringValues());
    }

    @Test
    public void getStatistics_Test() {
        String expectedCommonStatisticsForIntValues = "\nКоличество целочисленных элементов в файле in1.txt:\n2";
        String expectedCommonStatisticsForFloatValues = "\nКоличество вещественных элементов в файле in1.txt:\n2";
        String expectedCommonStatisticsForStringValues = "\nКоличество строк в файле in1.txt:\n5";

        String expectedExtendedStatisticsForInt = "\nКоличество целочисленных элементов в файле in1.txt:\n2\n" +
                "Минимальное значение:\n45\n" +
                "Максимальное значение:\n100500\n" +
                "Сумма всех элементов:\n100545\n" +
                "Среднее арифметическое:\n50272,50";
        String expectedExtendedStatisticsForFloat = "\nКоличество вещественных элементов в файле in1.txt:\n2\n" +
                "Минимальное значение:\n-0,00100000\n" +
                "Максимальное значение:\n3,14150\n" +
                "Сумма всех элементов:\n3,14050\n" +
                "Среднее арифметическое:\n1,6";
        String expectedExtendedStatisticsForString = "\nКоличество строк в файле in1.txt:\n5\n" +
                "Размер самой короткой строки:\n6\n" +
                "Размер самой длинной строки:\n29\n";

        String txtFile1 = filepath + "\\in1.txt";
        ParserFile parserFile1 = new ParserFile(txtFile1);

        assertEquals(expectedCommonStatisticsForIntValues, parserFile1.getCommonStatisticsForIntValues("in1.txt"));
        assertEquals(expectedCommonStatisticsForFloatValues, parserFile1.getCommonStatisticsForFloatValues("in1.txt"));
        assertEquals(expectedCommonStatisticsForStringValues, parserFile1.getCommonStatisticsForStringValues("in1.txt"));
        assertEquals(expectedExtendedStatisticsForInt, parserFile1.getExtendedStatisticsForInt("in1.txt"));
        assertEquals(expectedExtendedStatisticsForFloat, parserFile1.getExtendedStatisticsForFloat("in1.txt"));
        assertEquals(expectedExtendedStatisticsForString, parserFile1.getExtendedStatisticsForString("in1.txt"));
    }

    @Test
    public void empty_File_Test() {
        // Перехват вывода консоли
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        String txtFile = filepath + "\\empty.txt";

        ParserFile parserFile = new ParserFile(txtFile);

        // Получение вывода консоли
        String consoleOutput = baos.toString();

        //assertTrue(consoleOutput.contains("Файл пуст: " + txtFile));
    }

    @Test
    public void noCorrect_FilePath_Test() {
        // Перехват вывода консоли
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        String txtFile = filepath + "\\file.txt";

        ParserFile parserFile = new ParserFile(txtFile);

        // Получение вывода консоли
        String consoleOutput = baos.toString();

        //assertTrue(consoleOutput.contains("Файл не был найден: " + txtFile));
    }

}