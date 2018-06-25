/**
 * Created by jakeu on 2018. 6. 23..
 */
import java.net.*;

public class StrawBroker {
    final static int inPort = 1234;
    // socket for main server
    public static Socket servSocket;
    // socket for 3 different slaves
    public static Socket[] slaveSock = new Socket[3];

    public static void main(String[] args){

        try {
            // start server to broker first
            ServerBrokerThread sbt = new ServerBrokerThread();
            sbt.start();

            ServerSocket server = new ServerSocket(inPort);
            System.out.println("Broker running....");
            while(true){
                Socket socket = server.accept();
                ServerControl sc = new ServerControl(servSocket,slaveSock);
                //Thread for clients
                ClientBrokerThread cbt = new ClientBrokerThread(socket, sc);
                cbt.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}