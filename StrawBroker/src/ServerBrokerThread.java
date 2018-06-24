import java.net.*;

/**
 * Created by jakeu on 2018. 6. 23..
 */
public class ServerBrokerThread extends Thread{
    // ip address for slaves
    final static String servAddr = "127.0.0.1";
    // port# for slaves
    final static int[] slavePort = {6666,7777,8888};

    // socket for 3 different slaves
    private Socket[] slaveSock;

    public ServerBrokerThread(Socket[] slaveSock){
        this.slaveSock = slaveSock;
    }

    public void run(){
        try{
            for(int i = 0; i<slavePort.length; i++){
                slaveSock[i] = new Socket(servAddr,slavePort[i]);
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }
}
