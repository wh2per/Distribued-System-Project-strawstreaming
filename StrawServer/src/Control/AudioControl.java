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
    public void sendAudioFile(String filePath,String seq){
        try{
            // set file & file stream
            File file = new File(filePath);
            FileInputStream fin = new FileInputStream(file);
            // set buffer
            byte buffer[] = new byte[BUFSIZE];
            int start = getStartNum(seq);
            int end = getEndNum(seq);

            int count;
            int i=0;

            while ((count = fin.read(buffer)) != -1){
                if(i>=start && i<=end) {
                    System.out.println("send"+i);
                    out.write(buffer, 0, count);
                    out.flush();
                }else if(i>end)
                    break;
                i++;
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
        int start = 0 ;
        int end = 0;

        File file = new File(filePath);
        fileSize = file.length();
        start = 0;
        end = (int)(fileSize/BUFSIZE);
        //start = (int)((id)*(fileSize/BUFSIZE/max));
        //end = (int)((id+1)*(fileSize/BUFSIZE/max))-1;
        if(id == max-1){
            end++;
        }
        msg = start+","+end;

        return msg;
    }
}
