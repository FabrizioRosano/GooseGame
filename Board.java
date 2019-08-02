import java.util.*;

public class Board
{
	private final int SQUARES_NUM = 64;
	private final int[] GOOSE = {5, 9, 14, 18, 23, 27};
	private final int BRIDGE = 6;
	private final int BRIDGE_END = 12;
	
	private String[] board;
	private Map<String, Player> players;

	public Board()
	{
		board = new String[SQUARES_NUM];
		board[0] = "Start";
		board[BRIDGE] = "Bridge";
		board[SQUARES_NUM - 1] = "End";
		for (int i=0; i < GOOSE.length; i++)
		{
			board[GOOSE[i]] = "Goose";
		}
		players = new HashMap<>();
	}

	public void restart()
	{
		Iterator<Player> it = players.values().iterator();
		while (it.hasNext())
		{
			it.next().setPosition(0);
		}
	}
	
	private String getPlayers()
	{
		String str = "";
		Iterator<String> it = players.keySet().iterator();
		while (it.hasNext())
		{
			str += (" " + it.next() + ",");
		}
		return str.substring(0, str.length() - 1);
	}

	public String addPlayer(String name) throws BoardException
	{
		if (!players.containsKey(name))
		{
			players.put(name, new Player(name));
		}
		else
		{
			throw new BoardException("\nPlayer " + name + ": already existing player");
		}
		String str = "\nPlayers:" + getPlayers() + ".";
		return str;
	}

	public String movePlayer(String name, int dice1, int dice2) throws BoardException
	{
		if (((dice1 >= 1) && (dice1 <= 6)) && ((dice2 >= 0) && (dice2 <= 6)))
		{
			String str = "\n" + name + " rolls " + dice1 + ", " + dice2 + ". " + movePlayer(name, dice1 + dice2);
			return str;
		}
		else
		{
			throw new BoardException("\nDice's value has to be between 1-6");
		}
	}

	public String movePlayer(String name) throws BoardException
	{
		Random r = new Random();
		int dice1 = r.nextInt(6) + 1;
		int dice2 = r.nextInt(6) + 1;
		String str = "\n" + name + " rolls " + dice1 + ", " + dice2 + ". " + movePlayer(name, dice1 + dice2);
		return str;
	}

	private String movePlayer(String name, int steps) throws BoardException
	{
		Player movingPlayer = players.get(name);
		if (movingPlayer == null)
		{
			throw new BoardException("\nPlayer " + name + " doesn't exist");
		}
		int oldPosition = movingPlayer.getPosition();
		int newPosition = oldPosition + steps;
		String square;
		boolean finished = false;
		String str = name + " moves from " + (oldPosition == 0 ? "Start" : oldPosition) + " to ";
		if (newPosition >= SQUARES_NUM)
		{
			newPosition = (SQUARES_NUM - 1) * 2 - newPosition;
			str += "End. " + name + " bounces! " + name + " returns to " + newPosition;
		}
		else
		{
			str += newPosition;
		}
		
		while (!finished)
		{
			if ((square = board[newPosition]) != null)
			{
				switch (square)
				{
					case "Goose":
						newPosition += steps;
						str += ", The Goose. " + name + " moves again and goes to " + newPosition;
					break;

					case "Bridge":
						newPosition = BRIDGE_END;
						str += ", The Bridge. " + name + " jumps to " + newPosition;
					break;

					case "End":
						str += ", The End. " + name + " wins!";
						finished = true;
					break;
				}
			}
			else
			{
				Iterator<Player> it = players.values().iterator();
				Player nextPlayer;
				while (it.hasNext())
				{
					nextPlayer = it.next();
					if (nextPlayer.getPosition() == newPosition)
					{
						nextPlayer.setPosition(oldPosition);
						str += ". On " + newPosition + " there is " + nextPlayer.getName() + ", who returns to " + (oldPosition == 0 ? "Start" : oldPosition);
						break;
					}
				}
				finished = true;
			}
		}
		movingPlayer.setPosition(newPosition);
		str += ".";
		return str;
	}

	public String getPlayersPosition()
	{
		String str = "";
		Player player;
		int position;
		Iterator<Player> it = players.values().iterator();
		while (it.hasNext())
		{
			player = it.next();
			position = player.getPosition();
			str += "\n" + player.getName() + " is on square " + (position == 0 ? "Start" : position) + ".";
		}
		return str;
	}
}
