package Control;

import java.io.*;
import java.net.Socket;

/**
 * Created by jakeu on 2018. 6. 25..
 */
public class BrokerControl extends Thread{
    final static int BUFSIZE = 20480;

    // MARK: properties
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private BufferedReader br;
    private PrintWriter pw;
    private String msgIn = "";
    private String msgOut = "";
    private String fileName = "";
    private String filePath = "Database/OGaudios/";
    public BrokerControl(Socket socket){
        this.socket = socket;
        try{
            this.in = this.socket.getInputStream();
            this.out = this.socket.getOutputStream();
            this.br = new BufferedReader(new InputStreamReader(this.in));
            this.pw = new PrintWriter(new OutputStreamWriter(this.out));
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public void sendACK(PrintWriter pw, String msg){
        pw.println(msg);
        pw.flush();
    }
    public boolean isFileExist(String fileName){
        this.fileName = filePath+fileName;
        // find dir
        File file = new File(this.fileName);
        if(!file.exists()){
            return false;
        }
        return true;
    }
    public String getFileSize(String fileName){
        this.fileName = filePath+fileName;
        // find dir
        File file = new File(this.fileName);
        Integer temp = (int)file.length()/BUFSIZE;

        return temp.toString();
    }
    public void run(){
        try{
            while ((msgIn = br.readLine()) != null) {
                sendACK(pw,msgIn);
                msgIn = br.readLine();
                if(isFileExist(msgIn)){
                    sendACK(pw,msgIn);
                    msgIn = br.readLine();
                    sendACK(pw,getFileSize(msgIn));
                }else{
                    sendACK(pw,"NAK");
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
