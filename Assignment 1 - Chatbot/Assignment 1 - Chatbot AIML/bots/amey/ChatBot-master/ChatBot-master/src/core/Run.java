package core;

public class Run
{
	public static ChatBot instance;
	
	public static void main(String[] args)
	{
		instance = new ChatBot();
		instance.run();
	}
}