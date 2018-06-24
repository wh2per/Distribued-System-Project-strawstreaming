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
                i++;
                if(i==10){
                    i=0;
                    Thread.sleep(800);
                }
            }while(count>0);

            in.close();
            fos.flush();
            fos.close();

            System.out.println("done");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
