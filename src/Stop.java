import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Stop implements Runnable {
    private static Client client;
    private static Socket serverSocket;
    private static BufferedReader sercerReceive;
    private static Thread game;
    private static Socket socket;
public Stop(Client client, Socket serverSocket,BufferedReader sercerReceive,Thread game,Socket socket){
    this.client=client;
    this.serverSocket=serverSocket;
    this.sercerReceive=sercerReceive;
    this.game=game;
    this.socket=socket;
}
    @Override
    public void run() {
    try {
        try {
            while (!(client.receive(serverSocket, sercerReceive).compareTo("quit") == 0)) {

            }
            System.out.println("User trigger session termination!\nServer terminated!");
        }catch (NullPointerException e){
            System.err.println();
            System.err.println("============================================================");
            System.err.println("Server has terminate the connection due to connection issue!");
            System.err.println("============================================================");

        }
        game.interrupt();
        System.out.println("Session killed");
        game.setPriority(Thread.MIN_PRIORITY);
        socket.close();
        socket=null;
        System.exit(0);
        return;
    }catch(IOException e){
        e.printStackTrace();
    }
    }
}
