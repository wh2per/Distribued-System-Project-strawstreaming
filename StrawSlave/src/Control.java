import java.io.*;

public class Control {
    private OutputStream out;
    private InputStream in;
    private PrintWriter pw;
    private BufferedReader br;

    private String filename;
    private int startSQN;
    private int endSQN;

    public Control(OutputStream out, InputStream in, PrintWriter pw, BufferedReader br) {
        this.in = in;
        this.out = out;
        this.pw = pw;
        this.br = br;

        filename = null;
        startSQN = 0;
        endSQN = 0;
    }

    public int ServerToSlave(){
        try {
            String line = null;

            //line = br.readLine();       // 서버로 부터 메세지 받기
            pw.println("ACK1");      // 서버로 ACK 전송
            pw.flush();

            filename = br.readLine();   // 서버로 부터 파일명 받기

            pw.println("ACK2");          // 서버로 ACK 전송
            pw.flush();

            line = br.readLine();       // 서버로 부터 start, end 시퀀스 넘버를 받기  ex) 0,10
            int index = line.indexOf(",");
            startSQN = Integer.parseInt(line.substring(0,index));
            endSQN = Integer.parseInt(line.substring(index+1));
            pw.println("ACK3");          // 서버로 ACK 전송
            pw.flush();
            line = br.readLine();       // ID 받음
            AudioDownloader ad = new AudioDownloader(out,in,pw,br, startSQN, endSQN);
            ad.downloadAudioFile(filename+"/",line);
            //ad.audioEncoding();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return 0;
    }
}
