import java.net.*;

/**
 * Created by jakeu on 2018. 6. 23..
 */
public class ServerBrokerThread extends Thread{
    // ip address for slaves
    final static String servAddr = "127.0.0.1";
    final static String[] slaveAddr ={
            "192.168.56.19","192.168.56.20","192.168.56.21"
    };
    // port# for slave
    final static int[] slavePort = {7777};
    final static int mainServPort = 2020;


    public void run(){
        try{
            StrawBroker.servSocket = new Socket(servAddr,mainServPort);
            for(int i = 0; i<slaveAddr.length; i++){
                StrawBroker.slaveSock[i] = new Socket(slaveAddr[i],slavePort[0]);
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }
}
