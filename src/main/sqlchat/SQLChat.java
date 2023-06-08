package src.main.sqlchat;


import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class creates simple chat application between
 * client and server. Initially no arguments are passed
 * on to main method, the application then outputs its IP and port
 * to the terminal and waits for incoming request. Another instance
 * of this class using the earlier parameters can be started as client.
 * Connection is now established and chat can be initiated.
 * @author Ameya Benare
 * @version    1.0
 */
public class SQLChat {

    private static   Socket socket ;
    private static ServerSocket serverSocket ;

    /**
     * Entry point to the application. If argument is not present  {@link #startServer()}
     *  method is called. If arguments are present {@link #startClient(String[])} method will be called.
     *  <p>
     * Also, this main method may take two arguments : Ip-address and port.
     * @param args The command line arguments. args[0] should contain Ip-address
     *             and args[1] should contain port number.
     *
     *
     */
    public static void main (String args[]){
        if(args.length<1){
            startServer();
        }else{
            startClient(args);
        }
    }
    /**
     * This method initiates the server with random port number and prints Ip-address
     * and port on the console. Then waits for client to establish the connection.
     * <p>
     * Once connection is established, {@link #readData(BufferedReader)} method is called to read
     * the data from either client or from terminal. Data from client is printed on terminal
     * while data read from terminal is sent to client using PrintWriter Object.
     * <p>
     * The while loops continues till server sends or receives "exit" message.
     * At the moment {@link #closeConnection()} is called.
     * @see java.io.BufferedReader
     * @see java.io.InputStreamReader
     * @see java.net.Socket#getInputStream()
     * @see java.lang.System#in
     * @see java.io.PrintWriter#println(String)
     *
     */
    private static void startServer() {

        PrintWriter dataToClient ;
        BufferedReader readFromClient ;
        BufferedReader readFromTerminal;
        try {
            serverSocket = new ServerSocket(0);
            System.out.println("IP is "+ InetAddress.getLocalHost()+" port is "+serverSocket.getLocalPort());
            socket  = serverSocket.accept();
            System.out.println("Connection established");
            dataToClient = new PrintWriter(socket.getOutputStream(),true);
            readFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));//to read data from client
            readFromTerminal = new BufferedReader(new InputStreamReader(System.in));//to read data from terminal
            String s, s1;
            while((s = readData(readFromClient))!=null){
                System.out.println("Instance 2: "+s);
                s1 = readData(readFromTerminal);
                if(s1==null) break;
                dataToClient.println(s1);
            }
            closeConnection();
        } catch (IOException ex) {
            String exeMsg="Exception occurred while creating server.";
            Logger.getLogger(SQLChat.class.getName()).log(Level.SEVERE, exeMsg, ex);
            /**
             * Logs an exception indication error while creating a server.
             * @param ex #IOException that was caught
             */
        }

    }
    /**
     * This method creates {@link Socket} object using Ip-address and port provided
     * in the main method argument. Once, connection is established, the client can send
     * and receive message from server.
     * <p>
     * The {@link #readData(BufferedReader)} method is called to read
     * the data from either server or from terminal. Data from server is printed on terminal
     * while data read from terminal is sent to server using PrintWriter Object.
     * <p>
     * The while loops continues till client sends or receives "exit" message.
     * At the moment {@link #closeConnection()} is called.
     * @param args args[0] should contain Ip-address and args[1] should contain port number.
     * @see java.io.BufferedReader
     * @see java.io.InputStreamReader
     * @see java.net.Socket
     * @see java.lang.System#in
     * @see java.io.PrintWriter#println(String)
     *
     */

    private static void startClient(String args[]) {
        PrintWriter dataToServer ;
        BufferedReader dataFromServer ;
        BufferedReader dataFromTerminal;
        String s,s1;
        try {
            socket= new Socket(args[0],Integer.parseInt(args[1]));
            dataToServer = new PrintWriter(socket.getOutputStream() ,true);
            dataFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));//to read data from server
            dataFromTerminal = new BufferedReader(new InputStreamReader(System.in));//to read data from terminal
            while((s = readData(dataFromTerminal) ) !=null){
                dataToServer.println(s);
                s1=readData(dataFromServer);
                if(s1==null) break;
                System.out.println("Instance 1: "+s1);
            }
            closeConnection();
        } catch (IOException ex) {
            String exeMsg="Exception occurred while creating client socket.";
            Logger.getLogger(SQLChat.class.getName()).log(Level.SEVERE, exeMsg, ex);
            //Javadoc comment for catch block.
            /**
             * Logs an exception indication error while creating client.
             * @param ex #IOException that was caught
             */
        }

    }

    /**
     * This method reads message and calls {@link #chatCompleted(String)} to check if
     * message is equal to exit
     * @param bufferedReader method {@link BufferedReader#readLine()} is used to read message
     * @return message or 'null' is message value  is equal to  'exit'
     *
     */
    private static String readData(BufferedReader bufferedReader){
        String message = null;
        try {
            message = bufferedReader.readLine();
        } catch (IOException ex) {
            String exeMsg="Exception occurred while reading data.";
            Logger.getLogger(SQLChat.class.getName()).log(Level.SEVERE, exeMsg, ex);
            //Javadoc comment for catch block.
            /**
             * Logs an exception indication error while reading a message.
             * @param ex #IOException that was caught
             */
        }
        if(chatCompleted(message)) return null;
        return message;
    }

    /**
     * This method is used to close the {@link #socket} and {@link #serverSocket} objects.
     *
     */
    private static void closeConnection() {
        if(socket!=null  && !socket.isClosed()){
            try {
                socket.close();
            } catch (IOException ex) {
                String exeMsg="Exception occurred while closing the client socket.";
                Logger.getLogger(SQLChat.class.getName()).log(Level.SEVERE, exeMsg, ex);
                //Javadoc comment for catch block.
                /**
                 * Logs an exception indication error while closing {@link #socket}.
                 * @param ex #IOException that was caught
                 */
            }
            if (serverSocket!=null && !serverSocket.isClosed()){
                try {
                    serverSocket.close();
                } catch (IOException ex) {
                    String exeMsg="Exception occurred while closing the server socket.";
                    Logger.getLogger(SQLChat.class.getName()).log(Level.SEVERE, exeMsg, ex);
                    //Javadoc comment for catch block.
                    /**
                     * Logs an exception indication error while closing {@link #serverSocket}.
                     * @param ex #IOException that was caught
                     */
                }
            }
        }
    }

    /**
     * This method compares the message value to 'Exit'
     * @param message String which is to be compared
     * @return true if message value is 'Exit' or else false
     * @see java.lang.String#equalsIgnoreCase(String)
     *
     */
    private static Boolean chatCompleted(String message) {
        return "Exit".equalsIgnoreCase(message);
    }
}
