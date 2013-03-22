package com.cnl.conversion;

import com.cnl.conversion.classification.sentiment.SentimentClassification;
import com.cnl.conversion.data.SentimentData;

public class Main {
	
	public static void main(String[] args)
	{
		System.out.println("Hello World");
		
		SentimentData data = new SentimentData();
		
		SentimentClassification setimentClassification = new SentimentClassification(data);
		setimentClassification.train();
		setimentClassification.test();
	}

}
