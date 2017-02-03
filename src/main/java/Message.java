/**
 * Created by gesap on 2017-02-03.
 */
public class Message {
    String text;

    private Message(String text) {
        this.text = text;
    }

    public String getText(){
        return text;
    }

    public static Message newMessage(String text){
        return new Message(text);

    }
}
