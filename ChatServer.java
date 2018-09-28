import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

final class ChatServer {
    private List<ObjectOutputStream> NAMES=new ArrayList<>();
    private static int uniqueId = 0;
    // Data structure to hold all of the connected clients
    private final List<ClientThread> clients = new ArrayList<>();
    private final int port;			// port the server is hosted on
    public static List<String> clNames = new ArrayList<>();
    public static List<String> userNames = new ArrayList<>();
    private  int n=1;
    public String tUser;
    char[] board=new char[9];
    int place=0;
    TicTacToeGame NewGame=new TicTacToeGame(tUser);
    int altCnt=0;
    String altUser;
    int k1=0;


    /**
     * ChatServer constructor
     * @param port - the port the server is being hosted on
     */
    private ChatServer(int port) {
        this.port = port;
    }
    Date currentTime= new Date();
    SimpleDateFormat simpleDateFormat= new SimpleDateFormat("HH:mm:ss");

    /*
     * This is what starts the ChatServer.
     * Right now it just creates the socketServer and adds a new ClientThread to a list to be handled
     */
    private void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
           while (true) {
               System.out.println(simpleDateFormat.format(currentTime)+" Server waiting for Clients on port "+ port+".");
                Socket socket = serverSocket.accept();
               //System.out.println("Welcome"+ socket.getInetAddress()+"Bro"+socket.getLocalPort());


                Runnable r = new ClientThread(socket, uniqueId++);

                Thread t = new Thread(r);
                clients.add((ClientThread) r);
                t.start();
          }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    /**
//     *	Sample code to use as a reference for Tic Tac Toe
//     *
//     * directMessage - sends a message to a specific username, if connected
//     * @param message - the string to be sent
//     * @param username - the user the message will be sent to
//     */
//    private synchronized void directMessage(String message, String username) {
//        String time = sdf.format(new Date());
//        String formattedMessage = time + " " + message + "\n";
//        System.out.print(formattedMessage);
//
//        for (ClientThread clientThread : clients) {
//            if (clientThread.username.equalsIgnoreCase(username)) {
//                writeMessage(simpleDateFormat.format(currentTime) + " " +this.username+"->"+username+ ": "+message);
//                System.out.println(simpleDateFormat.format(currentTime) + " " + this.username+"->"+this.username + ": " +message);
//                clients.get(i).writeMessage(simpleDateFormat.format(currentTime) + " " +this.username+"->"+ username + ": " +message);
//            }
//        }
//    }


    /*
     *  > java ChatServer
     *  > java ChatServer portNumber
     *  If the port number is not specified 1500 is used
     */
    public static void main(String[] args) {
        ChatServer server = new ChatServer(1500);
        if(args.length==1){
             server = new ChatServer(Integer.parseInt(args[0]));

        }
        server.start();

    }


    /*
     * This is a private class inside of the ChatServer
     * A new thread will be created to run this every time a new client connects.
     */
    private final class ClientThread implements Runnable {
        Socket socket;                  // The socket the client is connected to
        ObjectInputStream sInput;       // Input stream to the server from the client
        ObjectOutputStream sOutput;     // Output stream to the client from the server
        String username;                // Username of the connected client
        ChatMessage cm;                 // Helper variable to manage messages
        int id;

