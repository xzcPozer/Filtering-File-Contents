package by.sharftim.parser;

import org.apache.log4j.Logger;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParserFile {

    private final String path;

    private final List<String> allData;
    private final List<Long> intValues;
    private final List<Double> floatValues;
    private final List<String> stringValues;

    private boolean isExisting;

    private final Logger LOGGER = Logger.getLogger(this.getClass());

    public ParserFile(String path) {
        this.path = path;

        allData = new ArrayList<>();
        floatValues = new ArrayList<>();
        intValues = new ArrayList<>();
        stringValues = new ArrayList<>();

        if (!checkFileOnEmpty()) {
            findAllData();
            if (!allData.isEmpty()) {
                findLongValues();
                findFloatValues();
                findStringValues();
            }
        }
    }

    private boolean checkFileOnEmpty() {
        try {
            if (Files.size(Paths.get(path)) == 0) {
                LOGGER.warn("Файл пуст: " + path);
                return true;
            }
        } catch (IOException e) {
            LOGGER.error("Файл не был найден: " + path);
            isExisting = false;
            return true;
        }
        isExisting = true;
        return false;
    }

    private void findAllData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                allData.add(line);
            }
        } catch (IOException e) {
            LOGGER.error("Ошибка чтения файла: "+ path);
        }
    }

    private void findLongValues() {
        for (String s : allData) {
            try {
                Long l = Long.parseLong(s);
                intValues.add(l);
            } catch (NumberFormatException e) {
            }
        }
    }

    private void findFloatValues() {
        for (String s : allData) {
            try {
                BigDecimal bd = new BigDecimal(s);
                if (!intValues.contains(bd.longValue())) {
                    floatValues.add(bd.doubleValue());
                }
            } catch (NumberFormatException e) {
            }
        }
    }

    private void findStringValues() {
        for (String s : allData) {
            try {
                Double.parseDouble(s);
            } catch (NumberFormatException e) {
                if(!s.isEmpty())
                    stringValues.add(s);
            }
        }
    }

    public List<Long> getIntValues() {
        return intValues;
    }

    public List<Double> getFloatValues() {
        return floatValues;
    }

    public List<String> getStringValues() {
        return stringValues;
    }

    public boolean isExisting() {
        return isExisting;
    }

    public int getSizeIntValues() {
        return intValues.size();
    }

    public int getSizeFloatValues() {
        return floatValues.size();
    }

    public int getSizeStringValues() {
        return stringValues.size();
    }

    public String getCommonStatisticsForIntValues(String filename) {
        return String.format("\nКоличество целочисленных элементов в файле %s:\n%d", filename, intValues.size());
    }

    public String getCommonStatisticsForFloatValues(String filename) {
        return String.format("\nКоличество вещественных элементов в файле %s:\n%d", filename, floatValues.size());
    }

    public String getCommonStatisticsForStringValues(String filename) {
        return String.format("\nКоличество строк в файле %s:\n%d", filename, stringValues.size());
    }

    public String getExtendedStatisticsForInt(String filename) {
        long min = Collections.min(intValues);

        long max = Collections.max(intValues);

        long sum = intValues.stream()
                .reduce(0L, Long::sum);

        double mean = sum / (double) intValues.size();

        return String.format("\nКоличество целочисленных элементов в файле %s:\n%d\n" +
                        "Минимальное значение:\n%d\n" +
                        "Максимальное значение:\n%d\n" +
                        "Сумма всех элементов:\n%d\n" +
                        "Среднее арифметическое:\n%.2f",
                filename, intValues.size(), min, max, sum, mean);
    }

    public String getExtendedStatisticsForFloat(String filename) {
        double min = Collections.min(floatValues);

        double max = Collections.max(floatValues);

        double sum = floatValues.stream()
                .reduce(0.0, Double::sum);

        double mean = sum / (double) intValues.size();

        return String.format("\nКоличество вещественных элементов в файле %s:\n%d\n" +
                        "Минимальное значение:\n%g\n" +
                        "Максимальное значение:\n%g\n" +
                        "Сумма всех элементов:\n%g\n" +
                        "Среднее арифметическое:\n%.2g",
                filename, floatValues.size(), min, max, sum, mean);
    }

    public String getExtendedStatisticsForString(String filename) {
        int shortestString = stringValues.stream()
                .mapToInt(String::length)
                .min()
                .orElse(0);

        int longestString = stringValues.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);

        return String.format("\nКоличество строк в файле %s:\n%d\n" +
                        "Размер самой короткой строки:\n%d\n" +
                        "Размер самой длинной строки:\n%d\n",
                filename, stringValues.size(), shortestString, longestString);
    }
}
