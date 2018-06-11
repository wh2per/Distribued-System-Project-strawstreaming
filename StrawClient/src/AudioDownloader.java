import java.io.*;

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
            int fileIdx = 0;
            // set file & file stream
            File file = new File(filePath+fileName+ (++fileIdx)+".mp3");
            FileOutputStream fos = new FileOutputStream(file);

            // set buffer
            byte buffer[] = new byte[BUFSIZE];

            long fileSize = file.length();
            int count;
            int i=0;

            do{
                count = in.read(buffer);
                if(count == -1){
                    break;
                }
                fos.write(buffer);
                i++;
                if(i==50) {
                    fos.flush();
                    fos.close();
                    File nFile = new File(filePath + fileName + (++fileIdx) + ".mp3");
                    fos = new FileOutputStream(nFile);
                    i=0;
                }
            }while(true);
            in.close();
            fos.flush();
            fos.close();

            System.out.println("done");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
