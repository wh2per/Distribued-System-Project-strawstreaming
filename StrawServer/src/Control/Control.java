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

<<<<<<< HEAD
    public int ServerToSlave(String filename, String filePath, int id, int max) throws InterruptedException {
        try {
            // MARK: Property
            String line = null; // use for pw
            String msg = null; // use split
=======
    public int ServerToSlave(String filename, String filePath, int id){
        try {
            String line = null;
            String msg = null;
>>>>>>> 7104a1b073812325b4b4443848cec5fa1f94124b

            pw.println("1");            // 슬레이브로 다운로드 요청 전송
            pw.flush();

            line = br.readLine();           // 슬레이브로 부터 메세지 받기
            if(line.equals("ACK1")){         // 슬레이브로 부터 ACK 옴
                pw.println(filename);       // 슬레이브로 파일명 전송
                pw.flush();

                line = br.readLine();       // 슬레이브로 부터 메세지 받기
                if(line.equals("ACK2")) {     // 슬레이브로 부터 ACK 옴
                    AudioControl ad = new AudioControl(out,in,pw,br);
<<<<<<< HEAD
                    msg = ad.getSQN(filePath,id, max);
                    System.out.println(msg);
                    pw.println(msg);              // 슬레이브로 end 시퀀스 넘버를 전송  ex) 10
=======
                    msg = ad.getSQN(filePath,id);
                    System.out.println(msg);
                    pw.println(msg);              // 슬레이브로 start, end 시퀀스 넘버를 전송  ex) 0,10
>>>>>>> 7104a1b073812325b4b4443848cec5fa1f94124b
                    pw.flush();

                    line = br.readLine();
                    if(line.equals("ACK3")){
<<<<<<< HEAD
                        pw.println(Integer.toString(id));       // ID전송 (이것으로 고,중,저 정해야함)
                        pw.flush();
                        ad.sendAudioFile(filePath,msg);         //슬레이브로 음원을 전송
                        //pw.println(Integer.toString(id));        // 슬레이브로 종료 전송 & id for encoding
                        //pw.flush();
=======
                        ad.sendAudioFile(filePath);      //슬레이브로 음원을 전송

                        pw.println("2");        // 슬레이브로 다운로드 종료 전송
                        pw.flush();
>>>>>>> 7104a1b073812325b4b4443848cec5fa1f94124b
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
<<<<<<< HEAD
=======

>>>>>>> 7104a1b073812325b4b4443848cec5fa1f94124b
