import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Library {
	
	public Library() {
		
	}

	/**
	 * Parses CSV file. Returns a database with key:Pair, value:ListOfWins.
	 * @param file: formatted CSV file containing arena data.
	 */
	public HashMap<Pair, ArrayList<Integer>> parseFile(File file) 
	{
		HashMap<Pair, ArrayList<Integer>> ret = new HashMap<>();
		
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		BufferedReader br = new BufferedReader(fr);
		
		try {
			int read_state = 0; //0: parsing header, 1: parsing content
			String arena_being_read_in = "";
			int arena_being_read_in_wins = 0;
			ArrayList<String> list_of_cards_in_this_arena = new ArrayList<>();
			while(br.ready()) 
			{
				String line = br.readLine();
				
				if(read_state == 0 && line.startsWith("game_version")) 
					read_state = 1;
				else if(read_state == 1)
				{
					//--if end of file
					if(line.startsWith(",") || line.startsWith(" "))
						break;
					
					String[] cols = line.split(",");	//game_version,end_time,date,character,card_title,wins,player_id
					
					//--if start of arena i
					if(!arena_being_read_in.equals(cols[1]+cols[2]+cols[6])) 
					{
						//--do stuff with info from arena i-1
						ArrayList<Pair> arena_being_read_in_pairs = makeUniquePairs(list_of_cards_in_this_arena, arena_being_read_in_wins);
						for(int i = 0; i < arena_being_read_in_pairs.size(); i++)
						{
							Pair currPair = arena_being_read_in_pairs.get(i);
							if(ret.containsKey(currPair))
							{
								ArrayList<Integer> win_history = ret.get(currPair);
								win_history.add(currPair.value);
								ret.put(currPair, win_history);
							}
							else
							{
								ArrayList<Integer> win_history = new ArrayList<>();
								win_history.add(currPair.value);
								ret.put(currPair, win_history);
							}
						}
						System.out.println("[Library] Arena '" + arena_being_read_in + "' has been added. (" + ret.size() + " in db)");
		
						//--prepare for arena i, like reset id and add character
						arena_being_read_in = cols[1]+cols[2]+cols[6];
						arena_being_read_in_wins = Integer.parseInt(cols[5]);
						list_of_cards_in_this_arena = new ArrayList<>();
						list_of_cards_in_this_arena.add(cols[3]);
					}
					list_of_cards_in_this_arena.add(cols[4]);
					
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}

	/**
	 * Gets metric of how well the pairing performs.
	 * @param map: the database used.
	 * @param pair: the pairing.
	 */
	public double getScore(HashMap<Pair, ArrayList<Integer>> map, Pair pair) {
		
		ArrayList<Integer> record = map.get(pair);
		Iterator<Integer> it = record.iterator();
		int sum = 0;
		while(it.hasNext())
		{
			sum += it.next();
		}
		
		return (double)sum/(double)record.size();
	}
	
	/**
	 * Gets a list
	 * @param db
	 * @param history
	 * @return
	 */
	public HashMap<String, Double> getAllNextScores(HashMap<Pair, ArrayList<Integer>> db, ArrayList<String> history)
	{
		
		return null;
	}
	
	
	private ArrayList<Pair> makeUniquePairs(ArrayList<String> list, int wins) 
	{
		ArrayList<Pair> ret = new ArrayList<>();
		
		for(int i = 0; i < list.size(); i++)
		{
			for(int j = 0; j < list.size(); j++)
			{
				if(i != j)
				{
					Pair currPair = new Pair(list.get(i), list.get(j), wins);
					if(!ret.contains(currPair)) ret.add(currPair);
				}
			}
		}
		
		return ret;
	}
	
}
