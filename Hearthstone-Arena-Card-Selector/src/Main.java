import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends JFrame {
	
	static final long serialVersionUID = 1l;
	final String DESKTOP = System.getProperty("user.home") +  System.getProperty("file.separator") + "Desktop";
	
	Library lib;
	HashMap<Pair, ArrayList<Integer>> db;
	
	/*
	JPanel panel_main;
	JPanel panel_arenaStats;
	
	JButton button_selectFile;
	
	JLabel label_fileName;
	JLabel label_arenasRan;
	JLabel label_cardsDrafted;
	
	GridBagConstraints c;
	*/
	
	File file_csv;
	
	public Main() 
	{
		super("Hearthstone Arena Card Tier Selector");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 500);
		try {
		UIManager.setLookAndFeel(
				UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			//UnsupportedLookAndFeelException e
			//ClassNotFoundException e
			//InstantiationException e
			//IllegalAccessException e
			System.out.println("Warning: can't set the system's look and feel-- resorting to default");
		}
		
		//--instantiation
		lib = new Library();
		db = new HashMap<>();
		
		//--run
		db = lib.parseFile(new File(DESKTOP + System.getProperty("file.separator") + "arena-stats-sample.csv"));
		
		System.out.println("Number of entries in database: " + db.size());
		System.out.println("==Sample Data:");
		Pair sample_pair_1 = new Pair("Master Jouster", "Demolisher", -1);
		Pair sample_pair_2 = new Pair("Dark Peddler", "Lost Tallstrider", -1);
		Pair sample_pair_3 = new Pair("Arcane Missiles", "Mage", -1);
		System.out.println(sample_pair_1.a + " + " + sample_pair_1.b + " -> " + lib.getScore(db, sample_pair_1));
		System.out.println(sample_pair_2.a + " + " + sample_pair_2.b + " -> " + lib.getScore(db, sample_pair_2));
		System.out.println(sample_pair_3.a + " + " + sample_pair_3.b + " -> " + lib.getScore(db, sample_pair_3));

		/*
		panel_main = new JPanel(new GridBagLayout());
		panel_arenaStats = new JPanel();
		panel_arenaStats.setLayout(new BoxLayout(panel_arenaStats, BoxLayout.Y_AXIS));
		button_selectFile = new JButton("Select File");
		label_fileName = new JLabel("No file chosen...");
		
		//--adding elements
		c = new GridBagConstraints();
		c.insets = new Insets(10, 25, 10, 25); //top, left, bottom, right
		c.gridx=0;
		c.gridy=0;
		panel_main.add(label_fileName, c);
		
		c.gridx++;
		panel_main.add(button_selectFile, c);
		
		c.gridy++;
		c.gridx--;
		panel_main.add(panel_arenaStats, c);
		panel_arenaStats.add(new JLabel("Here are your stats:"));
		panel_arenaStats.add(label_arenasRan = new JLabel());
		panel_arenaStats.add(label_cardsDrafted = new JLabel());
		
		//--listeners
		button_selectFile.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma-Separated Value Files (.csv)", "csv");
				String desktop = System.getProperty("user.home") +  System.getProperty("file.separator") + "Desktop";
				JFileChooser fileChooser = new JFileChooser(desktop);
				fileChooser.setFileFilter(filter);
				int returnValue = fileChooser.showOpenDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION)
				{
					file_csv = fileChooser.getSelectedFile();
					label_fileName.setText(file_csv.getName());
					ArrayList<ArenaRun> runs = ArenaRun.parseFile(file_csv);
					
					label_arenasRan.setText("Arenas ran: " + runs.size());
					
					Iterator<ArenaRun> runs_iterator = runs.iterator();
					int numOfCards = 0;
					while(runs_iterator.hasNext())
					{
						ArenaRun element = runs_iterator.next();
						numOfCards += element.cards.size();
					}
					label_cardsDrafted.setText("Cards drafted: " + numOfCards);
				}
			}
		});
		
		add(panel_main);
		setVisible(true);
		*/
	}
	
	public void generateReport(ArrayList<ArenaRun> arr) {
		//--generate new text file
		String desktop = System.getProperty("user.home") +  System.getProperty("file.separator") + "Desktop";
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(desktop + System.getProperty("file.separator") + "generatedReport.txt" , "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//--verification
		//todo: find out if numbers quantities make sense
		
		//--calculate card scores
		HashMap<String, Integer> map_cardScore = new HashMap<>();
		//todo: calculate weighted average.
		
		
		//--output report
		writer.println("The first line");
		writer.println("The second line");
		writer.close();
	}
	

	public static void main(String[] args) {
		Main app = new Main();
	}

}