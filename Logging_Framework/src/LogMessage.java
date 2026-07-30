import java.time.LocalDateTime;

public class LogMessage {
    final LogLevel level;
    final String message;
    final LocalDateTime timeStamp;
    final String threadName;

    public LogMessage(LogLevel level, String message){
        this.level=level;
        this.message=message;
        this.timeStamp=LocalDateTime.now();
        this.threadName=Thread.currentThread().getName();
    }
}
