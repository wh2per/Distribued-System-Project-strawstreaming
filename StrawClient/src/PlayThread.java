import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class PlayThread extends Thread {
    public void run(){
        try{
            JFXPanel panel = new JFXPanel();

            JFrame f = new JFrame("메인윈도우");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setLocationRelativeTo(null);
            f.setSize(200,200);
            f.setVisible(true);
            //Thread.sleep(10);

            File file =new File("./Downloads/download"+".mp3");
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            Player player = new Player(bis);
            player.play();


        }catch (Exception e){
            System.out.println(e);
        }
    }
}
