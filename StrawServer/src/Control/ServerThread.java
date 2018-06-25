package Control;
/**
 * Created by jakeu on 2018. 6. 10..
 */
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ServerThread extends Thread{
    // Socket property
    private Socket sock;

    //MARK: property
    String fileName = "audio1";
    String filePath = "Database/OGaudios/"+fileName+".mp3";
    private int id;
    private int max;
    //init thread & socket
    public ServerThread(Socket sock, int id,int max){
        this.sock = sock;
        this.id = id;
        this.max = max;
    }

    public void run(){
        try{
            // In & out streams.
            OutputStream out = sock.getOutputStream();
            InputStream in = sock.getInputStream();
            // PrintWriter & bufferReader.
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            // Properties.
            String msg = null;

            // Control objects
            Control control = new Control(out,in,pw,br);
            control.ServerToSlave(fileName, filePath, id, max);

            pw.close();
            br.close();
            sock.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
