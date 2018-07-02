package Control;

public class StrawClient {

    final static int port = 9999;
    public static String bandwidth = "l";

    private ClientThread ct;
    private PlayThread pt;
    private BnwthListener bl;

    public StrawClient(){
        this.ct = new ClientThread();
        this.pt = new PlayThread();
        this.bl = new BnwthListener();
        //ct.start();
        bl.start();
    }

    public void playAudio(){
        this.ct.start();
        this.pt.start();
    }
}
