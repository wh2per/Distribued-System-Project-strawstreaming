import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

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

    public void selectMode(String mode, ServerControl sc){          //시작
        try{
            int i=0;

            sendACK(pw,mode);       // 클라이언트한테 "GET"을 받았다고 클라이언트에게 ACK를 전송
            if(mode.equals("GET")){ // file send mode       // 클라이언트에게 "GET"을 받았다면
                if((msgIn = br.readLine())!=null){ // get file name     //클라이언트에게 파일이름을 기다림
                    if(sc.isFileExist(msgIn)){                          //서버에 파일이 존재하면 true
                        fileName = msgIn;                               //파일이름을 저장
                        sendACK(pw,msgIn);                              //파일이름을 받았다고 클라이언트에게 전송
                        fileSize = sc.getFileSize(fileName);            //슬레이브에게 파일이름을 보내서 파일 크기를 받아옴
                       // System.out.println("filesize : "+fileSize);
                        sendACK(pw,fileSize); //send ack for filesize   //클라이언트에게 파일 크기를 전송
                        //get seqNum
                        while(i<Integer.parseInt(fileSize)) {
                            while ((msgIn = br.readLine()) != null) {           // 클라이언트에게 값을 기다림 (l,0)
                                System.out.println(msgIn);
                                sc.fileSender(fileName, fileSize, msgIn, out); // (l,0)을 가지고 파일을 보내자
                            }
                            System.out.println("i = "+i);
                            i++;
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
