package Control;

import java.io.*;

public class AudioDownloader {
    // buffer Size
    final static int BUFSIZE = 20480;

    // MARK: properties
    private OutputStream out;
    private InputStream in;
    private PrintWriter pw;
    private BufferedReader br;

    public AudioDownloader(OutputStream out, InputStream in, PrintWriter pw, BufferedReader br){
        this.in = in;
        this.out = out;
        this.pw = pw;
        this.br = br;
    }
    public boolean sendMsg(BufferedReader br,PrintWriter pw, String msg){
        //send msg
        System.out.println("브로커한테 "+msg+"라고 보넴");
        pw.println(msg);
        pw.flush();
        try{
            String tempMsg = br.readLine();
            System.out.println("브로커한테 "+tempMsg+"라고 옴");
            if(msg.equals(tempMsg)){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public void downloadAudioFile(String filePath, String fileName){
        int fileSize = 0;

        String msg = "";
        try{
            if(sendMsg(this.br,this.pw,"GET")){         // 브로커에게 "GET"을 전송하고 ACK를 받았으면
                if(sendMsg(this.br,this.pw,fileName)){          // 파일이름 "audio1.mp3"을 전송하고 ACK를 받았으면
                    String tempMsg = br.readLine();             // 브로커에게 파일 크기를 기다림
                    fileSize = Integer.parseInt(tempMsg);       // 파일 크기를 저장
                }
            }
            // set file & file stream
            File file = new File(filePath+fileName);
            FileOutputStream fos = new FileOutputStream(file);

            // set buffer
            byte buffer[]= new byte[BUFSIZE];
            int count;

            for(int i=0; i<fileSize; i++){      // 파일 크기 만큼 반복

                msg = StrawClient.bandwidth+","+i;      // 메세지 생성 ex) (l,0)  ... (l,1)
                System.out.println(msg);
                pw.println(msg);                        // 브로커에게 메세지 전
                pw.flush();
                //System.out.println(i+" "+fileSize);
                while ((count = in.read(buffer)) != -1)     // 파일 받기를 기다림
                    fos.write(buffer,0,count);          // 스트림에 받은 파일을 저장
                                                        // fos.flush() 를 아래로 뺌
            }
            fos.flush();
            in.close();
            fos.close();

            System.out.println("done");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
