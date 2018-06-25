public class StrawSlave {

    public static void main(String[] args){
        try {
            SlaveThread ct = new SlaveThread();
            SlaveThread ct2 = new SlaveThread();
            SlaveThread ct3 = new SlaveThread();
            ct.start();
            ct2.start();
            ct3.start();
            BkrToSlvThread bt = new BkrToSlvThread(6666);
            bt.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}