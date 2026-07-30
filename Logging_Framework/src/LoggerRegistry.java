import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoggerRegistry {
    //threadsafe map for logger name and logger registry
    private static final Map<String,Logger> loggers=new ConcurrentHashMap<>();

    private static final LogLevel globalLevel=LogLevel.INFO;
    private static final LogAppender defaultAppender= new ConsoleAppender(new TextFormatter());

    public static Logger getLogger(String name){
        return loggers.computeIfAbsent(name, k->{
            Logger logger= new Logger(name, globalLevel);
            logger.addAppenders(defaultAppender);
            return logger;
        });
    }
}
