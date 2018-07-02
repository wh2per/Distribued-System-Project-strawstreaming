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
            while ((msgIn = br.readLine()) != null) {           //브로커에게 "NAME"이라고 왔는지 검사
                sendACK(pw,msgIn);                              // "NAME"이라고 받았다고 전송
                msgIn = br.readLine();                          // 브로커에게 파일이름 기다림
                if(isFileExist(msgIn)){                         // 파일이 존재하면
                    sendACK(pw,msgIn);                          // 브로커에게 파일이름을 받았다고 전송
                    msgIn = br.readLine();                      // 파일이름 받으면
                    sendACK(pw,getFileSize(msgIn));             // ACK으로 브로커에게 파일 사이즈를 전송
                }else{
                    sendACK(pw,"NAK");
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
