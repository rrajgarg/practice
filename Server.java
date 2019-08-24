import javafx.util.Pair;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server {
    public static ArrayList<Multi>SocketArray = new ArrayList<Multi>();
    private ServerSocket server = null;
    int i=0;
    public Socket socket = null;
    public ObjectInputStream objectInputStream = null;
    public ObjectOutputStream objectOutputStream =null;
    Server()
    {
        try
        {
            server = new ServerSocket(8123);
            System.out.println("Server has started");
            while(true)
            {
                System.out.println("Waiting for client...");
                socket=server.accept();
                System.out.println("Client "+i+" is connected");
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                System.out.println(":):):):):)");
                Multi multi = new Multi(socket,i,objectInputStream,objectOutputStream);
                Thread t=new Thread(multi);
                t.start();
                SocketArray.add(multi);
                i++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String args[])
    {
        Server server = new Server();
    }
}
class Multi implements Runnable
{
    private Socket socket;
    private int i;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private boolean isLoggedIn;
    public Multi(Socket socket,int i,ObjectInputStream objectInputStream,ObjectOutputStream objectOutputStream)
    {
        this.socket=socket;
        this.i=i;
        this.objectInputStream=objectInputStream;
        this.objectOutputStream=objectOutputStream;
        isLoggedIn = true;
    }
    public void run()
    {
        System.out.println("Thread has started");
        String recieved="";
        while(true)
        {
            try{
                Message message = (Message) objectInputStream.readObject();
                System.out.println("Recieved an object :)");
                recieved = message.getMessage();
                System.out.println(recieved);
                if(recieved.equals("log out"))
                {
                    isLoggedIn=false;
                    socket.close();
                    break;
                }
                else if(message.getTo()==i)
                {
                    System.out.println(message.getMessage());
                }
                else
                {
                    int to = message.getTo();
                    for(Multi multi: Server.SocketArray)
                    {
                        if(multi.isLoggedIn==true && multi.i==to)
                        {
                            objectOutputStream.writeObject(message);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}