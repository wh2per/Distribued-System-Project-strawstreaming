package Control;
/**
 * Created by jakeu on 2018. 6. 10..
 */

import java.net.*;

public class StrawServer {
    final static int port = 9999;

    public static void main(String[] args){
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("server running....");
            while(true){
                Socket socket = server.accept();
                ServerThread st = new ServerThread(socket);
                st.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
