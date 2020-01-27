import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import CloudClient;

public class CloudRequestHandler implements Runnable{

    private Thread cloudRequestHandlerThread = null;
    private ServerSocket cloudRequestHandlerServer = null;
    private ArrayList incomingTaskConnectionsList = null;
    public int clientCount;
    public HashMap taskToConnectionMap = null;
    public DatagramSocket cloudWorker1 = null;
    public DatagramSocket cloudWorker2 = null;
    public String ip1 = null;
    public String ip2 = null;
    public int port1;
    public int port2;
    
    public CloudRequestHandler(String ip_1, int port_1, String ip_2, int port_2){
        ip1 = ip_1;
        ip2 = ip_2;
        port1 = port_1;
        port2 = port_2;
        incomingTaskConnectionsList = new ArrayList<CloudClient>();
        clientCount = 0;
        taskToConnectionMap = new HashMap<Integer, Integer>();
        start();
    }

    public void start(){
        cloudRequestHandlerThread = new Thread(this);
        try{
            cloudWorker1 = new DatagramSocket(ip1, port1);
            cloudWorker2 = new DatagramSocket(ip2, port2);
        }catch(Exception e){
            System.out.println("DatagramSocket initialization failed.");
            e.printStackTrace();
        }
        try{
            cloudRequestHandlerServer = new ServerSocket(8080);
        }catch(Exception e){
            System.out.println("ServerSocket initialization failed.");
            e.printStackTrace();
        }
        try{
            cloudRequestHandlerThread.start();
        }catch(Exception e){
            System.out.println("CloudRequestHandlerThread could not be started.");
            e.printStackTrace();
        }
    }
    
    public void run(){
        while(true){
            try{
                Socket incomingCloudTaskRequestSocket = cloudRequestHandlerServer.accept();
                CloudClient newClient = new CloudClient(clientCount, incomingCloudTaskRequestSocket);
                clientCount++;
                incomingTaskConnectionsList.add(newClient);
            }catch(Exception e){
                System.out.println("Socket connection receive exception.");
                e.printStackTrace();
            } 
        }
    }

    public DatagramSocket getWorkerSocket(){
        int index = ThreadLocalRandom.current().nextInt(1,1000000);
        if(index%2==0){
            return cloudWorker1;
        }else{
            return cloudWorker2;
        }
    }

}