package by.sharftim.main;

import by.sharftim.exception.UnspecifiedFilesException;
import by.sharftim.filter.FileFilter;
import by.sharftim.parser.ParserOptionsCmd;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

import java.util.List;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class);

    public static void main(String[] args) {
        try {
            ParserOptionsCmd parser = new ParserOptionsCmd(args);

            parser.findOptionsAndFilesName();

            List<String> filesName = parser.getFilesName();
            String[] options = parser.getOptions();

            FileFilter filter = new FileFilter(options, filesName);

            filter.doFileFilter();

        } catch (UnspecifiedFilesException | ParseException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
