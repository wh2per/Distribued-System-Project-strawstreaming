package Control;

import java.io.*;

public class Control {
    private OutputStream out;
    private InputStream in;
    private PrintWriter pw;
    private BufferedReader br;

    private int startSQN;
    private int endSQN;

    public Control(OutputStream out, InputStream in, PrintWriter pw, BufferedReader br) {
        this.in = in;
        this.out = out;
        this.pw = pw;
        this.br = br;
    }

    public int ServerToSlave(String filename, String filePath, int id, int max){
        try {
            // MARK: Property
            String line = null; // use for pw
            String msg = null; // use split

            pw.println("1");            // 슬레이브로 다운로드 요청 전송
            pw.flush();

            line = br.readLine();           // 슬레이브로 부터 메세지 받기
            if(line.equals("ACK1")){         // 슬레이브로 부터 ACK 옴
                pw.println(filename);       // 슬레이브로 파일명 전송
                pw.flush();

                line = br.readLine();       // 슬레이브로 부터 메세지 받기
                if(line.equals("ACK2")) {     // 슬레이브로 부터 ACK 옴
                    AudioControl ad = new AudioControl(out,in,pw,br);
                    msg = ad.getSQN(filePath,id, max);
                    System.out.println(msg);
                    pw.println(msg);              // 슬레이브로 start, end 시퀀스 넘버를 전송  ex) 0,10
                    pw.flush();

                    line = br.readLine();
                    if(line.equals("ACK3")){
                        ad.sendAudioFile(filePath,msg);      //슬레이브로 음원을 전송
                        pw.println("2");        // 슬레이브로 다운로드 종료 전송
                        pw.flush();
                    }
                }
            }else{
                System.out.println("다운로드 요청부터 전송하세요 ! ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return 0;
    }
}