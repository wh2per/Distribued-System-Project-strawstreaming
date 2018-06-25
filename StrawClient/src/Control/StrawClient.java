package Control;

public class StrawClient {

    final static int port = 9999;

    private ClientThread ct;
    private PlayThread pt;

    public StrawClient(){
        this.ct = new ClientThread();
        this.pt = new PlayThread();
        ct.start();
    }

    public void playAudio(){
        this.pt.start();
    }
}
