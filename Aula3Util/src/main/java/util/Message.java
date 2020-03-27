package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {

    private static final long serialVersionUID = -870408540268214133L;

    private String user;
    private String message;

    private static List<Message> listMessage = new ArrayList<>();

    public Message(String user, String message) {
        this.user = user;
        this.message = message;
    }

    public String getUsuario() {
        return user;
    }

    public void setUsuario(String user) {
        this.user = user;
    }

    public String getMensagem() {
        return message;
    }

    public void setMensagem(String message) {
        this.message = message;
    }

    public static List<Message> getListMessage() {
        return listMessage;
    }

    public static void setListMessage(Message message) {
        listMessage.add(message);
    }
}
