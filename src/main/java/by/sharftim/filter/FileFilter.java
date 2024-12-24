package by.sharftim.filter;

import by.sharftim.option.MyFileOptions;
import by.sharftim.parser.ParserFile;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;


import java.io.File;

import java.io.FileWriter;
import java.io.IOException;

import java.util.List;


public class FileFilter {
    private final List<String> filesName;
    private final CommandLine commandLine;
    private final MyFileOptions options;
    private final String filepath = System.getProperty("user.dir");
    private String resultPath;
    private String selectStatistic = "";
    private boolean isAppendToExistingIntResults, isAppendToExistingFloatResults, isAppendToExistingStringResults;

    private final Logger LOGGER = Logger.getLogger(this.getClass());

    public FileFilter(String[] cmdOptions, List<String> filesName) throws ParseException {
        this.filesName = filesName;
        options = new MyFileOptions();
        commandLine = options.parseOptionsFromCmd(cmdOptions);

        if (commandLine == null)
            throw new ParseException("ошибка парсирования опций");

        checkHelpOption();

        checkResultFilePathOption();

        checkPrefixOption();

        checkExistingResultsOption();

        checkStatisticOption();
    }

    public void doFileFilter() {
        for (String filename : filesName) {

            ParserFile parserFile = new ParserFile(filepath + "\\" + filename);

            if (parserFile.getSizeIntValues() != 0) {
                createOrAppendIntegersFile(parserFile);
            }
            if (parserFile.getSizeFloatValues() != 0) {
                createOrAppendFloatsFile(parserFile);
            }
            if (parserFile.getSizeStringValues() != 0) {
                createOrAppendStringsFile(parserFile);
            }

            if (!selectStatistic.isEmpty() && parserFile.isExisting()) {
                showResultFileStatistics(parserFile, filename);
            }
        }
    }

    private void showResultFileStatistics(ParserFile parserFile, String filename) {
        String intStatistic = "";
        String floatStatistic = "";
        String stringStatistic = "";
        if (selectStatistic.equals("s")) {
            intStatistic = parserFile.getCommonStatisticsForIntValues(filename);
            floatStatistic = parserFile.getCommonStatisticsForFloatValues(filename);
            stringStatistic = parserFile.getCommonStatisticsForStringValues(filename);
        } else if (selectStatistic.equals("f")) {
            intStatistic = parserFile.getExtendedStatisticsForInt(filename);
            floatStatistic = parserFile.getExtendedStatisticsForFloat(filename);
            stringStatistic = parserFile.getExtendedStatisticsForString(filename);
        }
        if (!intStatistic.isEmpty())
            LOGGER.info(intStatistic);
        if (!floatStatistic.isEmpty())
            LOGGER.info(floatStatistic);
        if (!stringStatistic.isEmpty())
            LOGGER.info(stringStatistic);
    }

    private void createOrAppendIntegersFile(ParserFile parserFile) {
        try (FileWriter writer = new FileWriter(resultPath + "integers.txt", isAppendToExistingIntResults)) {
            List<Long> list = parserFile.getIntValues();
            for (Long element : list) {
                writer.write(element + "\n");
            }
            isAppendToExistingIntResults = true;
        } catch (IOException e) {
            LOGGER.error("Ошибка создания файла с результатами по пути: " + resultPath + "integers.txt");
        }
    }

    private void createOrAppendFloatsFile(ParserFile parserFile) {
        try (FileWriter writer = new FileWriter(resultPath + "floats.txt", isAppendToExistingFloatResults)) {
            List<Double> list = parserFile.getFloatValues();
            for (Double element : list) {
                writer.write(element + "\n");
            }
            isAppendToExistingFloatResults = true;
        } catch (IOException e) {
            LOGGER.error("Ошибка создания файла с результатами по пути: " + resultPath + "floats.txt");
        }
    }

    private void createOrAppendStringsFile(ParserFile parserFile) {
        try (FileWriter writer = new FileWriter(resultPath + "strings.txt", isAppendToExistingStringResults)) {
            List<String> list = parserFile.getStringValues();
            for (String element : list) {
                writer.write(element + "\n");
            }
            isAppendToExistingStringResults = true;
        } catch (IOException e) {
            LOGGER.error("Ошибка создания файла с результатами по пути: " + resultPath + "strings.txt");
        }
    }

    private void checkResultFilePathOption() {
        if (commandLine.hasOption("o")) {
            resultPath = commandLine.getOptionValue(options.getFILEPATH_OPTION());
        } else {
            resultPath = System.getProperty("user.dir");
        }
        resultPath += "\\";
    }

    private void checkExistingResultsOption() {

        if (commandLine.hasOption("a")) {
            isAppendToExistingIntResults = true;
            isAppendToExistingFloatResults = true;
            isAppendToExistingStringResults = true;

            File integersFile = new File(resultPath + "integers.txt");
            File floatsFile = new File(resultPath + "floats.txt");
            File stringsFile = new File(resultPath + "strings.txt");

            if (!integersFile.exists()) {
                LOGGER.warn("Файла не существует по указанному пути: " + resultPath + "integers.txt" + " будет создан новый");
            }
            if (!floatsFile.exists()) {
                LOGGER.warn("Файла не существует по указанному пути: " + resultPath + "floats.txt" + " будет создан новый");
            }
            if (!stringsFile.exists()) {
                LOGGER.warn("Файла не существует по указанному пути: " + resultPath + "strings.txt" + " будет создан новый");
            }
        } else {
            isAppendToExistingIntResults = false;
            isAppendToExistingFloatResults = false;
            isAppendToExistingStringResults = false;
        }
    }

    private void checkPrefixOption() {
        if (commandLine.hasOption("p")) {
            resultPath += commandLine.getOptionValue(options.getPREFIX_OPTION());
        }
    }

    private void checkStatisticOption() {
        if (commandLine.hasOption("s")) {
            selectStatistic = "s";
        } else if (commandLine.hasOption("f")) {
            selectStatistic = "f";
        }
    }

    private void checkHelpOption() {
        if (commandLine.hasOption("?")) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("java -jar FilteringFileContents.jar -o D:/path/to/dir -s -a -p sample- in1.txt in2.txt"
                    , options.getOptions());
        }
    }
}
