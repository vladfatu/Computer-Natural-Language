package com.cnl.conversion;

import java.io.IOException;

import com.cnl.conversion.classification.NaiveBayes.NaiveBayes;
import com.cnl.conversion.classification.pojo.Classification;
import com.cnl.conversion.classification.sentiment.SentimentClassification;
import com.cnl.conversion.data.SentimentData;
import com.cnl.conversion.wsd.WordnetTest;

public class Main {
	
	public static void main(String[] args)
	{
		//Sentiment analasys
//		Classification setimentClassification = new SentimentClassification(new SentimentData(), new NaiveBayes());
//		setimentClassification.trainAndTest();
		
		//WSD
		
		WordnetTest test = new WordnetTest();
		try {
			test.testDictionary();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
