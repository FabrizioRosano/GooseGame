public class BoardIO
{
	private Board board;

	public BoardIO()
	{
		board = new Board();
	}

	public void restart()
	{
		board.restart();
	}

	public void addPlayer(String info) throws BoardException
	{
		if (!info.isEmpty())
		{
			if (info.split("\\s").length == 1)
			{
				try
				{
					System.out.println(board.addPlayer(info));
				}
				catch (BoardException e)
				{
					throw e;
				}
			}
			else
			{
				throw new BoardException("\nCommand format is wrong");
			}
		}
		else
		{
			throw new BoardException("\nName missing");
		}
	}

	public void movePlayer(String info) throws BoardException
	{
		if (!info.isEmpty())
		{
			String[] tokens = info.split("\\s");
			int tokensLength = tokens.length;
			int dice1;
			int dice2;
			if (tokensLength == 1)
			{
				System.out.println(board.movePlayer(tokens[0]));
			}
			else if (tokensLength == 3)
			{
				try
				{
					dice1 = Integer.valueOf(tokens[1].replaceAll(",", ""));
					dice2 = Integer.valueOf(tokens[2]);
				}
				catch (NumberFormatException e)
				{
					throw new BoardException("\nCommand format is wrong: dice's value has to be an integer");
				}
				System.out.println(board.movePlayer(tokens[0], dice1, dice2));
			}
			else
			{
				throw new BoardException("\nCommand format is wrong");
			}
		}
		else
		{
			throw new BoardException("\nName missing");
		}
	}

	public void getPlayersPosition()
	{
		System.out.println(board.getPlayersPosition());
	}
}
