import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class ArenaRun {
	
	String game_version;
	Calendar endTime;	//end_time and date
	String character;
	int wins;
	int player_id;
	
	ArrayList<CardNode> cards;
	
	public ArenaRun(String[] cols) {
		if (cols.length == 7);
		cards = new ArrayList<CardNode>();
		
		//endTime = Calendar.getInstance();
		//endTime.set(); //year, month, date, hour, minute, second
	}
	
	public static ArrayList<ArenaRun> getFromFile(File file) 
	{
		ArenaRun curr = null;
		String curr_id = "";
		ArrayList<ArenaRun> ret = new ArrayList<>();
		
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		BufferedReader br = new BufferedReader(fr);
		
		try {
			int state = 0; //0: currently looking for header, 1: past header, reading data
			while(br.ready()) 
			{
				
				String line = br.readLine();
				
				if(state == 0 && line.startsWith("game_version")) 
				{
					state = 1;
				}
				else if(state == 1)
				{
					if(line.startsWith(",") || line.startsWith(" ")) //end of file
						break;
					
					String[] cols = line.split(",");
					if(!curr_id.equals(cols[1] + cols[2])) 
					{
						curr_id = cols[1] + cols[2];
						System.out.println("+" + curr_id);
						
						ret.add(curr);					//adds extra null in spot 0
						curr = new ArenaRun(cols);
						curr.addCard(new CardNode(cols[4]));
					}
					else 
					{
						CardNode temp = new CardNode(cols[4]);
						curr.addCard(temp);
					}
				}
			}
			ret.add(curr);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ret.remove(0);	
		return ret;
	}
	
	public void addCard(CardNode card) {
		cards.add(card);
	}


}