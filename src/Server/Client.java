package Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){
        try {
            Socket socket = new Socket("localhost", 1004);
            PrintWriter os = new PrintWriter(socket.getOutputStream(),true);
            Scanner sc = new Scanner(System.in);
            String message;
            while(true){
                message = sc.next();
                os.println(message);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
