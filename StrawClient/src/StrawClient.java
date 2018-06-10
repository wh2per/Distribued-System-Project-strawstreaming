/**
 * Created by jakeu on 2018. 6. 11..
 */
import java.net.*;

public class StrawClient {

    final static int port = 9999;

    public static void main(String[] args){
        try {
            ClientThread ct = new ClientThread();
            ct.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
