import java.util.logging.*;

public class LoggerUtil {
    public static final Logger logger = Logger.getLogger("RestaurantLogger");

    static {
        logger.setLevel(Level.INFO);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
    }
}