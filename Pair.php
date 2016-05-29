<?php

class Pair extends SplObjectStorage 
{

	public $a;			//card 1
	public $b;			//card 2
	public $history;	//historical performance of pairing

	public function __construct($a, $b, $history)
	{
		$this->a = $a;
		$this->b = $b;
		$this->history = $history;
	}

	public function getHash()
	{
		if(strcmp($this->a, $this->b) < 0)
			return $this->a . $this->b;
		else
			return $this->b . $this->a;
	}
}