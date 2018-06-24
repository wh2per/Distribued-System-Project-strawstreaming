import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by jakeu on 2018. 6. 23..
 */
public class ClientControl {
    // MARK: properties
    private OutputStream out;
    private InputStream in;
    private PrintWriter pw;
    private BufferedReader br;

    public ClientControl (InputStream in, OutputStream out, BufferedReader br,PrintWriter pw){
        this.in = in;
        this.out = out;
        this.br = br;
        this.pw = pw;
    }

    public void selectMode(String mode, ServerControl sc){
        String msg = null;
        try{
            pw.print(msg);
            pw.flush();
            if(mode == "GET"){ // file send mode
                if((msg = br.readLine())!=null){ // get file name
                    pw.print(msg);
                    pw.flush();
                    sc.fileSender(msg, in, out);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
