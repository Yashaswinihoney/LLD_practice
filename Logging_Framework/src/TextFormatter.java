public class TextFormatter implements LogFormatter{
    @Override
    public String format(LogMessage msg) {
        return String.format(msg.timeStamp+" "+msg.threadName+" "+msg.level+" "+msg.message);
    }
}
