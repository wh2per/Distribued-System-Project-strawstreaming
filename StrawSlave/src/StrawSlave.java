public class StrawSlave {

    public static void main(String[] args){
        try {
            SlaveThread ct = new SlaveThread();
            ct.start();
            BkrToSlvThread bt = new BkrToSlvThread(7777);
            bt.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}