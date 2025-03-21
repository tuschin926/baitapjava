package b1;

public class Test {
    public static void main(String[] args) {
        String filePath = "filePath.txt";

        FileReaderThread fileReaderThread = new FileReaderThread(filePath);
        fileReaderThread.start();
    }
}