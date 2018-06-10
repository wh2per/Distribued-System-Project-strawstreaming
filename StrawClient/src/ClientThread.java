/**
 * Created by jakeu on 2018. 6. 11..
 */
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket sock;

    public void run(){
        try{
            //socket
            sock = new Socket("127.0.0.1",9999);
            // In & out streams.
            OutputStream out = sock.getOutputStream();
            InputStream in = sock.getInputStream();
            // PrintWriter & bufferReader.
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            // Properties.
            String msg = null;
            AudioDownloader ad = new AudioDownloader(out,in,pw,br);
            ad.downloadAudioFile("Downloads/","download");
            pw.close();
            br.close();
            sock.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
