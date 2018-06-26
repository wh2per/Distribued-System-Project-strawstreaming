package Control;

import java.util.Scanner;

/**
 * Created by jakeu on 2018. 6. 24..
 */
public class BnwthListener extends Thread{
    private Scanner scan = new Scanner(System.in);

    public void run(){
        do{
            StrawClient.bandwidth = scan.nextLine();
        }while(!(StrawClient.bandwidth.equals("EXIT")));
    }
}
