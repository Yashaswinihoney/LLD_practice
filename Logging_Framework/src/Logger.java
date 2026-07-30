import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


//THE SUBJECT: WHERE THE ACTUAL WORK HAPPENS
public class Logger {
    private final String name;
    private LogLevel currentLevel;

    //Threadsafe list of in case loggers are added dynamically
    private final List<LogAppender> appenders=new CopyOnWriteArrayList<>();

    public Logger(String name, LogLevel defaultLevel){
        this.currentLevel=defaultLevel;
        this.name=name;
    }

    public void addAppenders(LogAppender appender){
        this.appenders.add(appender);
    }

    public void setLevel(LogLevel level){
        this.currentLevel=level;
    }

    public void info(String msg){
        log(LogLevel.INFO,msg);
    }

    public void error(String msgs){
        log(LogLevel.ERROR,msgs);
    }

    public void debug(String msg){
        log(LogLevel.DEBUG,msg);
    }

    private void log(LogLevel logLevel, String msg) {
        if(logLevel.level< currentLevel.level) return;

        LogMessage logMessage=new LogMessage(logLevel,msg);
        for (LogAppender appender: appenders){
            appender.append(logMessage);
        }
    }
}
