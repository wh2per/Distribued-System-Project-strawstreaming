import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by jakeu on 2018. 6. 23..
 */
public class ServerControl {
    // buffer Size
    final static int BUFSIZE = 2048;

    private Socket[] slaveSock;

    public ServerControl(Socket[] slaveSock){
        for(int i=0; i<slaveSock.length; i++){
            this.slaveSock[i] = slaveSock[i];
        }
    }
    public void fileSender(String fileName, InputStream in, OutputStream out){
        // set buffer
        byte buffer[] = new byte[BUFSIZE];
        int count;

    }
}

