package com.cnl.conversion;

import com.cnl.conversion.classification.NaiveBayes.NaiveBayes;
import com.cnl.conversion.classification.pojo.Classification;
import com.cnl.conversion.classification.sentiment.SentimentClassification;
import com.cnl.conversion.data.SentimentData;

public class Main {
	
	public static void main(String[] args)
	{
		//Sentiment analasys
		Classification setimentClassification = new SentimentClassification(new SentimentData(), new NaiveBayes());
		setimentClassification.trainAndTest();
		
		//WSD
		
//		WsdData wsdData = new WsdData();
//		
//		Word line = new Word("line", POS.NOUN);
//		
//		WsdMethod wsdMethod = new WsdSupervised();
//		
//		wsdMethod.test(line, wsdData.getContexts());
//		
//		
//		List<Word> featureWords = new ArrayList<Word>();
//		Word featureWord = new Word("mooring", POS.NOUN);
//		featureWords.add(featureWord);
//		
//		featureWord = new Word("seabed", POS.NOUN);
//		featureWords.add(featureWord);
//		
//		featureWord = new Word("steel", POS.NOUN);
//		featureWords.add(featureWord);
//		
//		featureWord = new Word("design", POS.NOUN);
//		featureWords.add(featureWord);
//		
//		featureWord = new Word("platform", POS.NOUN);
//		featureWords.add(featureWord);
//		
//		featureWord = new Word("structure", POS.NOUN);
//		featureWords.add(featureWord);
		
//		long t = System.currentTimeMillis();
//		
//		wsdMethod.getBestWordId(line, featureWords);
//		
//		System.out.printf("Finished in (%1d msec )" , System.currentTimeMillis()-t ) ;
	}

}
