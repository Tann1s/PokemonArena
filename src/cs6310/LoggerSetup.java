package cs6310;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

public class LoggerSetup {
    private static FileHandler fileHandler;
    private static FileHandler logfileHandler;

    static {
        setup();
    }
    private static void setup() {
        Logger logger = Logger.getLogger("Pokemon");
        // Console Handler with custom formatter
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new MyFormatter());
        consoleHandler.setLevel(Level.INFO);
        logger.addHandler(consoleHandler);

        // File Handler
        try {
            fileHandler = new FileHandler("Pokemon.txt", true);
            fileHandler.setLevel(Level.INFO);
            fileHandler.setFormatter(new MyFormatter());
            logger.addHandler(fileHandler);

            logfileHandler = new FileHandler("Pokemon.log", true);
            logfileHandler.setLevel(Level.INFO);
            logfileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(logfileHandler);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occur in loading FileHandler.", e);
        }

        // Setting the level of the logger and disabling the default console handler of the parent logger
        logger.setLevel(Level.INFO);
        logger.setUseParentHandlers(false);

    }

    public static Logger getLogger() {
        return Logger.getLogger("Pokemon");
    }

}

