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
    public File audioEncoding(String dir, File file, int id){
        int rate = getBitRate(id);
        File encFile = new File(dir+"encoded"+Integer.toString(rate)+".mp3");
        new AudioEncoder(file,encFile,rate);
        return encFile;
    }
    public void audioSpliting(File file, String dir, int maxSeq){
        File temp;
        FileOutputStream fos;
        FileInputStream fin;
        byte buffer[];
        int fileSize = 0, bufsize = 0;
        try{
            fileSize = (int)file.length();
            bufsize = fileSize/(maxSeq-1);
            buffer = new byte[bufsize];
            fin = new FileInputStream(file);
            for(int i=0; i<maxSeq; i++){
                fin.read(buffer);
                temp = new File(dir+i+".mp3");
                fos = new FileOutputStream(temp);
                fos.write(buffer);
                fos.flush();
                fos.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void downloadAudioFile(String dirName,String slaveID) {
        try {
            String msg ="";
            int id = 0;
            int fileIdx = 0;
            // set file & file stream
            File file = makeDir(dirName);
            File encFile = makeDir(dirName+"temp/");

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
                if(count >-1){
                    fos.write(buffer,0,count);
                }
                //System.out.println(slaveID+" "+i+" "+count);
                /*
                if(i<endSQN){
                    fileName = dirName+(i+1)+".mp3";
                    file = new File(fileName);
                    fos = new FileOutputStream(file);
                }*/
            }
            fos.flush();
            fos.close();
            Thread.sleep(50);
            //get slave id from server
            id = Integer.parseInt(slaveID);
            encFile = audioEncoding(dirName,file,id);
            audioSpliting(encFile,dirName+"temp/",endSQN);

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
