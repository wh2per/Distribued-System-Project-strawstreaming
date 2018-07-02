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
    final static int BUFSIZE = 20480;
    // MARK: properties
    private OutputStream out;
    private InputStream in;
    private PrintWriter pw;
    private BufferedReader br;
    private int start;
    private int end;

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
    }
    public int getStartNum(String msg){
        String[] temp = msg.split(",");
        return Integer.parseInt(temp[0]);
    }
    public int getEndNum(String msg){
        String[] temp = msg.split(",");
        return Integer.parseInt(temp[1]);
    }
    //send audio file(par: path of audio file)
    public void sendAudioFile(String filePath,String seq) throws InterruptedException {
        try{
            // set file & file stream
            File file = new File(filePath);
            FileInputStream fin = new FileInputStream(file);
            // set buffer
            byte buffer[] = new byte[BUFSIZE];
            int end = Integer.parseInt(seq);

            int count;
            int i=0;

            while ((count = fin.read(buffer)) != -1){
                System.out.println("send"+i+", "+count);
                out.write(buffer, 0, count);
                out.flush();
                //Thread.sleep(100);
                i++;
                if(i>end)
                    break;
            }

            System.out.println("done");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // get sequence # for audios
    public String getSQN(String filePath, int id, int max){
        long fileSize = 0;
        String msg = null;
        int end = 0;

        File file = new File(filePath);
        fileSize = file.length();
        end = (int)(fileSize/BUFSIZE);
        if(fileSize%BUFSIZE!=0){
            end++;
        }
        msg = Integer.toString(end);

        return msg;
    }
}
