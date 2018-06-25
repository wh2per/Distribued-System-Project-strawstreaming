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
    public void audioEncoding(int start, int end, String s, String d, int rate){
        // audio Encoding
        String qual ="h";
        String source = s;
        File sour;
        File dest;
        AudioEncoder ae;
        switch (rate){
            case 128:
                qual = "m";
                break;
            case 64:
                qual = "l";
                break;
            default:
                qual = "h";
                break;
        }
        for(int i=start; i<=end; i++){
            source = source+i+".mp3";
            sour = new File(source);
            dest = new File(d+qual+i+".mp3");
            ae = new AudioEncoder(sour,dest,rate);
        }
    }
    public void downloadAudioFile(String dirName) {
        try {
            int fileIdx = 0;
            // set file & file stream
            File file = makeDir(dirName);
            File mFile = makeDir(dirName+"m/");
            File lFile = makeDir(dirName+"h/");

            //File total = makeDir(dirName+"temp.mp3");

            fileName = dirName+startSQN+".mp3";
            file = new File(fileName);      // 첫 번째 파일 생성
            FileOutputStream fos = new FileOutputStream(file);
            //FileOutputStream fost = new FileOutputStream(total);
            // set buffer
            byte buffer[] = new byte[BUFSIZE];

            int count;
            int i = 0;

            // download from server
            for(i=startSQN; i<=endSQN; i++){
                count = in.read(buffer);
                fos.write(buffer);
                //fost.write(buffer);
                System.out.println(i+" "+endSQN);
                if(i<endSQN){
                    fileName = dirName+(i+1)+".mp3";
                    file = new File(fileName);
                    fos = new FileOutputStream(file);
                }
            }
            //fost.flush();
            //fost.close();
            fos.flush();
            fos.close();

            audioEncoding(startSQN,endSQN,dirName,dirName+"m/",128);
            audioEncoding(startSQN,endSQN,dirName,dirName+"l/",64);

            //System.out.println("done");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}