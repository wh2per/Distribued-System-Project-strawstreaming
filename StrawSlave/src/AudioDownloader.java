import java.io.*;

public class AudioDownloader {
    // buffer Size
    final static int BUFSIZE = 20480;

    // MARK: properties
    private OutputStream out;
    private InputStream in;
    private PrintWriter pw;
    private BufferedReader br;

    private int startSQN;
    private int endSQN;

    public AudioDownloader(OutputStream out, InputStream in, PrintWriter pw, BufferedReader br, int startSQN, int endSQN) {
        this.in = in;
        this.out = out;
        this.pw = pw;
        this.br = br;

        this.startSQN = startSQN;
        this.endSQN = endSQN;
    }

    public void downloadAudioFile(String dirName) {
        try {
            int fileIdx = 0;
            // set file & file stream
            File file = new File(dirName);     // 파일명으로 디렉토리 생성
            if(!file.exists()) {               // 디렉토리가 존재하지 않으면
                file.mkdirs();
            }/*else{                             // 디렉토리가 존재하면
                File[] files = file.listFiles();
                for(File f : files){
                    f.delete();                // 디렉토리 안에 파일들을 삭제
                }
            }*/

            file = new File(dirName+startSQN+".mp3");      // 첫 번째 파일 생성
            FileOutputStream fos = new FileOutputStream(file);

            // set buffer
            byte buffer[] = new byte[BUFSIZE];

            int count;
            int i = 0;

            for(i=startSQN; i<=endSQN; i++){
                count = in.read(buffer);
                fos.write(buffer);
                System.out.println(i+" "+endSQN);
                if(i<endSQN){
                    file = new File(dirName+(i+1)+".mp3");
                    fos = new FileOutputStream(file);
                }
            }
            fos.flush();
            fos.close();

            //System.out.println("done");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}