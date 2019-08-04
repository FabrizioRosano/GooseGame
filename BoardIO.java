import java.util.*;

public class BoardIO
{
	private Board board;

	public BoardIO()
	{
		board = new Board();
	}

	public void createBoard(Scanner input)
	{
		int squaresNum = askEndSquare(input);
		int[] bridge = askBridge(input, squaresNum);
		int[] goose = askGoose(input, squaresNum, bridge);
		board.createBoard(squaresNum, goose, bridge);
		showBoard();
	}

	private int askEndSquare(Scanner input)
	{
		int squaresNum;
		do
		{
			System.out.print("\nInsert the number of the last square" +
								"\nNumber has to be bigger than 20: ");
			try
			{
				squaresNum = Integer.valueOf(input.nextLine().strip()) + 1;
				if (squaresNum > 20)
				{
					break;
				}
				else
				{
					System.out.println("\nError: Number has to be bigger than 20");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println("\nError: Insert a integer number");
			}
		} while (true);
		return squaresNum;
	}

	private int[] askBridge(Scanner input, int squaresNum)
	{
		int[] bridge = new int[2];
		String[] bridgeStr;
		boolean success = false;
		do
		{
			System.out.print("\nInsert the bridge's positions (start and end separated by space)" +
								"\nNumber has to be in range 2-"  + (squaresNum - 2) + ": ");
			try
			{
				bridgeStr = input.nextLine().strip().split("\\s");
				for (int i=0; i < 2; i++)
				{
					bridge[i] = Integer.valueOf(bridgeStr[i]);
					if ((bridge[i] >= 2) && (bridge[i] <= (squaresNum - 2)))
					{
						if ((i == 1) && (bridge[0] != bridge[1]))
						{
							success = true;
						}
					}
					else
					{
						System.out.println("\nError: Number has to be in range 2-" + (squaresNum - 2));
						break;
					}
				}
				if (success)
				{
					break;
				}
			}
			catch (Exception e)
			{
				System.out.println("\nError: Insert two integer numbers");
			}
		} while (true);
		return bridge;
	}

	private int[] askGoose(Scanner input, int squaresNum, int[] bridge)
	{
		int gooseSquaresNum = squaresNum / 10;
		int gooseSquaresMax = squaresNum - 13;
		String[] gooseStr;
		int[] goose = new int[gooseSquaresNum];
		boolean success = false;
		do
		{
			System.out.print("\nInsert " + gooseSquaresNum + " goose's positions (separated by space)" +
								"\nNumber has to be in range 2-" + gooseSquaresMax + " except " + bridge[0] + " " + bridge[1] + ": ");
			try
			{
				gooseStr = input.nextLine().strip().split("\\s");
				for (int i=0; i < gooseSquaresNum; i++)
				{
					goose[i] = Integer.valueOf(gooseStr[i]);
					if ((goose[i] >= 2) && (goose[i] <= gooseSquaresMax))
					{
						if (i == (gooseSquaresNum - 1))
						{
							success = true;
						}
					}
					else
					{
						System.out.println("\nNumber has to be in range 2-" + gooseSquaresMax + " except " + bridge[0] + " " + bridge[1] + ": ");
						break;
					}
				}
				if (success)
				{
					break;
				}
			}
			catch (Exception e)
			{
				System.out.println("\nError: Insert " + gooseSquaresNum + " integer numbers");
			}
		} while (true);
		return goose;
	}

	public void showBoard()
	{
		System.out.println("\nTHE BOARD" + board.showBoard());
	}

	public void restart()
	{
		board.restart();
	}

	public void addPlayer(String argument) throws BoardException, CommandFormatException
	{
		if (argument.split("\\s").length == 1)
		{
			System.out.println(board.addPlayer(argument));
		}
		else
		{
			throw new CommandFormatException("Number of argument is wrong");
		}
	}

	public void movePlayer(String argument) throws BoardException, CommandFormatException
	{
		String[] argumentParts = argument.split("\\s");
		int argumentPartsNum = argumentParts.length;
		int dice1;
		int dice2;
		switch (argumentPartsNum)
		{
			case 1:
				System.out.println(board.movePlayer(argumentParts[0]));
			break;

			case 3:
				try
				{
					dice1 = Integer.valueOf(argumentParts[1].replaceAll(",", ""));
					dice2 = Integer.valueOf(argumentParts[2]);
				}
				catch (NumberFormatException e)
				{
					throw new CommandFormatException("Value of dices has to be an integer number");
				}
				System.out.println(board.movePlayer(argumentParts[0], dice1, dice2));
			break;

			default:
				throw new CommandFormatException("Number of argument is wrong");
		}
	}

	public void getPlayersPosition()
	{
		String playersPosition = board.getPlayersPosition();
		if (playersPosition != null)
		{
			playersPosition = "\nPLAYERS POSITION" + playersPosition;
		}
		else
		{
			playersPosition = "\nThere are no players";
		}
		System.out.println(playersPosition);
	}
	
}
