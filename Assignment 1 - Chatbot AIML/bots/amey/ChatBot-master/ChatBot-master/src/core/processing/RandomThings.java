package core.processing;

import java.util.Random;

public class RandomThings
{
	public static String askRandomQuestion(Random rand)
	{
		switch(rand.nextInt(5))
		{
		case 0:
			return "What's your name?";
		case 1:
			return "What is your favorite " + getRandomSubject(rand) + "?";
		case 2:
			return "Where do you live?";
		case 3:
			return "What do you want to do?";
		case 4:
			return "What do you want to talk about?";
		default:
			return "";
		}
	}
	
	public static String getRandomSubject(Random rand)
	{
		switch(rand.nextInt(7))
		{
		case 0:
			return "anime";
		case 1:
			return "food";
		case 2:
			return "hobby";
		case 3:
			return "tv show";
		case 4:
			return "movie";
		case 5:
			return "game";
		case 6:
			return "sport";
		default:
			return "";
		}
	}
}