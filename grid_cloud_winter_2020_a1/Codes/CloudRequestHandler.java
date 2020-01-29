import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;



public class CloudRequestHandler implements Runnable{

    private Thread cloudRequestHandlerThread = null;
    private ServerSocket cloudRequestHandlerServer = null;
    private ArrayList incomingTaskConnectionsList = null;
    public int clientCount;
    public HashMap taskToConnectionMap = null;
    public DatagramSocket cloudWorker = null;
    public int port;



    public String worker_ip0 = null;
    public String worker_ip1 = null;
    
    public CloudRequestHandler(int _port, String ip1, String ip2){
        port = _port;
        worker_ip0 = ip1;
        worker_ip1 = ip2;
        incomingTaskConnectionsList = new ArrayList<CloudClient>();
        clientCount = 0;
        taskToConnectionMap = new HashMap<Integer, Integer>();
        start();
    }

    public void start(){
        cloudRequestHandlerThread = new Thread(this);
        try{
            cloudWorker = new DatagramSocket(port);
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

        System.out.println("Request Handler successfully start. Ready to operate on the Cloud.");
    }
    
    public void run(){
        while(true){
            try{
                Socket incomingCloudTaskRequestSocket = cloudRequestHandlerServer.accept();
                CloudClient newClient = new CloudClient(clientCount, incomingCloudTaskRequestSocket);
                clientCount++;
                incomingTaskConnectionsList.add(newClient);
                System.out.println("Received a connection request and processing now");
                // A new CloudTenant is created as soon as a new Cloud Request comes from a new connection

                CloudTenant tempTenant = new CloudTenant(this, newClient.getIncomingConnection(), newClient.getId());
                Thread.sleep(1);
                // CloudTenant creation ends.  
            }catch(Exception e){
                System.out.println("Socket connection receive exception.");
                e.printStackTrace();
            } 
        }
    }

}