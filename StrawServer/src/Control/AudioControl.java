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
<<<<<<< HEAD
=======

>>>>>>> 7104a1b073812325b4b4443848cec5fa1f94124b
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
<<<<<<< HEAD
    }
    public int getStartNum(String msg){
        String[] temp = msg.split(",");
        return Integer.parseInt(temp[0]);
    }
    public int getEndNum(String msg){
        String[] temp = msg.split(",");
        return Integer.parseInt(temp[1]);
=======
        enc = new AudioEncoder(file,fileName);
        enc.changeSampleRate();
>>>>>>> 7104a1b073812325b4b4443848cec5fa1f94124b
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
<<<<<<< HEAD
                System.out.println("send"+i+", "+count);
                out.write(buffer, 0, count);
                out.flush();
                //Thread.sleep(100);
                i++;
                if(i>end)
                    break;
=======
                if(i>=start && i<=end) {
                    System.out.println("send"+i);
                    out.write(buffer, 0, count);
                    out.flush();
                }else if(i>end)
                    break;
                i++;
>>>>>>> 7104a1b073812325b4b4443848cec5fa1f94124b
            }

            System.out.println("done");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
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
=======
    public String getSQN(String filePath, int id){
        long fileSize=0;
        String msg = null;

        File file = new File(filePath);
        fileSize = file.length();

        switch (id){
            case 1:
                start = 0;
                end = (int)(fileSize/20480/3-1);
                msg = start+","+end;
                break;
            case 2:
                start = (int)(fileSize/20480/3);
                end = (int)(fileSize/20480/3)+(int)(fileSize/20480/3-1);
                msg = start+","+end;
                break;
            case 3:
                start = (int)(fileSize/20480/3)+(int)(fileSize/20480/3);
                end = (int)(fileSize/20480);
                msg = start+","+end;
                break;
            default:
                System.out.println("슬레이브 ID가 잘못되었습니다!");
                break;
        }

>>>>>>> 7104a1b073812325b4b4443848cec5fa1f94124b

        return msg;
    }
}
