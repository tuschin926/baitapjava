package b5;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class LogWriterThread extends Thread {
    private String logFilePath;
    private String message;

    public LogWriterThread(String logFilePath, String message) {
        this.logFilePath = logFilePath;
        this.message = message;
    }

    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.write(timeStamp + " - " + message);
            writer.newLine();
            System.out.println("Log written: " + timeStamp + " - " + message);
        } catch (IOException e) {
            System.err.println("Error writing log: " + e.getMessage());
        }
    }
}



