package B9;

import java.io.*;

class CharacterCounter implements Runnable {
    private String inputFile;
    private String outputFile;

    public CharacterCounter(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            int charCount = content.length();
            writer.write("Số ký tự trong file: " + charCount);

            System.out.println("Kết quả đã được ghi vào " + outputFile);
        } catch (IOException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String inputFile = "input.txt"; // Đổi tên file đầu vào theo nhu cầu
        String outputFile = "output.txt";

        Thread thread = new Thread(new CharacterCounter(inputFile, outputFile));
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println("Thread bị gián đoạn");
        }

        System.out.println("Hoàn thành đếm ký tự.");
    }
}

