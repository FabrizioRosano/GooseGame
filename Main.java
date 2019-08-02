import java.util.*;

public class Main
{
	public static void main(String[] args)
	{
		try(Scanner input = new Scanner(System.in);)
		{
			BoardIO board = new BoardIO();
			String[] tokens;
			System.out.println("\nWELLCOME TO GOOSEGAME");
			do
			{
				tokens = printMenu(input);
				switch (tokens[0])
				{
					case "add player":
						try
						{
							board.addPlayer(tokens[1]);
						}
						catch (BoardException e)
						{
							System.out.println(e.getMessage() + "\nPlease insert command again");
						}
					break;

					case "move":
						try
						{
							board.movePlayer(tokens[1]);
						}
						catch (BoardException e)
						{
							System.out.println(e.getMessage() + "\nPlease insert command again");
						}
					break;

					case "show":
						board.getPlayersPosition();
					break;

					case "restart":
						board.restart();
					break;
					
					case "exit":
						return;
					
					default:
						System.out.println("\nUnknown command\nPlease insert command again");
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
							"\nTo add a player write: add player playerName" +
							"\nTo move with random dices write: move playerName" +
							"\nTo move write: move playerName dice1, dice2" +
							"\nTo know players's position write: show" +
							"\nTo restart write: restart" +
							"\nTo exit write: exit" +
							"\n>> ");

		str[0] = input.next();
		str[1] = input.nextLine().strip();
		if (str[0].equals("add"))
		{
			if (str[1].contains("player"))
			{
				str[1] = str[1].replaceAll("player", "").stripLeading();
				str[0] += " player";
			}
		}
		return str;
	}
}
