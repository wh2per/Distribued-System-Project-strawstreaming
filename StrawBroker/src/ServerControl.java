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

            for(int i=0; i<slaveSock.length; i++){
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
        sendMsg(this.servBr,this.servPw,"NAME");        //서버에게 NAME이라고 전송하고 ACK을 받기
        if(sendMsg(this.servBr,this.servPw,fileName)){          //파일이름을 서게 보내 있으면 true
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
        this.servPw.println(fileName);      // 서버에게 파일이름 전송
        this.servPw.flush();
        try{
            fileSize = this.servBr.readLine();      // 서버가 파일크기를 주기를 기다림
        }catch (IOException e){
            e.printStackTrace();
        }
        return fileSize;                // 파일크기 리턴
    }
    public boolean sendMsg(BufferedReader br,PrintWriter pw, String msg){
        //send msg
       // System.out.println(msg+"라 보내고 ");
        pw.println(msg);
        pw.flush();
        try{
            String tempMsg = br.readLine();
          //  System.out.println(tempMsg + "라고 ACK옴 ");
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

            int sockID = findSlaveSock(getQuality(msg));            // msg = (l,0) 어느 슬레이브 소켓을 통신할지 결정

            msgOut = "GET";
            if(sendMsg(br[sockID],pw[sockID],msgOut)){              // 해당 슬레이브에게 "GET"을 전송하고 ACK를 기다림
                if(sendMsg(br[sockID],pw[sockID],fileName)){        // 해당 슬레이브에게 파일 이름을 전송하고 ACK를 기다림
                    pw[sockID].println(getSeqtNum(msg));            // 해당 슬레이브에게 시퀀스 넘버를 전송
                    pw[sockID].flush();
                    count = in[sockID].read(buffer);                // 해당 슬레이브가 준 파일을 버퍼에 읽기
                    clntout.write(buffer,0,count);              // 클라이언트 out에 읽은 버퍼의 데이터를 쓰기
                    clntout.flush();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

