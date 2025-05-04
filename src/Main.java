import cs6310.CommandProcessor;
import cs6310.LoggerSetup;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public class Main {
    private  static final Logger LOGGER = LoggerSetup.getLogger();

    public static void main(String[] args) {
        LOGGER.info("Welcome to the thunder dome!");
        var methodCaller = new CommandProcessor();
        methodCaller.ProcessCommands(args);
    }
}