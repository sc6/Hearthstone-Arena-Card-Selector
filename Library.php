<?php
	require_once "Pair.php";

	class Library
	{

		public $db;

		private function startsWith($haystack, $needle) {
		    // search backwards starting from haystack length characters from the end
		    return $needle === "" || strrpos($haystack, $needle, -strlen($haystack)) !== false;
		}

		private function endsWith($haystack, $needle) {
		    // search forward starting from end minus needle length characters
		    return $needle === "" || (($temp = strlen($haystack) - strlen($needle)) >= 0 && strpos($haystack, $needle, $temp) !== false);
		}
		
		//Returns a DB with k:pairs v:wins-history.
		//Takes in the name of the formatted csv file.
		public function parseFile($file_name)
		{
			$ret = array();
			$read_state = 0; //0: looking for content, 1: in content
			$curr_arena_id = "";
			$curr_arena_wins = 0;

			$curr_arena_list = array();

			$contents = file_get_contents($file_name);
			$contents_array = explode("\n", $contents);

			foreach($contents_array as $line)
			{
				if($read_state == 0 && $this->startsWith($line, "game_version"))
					$read_state = 1;
				else if($read_state == 1)
				{
					//--if end of line
					if($this->startsWith($line, ",") || $this->startsWith($line, " "))
						break;

					$cols = explode(",", $line);

					//--if start of arena 'i'
					if($curr_arena_id != ($cols[1] . $cols[2] . $cols[6]))
					{
						//--do stuff with info from arena 'i-1'
						$curr_arena_pairs = $this->makeUniquePairs($curr_arena_list);
						for($i = 0; $i < count($curr_arena_pairs); $i++)
						{
							$pair = $curr_arena_pairs[$i];
							if(isset($ret[$pair->getHash()]))
							{
								$ret[$pair->getHash()]->history[] = $curr_arena_wins;
							}
							else
							{
								$ret[$pair->getHash()] = $pair;
								$ret[$pair->getHash()]->history = array();
								$ret[$pair->getHash()]->history[] = $curr_arena_wins;
							}
						}
						//echo "[Library] Arena $curr_arena_id has been added in. (" . count($ret) . " rows in db)<br>";

						//--prepare for arena 'i' like reset $curr_arena_id and add character
						$curr_arena_id = ($cols[1] . $cols[2] . $cols[6]);
						$curr_arena_wins = $cols[5];
						$curr_arena_list = array();
						$curr_arena_list[] = $cols[3];
					}

					//--(else) add card into $curr_arena_list
					$curr_arena_list[] = $cols[4];

				}	
			}

			$this->db = $ret;
			return $ret;
		}

		public function getScore($reference_pair)
		{
			$ret = 0;
			$db = $this->db;
			$pair = $db[$reference_pair->getHash()];
			$history = $pair->history;
			foreach($history as $score)
			{
				$ret += $score;
			}

			return ($ret/count($history));
		}

		public function getAllNextScores($history)
		{
			$ret = array();
			$list = $this->getUniqueCards();

			foreach($list as $card)
			{
				$historyPlus = $history;
				$historyPlus[] = $card;
				$pairsPlus = $this->makePairs($historyPlus);

				$cardScore = 0;
				foreach($pairsPlus as $pair)
				{
					if(isset($this->db[$pair->getHash()]))
						$cardScore += $this->getScore($pair);
					else
						$cardScore += 3;	//pair not in db
				}

				$ret[$card] = $cardScore;
			}

			return $ret;
		}


		private function makeUniquePairs($list)
		{
			$ret = array();

			for($i = 0; $i < count($list); $i++)
			{
				for($j = 0; $j < $i; $j++)
				{
					$ret[] = new Pair($list[$i], $list[$j], array());
				}
			}

			return $ret;
		}

		private function makePairs($list)
		{
			$ret = array();

			for($i = 0; $i < count($list); $i++)
			{
				for($j = 0; $j < count($list); $j++)
				{
					if($i != $j)
					{
						$currPair = new Pair($list[$i], $list[$j], array());
						$ret[] = $currPair;
					}
				}
			}

			return $ret;
		}

		private function getUniqueCards()
		{
			$ret = array();
			foreach($this->db as $pair)
			{
				if(!in_array($pair->a, $this->db)) $ret[] = $pair->a; 
				if(!in_array($pair->b, $this->db)) $ret[] = $pair->b;
			}

			return $ret;
		}


		



	}


?>