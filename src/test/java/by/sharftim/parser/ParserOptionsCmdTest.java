package by.sharftim.parser;

import by.sharftim.exception.UnspecifiedFilesException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserOptionsCmdTest {

    @Test
    public void normal_Input_Data_Test() {
        String[] testArr = {"-s", "-a", "-p", "sample-", "in1.txt", "in2.txt"};

        String[] expectedOptions = {"-s", "-a", "-p", "sample-"};
        List<String> expectedFilesName = List.of("in1.txt", "in2.txt");

        ParserOptionsCmd parser = new ParserOptionsCmd(testArr);
        parser.findOptionsAndFilesName();

        String[] actualOptions = parser.getOptions();
        List<String> actualFilesName = parser.getFilesName();

        assertArrayEquals(expectedOptions, actualOptions);
        assertEquals(expectedFilesName, actualFilesName);
    }

    @Test
    public void specific_Input1_Data_Test() {
        String[] testArr = {"-sa", "-p", "sample-", "in1.txt", "in2.txt"};

        String[] expectedOptions = {"-s", "-a", "-p", "sample-"};
        List<String> expectedFilesName = List.of("in1.txt", "in2.txt");

        ParserOptionsCmd parser = new ParserOptionsCmd(testArr);
        parser.findOptionsAndFilesName();

        String[] actualOptions = parser.getOptions();
        List<String> actualFilesName = parser.getFilesName();

        assertArrayEquals(expectedOptions, actualOptions);
        assertEquals(expectedFilesName, actualFilesName);
    }

    @Test
    public void specific_Input2_Data_Test() {
        String[] testArr = {"-p", "sample-", "in1.txt", "-fa", "in2.txt"};

        String[] expectedOptions = {"-p", "sample-", "-f", "-a",};
        List<String> expectedFilesName = List.of("in1.txt", "in2.txt");

        ParserOptionsCmd parser = new ParserOptionsCmd(testArr);
        parser.findOptionsAndFilesName();

        String[] actualOptions = parser.getOptions();
        List<String> actualFilesName = parser.getFilesName();

        assertArrayEquals(expectedOptions, actualOptions);
        assertEquals(expectedFilesName, actualFilesName);
    }

    @Test
    public void noFiles_Exception_Test() {
        String[] testArr = {"-sa", "-p", "sample-"};

        ParserOptionsCmd parser = new ParserOptionsCmd(testArr);

        UnspecifiedFilesException exception = assertThrows(UnspecifiedFilesException.class,
                parser::findOptionsAndFilesName);

        assertEquals("Не было указано ни одного файла для фильтрации", exception.getMessage());
    }

    @Test
    public void repeat_CommonOption_Test() {
        // Перехват вывода консоли
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        ParserOptionsCmd parser = new ParserOptionsCmd(new String[]{"-s", "-p", "sample-", "-s", "in1.txt", "in2.txt"});
        parser.findOptionsAndFilesName();

        String[] expectedOptions = {"-s", "-p", "sample-"};
        List<String> expectedFilesName = List.of("in1.txt", "in2.txt");

        String[] actualOptions = parser.getOptions();
        List<String> actualFilesName = parser.getFilesName();

        // Получение вывода консоли
        String consoleOutput = baos.toString();

        assertArrayEquals(expectedOptions, actualOptions);
        assertEquals(expectedFilesName, actualFilesName);
        //assertTrue(consoleOutput.contains("Повторяется опция: -s"));
    }

    @Test
    public void repeat_SpecificOption_Test() {
        // Перехват вывода консоли
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        ParserOptionsCmd parser = new ParserOptionsCmd(new String[]{"-sa", "-p", "sample-", "-fa", "in1.txt", "in2.txt"});
        parser.findOptionsAndFilesName();

        String[] expectedOptions = {"-s", "-a", "-p", "sample-"};
        List<String> expectedFilesName = List.of("in1.txt", "in2.txt");

        String[] actualOptions = parser.getOptions();
        List<String> actualFilesName = parser.getFilesName();

        // Получение вывода консоли
        String consoleOutput = baos.toString();

        assertArrayEquals(expectedOptions, actualOptions);
        assertEquals(expectedFilesName, actualFilesName);
        //assertTrue(consoleOutput.contains("Повторяется/повторяются опции: -fa"));
    }
}