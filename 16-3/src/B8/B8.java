package B8;

import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class B8 {
    static BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    static class Reader extends Thread {
        private String source;

        public Reader(String source) {
            this.source = source;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new FileReader(source))) {
                String line;
                while ((line = br.readLine()) != null) {
                    queue.put(line);
                }
                queue.put("END");
            } catch (Exception e) {
                System.err.println("Lỗi khi đọc file: " + e.getMessage());
            }
        }
    }

    static class Writer extends Thread {
        private String destination;

        public Writer(String destination) {
            this.destination = destination;
        }

        @Override
        public void run() {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(destination))) {
                while (true) {
                    String line = queue.take();
                    if (line.equals("END")) break;
                    bw.write(line);
                    bw.newLine();
                    bw.flush();
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi ghi file: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        String sourceFile = "source.txt";
        String destinationFile = "destination.txt";

        Reader reader = new Reader(sourceFile);
        Writer writer = new Writer(destinationFile);

        reader.start();
        try {
            reader.join();  // Đảm bảo Reader hoàn thành trước khi Writer bắt đầu
        } catch (InterruptedException e) {
            System.err.println("Thread bị gián đoạn: " + e.getMessage());
        }

        writer.start();
        try {
            writer.join();  // Đảm bảo Writer hoàn thành
        } catch (InterruptedException e) {
            System.err.println("Thread bị gián đoạn: " + e.getMessage());
        }

        System.out.println("Hoàn thành đọc và ghi dữ liệu.");
    }
}
