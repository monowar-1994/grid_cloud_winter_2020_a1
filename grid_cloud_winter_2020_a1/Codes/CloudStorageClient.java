import java.io.*;
import java.net.*;

public class CloudStorageClient implements Runnable{
    private Thread storageClientThread = null;
    private Socket connection = null;
    private String cloud_ip = null;
    private int port;
    public CloudStorageClient(String _cloud_ip, int _port){
        cloud_ip = _cloud_ip;
        port = _port;
    }

    public void start(){

        try{
            connection = new Socket(cloud_ip, port);
            System.out.println("Connection established with the cloud side.");
            storageClientThread = new Thread(this);
            storageClientThread.start();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    @Override
    public void run() {
        BufferedReader br = new BufferedReader(new FileReader("output.txt"));
        String line=null;
        int index = 0;
        while( (line=br.readLine()) != null) {
            byte []buffer = line.getBytes();
            
            try{
                connection.getOutputStream().write(buffer);
                connection.getOutputStream().flush();
                index++;
                System.out.println("Uploaded chunk index: "+index);
                Thread.sleep(250);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        br.close();
        System.out.println("File upload complete.");

        String end= "END";
        try{
            connection.getOutputStream().write(end.getBytes());
            connection.getOutputStream().flush();
            System.out.println("Killing the Thread.");
        }catch(Exception e){
            e.printStackTrace();
        }

        while(true){
            try{
                Thread.sleep(10000);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }
}