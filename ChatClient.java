import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

final class ChatClient {
    private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
    private Socket socket;

    private final String server;
    private final String username;
    private final int port;
//    private static ArrayList<String> uSers=new ArrayList<>();

    /* ChatClient constructor
     * @param server - the ip address of the server as a string
     * @param port - the port number the server is hosted on
     * @param username - the username of the user connecting
     */
    private ChatClient(String server, int port, String username) {
        this.server = server;
        this.port = port;
        this.username = username;
    }

    Date currentTime= new Date();
    SimpleDateFormat simpleDateFormat= new SimpleDateFormat("HH:mm:ss");
    /**
     * Attempts to establish a connection with the server
     * @return boolean - false if any errors occur in startup, true if successful
     */
    private boolean start() {
        // Create a socket
        try {
            socket = new Socket(server, port);
            System.out.println(simpleDateFormat.format(currentTime)+" Connection accepted "+socket.getInetAddress()+":"+socket.getPort());

        }catch (ConnectException e){
            System.out.println("Please start the server first, can't connect");
            System.exit(0);
            return false;
        }
        catch (EOFException e){
            System.out.println("Please start the server first, can't connect");
            System.exit(0);
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Please start the server first, can't connect");
            System.exit(0);
            return false;

        }
        // Attempt to create output stream
        try {
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Attempt to create input stream
        try {
            sInput = new ObjectInputStream(socket.getInputStream());
        } catch (SocketException e){

        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Create client thread to listen from the server for incoming messages
        Runnable r = new ListenFromServer();
        Thread t = new Thread(r);
        t.start();

        // After starting, send the clients username to the server.
        try {
            sOutput.writeObject(username);
        }catch (SocketException e){
            e.printStackTrace();
            System.exit(0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }


    /*
     * Sends a string to the server
     * @param msg - the message to be sent
     */
    private void sendMessage(ChatMessage msg) {
        try {
            sOutput.writeObject(msg);
        } catch (SocketException e){
            System.out.println("You are not allowed to write anything.Please connect to a server first.");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
   static Scanner scanner=new Scanner(System.in);


    /*
     * To start the Client use one of the following command
     * > java ChatClient
     * > java ChatClient username
     * > java ChatClient username portNumber
     * > java ChatClient username portNumber serverAddress
     *
     * If the portNumber is not specified 1500 should be used
     * If the serverAddress is not specified "localHost" should be used
     * If the username is not specified "Anonymous" should be used
     */
    public static void main(String[] args) {
        // Get proper arguments and override defaults
        ChatClient client=new ChatClient("localhost", 1500, "CS 180 Student");

        if (args.length==0){
            args[0]="Anonymous";
            client=new ChatClient("localhost", 1500, args[0]);
          //  ChatServer.userNames.add(args[0]);

        }
        else if(args.length==1){
             client = new ChatClient("localhost", 1500, args[0]);
           // ChatServer.userNames.add(args[0]);

        }
       else if(args.length==2){
             client = new ChatClient("localhost",Integer.parseInt(args[1]), args[0]);
           // ChatServer.userNames.add(args[0]);


        }
        else if (args.length==3){
             client = new ChatClient(args[2],Integer.parseInt(args[1]), args[0]);
            //ChatServer.userNames.add(args[0]);


        }
//        for(int i=0;i<ChatClient.uSers.size();i++){
//            if(ChatClient.uSers.get(i)==client.username){
//                System.out.println("User Already exists");
//                System.exit(0);
//            }
//        }
//        ChatClient.uSers.add(args[0]);



        // Create your client and start it
       // ChatClient client = new ChatClient("localhost", 1500, "CS 180 Student");
try {


    client.start();
}catch (Exception e){

}


        // Send an empty message to the server



        String message="";
        String temp = "/logout";
        String ticTacToe="/ttt";
        String list="/list";
        String dm="/msg";

        do {

            while (message.isEmpty()) {
                try {
                    message = scanner.nextLine();
                }catch (IllegalStateException e){


                }catch (NoSuchElementException e){

                }
            }


            if ((message.toLowerCase()).equals(temp)) {
                client.sendMessage(new ChatMessage(1, message,""));
            }
            else if(message.toLowerCase().equals(list)){
                client.sendMessage(new ChatMessage(3, message,""));
            }
            else if (message.toLowerCase().contains(dm)){
                int n=message.indexOf(" ");
                int m=message.indexOf(" ",n+1);
                String recipient=message.substring(n+1,m);
                message=message.substring(m+1);
                client.sendMessage(new ChatMessage(2, message,recipient));

            }else if (message.toLowerCase().contains(ticTacToe)){
               // int n=message.indexOf(" ");
               // int m=message.indexOf(" ",n+1);
               // if(m!=-1) {
                   // message = message.substring(n + 1);
//                    client.sendMessage(new ChatMessage(4, message,""));

                //}
//                else {
//                    char ch=message.charAt(m+1);
//
//                }

                client.sendMessage(new ChatMessage(4,message,""));

            }
            // Send an empty message to the server
            else {
                client.sendMessage(new ChatMessage(0, message,""));


            }
            message="";
        }while(!((message.toLowerCase()).equals("/logout")));
    }


    /*
     * This is a private class inside of the ChatClient
     * It will be responsible for listening for messages from the ChatServer.
     * ie: When other clients send messages, the server will relay it to the client.
     */
    private final class ListenFromServer implements Runnable {
        public void run() {
            try {
                while (true) {
                    try {


                        String msg = (String) sInput.readObject();

                        System.out.println(msg);
                        if (msg.equals("Server has closed the connection.")){
                            socket.close();
                            System.exit(0);
                        }
                        if(msg.contains(">> Sorry, a user with username: ")){
                            close();
                            scanner.close();

                            break;
                        }
                    }catch (StreamCorruptedException e){

                    }
                    catch (SocketException e){
                        System.out.println("Sorry ! Server is closed. Please connect to the server.");
                        break;

                    }
                    catch (EOFException e){
                      //  close();
                        //System.exit(0);
                    }
                }
            } catch (IOException | ClassNotFoundException e ) {
                e.printStackTrace();
            }catch (NullPointerException e){

            }
        }
    }
    private void close() throws IOException{
        socket.close();
        sOutput.close();
        sInput.close();

    }
}