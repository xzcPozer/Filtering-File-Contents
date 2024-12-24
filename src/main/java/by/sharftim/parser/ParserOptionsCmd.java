package by.sharftim.parser;

import by.sharftim.exception.UnspecifiedFilesException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ParserOptionsCmd {

    private final List<String> options;
    private final List<String> filesName;

    private final String fileType = ".txt";
    private final String[] cmdRequest;
    private final String[] targetCommonOptions = {"-s", "-f", "-p", "-o", "-a", "-?"};
    private final String[] targetSpecificOptions = {"-sa", "-as", "-fa", "-af"};
    private boolean specificFlag = false;
    private boolean cycleFlag;
    private boolean addArgOption;

    private final Logger LOGGER = Logger.getLogger(this.getClass());

    public ParserOptionsCmd(String[] cmdRequest) {
        options = new ArrayList<>();
        filesName = new ArrayList<>();
        this.cmdRequest = cmdRequest;
    }

    public void findOptionsAndFilesName(){
        for (String str : cmdRequest) {
            cycleFlag = false;
            addArgOption = true;

            // проверка на файл
            if (str.contains(fileType)) {
                filesName.add(str);
                continue;
            }

            // проверка на опцию
            findCommonOption(str);

            // проверка на специфичную опцию
            if (!cycleFlag) {
                findSpecificOption(str);
                if(addArgOption)
                    // добавление значения опции
                    options.add(str);
            }
        }

        // без указания файлов программа не может выполнить свое назначение
        if(filesName.isEmpty())
            throw new UnspecifiedFilesException("Не было указано ни одного файла для фильтрации");
    }


    private void findCommonOption(String str) {
        for (String commonTarget : targetCommonOptions) {
            if (options.contains(str)){
                LOGGER.warn("Повторяется опция: " + str);
                cycleFlag = true;
                break;
            }
            else if (str.equals(commonTarget)) {
                options.add(str);
                cycleFlag = true;
                break;
            }
        }
    }

    private void findSpecificOption(String str){
        for (String specificTarget : targetSpecificOptions) {
            if (str.equals(specificTarget) && !specificFlag) {
                options.add(str.substring(0, 2));
                options.add("-" + str.charAt(2));
                specificFlag = true;
                addArgOption = false;
                break;
            } else if (str.equals(specificTarget)) {
                LOGGER.warn("Повторяется/повторяются опции: " + str);
                addArgOption = false;
                break;
            }
        }
    }

    public String[] getOptions() {
        return options.toArray(new String[0]);
    }

    public List<String> getFilesName() {
        return filesName;
    }
}
