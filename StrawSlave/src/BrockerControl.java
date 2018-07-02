
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
    private OutputStream out2;
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
    public void fileSender(String fileName, OutputStream out2){
        try{
            File file = new File(fileName);     //파일을 열고
            FileInputStream fin = new FileInputStream(file);        //파일인풋스트림 생성
            byte buffer[] = new byte[BUFSIZE];
            int count = fin.read(buffer);       //버퍼에 파일을 읽음
            fin.close();
            out2.write(buffer,0,count);         //out에 버퍼의 넣은 데이터를 쓰기
            out2.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public String getMsg(BufferedReader br){
        String msg="";
        try{
            while((msg = br.readLine())!=null){
                return  msg;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return msg;
    }
    public void run(){
        try{
            //Streams and reader writer
            this.in = this.sock.getInputStream();
            this.out = this.sock.getOutputStream();
            this.out2 = this.sock.getOutputStream();
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

            //msgIn = getMsg(br);                       // 이거 일단 중복이라 삭제함

            while((msgIn = br.readLine())!=null){           // 브로커에게 "GET"을 기다림
                /** if Broker wants audio
                 * msg check -> ACK -> fileName check -> ACK -> fileS
                 * */
                //System.out.println(msgIn);
                System.out.println(msgIn + "from broker!");
                if(msgIn.equals("GET")){                // 브로커가 "GET"을 보냈다면
                    //Send Ack
                    this.sendACK(pw,msgIn);             // 브로커에게 "GET"을 보냄      -> 여기가 두번째부터 오류남 pw에 자꾸 파일데이터가 나감
                    //Get fileName from broker
                    msgIn = br.readLine();          // 브로커에게 파일이름을 받음 "audio1.mp3"
                    System.out.println(msgIn);
                    int idx = msgIn.indexOf(".");
                    System.out.println(idx);
                    dirName =  msgIn.substring(0,idx);          // dirName = audio1
                   // System.out.println(dirName);
                    dirName = dirName+"/temp/";                 // dirName = audio1/temp/
                    //Send Ack
                    if(isFileExist(dirName)){ // file exist
                        this.sendACK(pw,msgIn);         //브로커에게 파일이름을 받았다고 ACK전송
                        // get seq num from broker
                        msgIn = getMsg(br);             // 브로커에게 시퀀스 넘버를 기다림
                        dirName = dirName+msgIn+".mp3";     //dirName = audio1/temp/1.mp3
                        // send file to broker by seqNum
                        if(isFileExist(dirName)){           //만약 파일이 존재 한다면
                            fileSender(dirName,out2);        // 브로커에게 파일을 전성 (out에 쓰기)
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
