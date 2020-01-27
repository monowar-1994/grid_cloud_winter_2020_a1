import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import CloudClient;

public class CloudRequestHandler implements Runnable{

    private Thread cloudRequestHandlerThread = null;
    private ServerSocket cloudRequestHandlerServer = null;
    private ArrayList incomingTaskConnectionsList = null;
    public int clientCount;
    public HashMap taskToConnectionMap = null;
    
    public CloudRequestHandler(){
        incomingTaskConnectionsList = new ArrayList<CloudClient>();
        clientCount = 0;
        taskToConnectionMap = new HashMap<Integer, Integer>();
        start();
    }

    public void start(){
        cloudRequestHandlerThread = new Thread(this);
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

}