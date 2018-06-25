import java.net.*;

/**
 * Created by jakeu on 2018. 6. 23..
 */
public class ServerBrokerThread extends Thread{
    // ip address for slaves
    final static String servAddr = "127.0.0.1";
    // port# for slaves
    final static int[] slavePort = {6666,7777,8888};
    final static int mainServPort = 2020;


    public void run(){
        try{
            StrawBroker.servSocket = new Socket(servAddr,mainServPort);
            for(int i = 0; i<=0/*slavePort.length*/; i++){
                StrawBroker.slaveSock[i] = new Socket(servAddr,slavePort[i]);
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }
}
