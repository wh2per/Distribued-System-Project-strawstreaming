package View;

import Control.StrawClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
/**
 * Created by jakeu on 2018. 6. 25..
 */
public class MainView {

    private StrawClient sc;

    private Color borderCr = new Color(87,171,150);
    private int border = 15;

    private LogoPanel logoPanel;
    private MainPanel mainPanel;
    private PlayPanel playPanel;
    public MainView() {
        //this.mc = new MainController();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                sc = new StrawClient();
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }

                JFrame frame = new JFrame("Straw Streaming");
                logoPanel = new LogoPanel();
                playPanel = new PlayPanel(sc);
                mainPanel = new MainPanel();

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(logoPanel,BorderLayout.NORTH);
                frame.add(mainPanel,BorderLayout.CENTER);
                frame.add(playPanel,BorderLayout.SOUTH);
                frame.setSize(1300, 800);
                //frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                //action manager

            }
        });
    }
    public static void main(String[] args){
        new MainView();
    }
}
