import org.alicebot.ab.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.michaelwflaherty.cleverbotapi.CleverBotQuery;
import java.io.*;
import java.text.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.net.URI;

public class Amey {
	public static void main(String args[])throws Exception
	{
		String botname="amey";
		String path="C:\\Users\\Siddhant Shenoy\\Documents\\Sem 2 17-18\\CS F407 - Artificial Intelligence\\Assignments\\Assignment 1 - Chatbot\\";
		Bot bot = new Bot(botname, path);
		Chat chatSession = new Chat(bot);
		
		String input = "",finalResponse;
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter wr = new PrintWriter(path+"bots/amey/logs/log_"+timeStamp+".txt", "UTF-8");
		
		System.out.println("---------- All configurations SUCCESSFUL!!! ----------");
		
		System.out.println("Amey: Hello! I am Amey. I was created by Siddhant Shenoy and Ayan Bhardwaj. How can I help you?");
		wr.write("Amey: Hello! I am Amey. I was created by Siddhant Shenoy and Ayan Bhardwaj. How can I help you?\n");
		
		while(1==1)
		{
			System.out.print("Human: ");
			
			input = br.readLine();
			
			wr.write("Human: "+input+"\n");
			
			if(input.equalsIgnoreCase("exit")) break;
			
			String words[] = input.split(" ");
			
			switch(words[0].toUpperCase())
			{
				case "WHAT":break;
				case "WHEN":
				case "WHERE":
				case "WHO":
				case "HOW": if(input.charAt(input.length() - 1) == '?')
								input = input.substring(0,input.length() - 1);
					
							String web_query = "https://api.duckduckgo.com/?q="+input+"&format=json&pretty=1";
				
							StringBuilder command = new StringBuilder("powershell cd 'C:\\Users\\Siddhant Shenoy\\Documents\\Sem 2 17-18\\CS F407 - Artificial Intelligence\\Assignments\\Assignment 1 - Chatbot\\bots\\amey' ; wget '"+web_query+"' -O sampling.txt");	
							
							//System.out.println(command);
							
							Process process = Runtime.getRuntime().exec(command.toString());
							
							process.waitFor();
							
							String contents = new String(Files.readAllBytes(Paths.get("C:\\Users\\Siddhant Shenoy\\Documents\\Sem 2 17-18\\CS F407 - Artificial Intelligence\\Assignments\\Assignment 1 - Chatbot\\bots\\amey\\sampling.txt")));
							
							Gson gson = new GsonBuilder().setPrettyPrinting().create();
							JsonParser jp = new JsonParser();
							JsonElement je = jp.parse(contents);
							String prettyJsonString = gson.toJson(je);
							JSONParser parser = new JSONParser();
							JSONObject json = (JSONObject) parser.parse(prettyJsonString);
							finalResponse = "Amey: "+json.get("Abstract").toString();
							System.out.println(finalResponse);
							wr.write(finalResponse+"\n");
							continue;
							
				/*case "WHO": CleverBotQuery bot2 = new CleverBotQuery("CC6gkWxyGVZOB_8xU2Xqvi1BcTA", input);
						    String response2;						    
						    try
						    {
						        bot2.sendRequest();
						        response2 = bot2.getResponse();
						        System.out.println("Amey: "+response2);
						    }
						    catch(IOException e)
						    {
						        response2 = e.getMessage();
						        System.out.println("Amey: "+response2);
						    } continue;*/
						    
				default: 	String request = input;
							String response = chatSession.multisentenceRespond(request);
							finalResponse = "Amey: "+response;
							System.out.println(finalResponse);
							wr.write(finalResponse+"\n");
							continue;
			}
			
			if(input.charAt(input.length() - 1) == '?')
				input = input.substring(0,input.length() - 1);
			
			words = input.split(" ");
			
			String location = words[words.length - 1];
			
			String web_query = "http://api.openweathermap.org/data/2.5/weather?q="+location+"&appid=b070c1ebac40dfa59b8533212cfc1bec";
			
			StringBuilder command = new StringBuilder("powershell cd 'C:\\Users\\Siddhant Shenoy\\Documents\\Sem 2 17-18\\CS F407 - Artificial Intelligence\\Assignments\\Assignment 1 - Chatbot\\bots\\amey' ; wget '"+web_query+"' -O sampling.txt");	
			
			System.out.println(command);
			
			Process process = Runtime.getRuntime().exec(command.toString());
			
			process.waitFor();
			
			String contents = new String(Files.readAllBytes(Paths.get("C:\\Users\\Siddhant Shenoy\\Documents\\Sem 2 17-18\\CS F407 - Artificial Intelligence\\Assignments\\Assignment 1 - Chatbot\\bots\\amey\\sampling.txt")));
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(contents);
			String prettyJsonString = gson.toJson(je);
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(prettyJsonString);
			JSONParser parserTemp = new JSONParser();
			JSONObject jsonTemp = (JSONObject) parser.parse(json.get("main").toString());
			finalResponse = "Amey: The temperature in "+location+" is "+ (Float.parseFloat(jsonTemp.get("temp").toString()) - 273.15)+" Celsius";
			System.out.println(finalResponse);
			wr.write(finalResponse+"\n");
		}
		
		finalResponse = "Amey: Bye. See you later!";
		System.out.println(finalResponse);
		wr.write(finalResponse+"\n");
		
		wr.flush();
		wr.close();
	}
}
