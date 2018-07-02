public class StrawClient {

    final static int port = 9999;

    public static void main(String[] args){
        try {
            ClientThread ct = new ClientThread();
            PlayThread pt = new PlayThread();
            ct.start();
            pt.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
