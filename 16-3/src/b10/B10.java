package b10;

import java.io.*;
import java.net.*;

class DownloadThread extends Thread {
    private String urlString;
    private String fileName;

    public DownloadThread(String urlString, String fileName) {
        this.urlString = urlString;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (InputStream inputStream = connection.getInputStream();
                 FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
                System.out.println("Download completed: " + fileName);
            }
        } catch (IOException e) {
            System.err.println("Download failed: " + e.getMessage());
        }
    }
}



