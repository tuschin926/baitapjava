package b5;

public class Test {
    public static void main(String[] args) {
        String logFilePath = "log.txt";
        String logMessage = "This is a log entry.";

        LogWriterThread logWriterThread = new LogWriterThread(logFilePath, logMessage);
        logWriterThread.start();
    }
}