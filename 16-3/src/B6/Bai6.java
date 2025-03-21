package B6;

import java.io.RandomAccessFile;

public class Bai6 {
    static class ReaderThread extends Thread {
        private String fileName;
        private long start;
        private long length;

        public ReaderThread(String fileName, long start, long length) {
            this.fileName = fileName;
            this.start = start;
            this.length = length;
        }

        @Override
        public void run() {
            try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {
                raf.seek(start);
                byte[] buffer = new byte[(int) length];
                raf.read(buffer);
                System.out.println(Thread.currentThread().getName() + ": " + new String(buffer).trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String fileName = "largefile.txt";
        RandomAccessFile raf = new RandomAccessFile(fileName, "r");
        long fileLength = raf.length();
        long partSize = fileLength / 2;

        ReaderThread thread1 = new ReaderThread(fileName, 0, partSize);
        ReaderThread thread2 = new ReaderThread(fileName, partSize, fileLength - partSize);

        thread1.start();
        thread2.start();
    }
}


