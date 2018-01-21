package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Scanner;

import core.database.MatrixDatabase;
import core.database.SpecialDatabase;
import core.gui.GUI;
import core.processing.StringProcessor;

public class ChatBot
{
	public boolean end = false;
	public boolean debug = false;
	public boolean connectedToInternet = false;
	public SpecialDatabase sd;
	public MatrixDatabase md;
	public StringProcessor sp;
	public Scanner scan;
	private int toggle = 0;
	public final String DATABASE_DIRECTORY = "database";
	public InputStream specialDatabase;
	public InputStream matrixDatabase;
	
	public void run()
	{
		System.out.println("ChatBot " + Properties.VERSION);
		scan = new Scanner(System.in);
		sp = new StringProcessor();
		System.out.println("Creating necessary files");
		File file = new File(DATABASE_DIRECTORY);
		File file2 = new File("log_" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "_" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "_" + Calendar.getInstance().get(Calendar.YEAR) + "_" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + "_" + Calendar.getInstance().get(Calendar.MINUTE) + "_" + Calendar.getInstance().get(Calendar.SECOND) + "_" + Calendar.getInstance().get(Calendar.MILLISECOND) + ".txt");
		if(!file.exists())
		{
			file.mkdir();
		}
		if(!file2.exists())
		{
			try
			{
				file2.createNewFile();
				System.out.println("Chat log created at: " + file2.getAbsolutePath());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		connectToInternet();
		initDatabases();
		if(debug)
		{
			System.err.println("WARNING: debug mode on");
		}
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file2, false));
			GUI gui = new GUI(bw);
			gui.start();
			while(!end && !debug)
			{
				String input = scan.nextLine();
				bw.write("User: " + input);
				bw.newLine();
				bw.flush();
				String output = sp.processString(input);
				bw.write("Bot: " + output);
				bw.newLine();
				bw.flush();
				System.out.println(output);
			}
			bw.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		while(!end && debug)
		{
			switch(toggle)
			{
			case 0:
				specialDebug();
				break;
			case 1:
				matrixDebug();
				break;
			default:
				System.err.println("ERROR: invalid toggle value");
				toggle = 0;
				specialDebug();
			}
		}
	}
	
	public void connectToInternet()
	{
		System.out.println("Attempting to connect to the Internet");
		try
		{
			URL url = new URL("https://dl.dropboxusercontent.com/u/87762025/specialdatabase.txt");
			URL url2 = new URL("https://dl.dropboxusercontent.com/u/87762025/matrixdatabase.txt");
			URLConnection connection = url.openConnection();
			URLConnection connection2 = url2.openConnection();
			InputStream stream = connection.getInputStream();
			InputStream stream2 = connection2.getInputStream();
			specialDatabase = stream;
			matrixDatabase = stream2;
			connectedToInternet = true;
			System.out.println("Internet connection Established");
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			connectedToInternet = false;
			System.out.println("ERROR: Failed to connect to the Internet");
		}
	}
	
	public void initDatabases()
	{
		System.out.println("Loading databases");
		sd = new SpecialDatabase();
		md = new MatrixDatabase();
		System.out.println("Databases loaded");
	}
	
	public void specialDebug()
	{
		String input = scan.nextLine();
		if(input.equals("END"))
		{
			end = true;
		}
		else if(input.equals("toggle."))
		{
			toggle ^= 0b1;
			System.out.println("mode switched to " + toggle);
		}
		else if(input.startsWith("delete."))
		{
			int index;
			try
			{
				index = Integer.valueOf(input.substring(7));
			}
			catch(Exception e)
			{
				index = sd.size();
			}
			sd.remove(index - 1);
		}
		else
		{
			sd.add(input);
			sd.saveSpecialDatabase();
		}
	}
	
	public void matrixDebug()
	{
		String input = scan.nextLine();
		if(input.equals("END"))
		{
			end = true;
		}
		else if(input.equals("toggle."))
		{
			toggle ^= 0b1;
			System.out.println("mode switched to " + toggle);
		}
		else if(input.startsWith("delete."))
		{
			int index;
			try
			{
				index = Integer.valueOf(input.substring(7));
			}
			catch(Exception e)
			{
				index = md.size();
			}
			md.remove(index - 1);
		}
		else
		{
			md.add(input);
			md.saveMatrixDatabase();
		}
	}
}