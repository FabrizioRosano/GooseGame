public class PlayerException extends BoardException
{
	public PlayerException(String txt) {
		super("Player error: " + txt);
	}
}
