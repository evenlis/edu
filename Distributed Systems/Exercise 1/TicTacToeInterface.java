import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.swing.event.ListSelectionEvent;

public interface TicTacToeInterface extends Remote {

    public void setMark(int x, int y)throws RemoteException;
    public void setOpponent(TicTacToeInterface server)throws RemoteException;
    public void setOpponentMark(char mark)throws RemoteException;
    public void newGame()throws RemoteException;
    public void leaveGame()throws RemoteException;
    public void setMyTurn(boolean myTurn)throws RemoteException;
    public boolean isMyTurn()throws RemoteException;

}
