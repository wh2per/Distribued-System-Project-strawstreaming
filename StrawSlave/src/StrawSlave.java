public class StrawSlave {

    final static int port = 9999;

    public static void main(String[] args){
        try {
            SlaveThread ct = new SlaveThread();

            ct.start();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}