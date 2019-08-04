import java.util.*;

public class Main
{
	public static void main(String[] args)
	{
		try(Scanner input = new Scanner(System.in);)
		{
			BoardIO board = new BoardIO();
			String[] commandParts;
			String commandName;
			String commandArgument;
			System.out.println("\nWELLCOME TO GOOSEGAME");
			do
			{
				commandParts = printMenu(input);
				commandName = commandParts[0];
				commandArgument = commandParts[1];
				switch (commandName)
				{

					case "createBoard":
						if (commandArgument.isBlank())
						{
							board.createBoard(input);
						}
						else
						{
							commandError(commandName, "\nCommand doesn't need arguments");
						}
					break;

					case "showBoard":
						if (commandArgument.isBlank())
						{
							board.showBoard();
						}
						else
						{
							commandError(commandName, "\nCommand doesn't need arguments");
						}
					break;

					case "add player":
						try
						{
							if (!commandArgument.isBlank())
							{
								board.addPlayer(commandArgument);
							}
							else
							{
								commandError(commandName, "Command needs argument");
							}
						}
						catch (BoardIOException e)
						{
							commandError(commandName, e.getMessage());
						}
					break;

					case "move":
						try
						{
							if (!commandArgument.isBlank())
							{
								board.movePlayer(commandArgument);
							}
							else
							{
								commandError(commandName, "Command needs arguments");
							}
						}
						catch (BoardIOException e)
						{
							commandError(commandName, e.getMessage());
						}
					break;

					case "showPositions":
						if (commandArgument.isBlank())
						{
							board.getPlayersPosition();
						}
						else
						{
							commandError(commandName, "Command doesn't need arguments");
						}
					break;

					case "restart":
						if (commandArgument.isBlank())
						{
							board.restart();
						}
						else
						{
							commandError(commandName, "\nCommand doesn't need arguments");
						}
					break;
					
					case "exit":
						if (commandArgument.isBlank())
						{
							return;
						}
						else
						{
							commandError(commandName, "Command doesn't need arguments");
						}
					break;
					
					default:
						commandError(commandName, "Unknown command");
				}
			} while (true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String[] printMenu (Scanner input)
	{
		String[] str = new String[2];
		System.out.print("\n\nCommand menu" +
							"\nTo create a new board (a default one already exists) write: createBoard" +
							"\nTo show the board write: showBoard" +
							"\nTo add a player write: add player playerName" +
							"\nTo move with random dices write: move playerName" +
							"\nTo move write: move playerName dice1Value, dice2Value" +
							"\nTo show players's position write: showPositions" +
							"\nTo restart write: restart" +
							"\nTo exit write: exit" +
							"\n\n>> ");

		str[0] = input.next();
		str[1] = input.nextLine().strip();
		if (str[0].equals("add"))
		{
			if (str[1].startsWith("player"))
			{
				str[1] = str[1].substring("player".length()).stripLeading();
				str[0] += " player";
			}
		}
		return str;
	}

	private static void commandError(String commandName, String errorMessage)
	{
		System.out.println("\nERROR:" + "\nCommand " + "\"" + commandName + "\"" + ": " + errorMessage + "\nPlease insert command again");
	}
}
