package core.processing;

import core.Run;
import core.data.Emotion;
import core.data.Message;

public class StringProcessor
{
	public String processString(String input)
	{
		if(input.equals("END:"))
		{
			Run.instance.sd.saveSpecialDatabase();
			Run.instance.end = true;
			return "ending";
		}
		else if(input.contains("ADD:"))
		{
			Run.instance.sd.add(input.substring(4));
			Run.instance.sd.saveSpecialDatabase();
			return "successfully added word to dictionary";
		}
		else if(input.equals("RELOAD:"))
		{
			Run.instance.sd.load();
			Run.instance.md.load();
			return "reload complete";
		}
		input = input.replace(",", "").replace(".", "").replace("!", "").replace("'", "").toLowerCase();
		Message message = Run.instance.sd.getFromSpecialDatabase(input);
		if(message.getEmotions().size() == 0)
		{
			message = Run.instance.md.getFromMatrixDatabase(input);
			if(message.getEmotions().size() == 0)
			{
				message = new Message(message.getMessage(), Emotion.UNKNOWN);
			}
		}
		Message response = (new Response(message)).getResponse();
		
		
		
		
		
		return response.getMessage();
	}
}