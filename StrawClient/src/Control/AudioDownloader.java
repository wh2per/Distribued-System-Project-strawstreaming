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
        pw.println(msg);
        pw.flush();
        try{
            String tempMsg = br.readLine();
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
        try{
            if(sendMsg(this.br,this.pw,"GET")){
                if(sendMsg(this.br,this.pw,fileName)){
                    String tempMsg = br.readLine();
                    fileSize = Integer.parseInt(tempMsg);
                }
            }
            // set file & file stream
            File file = new File(filePath+fileName);
            FileOutputStream fos = new FileOutputStream(file);

            // set buffer
            byte buffer[] = new byte[BUFSIZE];
            int count;

            for(int i=0; i<fileSize; i++){
                pw.println(i);
                pw.flush();
                //System.out.println(i+" "+fileSize);
                count = in.read(buffer);
                fos.write(buffer);
            }

            in.close();
            fos.flush();
            fos.close();

            System.out.println("done");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
