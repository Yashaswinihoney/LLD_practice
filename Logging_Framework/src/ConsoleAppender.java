public class ConsoleAppender implements LogAppender{
    private final LogFormatter formatter;

    public ConsoleAppender(LogFormatter formatter){
        this.formatter=formatter;
    }
    @Override
    public void append(LogMessage msg) {
        System.out.println(formatter.format(msg));
    }
}
