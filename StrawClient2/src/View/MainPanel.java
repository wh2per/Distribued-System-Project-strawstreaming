package View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jakeu on 2018. 6. 25..
 */
public class MainPanel extends JPanel {
    private JPanel adverPnl;
    private JPanel blankPnl;
    private JPanel menuPnl;
    private JPanel listPnl;
    private JLabel[] adverLbl;
    private JButton[] menuBtn;

    private Color bg = new Color(255, 255, 255);

    public MainPanel(){
        this.setLayout(new BorderLayout());
        adverPnl = new JPanel();
        menuPnl = new JPanel();
        listPnl = new JPanel();
        blankPnl = new JPanel();
        menuBtn = new JButton[4];
        adverLbl = new JLabel[4];

        menuBtn[0] = new JButton("전체 목록 보기");
        menuBtn[1] = new JButton("최신 음악");
        menuBtn[2] = new JButton("많이 재생된 노래");
        menuBtn[3] = new JButton("장르 음악");

        for(int i =0; i<adverLbl.length; i++){
            adverLbl[i] = new JLabel(new ImageIcon("Images/advericon.png"));
            adverPnl.add(adverLbl[i]);
            // remove button border, fill, focus paint
            menuBtn[i].setFont(menuBtn[i].getFont().deriveFont(18.0f));
            menuBtn[i].setBorderPainted(false);
            menuBtn[i].setContentAreaFilled(false);
            menuBtn[i].setFocusPainted(false);
            menuPnl.add(menuBtn[i]);
        }
        this.setBackground(bg);
        adverPnl.setBackground(bg);
        menuPnl.setBackground(bg);
        menuPnl.setLayout(new GridLayout(4,1));
        //this.add(menuPnl,BorderLayout.WEST);
        this.add(listPnl,BorderLayout.EAST);
        this.add(adverPnl,BorderLayout.NORTH);
    }
}
