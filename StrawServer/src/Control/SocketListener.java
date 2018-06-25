package Control;

/**
 * Created by jakeu on 2018. 6. 24..
 */
import javax.print.DocFlavor;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;

public class SocketListener extends Thread{
    public int id = 0;

    private ServerSocket server;
    public SocketListener(ServerSocket ss){
        this.server = ss;
    }
    public void run(){
        try{
            while(true){
                // wait for slave sock in
                Socket socket = server.accept();
                // add id# & sock list
                StrawServer.id++;
                StrawServer.sock.add(socket);
                // Socket connection.
                InetAddress inetAddr = socket.getInetAddress();
                System.out.println("NEW SLAVE : " + inetAddr.getHostAddress()+", "+StrawServer.id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
