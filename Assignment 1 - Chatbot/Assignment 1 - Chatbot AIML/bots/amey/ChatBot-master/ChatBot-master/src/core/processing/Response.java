package core.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.data.Emotion;
import core.data.EmotionalString;
import core.data.Message;

public class Response
{
	private Message message;
	
	public Response(Message message)
	{
		this.message = message;
	}
	
	public Message getResponse()
	{
		Random rand = new Random();
		List<EmotionalString> response = getResponses(message.getEmotions(), rand, message.getMessage());
		List<String> responseStrings = new ArrayList<String>();
		List<Emotion> responseEmotions = new ArrayList<Emotion>();
		for(int i = 0; i < response.size(); i++)
		{
			responseStrings.add(response.get(i).getMessage());
			responseEmotions.add(response.get(i).getEmotion());
		}
		Message finalResponse = getOrganizedResponse(responseStrings, responseEmotions, rand);
		
		
		
		return finalResponse;
	}
	
	private List<EmotionalString> getResponses(List<Emotion> emotions, Random rand, String message)
	{
		List<EmotionalString> responses = new ArrayList<EmotionalString>();
		for(Emotion emotion: emotions)
		{
			EmotionalString response = getResponse(emotion, rand, message);
			responses.add(response);
		}
		return responses;
	}
	
	private EmotionalString getResponse(Emotion emotion, Random rand, String message)
	{
		String responseMessage = "";
		Emotion responseEmotion = Emotion.NONE;
		switch(emotion)
		{
		case FAREWELL:
			switch(rand.nextInt(6))
			{
			case 0:
				responseMessage = "Bye.";
				responseEmotion = Emotion.FAREWELL;
				break;
			case 1:
				responseMessage = "Goodbye.";
				responseEmotion = Emotion.FAREWELL;
				break;
			case 2:
				responseMessage = "Farewell.";
				responseEmotion = Emotion.FAREWELL;
				break;
			case 3:
				responseMessage = "See you later.";
				responseEmotion = Emotion.FAREWELL;
				break;
			case 4:
				responseMessage = "Why do you have to go?";
				responseEmotion = Emotion.QUESTION;
				break;
			case 5:
				responseMessage = "I'll miss you!";
				responseEmotion = Emotion.FAREWELL;
				break;
			}
			break;
		case GREETING:
			switch(rand.nextInt(5))
			{
			case 0:
				responseMessage = "Hi.";
				responseEmotion = Emotion.GREETING;
				break;
			case 1:
				responseMessage = "Hello.";
				responseEmotion = Emotion.GREETING;
				break;
			case 2:
				responseMessage = "Hey!";
				responseEmotion = Emotion.GREETING;
				break;
			case 3:
				responseMessage = "Hi, how's it going?";
				responseEmotion = Emotion.QUESTION;
				break;
			case 4:
				responseMessage = "Hey, how's it going?";
				responseEmotion = Emotion.QUESTION;
				break;
			}
			break;
		case NONE:
			switch(rand.nextInt(2))
			{
			case 0:
				responseMessage = "ok.";
				responseEmotion = Emotion.NONE;
				break;
			case 1:
				responseMessage = "cool.";
				responseEmotion = Emotion.NONE;
				break;
			}
			break;
		case UNKNOWN:
			responseMessage = "That doesn't make sense.";
			responseEmotion = Emotion.CONFUSED;
			break;
		default:
			EmotionalString protoResponse = getAdditionalResponseData(emotion, rand, message);
			responseMessage = protoResponse.getMessage();
			responseEmotion = protoResponse.getEmotion();
			break;
		}
		return new EmotionalString(responseMessage, responseEmotion);
	}
	
	private EmotionalString getAdditionalResponseData(Emotion emotion, Random rand, String message)
	{
		String responseString = message;
		Emotion responseEmotion = emotion;
		if((message.contains("how") && (message.contains("you") || message.contains("going"))) || message.contains("hbu"))
		{
			responseString = "I'm doing ";
			switch(rand.nextInt(5))
			{
			case 0:
				responseString += "great.";
				responseEmotion = Emotion.HAPPY;
				break;
			case 1:
				responseString += "good.";
				responseEmotion = Emotion.HAPPY;
				break;
			case 2:
				responseString += "ok.";
				responseEmotion = Emotion.NONE;
				break;
			case 3:
				responseString = "I'm not doing too well.";
				responseEmotion = Emotion.SAD;
				break;
			case 4:
				responseString = "Horrible.";
				responseEmotion = Emotion.SAD;
				break;
			}
		}
		else if(emotion.equals(Emotion.THANKFUL))
		{
			switch(rand.nextInt(3))
			{
			case 0:
				responseString = "No problem.";
				responseEmotion = Emotion.HAPPY;
				break;
			case 1:
				responseString = "You're welcome.";
				responseEmotion = Emotion.HAPPY;
				break;
			case 2:
				responseString = "Any time.";
				responseEmotion = Emotion.HAPPY;
				break;
			}
		}
		else if(emotion.equals(Emotion.HAPPY))
		{
			responseString = RandomThings.askRandomQuestion(rand);
			responseEmotion = Emotion.QUESTION;
		}
		else if(emotion.equals(Emotion.SAD))
		{
			responseString = "yep...";
			responseEmotion = Emotion.NONE;
		}
		
		
		
		
		return new EmotionalString(responseString, responseEmotion);
	}
	
	private Message getOrganizedResponse(List<String> responses, List<Emotion> emotions, Random rand)
	{
		Message message = new Message("not done yet", Emotion.NONE);
		for(int i = 0; i < emotions.size(); i++)
		{
			if(emotions.get(i).equals(Emotion.GREETING))
			{
				message = new Message(responses.get(i), Emotion.GREETING);
				for(int j = 0; j < emotions.size(); j++)
				{
					if(emotions.get(j).equals(Emotion.HAPPY) || emotions.get(j).equals(Emotion.NONE))
					{
						String response = responses.get(i).replace(".", ",").replace("!", ",").replace("?", ",");
						char[] responseCharArray = responses.get(j).toCharArray();
						responseCharArray[0] = Character.toLowerCase(responseCharArray[0]);
						String response2 = String.valueOf(responseCharArray);
						message = new Message(response + " " + response2, emotions.get(j));
						if(rand.nextBoolean())
						{
							switch(rand.nextInt(2))
							{
							case 0:
								message = new Message(message.getMessage() + " How about you?", Emotion.QUESTION);
								break;
							case 1:
								message = new Message(message.getMessage() + " You?", Emotion.QUESTION);
								break;
							}
						}
					}
					else if(emotions.get(j).equals(Emotion.SAD))
					{

						message = new Message(responses.get(j), emotions.get(j));
					}
				}
			}
			else
			{
				message = new Message(responses.get(i), emotions.get(i));
			}
		}
		
		
		
		
		
		return message;
	}
}