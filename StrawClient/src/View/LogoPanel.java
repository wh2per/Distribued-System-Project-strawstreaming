package View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jakeu on 2018. 6. 25..
 */
public class LogoPanel extends JPanel{
    private JPanel logoPnl;
    private JLabel logoLbl;

    private Color bg = new Color(255 , 255, 255);

    public LogoPanel(){
        setLayout(new BorderLayout());
        logoPnl = new JPanel();
        logoLbl = new JLabel(new ImageIcon("Images/logoicon.png"));

        this.setBackground(bg);
        logoPnl.setBackground(bg);
        logoLbl.setOpaque(false);
        this.add(logoPnl);
        logoPnl.add(logoLbl,BorderLayout.CENTER);
    }
}

