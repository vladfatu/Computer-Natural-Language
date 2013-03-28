package com.cnl.conversion;

import com.cnl.conversion.classification.NaiveBayes.NaiveBayes;
import com.cnl.conversion.classification.pojo.Classification;
import com.cnl.conversion.classification.sentiment.SentimentClassification;
import com.cnl.conversion.data.SentimentData;

public class Main {
	
	public static void main(String[] args)
	{
		Classification setimentClassification = new SentimentClassification(new SentimentData(), new NaiveBayes());
		setimentClassification.trainAndTest();
	}

}
