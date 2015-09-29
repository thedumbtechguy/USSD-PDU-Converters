<?php

namespace Pdu;


/**
* A simple Pdu Converter utility. Funny enough, this is hard to come by
* All credit to https://github.com/gnomeby/ussd
*/
class Converter
{

	public static function toPDU($command)
	{
	  $bin = "";
	  for($i = 0; $i < strlen($command); $i++)
		$bin .= strrev(sprintf("%07b", ord($command[$i])));
		
	  $bin .= str_repeat("0", 8 - strlen($bin) % 8);
	  $pdu = "";
	  while(strlen($bin))
	  {
		$symbol = substr($bin, 0, 8);
		$symbol = strrev($symbol);
		$bin = substr($bin, 8);
		$pdu .= static::binhex(substr($symbol,0,4)).static::binhex(substr($symbol,4));
	  }
	  return $pdu;
	}
	
	public static function toText($pduanswer)
	{
	  $pdu = pack("H*", $pduanswer);
	  $bin = "";
	  for($i = 0; $i < strlen($pdu); $i++)
		$bin .= strrev(sprintf("%08b", ord($pdu[$i])));
	  $hex = "";
	  while(strlen($bin)>=7)
	  {
		$symbol = substr($bin, 0, 7);
		$bin = substr($bin, 7);
		
		$symbol = "0".strrev($symbol);    
		$hex .= static::binhex(substr($symbol,0,4)).static::binhex(substr($symbol,4));
	  }
	  return pack("H*", $hex);
	}

	protected static function binhex($string)
	{
	  return strtoupper(dechex(bindec($string)));
	}	
	
}
