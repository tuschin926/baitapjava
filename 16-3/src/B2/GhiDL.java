
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

    public class GhiDL {

        public static void main(String[] args) {
            String filePath = "ghidl.txt";
            String thread1Data = "Day la thread 1 \n";
            String thread2Data = "Con toi la thread 2\n";

            Thread thread1 = new Thread(() -> writeToFile(filePath, thread1Data));
            Thread thread2 = new Thread(() -> writeToFile(filePath, thread2Data));

            thread1.start();
            thread2.start();

            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Ghi file hoàn tất.");
        }

        private static void writeToFile(String filePath, String data) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // true để append
                writer.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


