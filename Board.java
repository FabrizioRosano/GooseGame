import java.util.*;

public class Board
{

	private final int SQUARES_NUM = 64;
	private final int[] GOOSE = {5, 9, 14, 18, 23, 27};
	private final int[] BRIDGE = {6, 12};

	private int squaresNum;
	private int[] goose;
	private int[] bridge;
	private String[] board;
	private Map<String, Player> players;

	public Board()
	{
		buildBoard(SQUARES_NUM, GOOSE, BRIDGE);
		players = new HashMap<>();
	}

	public Board(int squaresNum, int[] goose, int[] bridge)
	{
		buildBoard(squaresNum, goose, bridge);
		players = new HashMap<>();
	}

	private void buildBoard(int squaresNum, int[] goose, int[] bridge)
	{
		this.squaresNum = squaresNum;
		this.goose = goose;
		this.bridge = bridge;
		board = new String[squaresNum];
		board[0] = "Start";
		board[bridge[0]] = "bridge";
		board[squaresNum - 1] = "End";
		for (int i=0; i < goose.length; i++)
		{
			board[goose[i]] = "goose";
		}	
	}

	public void createBoard(int squaresNum, int[] goose, int[] bridge)
	{
		buildBoard(squaresNum, goose, bridge);
		this.restart();
	}

	public void restart()
	{
		Iterator<Player> playersIt = players.values().iterator();
		while (playersIt.hasNext())
		{
			playersIt.next().setPosition(0);
		}
	}
	
	private String getPlayers()
	{
		String playersList = "";
		Iterator<String> playersIt = players.keySet().iterator();
		while (playersIt.hasNext())
		{
			playersList += (" " + playersIt.next() + ",");
		}
		return playersList.substring(0, playersList.length() - 1);
	}

	public String addPlayer(String playerName) throws PlayerException
	{
		if (!players.containsKey(playerName))
		{
			players.put(playerName, new Player(playerName));
		}
		else
		{
			throw new PlayerException("Player " + playerName + ": already existing player");
		}
		return ("\nPlayers:" + getPlayers() + ".");
	}

	public String movePlayer(String playerName, int dice1, int dice2) throws PlayerException, DiceException
	{
		if (((dice1 >= 1) && (dice1 <= 6)) && ((dice2 >= 0) && (dice2 <= 6)))
		{
			return ("\n" + playerName + " rolls " + dice1 + ", " + dice2 + ". " + movePlayer(playerName, dice1 + dice2));
		}
		else
		{
			throw new DiceException("Dice's value has to be between 1-6");
		}
	}

	public String movePlayer(String playerName) throws PlayerException
	{
		Random r = new Random();
		int dice1 = r.nextInt(6) + 1;
		int dice2 = r.nextInt(6) + 1;
		return ("\n" + playerName + " rolls " + dice1 + ", " + dice2 + ". " + movePlayer(playerName, dice1 + dice2));
	}

	private String movePlayer(String playerName, int steps) throws PlayerException
	{
		Player movingPlayer = players.get(playerName);
		if (movingPlayer == null)
		{
			throw new PlayerException("Player " + playerName + " doesn't exist");
		}
		int oldPosition = movingPlayer.getPosition();
		int newPosition = oldPosition + steps;
		String square;
		String move = playerName + " moves from " + (oldPosition == 0 ? "Start" : oldPosition) + " to ";
		if (newPosition >= squaresNum)
		{
			newPosition = (squaresNum - 1) * 2 - newPosition;
			move += "End. " + playerName + " bounces! " + playerName + " returns to " + newPosition;
		}
		else
		{
			move += newPosition;
		}
		boolean victory = false;
		String squareMsg = "";
		while (((square = board[newPosition]) != null) && (!victory))
		{
			switch (square)
			{
				case "goose":
					newPosition += steps;
					squareMsg = " moves again and goes to " + newPosition;
				break;

				case "bridge":
					newPosition = bridge[1];
					squareMsg = " jumps to " + newPosition;
				break;

				case "End":
					squareMsg = " wins!";
					victory = true;
				break;
			}
			move += ", The " + square + ". " + playerName + squareMsg;
		}
		if (!victory)
		{
			Iterator<Player> playersIt = players.values().iterator();
			Player nextPlayer;
			while (playersIt.hasNext())
			{
				nextPlayer = playersIt.next();
				if (nextPlayer.getPosition() == newPosition)
				{
					nextPlayer.setPosition(oldPosition);
					move += ". On " + newPosition + " there is " + nextPlayer.getName() + ", who returns to " + (oldPosition == 0 ? "Start" : oldPosition);
					break;
				}
			}
			move += ".";
		}
		movingPlayer.setPosition(newPosition);
		return move;
	}

	public String getPlayersPosition()
	{
		String playersPosition = "";
		Player player;
		int position;
		if (players.isEmpty())
		{
			return null;
		}
		Iterator<Player> playersIt = players.values().iterator();
		while (playersIt.hasNext())
		{
			player = playersIt.next();
			position = player.getPosition();
			playersPosition += "\n" + player.getName() + " is on square " + (position == 0 ? "Start" : position) + ".";
		}
		return playersPosition;
	}

	public String showBoard()
	{
		String boardStr = "\n";
		for (int i=0; i < board.length; i++)
		{
			boardStr += ((board[i] == null ? i : board[i]) + " ");
		}
		return boardStr.stripTrailing();
	}
}
