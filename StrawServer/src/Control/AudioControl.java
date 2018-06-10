package Control;
/**
 * Created by jakeu on 2018. 6. 10..
 */
import Utils.AudioEncoder;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.*;

public class AudioControl {
    // buffer Size
    final static int BUFSIZE = 2048;

    // MARK: properties
    private OutputStream out;
    private InputStream in;
    private PrintWriter pw;
    private BufferedReader br;

    // MARK: other objects
    AudioEncoder enc;

    public AudioControl(OutputStream out, InputStream in, PrintWriter pw, BufferedReader br){
        this.in = in;
        this.out = out;
        this.pw = pw;
        this.br = br;
    }
    // make audio file in different sampling rate
    public void setAudioEncoding(String filePath, String fileName) throws InterruptedException, UnsupportedAudioFileException, IOException {
            File file = new File(filePath);
            enc = new AudioEncoder(file,fileName);
            enc.changeSampleRate();
    }

    //send audio file(par: path of audio file)
    public void sendAudioFile(String filePath){
        try{
            // set file & file stream
            File file = new File(filePath);
            FileInputStream fin = new FileInputStream(file);
            // set buffer
            byte buffer[] = new byte[BUFSIZE];

            long fileSize = file.length();
            int count;
            int i=0;
            pw.print(String.valueOf(fileSize));
            pw.flush();
            while ((count = fin.read(buffer)) != -1){
                System.out.println("send"+i);
                out.write(buffer, 0, count);
                i++;
                out.flush();
            }

            System.out.println("done");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
