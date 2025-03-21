
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Saochep {
    public static void main(String[] args) throws IOException {
        String saochep = "copy.txt";
        String output =  "paste.txt";
        Thread copyThread = new Thread (() ->{
            try(FileInputStream inputStream = new FileInputStream(saochep);
                FileOutputStream outputStream = new FileOutputStream(output))
            {
                byte [] buffer = new byte[1024];
                int length;
                while((length = inputStream.read(buffer))>0)
                {
                    outputStream.write(buffer,0, length);
                }
                System.out.println("sao chep file hoan tat");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
        copyThread.start();
        try{
            copyThread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
