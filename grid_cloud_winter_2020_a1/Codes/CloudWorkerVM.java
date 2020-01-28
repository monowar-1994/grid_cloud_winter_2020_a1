import java.io.*;
import java.net.*;

public class CloudWorkerVM implements Runnable{
    private Thread vmThread = null;
    private DatagramSocket connection = null;
    
    public void start(){
        try{
            connection = new DatagramSocket(80);
            vmThread = new Thread(this);
            vmThread.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        
        
    }
}