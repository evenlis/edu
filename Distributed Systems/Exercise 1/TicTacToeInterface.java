import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.swing.event.ListSelectionEvent;

public interface TicTacToeInterface extends Remote {

    public void valueChanged(ListSelectionEvent e) throws RemoteException;

}
