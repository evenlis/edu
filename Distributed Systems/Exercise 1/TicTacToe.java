import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RMISecurityManager;

/**
 * A Tic Tac Toe application.
 * Currently this is a stand-alone application where
 * players take alternating turns using the same computer.
 * <p/>
 * The task is to transform it to a networking application using RMI.
 */
public class TicTacToe extends JFrame implements ListSelectionListener, TicTacToeInterface
{
    private static final int BOARD_SIZE = 15;
    private final BoardModel boardModel;
    private final JTable board;
    private final JLabel statusLabel = new JLabel();
    private final char playerMarks[] = {'X', 'O'};
    private int currentPlayer = 0; // Player to set the next mark.

    public static void main(String args[])
    {
	System.setSecurityManager(new LenientSecurityManager());
	String address = "127.0.0.1";
	String host = (args.length > 0 ? args[0] : address)+":3070";
	try{
	    System.out.println("1.1");
	    TicTacToe obj = new TicTacToe();
	    System.out.println("1.2");
	    TicTacToeInterface stub = (TicTacToeInterface)UnicastRemoteObject.exportObject(obj, 0);
	    System.out.println("1.3");
	    Registry registry = LocateRegistry.createRegistry(3070);
	    System.out.println("1.4");
	    registry.rebind("tictactoe", stub);
	    registry.rebind("pelle", stub);
	    System.out.println("1.5");
	    System.out.println("Server running");
	} catch(Exception e) {
	    try{
		System.out.println("2.1");
		Registry registry = LocateRegistry.getRegistry(host, 3070);
		System.out.println("2.2");
		TicTacToeInterface stub = (TicTacToeInterface)registry.lookup("tictactoe");
		System.out.println("2.3");
		System.out.println("Server setup failed, client connected");
		System.out.println("2.4");
	    } catch(Exception allHopeIsDead){
		allHopeIsDead.printStackTrace();
		System.err.println("You're screwed");
	    }
	}
    }

    public TicTacToe()
    {
	super("TDT4190: Tic Tac Toe");

	boardModel = new BoardModel(BOARD_SIZE);
	board = new JTable(boardModel);
	board.setFont(board.getFont().deriveFont(25.0f));
	board.setRowHeight(30);
	board.setCellSelectionEnabled(true);
	for (int i = 0; i < board.getColumnCount(); i++)
	    board.getColumnModel().getColumn(i).setPreferredWidth(30);
	board.setGridColor(Color.BLACK);
	board.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	DefaultTableCellRenderer dtcl = new DefaultTableCellRenderer();
	dtcl.setHorizontalAlignment(SwingConstants.CENTER);
	board.setDefaultRenderer(Object.class, dtcl);
	board.getSelectionModel().addListSelectionListener(this);
	board.getColumnModel().getSelectionModel().addListSelectionListener(this);

	statusLabel.setPreferredSize(new Dimension(statusLabel.getPreferredSize().width, 40));
	statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

	Container contentPane = getContentPane();
	contentPane.setLayout(new BorderLayout());
	contentPane.add(board, BorderLayout.CENTER);
	contentPane.add(statusLabel, BorderLayout.SOUTH);
	pack();

	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	int centerX = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - getSize().width) / 2;
	int centerY = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - getSize().height) / 2;
	setLocation(centerX, centerY);
	setVisible(true);
    }

    void setStatusMessage(String status)
    {
	statusLabel.setText(status);
    }

    /**
     * This has to be modified. Currently the application is stand-alone so
     * both players have to use the same computer.
     * <p/>
     * When completed, marks from the first player originates from a ListSelectionEvent
     * and is then sent to the second player. And marks from the second player is received
     * and added to the board of the first player.
     */
    public void valueChanged(ListSelectionEvent e)
    {
	if (e.getValueIsAdjusting())
	    return;
	int x = board.getSelectedColumn();
	int y = board.getSelectedRow();
	if (x == -1 || y == -1 || !boardModel.isEmpty(x, y))
	    return;
	if (boardModel.setCell(x, y, playerMarks[currentPlayer]))
	    setStatusMessage("Player " + playerMarks[currentPlayer] + " won!");
	currentPlayer = 1 - currentPlayer; // The next turn is by the other player.
    }
}
