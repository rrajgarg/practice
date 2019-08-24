import javafx.application.Application;
import javafx.stage.Stage;
import java.net.*;
import java.io.*;
class MultiThreading extends Thread
{
    private Socket socket = null;
    private ObjectInputStream objectInputStream=null;
    public MultiThreading(Socket socket)
    {
        try{
            this.socket=socket;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void run()
    {
        try{
            System.out.println("New ClientSocket created");
            objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            String line="";
            while(!line.equals("Over"))
            {
                try
                {
                    Message message = (Message) objectInputStream.readObject();
                    line = message.getMessage();
                    System.out.println(line);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
public class Client {
    protected Socket socket = null;
    private DataInputStream dataInputStream = null;
    private ObjectOutputStream objectOutputStream = null;
    public Client(String ip)
    {
        try
        {
            socket = new Socket(ip,8123);
            dataInputStream = new DataInputStream(System.in);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        MultiThreading multiThreading = new MultiThreading(socket);
        multiThreading.start();
        String line="";
        int to;
        while(!line.equals("Over")) {
            try {
                to = Integer.parseInt(dataInputStream.readLine());
                line = dataInputStream.readLine();
                Message message = new Message(line, to);
                objectOutputStream.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args)
    {
        Client client = new Client("127.0.0.1");
    }
}
