package server;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    private FileHandler fh;
    public Logger logger;
    public Log(String fileName) throws IOException {
        fh = new FileHandler(fileName, true);
        logger = Logger.getLogger("logger");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }
}
