public class DiceException extends BoardException
{
	public DiceException(String txt) {
		super("Dice error: " + txt);
	}
}
