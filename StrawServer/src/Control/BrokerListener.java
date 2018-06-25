package Control;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by jakeu on 2018. 6. 25..
 */
public class BrokerListener extends Thread{
    private ServerSocket server;
    public BrokerListener(ServerSocket ss){
        this.server = ss;
    }

    public void run(){
        try{
            while(true){
                // wait for slave sock in
                Socket socket = server.accept();
                // add id# & sock list
                StrawServer.broSock.add(socket);
                // Socket connection.
                InetAddress inetAddr = socket.getInetAddress();
                System.out.println("NEW Broker : " + inetAddr.getHostAddress());
                // create Broker Control
                BrokerControl bc = new BrokerControl(socket);
                bc.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
