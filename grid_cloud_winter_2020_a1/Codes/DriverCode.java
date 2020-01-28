import java.io.*;

public class DriverCode{
    public static void main(String[] args) {
        CloudRequestHandler reqHandler = new CloudRequestHandler(Integer.parseInt(args[0]),args[1],args[2]);
    }
}