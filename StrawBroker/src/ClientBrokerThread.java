import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by jakeu on 2018. 6. 23..
 */
public class ClientBrokerThread extends Thread{
    private Socket sock;
    private ServerControl sc;

    public ClientBrokerThread(Socket sock, ServerControl sc){
        this.sock = sock;
        this.sc = sc;
    }

    public void run(){
        try{
            // Socket connection.
            InetAddress inetAddr = sock.getInetAddress();
            System.out.println("NEW CLIENT : " + inetAddr.getHostAddress());
            // In & out streams.
            OutputStream out = sock.getOutputStream();
            InputStream in = sock.getInputStream();
            // PrintWriter & bufferReader.
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            // Properties.
            String msg = null;

            ClientControl cc = new ClientControl(in,out,br,pw);
            while((msg = br.readLine())!= null){
                // Print message from client.
                System.out.println("client-> " + msg);
                cc.selectMode(msg,sc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
