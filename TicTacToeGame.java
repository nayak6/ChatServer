public class TicTacToeGame {

    private static final char PLAYERX = 'X';     // Helper constant for X player
    private static final char PLAYERO = 'O';     // Helper constant for O player
    private static final char SPACE = ' ';       // Helper constant for spaces
    private static int Count=10;

    private boolean hasWon;

    private boolean check;

    private String lastPlayer;

    private boolean TIE;

    public char WINNER;

    public void setLastPlayer(String lastPlayer)
    {
        this.lastPlayer=lastPlayer;
    }

    public String getLastPlayer() {
        return lastPlayer;
    }

    private char[] tableDisplay;

    /*
    Sample TicTacToe Board
      0 | 1 | 2
     -----------
      3 | 4 | 5
     -----------
      6 | 7 | 8
     */
    // TODO 4: Implement necessary methods to manage the games of Tic Tac Toe
    public TicTacToeGame(String otherPlayer)
    {

        tableDisplay=new char[9];
        for(int i=0;i<9;i++)
        {
            tableDisplay[i]=SPACE;
        }

    }
    public synchronized void updateTable(int place)
    {
        if(place>8||place<0)
        {
            return;
        }
        else
        {
            //returnBoo();
            if(tableDisplay[place]!='O'&&tableDisplay[place]!='X')
            {
                if(check)
                {
                   tableDisplay[place]='O';

                   check=false;
                }
                else
                {
                   tableDisplay[place]='X';

                   check=true;
                }
            }
            char player = checkWinner();
            if(player==PLAYERX){
                WINNER=PLAYERX;
                returnWinner();
            }
            else if(player==PLAYERO){
                WINNER=PLAYERO;
                returnWinner();
            }
            else if(player==SPACE&&Count==0){
                WINNER=SPACE;
            }
        }
    }
    public char returnWinner()
    {
        return WINNER;
    }
    public String displayTable()
    {
        return tableDisplay[0] + " | " + tableDisplay[1] + " | " + tableDisplay[2] + "\n" + tableDisplay[3] + " | " + tableDisplay[4] + " | " + tableDisplay[5] + "\n" +
                       tableDisplay[6] + " | " + tableDisplay[7] + " | " + tableDisplay[8]+"\n";
    }
    public char checkWinner()
    {
        for (int a = 0; a < 8; a++) {
            String line = null;
            switch (a) {
                case 0:
                    line =Character.toString(tableDisplay[0]) + tableDisplay[1] + tableDisplay[2];
                    break;
                case 1:
                    line = Character.toString(tableDisplay[3]) + tableDisplay[4] + tableDisplay[5];
                    break;
                case 2:
                    line = Character.toString(tableDisplay[6]) + tableDisplay[7] + tableDisplay[8];
                    break;
                case 3:
                    line =Character.toString (tableDisplay[0]) + tableDisplay[3] + tableDisplay[6];
                    break;
                case 4:
                    line = Character.toString (tableDisplay[1]) + tableDisplay[4] + tableDisplay[7];
                    break;
                case 5:
                    line = Character.toString (tableDisplay[2]) + tableDisplay[5] + tableDisplay[8];
                    break;
                case 6:
                    line = Character.toString (tableDisplay[0]) + tableDisplay[4] + tableDisplay[8];
                    break;
                case 7:
                    line =Character.toString (tableDisplay[2]) + tableDisplay[4] + tableDisplay[6];
                    break;
            }
            int count=0;
            for(int i=0;i<9;i++)
            {
                if(tableDisplay[i]==' ')
                {
                    count++;
                }
            }
            if(count==0)
            {
                TIE=true;
            }
            if (line.equals("XXX")) {
                return PLAYERX;
            } else if (line.equals("OOO")) {
                return PLAYERO;
            }
        }
        return SPACE;
    }
    public int takeTurn(int index)
    {
        return 0;
    }
    public char getWinner()
    {
        return ' ';
    }
    public int isTied()
    {
        return 0;
    }
    public char getSpace(int index)
    {
        return ' ';
    }
    public String toString()
    {
        return "";
    }
    public int returnCount()
    {
        return Count++;
    }
    public synchronized boolean returnBoo()
    {
        if(!check)
        {
            check=true;
            return true;
        }
        else
        {
            check = false;
            return false;
        }
    }
}





















































































