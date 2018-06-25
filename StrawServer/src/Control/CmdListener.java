package Control;

import java.util.Scanner;

/**
 * Created by jakeu on 2018. 6. 24..
 */
public class CmdListener extends Thread{
    private String cmd = null;
    private int i = 0;
    private Scanner scan = new Scanner(System.in);

    public void run(){
        do{
            StrawServer.cmd = scan.nextLine();
        }while(!(StrawServer.cmd.equals("EXIT")));
    }
}
