package B4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Songsong {
    static BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    static class ReaderThread extends Thread {
        private String fileName;

        public ReaderThread(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    queue.put(line);
                }
                queue.put("END");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class ProcessorThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    String line = queue.take();
                    if (line.equals("END")) break;
                    int wordCount = line.split("\\s+").length;
                    System.out.println("Số từ: " + wordCount);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ReaderThread reader = new ReaderThread("songsong.txt");
        ProcessorThread processor = new ProcessorThread();

        reader.start();
        processor.start();
    }
}



