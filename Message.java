import java.io.Serializable;

public class Message implements Serializable {
    private String message;
    private int to;

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public Message(String message,int to) {
        this.to = to;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
