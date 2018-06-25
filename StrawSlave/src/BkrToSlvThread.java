import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by jakeu on 2018. 6. 24..
 */
public class BkrToSlvThread extends Thread{
    private int inPort;
    public BkrToSlvThread(int port){
        this.inPort = port;
    }
    public void run(){
        try {
            ServerSocket server = new ServerSocket(inPort);
            System.out.println("slave is running....");

            // wait for broker connection
            while(true){
                Socket socket = server.accept();
                System.out.println("connet broker");
                BrockerControl bc = new BrockerControl(socket);
                bc.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
