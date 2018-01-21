import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

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

public class MainGUI {

    String      appName     = "Amey - The Chatbot";
    MainGUI     mainGUI;
    JFrame      newFrame    = new JFrame(appName);
    JButton     sendMessage;
    JTextField  messageBox;
    JTextArea   chatBox;
    JTextField  usernameChooser;
    JFrame      preFrame;
    static Chat chatSession;
    static PrintWriter wr;

    public static void main(String[] args)throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager
                            .getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MainGUI mainGUI = new MainGUI();
                mainGUI.preDisplay();
            }
        });
        
        String botname="amey";
		String path="C:\\Users\\Siddhant Shenoy\\Documents\\Sem 2 17-18\\CS F407 - Artificial Intelligence\\Assignments\\Assignment 1 - Chatbot\\";
		Bot bot = new Bot(botname, path);
		chatSession = new Chat(bot);
		
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		wr = new PrintWriter(path+"bots/amey/logs/log_"+timeStamp+".txt", "UTF-8");
    }

    public void preDisplay() {
        newFrame.setVisible(false);
        preFrame = new JFrame(appName);
        usernameChooser = new JTextField(15);
        JLabel chooseUsernameLabel = new JLabel("Pick a username:");
        JButton enterServer = new JButton("Enter Chat Server");
        enterServer.addActionListener(new enterServerButtonListener());
        JPanel prePanel = new JPanel(new GridBagLayout());

        GridBagConstraints preRight = new GridBagConstraints();
        preRight.insets = new Insets(0, 0, 0, 10);
        preRight.anchor = GridBagConstraints.EAST;
        GridBagConstraints preLeft = new GridBagConstraints();
        preLeft.anchor = GridBagConstraints.WEST;
        preLeft.insets = new Insets(0, 10, 0, 10);
        // preRight.weightx = 2.0;
        preRight.fill = GridBagConstraints.HORIZONTAL;
        preRight.gridwidth = GridBagConstraints.REMAINDER;

        prePanel.add(chooseUsernameLabel, preLeft);
        prePanel.add(usernameChooser, preRight);
        preFrame.add(BorderLayout.CENTER, prePanel);
        preFrame.add(BorderLayout.SOUTH, enterServer);
        preFrame.setSize(300, 300);
        preFrame.setVisible(true);

    }

    public void display() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLUE);
        southPanel.setLayout(new GridBagLayout());

        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();

        sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(new sendMessageButtonListener());

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);

        mainPanel.add(BorderLayout.SOUTH, southPanel);

        newFrame.add(mainPanel);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(850, 400);
        newFrame.setVisible(true);
        chatBox.append("<Amey>:  Hello! I am Amey. I was created by Siddhant Shenoy and Ayan Bhardwaj. How can I help you?\n");
		wr.write("Amey: Hello! I am Amey. I was created by Siddhant Shenoy and Ayan Bhardwaj. How can I help you?\n");
    }

    class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageBox.getText().length() < 1) {
                // do nothing
            } else if (messageBox.getText().equals(".clear")) {
                chatBox.setText("Cleared all messages\n");
                messageBox.setText("");
            } else {
                chatBox.append("<" + username + ">:  " + messageBox.getText()
                        + "\n");
                
                //after input processing starts
                
                wr.write(username+": "+ messageBox.getText()+ "\n");
                
                String finalResponse = "Amey: Bye. See you later!";
                
                if(messageBox.getText().equalsIgnoreCase("exit"))
                {
	                chatBox.append("<Amey>:  " + finalResponse
	                        + "\n");
	        		wr.write(finalResponse+"\n");
	        		
	        		wr.flush();
	        		wr.close();
	        		preFrame.setVisible(false);
	                display();
	                System.exit(0);
                }	
	        	
                try
                {
	                String input = messageBox.getText();
	                
	                if(input.equalsIgnoreCase("exit")) return;
	    			
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
	    							
	    							////System.out.println(command);
	    							
	    							Process process = Runtime.getRuntime().exec(command.toString());
	    							
	    							process.waitFor();
	    							
	    							String contents = new String(Files.readAllBytes(Paths.get("C:\\Users\\Siddhant Shenoy\\Documents\\Sem 2 17-18\\CS F407 - Artificial Intelligence\\Assignments\\Assignment 1 - Chatbot\\bots\\amey\\sampling.txt")));
	    							
	    							Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    							JsonParser jp = new JsonParser();
	    							JsonElement je = jp.parse(contents);
	    							String prettyJsonString = gson.toJson(je);
	    							JSONParser parser = new JSONParser();
	    							JSONObject json = (JSONObject) parser.parse(prettyJsonString);
	    							finalResponse = json.get("Abstract").toString();
	    							chatBox.append("<Amey>:  " + finalResponse
	    	                        + "\n");
	    							wr.write("Amey: "+finalResponse+"\n");
	    							messageBox.setText("");
	    				            messageBox.requestFocusInWindow();
	    				            return;
	    							
	    				/*case "WHO": CleverBotQuery bot2 = new CleverBotQuery("CC6gkWxyGVZOB_8xU2Xqvi1BcTA", input);
	    						    String response2;						    
	    						    try
	    						    {
	    						        bot2.sendRequest();
	    						        response2 = bot2.getResponse();
	    						        //System.out.println("Amey: "+response2);
	    						    }
	    						    catch(IOException e)
	    						    {
	    						        response2 = e.getMessage();
	    						        //System.out.println("Amey: "+response2);
	    						    } continue;*/
	    						    
	    				default: 	String request = input;
	    							String response = chatSession.multisentenceRespond(request);
	    							finalResponse = response;
	    							chatBox.append("<Amey>:  " + finalResponse
	    	                        + "\n");
	    							wr.write("Amey: "+finalResponse+"\n");
	    							messageBox.setText("");
	    				            messageBox.requestFocusInWindow();
	    				            return;
	    			}
	    			
	    			if(input.charAt(input.length() - 1) == '?')
	    				input = input.substring(0,input.length() - 1);
	    			
	    			words = input.split(" ");
	    			
	    			String location = words[words.length - 1];
	    			
	    			String web_query = "http://api.openweathermap.org/data/2.5/weather?q="+location+"&appid=b070c1ebac40dfa59b8533212cfc1bec";
	    			
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
	    			JSONParser parserTemp = new JSONParser();
	    			JSONObject jsonTemp = (JSONObject) parser.parse(json.get("main").toString());
	    			finalResponse = "The temperature in "+location+" is "+ (Float.parseFloat(jsonTemp.get("temp").toString()) - 273.15)+" Celsius";
	    			chatBox.append("<Amey>:  " + finalResponse
	                + "\n");
	    			wr.write("Amey: "+finalResponse+"\n");
                }
                catch(Exception e)
                {
                }
                //after input processing ends
                
                messageBox.setText("");
            }
            messageBox.requestFocusInWindow();
        }
    }

    String  username;

    class enterServerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            username = usernameChooser.getText();
            if (username.length() < 1) {
                System.out.println("No!");
            } else {
                preFrame.setVisible(false);
                display();
            }
        }

    }
}