package net.kigawa.data;

import net.kigawa.util.Logger;

import java.nio.file.Path;
import java.util.logging.Handler;
import java.util.logging.Level;

public class DataLogger extends Logger {
    private static DataLogger logger;

    protected DataLogger(String name, java.util.logging.Logger parentLogger, Level logLevel, Path logDirPath, Handler... handlers) {
        super(name, parentLogger, logLevel, logDirPath, handlers);
    }

    public static DataLogger getInstance() {
        if (logger == null)
            logger = new DataLogger(DataLogger.class.getName(), null, Level.INFO, null);
        return logger;
    }

    public static void enable(String name, java.util.logging.Logger logger, Level level, Path logDirPath, Handler... handlers) {
        DataLogger.logger = new DataLogger(name, logger, level, logDirPath, handlers);
    }
}