        /*
         * socket - the socket the client is connected to
         * id - id of the connection
         */
        private ClientThread(Socket socket, int id) {
            this.id = id;
            this.socket = socket;
            try {
                //for(int i=0;i<clients.size();i++)
               // {
                    //if(!clients.get(i).username.equals(username))
                    //{
              //  if()
                //for(int i=0;i<clients.size();i++)
                //{
                    sOutput = new ObjectOutputStream(socket.getOutputStream());
                    sInput = new ObjectInputStream(socket.getInputStream());
                //}




                username = (String) sInput.readObject();
                System.out.println(simpleDateFormat.format(currentTime)+" "+username+" just connected.");
//                if(!userNames.contains(username))
//                {
                   userNames.add(username);
//                    n=1;
                   NAMES.add(sOutput);
                   //clients.clear();
 //              }
               for(int i=0;i<clients.size();i++)
                {
                    if(!clients.get(i).username.equals(username))
                    {

//                        //  NAMES.get(i).writeObject(this.username+"->"+username+ ": "+message);
//                        writeMessage(simpleDateFormat.format(currentTime) + " " +this.username+"->"+username+ ": "+message);
//                        System.out.println(simpleDateFormat.format(currentTime) + " " + this.username+"->"+username + ": " +message);
//                        clients.get(i).writeMessage(simpleDateFormat.format(currentTime) + " " +this.username+"->"+ username + ": " +message);
                    }
                    else {
                        userNames.remove(username);
                        broadcast(">> Sorry, a user with username: "+username+" already exists.\n>Server has closed the connection.");
                        close();

                        clients.remove(new ClientThread(this.socket,this.id));
                      // clients.get(i).remove(id);
                     //  clients.get(i).close();
                      //  //userNames.remove(username);
                        break;
                        // System.out.println("Connection Closed");

                        // remove(id);

                        // n=2;



                    }

                }



                //NAMES.add(sOutput);
            } catch (EOFException e){

            }
            catch (SocketException e){
               // System.out.println("fjjf");

            }catch (StreamCorruptedException e){

            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }






        private synchronized void directMessage(String message, String username) throws IOException{
            // String time = sdf.format(new Date());
            // String formattedMessage = time + " " + message + "\n";
            // System.out.print(formattedMessage);
            //TicTacToeGame NewGame=new TicTacToeGame(username);
            if(username.equals(this.username)){
                writeMessage("You cannot play with yourself.");
            }else {

                if (message.equals("")) {
                    writeMessage(simpleDateFormat.format(currentTime) + " Started TicTacToe with " + username + ".");

                    for (int i = 0; i < clients.size(); i++) {
                        if (clients.get(i).username.equals(username)) {
                            System.out.println(simpleDateFormat.format(currentTime) + " TicTacToe started between " + this.username + " and " + username + ".");
                            clients.get(i).writeMessage(simpleDateFormat.format(currentTime) + " Started TicTacToe with " + this.username + ".");
                        }
                    }
                } else {

                    if(!this.username.equals(NewGame.getLastPlayer())){
                        place = Integer.parseInt(message);
                        NewGame.updateTable(place);
                        NewGame.setLastPlayer(this.username);

                        writeMessage(NewGame.displayTable());}

                    else{

                        writeMessage("It's not your turn yet");
                    }

                    // writeMessage(board[0] + " | " + board[1] + " | " + board[2] + "\n" + board[3] + " | " + board[4] + " | " + board[5] + "\n" +
                    //       board[6] + " | " + board[7] + " | " + board[8]+"\n");
                    for (int j = 0; j < clients.size(); j++) {
                        if (clients.get(j).username.equals(username)) {
                            clients.get(j).writeMessage(NewGame.displayTable());
                            if (NewGame.WINNER == 'X') {
                                clients.get(j).writeMessage("X is the Winner");
                                writeMessage("X is the Winner");

                            } else if (NewGame.WINNER == 'O') {
                                clients.get(j).writeMessage("O is the Winner");
                                writeMessage("O is the Winner");

                            } else if (NewGame.WINNER == ' ') {
                                clients.get(j).writeMessage("It is a Tie.Sorry! try again.");
                                writeMessage("It is a Tie.Sorry! try again.");

                            }
                            // System.out.println(simpleDateFormat.format(currentTime) + " TicTacToe started between" + this.username + " and " + username + ".");
                            //   clients.get(i).writeMessage(board[0] + " | " + board[1] + " | " + board[2] + "\n" + board[3] + " | " + board[4] + " | " + board[5] + "\n" +
                            //         board[6] + " | " + board[7] + " | " + board[8]+"\n");
                        }
                    }

//                for (int i = 0; i < clients.size(); i++) {
//                    if (clients.get(i).username.equals(username)) {
//                        if(NewGame.returnCount()%2==0)
//                        {
//                            clients.get(i).board[place]='X';
//                        }
//                        else
//                        {
//                            clients.get(i).board[place]='O';
//                        }
//                        // System.out.println(simpleDateFormat.format(currentTime) + " TicTacToe started between" + this.username + " and " + username + ".");
//                     //   clients.get(i).writeMessage(board[0] + " | " + board[1] + " | " + board[2] + "\n" + board[3] + " | " + board[4] + " | " + board[5] + "\n" +
//                       //         board[6] + " | " + board[7] + " | " + board[8]+"\n");
//                    }
//                }
                }
            }
        }
        private void DirectMessage(String username, String message) throws IOException
        {
            for(int i=0;i<clients.size();i++)
            {
                if(clients.get(i).username.equals(username))
                {

                  //  NAMES.get(i).writeObject(this.username+"->"+username+ ": "+message);
                    writeMessage(simpleDateFormat.format(currentTime) + " " +this.username+"->"+username+ ": "+message);
                    System.out.println(simpleDateFormat.format(currentTime) + " " + this.username+"->"+username + ": " +message);
                    clients.get(i).writeMessage(simpleDateFormat.format(currentTime) + " " +this.username+"->"+ username + ": " +message);
                }

            }
        }

        /*
         * This is what the client thread actually runs.
         */
        @Override
        public void run() {
            // Read the username sent to you by client
//            try {
//                cm = (ChatMessage) sInput.readObject();
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
            // Send message back to the client
//            try {
         //   if(n==1){
                while (true) {
                    try {
                        cm = (ChatMessage) sInput.readObject();


                    } catch (EOFException e){

                    }catch (SocketException e){//EXTRA SOCKET EXCEPTION CATCH

                    }catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();

                    }
                    try {
                        if(cm!=null) {
                            if (cm.getType() == 3) {
                                if (cm.getMessage().equals("/list")) {
                                    ArrayList<String> arr=new ArrayList<>();


                                    for (int i = 0; i < userNames.size(); i++) {
//                                        clNames.add(clients.get(i).username);
                                       // for (int j = 0; j < clNames.size(); j++){
//                                            if((clNames.get(j)==clients.get(i).username)) {

                                                    for(int j=i+1;j<clients.size();j++){
                                                        if (clients.get(j)==clients.get(i)){
                                                            arr.add(clients.get(j).username);

                                                        }
                                                    }

                                        if (!(userNames.get(i).equals(this.username))) {
//                                                    for (int k=0;k<arr.size();k++) {
//                                                        if (clients.get(i).username != arr.get(k)){
//
//                                                            sOutput.writeObject(clients.get(i).username);
//                                                    }
//                                                    }
                                            try {
                                                sOutput.writeObject(userNames.get(i));
                                            }
                                            catch (SocketException e){

                                            }
                                                }
//                                            }else {
//
//                                            }
                                  //  }
                                    }
                                }
                            } else if (cm.getType() == 2) {

                                DirectMessage(cm.getRecipient(), cm.getMessage());
                                //sOutput.writeObject(cm.getMessage());
                            } else if (cm.getType() == 0) {
                                broadcast(cm.getMessage());

                            } else if (cm.getType() == 1) {
                                broadcast("Server has closed the connection.");
                                userNames.remove(this.username);
                                clients.remove(new ClientThread(this.socket,this.id));
                                remove(id);
                                close();
                                 break;
                            } else if (cm.getType()==4) {
                                int n=cm.getMessage().indexOf(" ");
                                int m=cm.getMessage().indexOf(" ",n+1);

                                if(m==-1) {
                                   // System.out.println("=1");
                                  directMessage("",cm.getMessage().substring(n+1));

                                }
                                else {
                                   // System.out.println(cm.getMessage().substring(n+1,m));
                                    directMessage(cm.getMessage().substring(m+1),cm.getMessage().substring(n+1,m));
                                    tUser=cm.getMessage().substring(n+1,m);



                                }




                            }else {
                                //System.out.println("Please as");
                                sOutput.writeObject("Please write the correct type");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
          //  }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        private synchronized void broadcast(String message) throws IOException
        {
            if(cm!=null) {
                if (cm.getType() != 1) {

                    for (int i = 0; i < clients.size(); i++) {
                        clients.get(i).writeMessage(simpleDateFormat.format(currentTime) + " " + username + ": " + message);

                    }

                    // System.out.println("broadcast ke andar");
                    System.out.println(simpleDateFormat.format(currentTime) + " " + username + ": " + message);
                } else {
                    for (int i = 0; i < clients.size(); i++) {
                        if (clients.get(i).username.equals(this.username)) {
                            userNames.remove(clients.get(i));
                            clients.get(i).writeMessage(simpleDateFormat.format(currentTime) + " " + username + ": " + message);
                            clients.get(i).writeMessage( message);
                        }

                    }

                    // System.out.println("broadcast ke andar");
                    System.out.println(simpleDateFormat.format(currentTime) + " " + username + ": " + "has disconnected with a LOGOUT message");

                }
            }
            else {
                writeMessage(message);
                try {
                    clients.remove(id);
                }
                catch (IndexOutOfBoundsException e){

                }
            }
        }
        private boolean writeMessage(String msg) throws IOException
        {
            if(socket.isConnected()) {
                if (msg.length() > 0) {
                   // System.out.println("Writemessage");
                    try {
                        sOutput.writeObject(msg);
                    }catch (SocketException e) {
                        try {


                           // sOutput.writeObject(msg);

                        }catch(Exception k){

                        }
                    }

                    return true;
                }
                return false;
            }
            return false;
        }
        private synchronized void remove(int id)
        {
            try {
               // clients.remove(username);

                clients.remove(id);
            }
            catch (IndexOutOfBoundsException e){

            }
        }
        private void close() throws IOException
        {
           // clients.remove(new ClientThread(this.socket,this.id));
            socket.close();
            sOutput.close();
            sInput.close();
        }
    }


}