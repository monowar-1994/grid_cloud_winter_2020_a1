public class CloudClientDriver{
    public static void main(String[] args) {
        CloudStorageClient temp = new CloudStorageClient(args[0], Integer.parseInt(args[1]));
    }
}