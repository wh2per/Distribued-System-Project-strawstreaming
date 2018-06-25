import java.io.*;
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
    public int findSlaveSock(String seqNum, String fileSize){
        int maxSlave = 3;
        int seq = Integer.parseInt(seqNum);
        int size = Integer.parseInt(fileSize);
        //System.out.println(size/maxSlave);
        if(seq<=(size/maxSlave)){
            return 0;
        }else if(seq<=(size/maxSlave)*2){
            return 0;
        }else{
            return 0;
        }
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
    public void fileSender(String fileName, String fileSize, String seqNum, OutputStream clntout){
        try{
            byte buffer[] = new byte[BUFSIZE];
            int count;
            int sockID = findSlaveSock(seqNum,fileSize);
            msgOut = "GET";
            if(sendMsg(br[sockID],pw[sockID],msgOut)){
                if(sendMsg(br[sockID],pw[sockID],fileName)){
                    pw[sockID].println(seqNum);
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

