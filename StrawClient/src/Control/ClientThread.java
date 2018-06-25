package Control;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket sock;

    public void run(){
        try{

            //socket
            sock = new Socket("127.0.0.1",1234);
            // In & out streams.
            OutputStream out = sock.getOutputStream();
            InputStream in = sock.getInputStream();
            // PrintWriter & bufferReader.
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            // Properties.
            String msg = null;
            AudioDownloader ad = new AudioDownloader(out,in,pw,br);
            ad.downloadAudioFile("Downloads/","audio1.mp3");
            pw.close();
            br.close();
            sock.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}