import java.io.*;
import java.net.*;

public class CloudWorkerVM implements Runnable{
    private Thread vmThread = null;
    private DatagramSocket connection = null;

    public CloudWorkerVM(){
        start();
    }
    
    public void start(){
        try{
            connection = new DatagramSocket(100);
            vmThread = new Thread(this);
            vmThread.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        System.out.println("Started the VM on cloud");
        byte []buf = new byte[2048];
        DatagramPacket pack = new DatagramPacket(buf, 2048);
        BufferedWriter bw = null; 
        try{
            bw = new BufferedWriter(new FileWriter("output_stored.txt"));
        }catch(Exception e){
            e.printStackTrace();
        }
        while(true){
            try{
                connection.receive(pack);
                Thread.sleep(1);
            }catch(Exception e){
                e.printStackTrace();
                break;
            }
            
            byte []data = pack.getData();
            String str = null; 
            try{
                str = new String(data,0,pack.getLength(),"UTF-8");
            }catch(Exception e){
                e.printStackTrace();
            }
            if(str.equalsIgnoreCase("END")){
                try{
                    bw.flush();
                    bw.close();
                    System.out.println("Got the end");
                    break;
                }catch(Exception e){
                    e.printStackTrace();
                    break;
                }
            }
            else{
                //System.out.println("Writing: "+data.length + str);
                try{
                    bw.write(str);
                    bw.flush();
                }catch(Exception e){
                    e.printStackTrace();
                    break;
                }
            }
        }
        System.out.println("Upload Complete.");
        while(true){
            try{
                Thread.sleep(10000);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
    }
}