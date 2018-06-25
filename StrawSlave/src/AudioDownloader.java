import java.io.*;
import java.lang.reflect.Executable;

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
    private String fileName;

    public AudioDownloader(OutputStream out, InputStream in, PrintWriter pw, BufferedReader br, int startSQN, int endSQN) {
        this.in = in;
        this.out = out;
        this.pw = pw;
        this.br = br;

        this.startSQN = startSQN;
        this.endSQN = endSQN;
    }
    public File makeDir(String dirName){
        File file = new File(dirName);     // 파일명으로 디렉토리 생성
        if(!file.exists()) {               // 디렉토리가 존재하지 않으면
            file.mkdirs();
        }
        return file;
    }
    public int getBitRate(int id){
        int rate = 192;
        switch (id){
            case 0:
                rate = 192;
                break;
            case 1:
                rate = 128;
                break;
            case 2:
                rate = 64;
                break;
            default:
                rate = 192;
                break;
        }
        return rate;
    }
    public void audioEncoding(String dir, File file, int id){
        int rate = getBitRate(id);
        File encFile = new File(dir+"encoded"+Integer.toString(rate)+".mp3");
        new AudioEncoder(file,encFile,rate);
    }
    public void downloadAudioFile(String dirName,String slaveID) {
        try {
            String msg ="";
            int id = 0;
            int fileIdx = 0;
            // set file & file stream
            File file = makeDir(dirName);


            //fileName = dirName+startSQN+".mp3";
            fileName = dirName+"original.mp3";
            file = new File(fileName);      // 첫 번째 파일 생성
            FileOutputStream fos = new FileOutputStream(file);
            // set buffer
            byte buffer[] = new byte[BUFSIZE];

            int count;
            int i = 0;

            // download from server
            for(i=startSQN; i<=endSQN; i++){
                count = in.read(buffer);
                fos.write(buffer);
                //System.out.println(i+" "+endSQN);
                /*
                if(i<endSQN){
                    fileName = dirName+(i+1)+".mp3";
                    file = new File(fileName);
                    fos = new FileOutputStream(file);
                }*/
            }
            fos.flush();
            fos.close();

            //get slave id from server
            id = Integer.parseInt(slaveID);
            audioEncoding(dirName,file,id);

            //System.out.println("done");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}