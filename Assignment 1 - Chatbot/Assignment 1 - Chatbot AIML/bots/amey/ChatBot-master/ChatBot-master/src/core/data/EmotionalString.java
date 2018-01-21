package core.data;

public class EmotionalString
{
	private String message;
	private Emotion emotion;
	
	public EmotionalString(String message, Emotion emotion)
	{
		this.message = message;
		this.emotion = emotion;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public Emotion getEmotion()
	{
		return emotion;
	}
}