import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Game implements Runnable {
    private static Socket socket=null;
    private static Scanner keyboard=null;
    private static PrintWriter send =null;
    private static BufferedReader receive = null;
    private static PrintWriter sercerSend =null;
    public Game(Socket socket,Scanner keyboard,PrintWriter print,BufferedReader read,PrintWriter sercerSend){
        this.socket=socket;
        this.keyboard=keyboard;
        this.send=print;
        this.receive=read;
        this.sercerSend=sercerSend;
    }

    //main
    @Override
    public void run() {
        try {
            while(true) {
                //current session
                System.out.println("Session start!");
                String length = receive();
                System.out.println("The length of the word is: " + length);

                //start guessing
                while (true) {
                    //current word
                    if (receive().compareTo("end") == 0) {
                        break;
                    }
                    System.out.print("Your guess is: ");
                    send(keyboard.nextLine());
                    System.out.println();
                    String result = receive();
                    if (result.compareTo("correct") == 0) {
                        System.out.println("The answer is correct!");
                    } else if (result.compareTo("wrong") == 0) {
                        System.out.println("The answer is wrong!");
                    } else if (result.compareTo("KO") == 0) {
                        System.out.println("YOU HAVE GUESSED OUT THE WHOLE WORD!");
                    }
                }
                String status = receive();
                String score = receive();
                if (status.compareTo("gameover") == 0) {
                    System.out.println("Game Over");
                    System.out.println("Your current score is: "+score);
                } else {
                    System.out.println("We have a winner!");
                    System.out.println("Your current score is: " + score);
                }
                System.out.println();
                System.out.println("==========================================================");
                System.out.println("Next round!");

            }

        }catch (IOException e){
            e.printStackTrace();
        }
keyboard.close();
    }
    public ArrayList returnMsg(ArrayList lst){
        return lst;
    }


    private String receive() throws IOException {
        String str;
         str = receive.readLine();
        return str;
    }

    private void send(String message) {
        if(message.compareTo("quit")==0){
            sercerSend.println(message);
            sercerSend.flush();

        }else {
            send.println(message);
            send.flush();
        }
        }
    }


