public class CommandFormatException extends BoardIOException
{
	public CommandFormatException(String txt) {
		super("Format error: " + txt);
	}
}
