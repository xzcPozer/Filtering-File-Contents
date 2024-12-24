package by.sharftim.option;

import org.apache.commons.cli.*;
import org.apache.log4j.Logger;


public class MyFileOptions {

    private final Options options;
    private final Option PREFIX_OPTION;
    private final Option FILEPATH_OPTION;
    private final Option EXISTING_RESULTS_FILES_OPTION;
    private final Option COMMON_STATISTIC_OPTION;
    private final Option EXTENDED_STATISTIC_OPTION;
    private final Option HELP_OPTION;

    private final Logger LOGGER = Logger.getLogger(this.getClass());

    public MyFileOptions() {
        options = new Options();

        PREFIX_OPTION = createOption("p", "PREFIX", "prefix for result files", false);
        FILEPATH_OPTION = createOption("o", "FILE_PATH", "file path for result files", false);
        EXISTING_RESULTS_FILES_OPTION = createOption("a", "write to existing result files by file name", false);
        COMMON_STATISTIC_OPTION = createOption("s", "common statistics for input files", false);
        EXTENDED_STATISTIC_OPTION = createOption("f", "extended statistics for input files", false);
        HELP_OPTION = createOption("?", "show help information", false);

        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(COMMON_STATISTIC_OPTION)
                .addOption(EXTENDED_STATISTIC_OPTION);

        options.addOption(PREFIX_OPTION)
                .addOption(FILEPATH_OPTION)
                .addOption(EXISTING_RESULTS_FILES_OPTION)
                .addOption(HELP_OPTION)
                .addOptionGroup(optionGroup);
    }

    private Option createOption(String shortName, String argName, String description, boolean required) {
        return Option.builder(shortName)
                .argName(argName)
                .desc(description)
                .hasArg()
                .required(required)
                .build();
    }

    private Option createOption(String shortName, String description, boolean required) {
        return Option.builder(shortName)
                .desc(description)
                .required(required)
                .build();
    }

    public CommandLine parseOptionsFromCmd(String[] cmdOptions) {
        try {
            CommandLineParser commandLineParser = new DefaultParser();

            return commandLineParser.parse(options, cmdOptions);

        } catch (UnrecognizedOptionException e) {
            LOGGER.error("Одна или несколько опций не существует у текущей программы\n" +
                    "Для вывода всех возможных опций и их значений используйте опцию: -?");
            return null;
        } catch (MissingArgumentException e) {
            LOGGER.error("У одной или нескольких опций не было передано обязательного аргумента\n" +
                    "Для вывода всех возможных опций и их значений используйте опцию: -?");
            return null;
        } catch (AlreadySelectedException e) {
            LOGGER.error("Опции -s, -f не могут быть прописаны вместе");
            return null;
        } catch (ParseException e) {
            LOGGER.error("Что-то пошло не так\n" +
                    "Для вывода всех возможных опций и их значений используйте опцию: -?");
            return null;
        }
    }

    public Options getOptions() {
        return options;
    }

    public Option getPREFIX_OPTION() {
        return PREFIX_OPTION;
    }

    public Option getFILEPATH_OPTION() {
        return FILEPATH_OPTION;
    }
}
