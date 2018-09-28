import java.io.Serializable;

final class ChatMessage implements Serializable {
    private static final long serialVersionUID = 6898543889087L;

    // Types of messages
    static final int MESSAGE = 0, LOGOUT = 1, DM = 2, LIST = 3, TICTACTOE = 4;

    // Here is where you should implement the chat message object.
    // Variables, Constructors, Methods, etc.
    private int type;
    private String message;
    private String recipient;
    public ChatMessage(int type, String message,String recipient)
    {
        this.type=type;
        this.message=message;
        this.recipient=recipient;
    }
    public int getType()
    {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getRecipient() {
        return recipient;
    }
}