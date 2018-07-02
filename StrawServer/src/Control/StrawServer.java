package Control;
/**
 * Created by jakeu on 2018. 6. 10..
 */

import java.net.Socket;
import java.net.ServerSocket;
import java.util.List;
import java.util.ArrayList;


public class StrawServer {

    // MARK: Property
    final static int port = 9999;
<<<<<<< HEAD
    final static int broPort = 2020;
    public static int id = 0;
    public static String cmd = "";
    public static List <Socket> sock = new ArrayList<Socket>();
    public static List <Socket> broSock = new ArrayList<Socket>();
=======
    static int id = 0;
>>>>>>> 7104a1b073812325b4b4443848cec5fa1f94124b

    public static void main(String[] args){
        try {
            // MARK: Sockets
            ServerSocket server = new ServerSocket(port);
            ServerSocket broServer = new ServerSocket(broPort);
            Socket tempSocket;
            // MARK: Threads
            // cmdListener READ input commaned
            CmdListener cmdLstn = new CmdListener();
            // SocketListener Uses for accepting the slaves and add to sock Array
            SocketListener sockLstn = new SocketListener(server);
            BrokerListener broLstn = new BrokerListener(broServer);
            // MARK: Thread starts
            cmdLstn.start();
            sockLstn.start();
            broLstn.start();

            System.out.println("server running....");

            while(true){
<<<<<<< HEAD
                // Refresh time & wait for IO
                Thread.sleep(30);
                if(cmd.equals("UP")){
                    System.out.println("upload proceed");
                    cmd = "";
                    for(int i=0; i<id; i++){
                        tempSocket = sock.get(i);
                        ServerThread st = new ServerThread(tempSocket, i, id);
                        st.start();
                    }
                    id=0;
                    sock.clear();
                }
=======
                Socket socket = server.accept();
                id++;
                ServerThread st = new ServerThread(socket, id);
                st.start();
>>>>>>> 7104a1b073812325b4b4443848cec5fa1f94124b
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
