import java.io.*;

/**
 * Created by jakeu on 2018. 6. 11..
 */
public class AudioDownloader {
    // buffer Size
    final static int BUFSIZE = 2048;

    // MARK: properties
    private OutputStream out;
    private InputStream in;
    private PrintWriter pw;
    private BufferedReader br;

    public AudioDownloader(OutputStream out, InputStream in, PrintWriter pw, BufferedReader br){
        this.in = in;
        this.out = out;
        this.pw = pw;
        this.br = br;
    }

    public void downloadAudioFile(String filePath, String fileName){
        try{
            // set file & file stream
            File file = new File(filePath+fileName+".mp3");
            FileOutputStream fos = new FileOutputStream(file);

            // set buffer
            byte buffer[] = new byte[BUFSIZE];

            long fileSize = file.length();
            int count;
            int i=0;


            do{
                count = in.read(buffer);
                fos.write(buffer);
            }while(count >0);

            System.out.println("done");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
