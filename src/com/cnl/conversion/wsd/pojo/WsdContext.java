package com.cnl.conversion.wsd.pojo;

import java.util.ArrayList;
import java.util.List;

public class WsdContext {
	
	private List<Word> words;
	private String senseId;
	
	public WsdContext()
	{
		this.words = new ArrayList<Word>();
	}

	public List<Word> getWords()
	{
		return words;
	}

	public void setWords(List<Word> words)
	{
		this.words = words;
	}

	public String getSenseId()
	{
		return senseId;
	}

	public void setSenseId(String senseId)
	{
		this.senseId = senseId;
	}

}
