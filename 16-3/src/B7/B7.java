package B7;

import java.io.*;
import java.util.Scanner;

public class B7 {
    static class WriterThread extends Thread {
        private String destination;

        public WriterThread(String destination) {
            this.destination = destination;
        }

        @Override
        public void run() {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(destination))) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Nhập dữ liệu (gõ 'exit' để kết thúc):");

                while (true) {
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("exit")) {
                        break;
                    }
                    bw.write(input);
                    bw.newLine();
                    bw.flush();
                }

                scanner.close();
            } catch (IOException e) {
                System.err.println("Lỗi khi ghi file: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        String destinationFile = "output.txt";
        WriterThread writerThread = new WriterThread(destinationFile);
        writerThread.start();

        try {
            writerThread.join();
        } catch (InterruptedException e) {
            System.err.println("Thread bị gián đoạn: " + e.getMessage());
        }

        System.out.println("Hoàn thành ghi dữ liệu vào file.");
    }
}

