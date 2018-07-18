package com.sam.caseconverter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

// reference: https://medium.com/prodsters/how-to-build-a-desktop-application-with-java-a34ee9c18ee3

public class App 
{
	String defaultInputText = "What's in your mind...";
	String defaultOutputText = "";
	String infoPrefix = "Character Count: ";
	
	public App() {
		
        final JTextArea infoTextArea = new JTextArea();
        infoTextArea.setLineWrap(true);
        infoTextArea.setWrapStyleWord(true);
        infoTextArea.setText(infoPrefix + defaultInputText.length());
        infoTextArea.setBackground(new Color(241,241,241));
        infoTextArea.setEditable(false);
        infoTextArea.setMargin(new Insets(5, 5, 5,5));
        
		JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(infoTextArea, BorderLayout.CENTER);
        
        
        JButton clearInputText = new JButton("Clear Text");
        JButton toUpperButton = new JButton("To Upper");
        JButton toLowerButton = new JButton("To Lower");

        JPanel inputControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputControlPanel.add(clearInputText);
        inputControlPanel.add(toUpperButton);
        inputControlPanel.add(toLowerButton);
        
        final JTextArea inputTextArea = new JTextArea(20,20);
        inputTextArea.setText(defaultInputText);
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        inputTextArea.setMargin(new Insets(5, 5, 5,5));
        inputTextArea.setFont(new Font("", 0, 12));
        
        JLabel inputTextLabel = new JLabel("Input Text");
        inputTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel inputTextPanel = new JPanel();
        inputTextPanel.setLayout(new BorderLayout());
        inputTextPanel.add(inputTextLabel, BorderLayout.NORTH);
        inputTextPanel.add(new JScrollPane(inputTextArea), BorderLayout.CENTER);
        inputTextPanel.add(inputControlPanel, BorderLayout.SOUTH);
        
        final JTextArea outputTextArea = new JTextArea();
        outputTextArea.setText(defaultOutputText);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setLineWrap(true);
        outputTextArea.setMargin(new Insets(5, 5, 5,5));
        outputTextArea.setFont(new Font("", 0, 12));
        outputTextArea.setEditable(false);

        JLabel outputTextLabel = new JLabel("Output Text");
        outputTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton copyOutputText = new JButton("Copy Text");
        JButton clearOutputText = new JButton("Clear Text");

        JPanel outputControlPanel = new JPanel();
        outputControlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        outputControlPanel.add(copyOutputText);
        outputControlPanel.add(clearOutputText);

        JPanel outputTextPanel = new JPanel();
        outputTextPanel.setLayout(new BorderLayout());
        outputTextPanel.add(outputTextLabel, BorderLayout.NORTH);
        outputTextPanel.add(new JScrollPane(outputTextArea), BorderLayout.CENTER);
        outputTextPanel.add(outputControlPanel, BorderLayout.SOUTH);
        
		JSplitPane splitPane =
                new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputTextPanel, outputTextPanel);
        splitPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
		JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        
        inputTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
            	infoTextArea.setText(infoPrefix + inputTextArea.getText().trim().length());
            }
        });
        
        toUpperButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String input = inputTextArea.getText().trim();
			    outputTextArea.setText(input.toUpperCase());
			}
		});
        
        toLowerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String input = inputTextArea.getText().trim();
			    outputTextArea.setText(input.toLowerCase());
			}
		});

        clearInputText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    inputTextArea.setText(null);
			    outputTextArea.setText(null);
		        infoTextArea.setText(infoPrefix + 0);
			}
		});
        
        copyOutputText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringSelection stringSelection = new StringSelection(outputTextArea.getText());
			    getSystemClipboard().setContents(stringSelection, null);
			}
		});

        clearOutputText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    outputTextArea.setText(null);
			}
		});
        
		JFrame frame = new JFrame(); 
		frame.setTitle("CaseConverter");
		frame.setLayout(new BorderLayout());
		frame.add(mainPanel, BorderLayout.CENTER); 
		frame.setSize(new Dimension(800, 650));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	// reference: https://stackoverflow.com/questions/6710350/copying-text-to-the-clipboard-using-java
	private static Clipboard getSystemClipboard()
    {
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Clipboard systemClipboard = defaultToolkit.getSystemClipboard();

        return systemClipboard;
    }
	
    public static void main( String[] args )
    {
    	try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			    new App();
			}
		});
    }
}
