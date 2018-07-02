import java.io.*;
import java.net.Socket;

public class SlaveThread extends Thread {
    private Socket sock;

    public void run(){
        try{

            //socket
            sock = new Socket("192.168.56.1",9999);
            // In & out streams.
            OutputStream out = sock.getOutputStream();
            InputStream in = sock.getInputStream();
            // PrintWriter & bufferReader.
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            // Properties.
            String msg = "";

            Control control = new Control(out,in,pw,br);

            do{
                msg = br.readLine();
                if(msg.equals("1")){
                    control.ServerToSlave();
                    break;
                }
            }while(!msg.equals("EXIT"));
            pw.close();
            br.close();
            sock.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}