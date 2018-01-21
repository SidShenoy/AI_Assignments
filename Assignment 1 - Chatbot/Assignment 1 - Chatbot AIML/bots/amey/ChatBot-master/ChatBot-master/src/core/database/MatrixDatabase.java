package core.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import core.Run;
import core.data.Emotion;
import core.data.Message;

public class MatrixDatabase
{
	private List<String> matrixDatabase;
	private List<Emotion> matrixDatabaseEmotions;
	
	private File file = new File(Run.instance.DATABASE_DIRECTORY + "/matrixdatabase.txt");
	
	public MatrixDatabase()
	{
		init();
		load();
		checkForInvalid();
	}
	
	public void init()
	{
		if(!file.exists())
		{
			try
			{
				if(file.createNewFile())
				{
					BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
					bw.write("0");
					bw.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void checkForInvalid()
	{
		if(Run.instance.debug)
		{
			for(int i = 0; i < matrixDatabaseEmotions.size(); i++)
			{
				if(matrixDatabaseEmotions.get(i).equals(Emotion.UNKNOWN))
				{
					System.err.println("database entry found with an unknown emotion. delete, replace, ignore");
					System.err.println(matrixDatabase.get(i));
					String response = Run.instance.scan.nextLine();
					if(response.equals("delete"))
					{
						matrixDatabase.remove(i);
						matrixDatabaseEmotions.remove(i);
						i--;
					}
					else if(response.equals("replace"))
					{
						System.err.println("what do you want to replace it with?");
						response = Run.instance.scan.nextLine();
						for(Emotion emote : Emotion.values())
						{
							if(response.toUpperCase().equals(emote.toString()))
							{
								matrixDatabaseEmotions.set(i, emote);
							}
						}
					}
				}
			}
			saveMatrixDatabase();
		}
	}
	
	public void load()
	{
		try
		{
			BufferedReader br;
			if(Run.instance.connectedToInternet)
				br = new BufferedReader(new InputStreamReader(Run.instance.matrixDatabase));
			else
				br = new BufferedReader(new FileReader(file));
			int size = 0;
			try
			{
				size = Integer.valueOf(br.readLine());
			}
			catch(NumberFormatException e)
			{
				
			}
			matrixDatabase = new ArrayList<String>();
			matrixDatabaseEmotions = new ArrayList<Emotion>();
			for(int i = 0; i < size; i++)
			{
				String line = br.readLine();
				add(line);
			}
			br.close();
			if(Run.instance.connectedToInternet)
				saveMatrixDatabase();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void saveMatrixDatabase()
	{
		try
		{
			File file2 = new File(file.getPath() + ".old");
			file.renameTo(file2);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
			bw.write("" + matrixDatabase.size());
			for(int i = 0; i < matrixDatabase.size(); i++)
			{
				bw.newLine();
				bw.write(matrixDatabase.get(i) + ":" + matrixDatabaseEmotions.get(i));
			}
			bw.close();
			file2.delete();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Message getFromMatrixDatabase(String input)
	{
		input = input.toLowerCase();
		List<Emotion> emotions = new ArrayList<Emotion>();
		for(int i = 0; i < matrixDatabase.size(); i++)
		{
			if(input.contains(matrixDatabase.get(i)))
			{
				emotions.add(matrixDatabaseEmotions.get(i));
			}
		}
		return new Message(input, emotions);
	}
	
	public void add(String input)
	{
		input = input.toLowerCase();
		boolean validEmote = false;
		for(int j = 0; j < Emotion.values().length; j++)
		{
			Emotion emote = Emotion.values()[j];
			if(input.contains(emote.toString().toLowerCase()))
			{
				validEmote = true;
				matrixDatabaseEmotions.add(emote);
				input = input.replace(":" + emote.toString().toLowerCase(), "");
				break;
			}
		}
		if(!validEmote)
		{
			matrixDatabaseEmotions.add(Emotion.UNKNOWN);
			for(int i = 0; i < input.length(); i++)
			{
				if(input.charAt(i) == ':')
				{
					input = input.substring(0, i);
				}
			}
		}
		matrixDatabase.add(input);
	}
	
	public void remove(int index)
	{
		matrixDatabase.remove(index);
		matrixDatabaseEmotions.remove(index);
		saveMatrixDatabase();
	}
	
	public int size()
	{
		return matrixDatabase.size();
	}
}