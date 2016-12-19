/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package program1knappcha;
import java.net.*;

import java.io.*;
/**
 *
 * @author knappcha
 */
public class Program1KnappCha {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws java.io.IOException {
        // TODO code application logic here
        new Main().run();
        ServerSocket listener = new ServerSocket(9876);
        int clientNumber = 0;
        System.out.println("RUNNING");
        BufferedReader readSock;
        try {
            while (true) {
                new Factorial(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }

    }
    private static class Factorial extends Thread {
        private Socket socket;
        private int clientNumber;
        public Factorial(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            System.out.println("New connection with client# " + clientNumber 
                                + " at " + socket);
        }
        public void run(){
            try {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    String input = in.readLine();
                    int convert;
                    convert = Integer.parseInt(input);
                    int n = convert--;
                    int fin = 1;
                    for (int i = 1; i <=n; i++) {
                        fin = fin * i;
                    }
                    out.println(Integer.toString(fin));
                }
            } catch (IOException ex) {                    

                System.out.println( "Error: " + ex );
            } finally {
                try {
                    socket.close();
                } catch (IOException ex) {
                    System.out.println( "Error: " + ex );
                }    
        }
        }
    }
}