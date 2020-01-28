import java.net.*;
import java.io.*;

public class CloudTenant implements Runnable{
    private Socket connection = null;
    private CloudRequestHandler refHandler = null;
    private Thread tenantCommunicationsThread = null;
    private int id;

    public CloudTenant(CloudRequestHandler ref , Socket con, int serial){
        connection = con;
        refHandler = ref;
        id = serial;
        start();
    }

    public void start(){
        tenantCommunicationsThread = new Thread(this);
        try{
            tenantCommunicationsThread.start();
        }catch(Exception e){
            System.out.println("Tenant Communications Thread got interrupted.");
            e.printStackTrace();
        }    

    }
    
    @Override
    public void run() {
        System.out.println("CloudTenant started with id: "+id);
        byte []buffer = new byte[2048];
        while(true){
            try{
                int numBytesRead = connection.getInputStream().read(buffer);
                DatagramPacket pack = new DatagramPacket(buffer, numBytesRead);
                if(id%2==0){
                    pack.setAddress(InetAddress.getByName(refHandler.worker_ip0));
                    pack.setPort(80);
                }
                else{
                    pack.setAddress(InetAddress.getByName(refHandler.worker_ip1));
                    pack.setPort(80);
                }
                refHandler.cloudWorker.send(pack);
            }catch(Exception e){
                System.out.println("Exception while reading data from cloudConnection");
                e.printStackTrace();
                break;
            }
        }
    }
}