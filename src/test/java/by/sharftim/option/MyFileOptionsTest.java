package by.sharftim.option;

import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MyFileOptionsTest {

    private final String filepathResult = "D:\\Java-projects\\FilteringFileContents\\src\\main\\resources\\test_files";

    @Test
    public void createOptions_WhereParserCorrect(){
        String[] optionsFromParser = {"-s", "-a", "-p", "sample-", "-o", filepathResult};

        MyFileOptions options = new MyFileOptions();
        CommandLine cmd = options.parseOptionsFromCmd(optionsFromParser);

        String prefixResult = cmd.getOptionValue("p");
        String filepathRes = cmd.getOptionValue("o");

        assertTrue(cmd.hasOption("s"));
        assertTrue(cmd.hasOption("a"));
        assertEquals(prefixResult, "sample-");
        assertEquals(filepathRes, filepathResult);
    }

    @Test
    public void UnrecognizedOptionException_Test(){
        // Перехват вывода консоли
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(ps);

        String loggerExpected = "Одна или несколько опций не существует у текущей программы\n" +
                "Для вывода всех возможных опций и их значений используйте опцию: -?";

        String[] optionsFromParser = {"-s", "-a", "-j", "-p", "sample-", "-e"};

        MyFileOptions options = new MyFileOptions();
        CommandLine cmd = options.parseOptionsFromCmd(optionsFromParser);

        String consoleOutput = baos.toString();

        assertNull(cmd);
        //assertTrue(consoleOutput.contains(loggerExpected));
    }

    @Test
    public void MissingArgumentException_ByExistFunction_Test(){
        // Перехват вывода консоли
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(ps);

        String loggerExpected = "У одной или нескольких опций не было передано обязательного аргумента\n" +
                "Для вывода всех возможных опций и их значений используйте опцию: -?";

        String[] optionsFromParser = {"-s", "-a", "-p"};

        MyFileOptions options = new MyFileOptions();
        CommandLine cmd = options.parseOptionsFromCmd(optionsFromParser);

        String consoleOutput = baos.toString();

        assertNull(cmd);
        //assertTrue(consoleOutput.contains(loggerExpected));
    }

    @Test
    public void IgnoreArg_ByRandomValue_ToOptionWithoutArg_Test(){
        String[] optionsFromParser = {"-s", "-a", "aboba", "-p", "sample-"};

        MyFileOptions options = new MyFileOptions();
        CommandLine cmd = options.parseOptionsFromCmd(optionsFromParser);

        String aValue = cmd.getOptionValue("a");

        assertNull(aValue);
    }

    @Test
    public void AlreadySelectedException_Test(){
        // Перехват вывода консоли
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(ps);

        String[] optionsFromParser = {"-s", "-a", "-f"};

        String loggerExpected = "Опции -s, -f не могут быть прописаны вместе";

        MyFileOptions options = new MyFileOptions();
        CommandLine cmd = options.parseOptionsFromCmd(optionsFromParser);

        String consoleOutput = baos.toString();

        assertNull(cmd);
        //assertTrue(consoleOutput.contains(loggerExpected));

    }

}