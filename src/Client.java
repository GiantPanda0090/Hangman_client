import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private int port = 9000;
    private String ip;

    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.ip="localhost";
            ServerSocket listendingSocket= new ServerSocket(8001);//back door
            Socket socket = new Socket(client.ip, client.port);//normal com
                Socket serverSocket = listendingSocket.accept();
            Scanner keyboard = new Scanner(System.in);
                System.out.println("Socket open!");
                //backdoor send/receive
                BufferedReader sercerReceive = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            PrintWriter sercerSend = new PrintWriter(serverSocket.getOutputStream());
            PrintWriter send = new PrintWriter(socket.getOutputStream());


            BufferedReader receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("\n==================================");
            System.out.println("Input Option:\nstart -> Start the game\nquit -> Quit the game\n==================================\n\nUser allow to input quit into the guess box anytime during the game to Quit the game!\n\nHow to play:\nYou can either guess word by word or finish up the rest of the letter once for all\nPlayer has same amount of chance as the length of the word!\nGood luck and Have fun!");
            System.out.print("\nInput command:");
                String keyboardIn = keyboard.nextLine();
                client.send(keyboardIn, socket, send);

                String confirm =client.receive(socket,receive);
                while(confirm.compareTo("session")!=0){
                    if(confirm.compareTo("quittrigger")==0){
                        System.exit(0);
                        break;
                    }
                    System.out.print("Input command:");
                    keyboardIn = keyboard.nextLine();
                    client.send(keyboardIn, socket, send);
                    confirm =client.receive(socket,receive);
                };

                Thread game = new Thread(new Game(socket, keyboard, send, receive,sercerSend));
                game.setPriority(Thread.MAX_PRIORITY);
                game.start();
           Thread stopMonitor =new Thread(new Stop(client,serverSocket,sercerReceive,game,socket));
           stopMonitor.start();


        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public String receive(Socket socket,BufferedReader receive) throws IOException{
        String str=receive.readLine();
            return str;

    }

    private void send(String message,Socket socket,PrintWriter send) {
            send.println(message);
            send.flush();

    }

}
