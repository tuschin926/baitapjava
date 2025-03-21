package b10;

public class Test {
    public static void main(String[] args) {
        String url = "https://vnexpress.net/tong-bi-thu-kinh-te-thuong-mai-dau-tu-la-tru-cot-cua-quan-he-viet-my-4863944.txt";
        String fileName = "downloaded_file.txt";

        DownloadThread downloadThread = new DownloadThread(url, fileName);
        downloadThread.start();
    }
}
