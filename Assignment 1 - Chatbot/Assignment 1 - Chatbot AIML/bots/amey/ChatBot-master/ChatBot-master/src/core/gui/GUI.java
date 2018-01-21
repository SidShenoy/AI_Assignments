package core.gui;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import core.Run;

public class GUI implements Runnable
{
	public static JTextArea display;
	public static JTextField userInput;
	private BufferedWriter bw;
	
	public GUI(BufferedWriter writer)
	{
		bw = writer;
	}

	public void start()
	{
		Thread runner = new Thread(this);
		runner.start();
	}

	public void run()
	{
		setFrame();
	}
	
	public void setFrame()
	{
		//main screen
		JFrame mainScreen = new JFrame("My Testing Frame");
		mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainScreen.setLayout(new FlowLayout());
		mainScreen.setSize(450, 350);
		mainScreen.setLocationRelativeTo(null);
		mainScreen.setVisible(true);
		
		JPanel displayText = new JPanel();
		final JTextArea display = new JTextArea(15, 35);
		display.setEditable(false);
		JScrollPane scroll = new JScrollPane(display);
		displayText.add(scroll);
		mainScreen.add(displayText);
		
		JPanel input = new JPanel();
		JLabel enterText = new JLabel("Enter text here: ", JLabel.LEFT);
		userInput = new JTextField(29);
		input.add(enterText);
		input.add(userInput);
		
		userInput.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					displayText();
				}
			}
			
			private void displayText()
			{
				String input = userInput.getText();
				display.append("User: " + input + "\n");
				try
				{
					bw.write("User: " + input);
					bw.newLine();
					bw.flush();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				userInput.setText(null);
				String output = Run.instance.sp.processString(input);
				display.append("Bot: " + output + "\n");
				try
				{
					bw.write("Bot: " + output);
					bw.newLine();
					bw.flush();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				if(Run.instance.end)
				{
					try
					{
						bw.close();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					System.exit(0);
				}
			}

			public void keyReleased(KeyEvent e)
			{
				
			}
			
			public void keyTyped(KeyEvent e)
			{
				
			}
		});
		
		mainScreen.add(displayText);
		mainScreen.add(input);
	}
}