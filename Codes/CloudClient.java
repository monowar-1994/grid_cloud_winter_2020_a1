/*
Author: Md. Monowar Anjum
This class contains the utilities for storing incoming cloud connection requests.
*/


import java.net.*;

public class CloudClient{
    private Socket incomingConnection;
    private int id;
    
    public CloudClient(int _id, Socket inc){
        incomingConnection = inc;
        id = _id;
    }

    public int getId(){
        return id;
    }

    public Socket getIncomingConnection(){
        return incomingConnection;
    }

}