//import java.util.InputMismatchException;
//public class TicTacToeGame {
//    private static final char PLAYERX = 'X';     // Helper constant for X player
//    private static final char PLAYERO = 'O';     // Helper constant for O player
//    private static final char SPACE = ' ';       // Helper constant for spaces
//    /*
//    Sample TicTacToe Board
//      0 | 1 | 2
//     -----------
//      3 | 4 | 5
//     -----------
//      6 | 7 | 8
//     */
//    TicTacToeGame(String otherPlayer , boolean isX){
//
//    }
//    public int takeTurn(){
//        return -1;
//    }
//    // TODO 4: Implement necessary methods to manage the games of Tic Tac Toe
//
//    static char[] board=new char[9];
//    static char turn=PLAYERX;
//
//    //  public static void main(String[] args) {
//
//    //  int n=Integer.parseInt(args[0]);
//
//    // board = new char[9];
//    // turn = PLAYERX;
//    static char winner = SPACE;
//    // populateEmptyBoard();
//    static int numInput;
//
////            System.out.println("Welcome to 2 Player Tic Tac Toe.");
//    // System.out.println("--------------------------------");
//    // printBoard();
//    // System.out.println("X's will play first. Enter a slot number to place X in:");
//
//    // while (winner == null) {
//
//    //  int numInput=Integer.parseInt(args[0]);
//    //   System.out.println();
//    public void trying() {
//        try {
////                    System.out.println("kk");
////                    numInput = in.nextInt();
////                    in.nextLine();
//            while (numInput >= 0 && numInput < 9) {
//                System.out.println("Invalid input; re-enter slot number:");
//                // continue;
//            }
//        } catch (InputMismatchException e) {
//            System.out.println("Invalid input; re-enter slot number:");
//            //continue;
//        }
//
//        if (board[numInput] == (char) (numInput + 48)) {
//            board[numInput] = turn;
//            if (turn == PLAYERX) {
//                turn = PLAYERO;
//            } else {
//                turn = PLAYERX;
//            }
//            //printBoard();
//            winner = getWinner();
//        } else {
//            System.out.println("Slot already taken; re-enter slot number:");
//            //continue;
//        }
//        if (winner == PLAYERX || winner == PLAYERO) {
//            System.out.println("Congratulations! " + winner + "'s have won! Thanks for playing.");
//        }
//    }
//    public static int isTied(){
//        return -1;
//    }
//    public static char getSpace(int index){
//        return SPACE;
//    }
//    @Override
//    public String toString() {
//        return super.toString();
//    }
//    static char getWinner() {
//        for (int a = 0; a < 8; a++) {
//            String line = null;
//            switch (a) {
//                case 0:
//                    line =Character.toString(board[0]) + board[1] + board[2];
//                    break;
//                case 1:
//                    line = Character.toString(board[3]) + board[4] + board[5];
//                    break;
//                case 2:
//                    line = Character.toString(board[6]) + board[7] + board[8];
//                    break;
//                case 3:
//                    line =Character.toString (board[0]) + board[3] + board[6];
//                    break;
//                case 4:
//                    line = Character.toString (board[1]) + board[4] + board[7];
//                    break;
//                case 5:
//                    line = Character.toString (board[2]) + board[5] + board[8];
//                    break;
//                case 6:
//                    line = Character.toString (board[0]) + board[4] + board[8];
//                    break;
//                case 7:
//                    line =Character.toString (board[2]) + board[4] + board[6];
//                    break;
//            }
//            if (line.equals("XXX")) {
//                return PLAYERX;
//            } else if (line.equals("OOO")) {
//                return PLAYERO;
//            }
//        }
//        System.out.println(turn + "'s turn; enter a slot number to place " + turn + " in:");
//        return SPACE;
//    }
////        static void printBoard() {
////            System.out.println(  board[0] + " | " + board[1] + " | " + board[2] );
////            System.out.println("----------");
////            System.out.println( board[3] + " | " + board[4] + " | " + board[5]);
////            System.out.println("----------");
////            System.out.println( board[3] + " | " + board[4] + " | " + board[5]);
////        }
////        static void populateEmptyBoard() {
////            for (int a = 0; a < 9; a++) {
////                board[a] = (char)(a+48);
////            }
////        }
//}














