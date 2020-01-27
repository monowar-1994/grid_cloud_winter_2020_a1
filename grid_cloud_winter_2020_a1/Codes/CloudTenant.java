import java.net.*;
import java.io.*;

public class CloudTenant implements Runnable{
    private Socket connection = null;
    private CloudRequestHandler refHandler = null;
    private Thread tenantCommunicationsThread = null;

    public CloudTenant(CloudRequestHandler ref , Socket con){
        connection = con;
        refHandler = ref;
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
        byte []buffer = new byte[2048];
        while(true){
            try{
                int numBytesRead = connection.getInputStream().read(buffer);
            }catch(Exception e){
                System.out.println("Exception while reading data from cloudConnection");
                e.printStackTrace();
                break;
            }
        }
    }
}