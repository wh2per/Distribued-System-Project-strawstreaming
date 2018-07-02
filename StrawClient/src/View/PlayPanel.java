package View;

import Control.PlayThread;
import Control.StrawClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;

/**
 * Created by jakeu on 2018. 6. 25..
 */
public class PlayPanel extends JPanel{
    private JPanel playPnl;
    private JLabel nameLbl;
    private JButton listBtn;
    private JButton playBtn;

    private StrawClient sc;
    //색상
    private Color bg = new Color(49, 49, 49);
    private Color fontColor = new Color(255,255,255);
    private Color resultC = new Color(126, 135, 191);

    public PlayPanel(StrawClient sc) {
        this.sc = sc;
        setLayout(new BorderLayout());
        playPnl = new JPanel();
        nameLbl = new JLabel("now Playing ..");
        playBtn = new JButton(new ImageIcon("Images/playicon.png"));

        // label size
        // remove button border, fill, focus paint
        playBtn.setBorderPainted(false);
        playBtn.setContentAreaFilled(false);
        playBtn.setFocusPainted(false);

        // Button Listener
        playBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sc.playAudio();
            }
        });

        this.setBackground(bg);
        playPnl.setBackground(bg);
        playBtn.setOpaque(false);
        nameLbl.setForeground(fontColor);
        this.add(playPnl);
        playPnl.add(nameLbl,BorderLayout.WEST);
        playPnl.add(playBtn,BorderLayout.EAST);
    }
}

