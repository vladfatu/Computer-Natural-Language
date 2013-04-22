package com.cnl.conversion.wsd.pojo;

import edu.mit.jwi.item.POS;

public class Word {

	private String word;
	private POS wordType;

	public Word(String word, POS wordType) {
		this.word = word;
		this.wordType = wordType;
	}

	public POS getWordType()
	{
		return wordType;
	}

	public void setWordType(POS wordType)
	{
		this.wordType = wordType;
	}

	public String getWord()
	{
		return word;
	}

	public void setWord(String word)
	{
		this.word = word;
	}

}
