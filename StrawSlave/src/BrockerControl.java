
import java.io.*;
import java.net.Socket;

/**
 * Created by jakeu on 2018. 6. 24..
 * Broker -> slave
 * ACK 는 맞으면 들어온 메시지 그대로 보냄, 틀리면 NAK
 */
public class BrockerControl extends Thread {
    // MARK: Property
    private final static int BUFSIZE = 20480;
    private Socket sock;
    private InputStream in;
    private OutputStream out;
    private BufferedReader br;
    private PrintWriter pw;
    private String msgIn = "";
    private String msgOut = "";
    private String dirName = "";

    public BrockerControl(Socket sock){
        this.sock = sock;
    }
    public void sendACK(PrintWriter pw, String msg){
        //send ACK
        pw.println(msg);
        pw.flush();
    }
    public boolean isFileExist(String fileName){
        // find dir
        File file = new File(fileName);
        if(!file.exists()){
            return false;
        }
        return true;
    }
    public void fileSender(String fileName, OutputStream out){
        try{
            File file = new File(fileName);
            FileInputStream fin = new FileInputStream(file);
            byte buffer[] = new byte[BUFSIZE];
            int count = fin.read(buffer);
            out.write(buffer,0,count);
            out.flush();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void run(){
        try{
            //Streams and reader writer
            this.in = this.sock.getInputStream();
            this.out = this.sock.getOutputStream();
            this.br = new BufferedReader(new InputStreamReader(this.in));
            this.pw = new PrintWriter(new OutputStreamWriter(this.out));

            /*
            // Check Connection
            do{
                // tell broker, connection is fine
                pw.print("OK");
                pw.flush();
                // get ACK
                msgIn = br.readLine();
            }while(!msgIn.equals("OK"));
            */

            while((msgIn = br.readLine())!=null){
                /** if Broker wants audio
                 * msg check -> ACK -> fileName check -> ACK -> fileS
                 * */
                if(msgIn.equals("GET")){
                    //Send Ack
                    this.sendACK(pw,msgIn);
                    //Get fileName from broker
                    msgIn = br.readLine();
                    int idx = msgIn.indexOf(".");
                    dirName =  msgIn.substring(0,idx);
                    dirName = dirName+"/";
                    //Send Ack
                    if(isFileExist(dirName)){ // file exist
                        this.sendACK(pw,msgIn);
                        // get seq num from broker
                        msgIn = br.readLine();
                        dirName = dirName+msgIn+".mp3";
                        // send file to broker by seqNum
                        if(isFileExist(dirName)){
                            fileSender(dirName,out);
                        }
                    }else{
                        this.sendACK(pw,"NAK");
                    }
                }else if(msgIn.equals("EXIT")){
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
