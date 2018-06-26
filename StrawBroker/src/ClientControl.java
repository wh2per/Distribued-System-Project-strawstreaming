import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by jakeu on 2018. 6. 23..
 */
public class ClientControl {
    // MARK: properties
    private InputStream in;
    private OutputStream out;
    private BufferedReader br;
    private PrintWriter pw;
    private String msgIn = "";
    private String msgOut = "";
    private String fileName = "";
    private String fileSize = "";

    public ClientControl (InputStream in, OutputStream out, BufferedReader br,PrintWriter pw){
        this.in = in;
        this.out = out;
        this.br = br;
        this.pw = pw;
    }

    public void sendACK(PrintWriter pw, String msg){
        //send ACK
        pw.println(msg);
        pw.flush();
    }

    public void selectMode(String mode, ServerControl sc){
        try{
            sendACK(pw,mode);
            if(mode.equals("GET")){ // file send mode
                if((msgIn = br.readLine())!=null){ // get file name
                    if(sc.isFileExist(msgIn)){
                        fileName = msgIn;
                        sendACK(pw,msgIn);
                        fileSize = sc.getFileSize(fileName);
                        sendACK(pw,fileSize); //send ack for filesize
                        //get seqNum
                        while((msgIn = br.readLine())!=null){
                            sc.fileSender(fileName,fileSize,msgIn,out);
                        }
                    }else{
                        sendACK(pw,"NAK");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
