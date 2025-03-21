package b1;

import java.io.*;

class FileReaderThread extends Thread {
    private String filePath;

    public FileReaderThread(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        System.out.println("Đang mở file: " + filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(Thread.currentThread().getName() + ": " + line);
                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Lỗi đọc file: " + e.getMessage());
        }
    }
}


