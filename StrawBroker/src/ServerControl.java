import java.io.*;
import java.lang.invoke.SwitchPoint;
import java.net.Socket;

/**
 * Created by jakeu on 2018. 6. 23..
 */
public class ServerControl {
    // buffer Size
    final static int BUFSIZE = 20480;

    // MARK: properties
    private Socket servSock;
    private InputStream servIn;
    private OutputStream servOut;
    private BufferedReader servBr;
    private PrintWriter servPw;
    private Socket[] slaveSock;
    private InputStream[] in;
    private OutputStream[] out;
    private BufferedReader[] br;
    private PrintWriter[] pw;
    private String msgOut = "";

    public ServerControl(Socket servSock,Socket[] slaveSock){
        try{
            // set for main server sockets;
            this.servSock = servSock;
            this.servIn = servSock.getInputStream();
            this.servOut = servSock.getOutputStream();
            this.servBr = new BufferedReader(new InputStreamReader(this.servIn));
            this.servPw = new PrintWriter(new OutputStreamWriter(this.servOut));

            //set for slave sockets
            this.slaveSock = new Socket[slaveSock.length];
            this.in = new InputStream[slaveSock.length];
            this.out = new OutputStream[slaveSock.length];
            this.br = new BufferedReader[slaveSock.length];
            this.pw = new PrintWriter[slaveSock.length];

            for(int i=0; i<=0/*slaveSock.length*/; i++){
                this.slaveSock[i] = slaveSock[i];
                //Streams and reader writer
                this.in[i] = this.slaveSock[i].getInputStream();
                this.out[i] = this.slaveSock[i].getOutputStream();
                this.br[i] = new BufferedReader(new InputStreamReader(this.in[i]));
                this.pw[i] = new PrintWriter(new OutputStreamWriter(this.out[i]));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean isFileExist(String fileName){
        sendMsg(this.servBr,this.servPw,"NAME");
        if(sendMsg(this.servBr,this.servPw,fileName)){
            return true;
        }
        return false;
    }
    /*****************/
    // find socket by seq num
    public int findSlaveSock(String seqNum){
        int slave =0;
        switch (seqNum) {
            case "h":
                slave = 0;
                break;
            case "m":
                slave = 1;
                break;
            case "l":
                slave = 2;
                break;
            default:
                slave = 0;
                break;
        }
        return slave;
    }
    /******************/
    public String getFileSize(String fileName){
        String fileSize ="";
        this.servPw.println(fileName);
        this.servPw.flush();
        try{
            fileSize = this.servBr.readLine();
        }catch (IOException e){
            e.printStackTrace();
        }
        return fileSize;
    }
    public boolean sendMsg(BufferedReader br,PrintWriter pw, String msg){
        //send msg
        pw.println(msg);
        pw.flush();
        try{
            String tempMsg = br.readLine();
            if(msg.equals(tempMsg)){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public String getQuality(String msg){
        String[] temp = msg.split(",");
        return temp[0];
    }
    public String getSeqtNum(String msg){
        String[] temp = msg.split(",");
        return temp[1];
    }
    public void fileSender(String fileName, String fileSize, String msg, OutputStream clntout){
        try{
            byte buffer[] = new byte[BUFSIZE];
            int count;

            int sockID = findSlaveSock(getQuality(msg));
            msgOut = "GET";
            if(sendMsg(br[sockID],pw[sockID],msgOut)){
                if(sendMsg(br[sockID],pw[sockID],fileName)){
                    pw[sockID].println(getSeqtNum(msg));
                    pw[sockID].flush();
                    count = in[sockID].read(buffer);
                    clntout.write(buffer,0,count);
                    clntout.flush();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

