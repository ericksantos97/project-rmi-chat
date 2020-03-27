package util;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ChatAula extends UnicastRemoteObject implements IChatAula {

    private static final long serialVersionUID = 8463491956408686274L;

    public ChatAula() throws RemoteException {
        super();
    }

    @Override
    public void sendMessage(Message msg) throws RemoteException {
        Message.setListMessage(msg);
    }

    @Override
    public List<Message> retrieveMessage() throws RemoteException {
        return Message.getListMessage();
    }
}
