package util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

// Thin wrapper over java.util.logging so tests can log without extra dependencies
public final class LoggerUtil {

    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private static final Logger LOGGER = createLogger();

    private LoggerUtil() {
    }

    private static Logger createLogger() {
        Logger logger = Logger.getLogger("tests");
        logger.setUseParentHandlers(false);

        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord r) {
                return String.format("%s [%s] %s%n",
                        LocalTime.now().format(TIME), r.getLevel(), formatMessage(r));
            }
        });
        logger.addHandler(handler);
        return logger;
    }

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static void warn(String message) {
        LOGGER.warning(message);
    }

    public static void error(String message, Throwable t) {
        LOGGER.log(Level.SEVERE, message, t);
    }
}
