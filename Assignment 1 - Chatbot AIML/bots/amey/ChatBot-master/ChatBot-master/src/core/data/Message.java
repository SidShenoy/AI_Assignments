package core.data;

import java.util.ArrayList;
import java.util.List;

public class Message
{
	private String message;
	private List<Emotion> emotions;
	
	public Message(String message, List<Emotion> emotions)
	{
		this.message = message;
		this.emotions = emotions;
	}
	
	public Message(String message, Emotion emotion)
	{
		this(message, (List<Emotion>) null);
		List<Emotion> emotions = new ArrayList<Emotion>();
		emotions.add(emotion);
		this.emotions = emotions;
	}
	
	public Message(EmotionalString message)
	{
		this(message.getMessage(), message.getEmotion());
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public List<Emotion> getEmotions()
	{
		return emotions;
	}
}