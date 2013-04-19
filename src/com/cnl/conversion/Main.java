package com.cnl.conversion;

import java.util.ArrayList;
import java.util.List;

import com.cnl.conversion.wsd.pojo.Word;
import com.cnl.conversion.wsd.pojo.WsdMethod;
import com.cnl.conversion.wsd.supervised.WsdSupervised;

import edu.mit.jwi.item.POS;

public class Main {
	
	public static void main(String[] args)
	{
		//Sentiment analasys
//		Classification setimentClassification = new SentimentClassification(new SentimentData(), new NaiveBayes());
//		setimentClassification.trainAndTest();
		
		//WSD
		
		Word line = new Word("line", POS.NOUN);
		
		WsdMethod wsdMethod = new WsdSupervised();
		List<Word> featureWords = new ArrayList<Word>();
		Word featureWord = new Word("mooring", POS.NOUN);
		featureWords.add(featureWord);
		
		featureWord = new Word("seabed", POS.NOUN);
		featureWords.add(featureWord);
		
		featureWord = new Word("steel", POS.NOUN);
		featureWords.add(featureWord);
		
		featureWord = new Word("design", POS.NOUN);
		featureWords.add(featureWord);
		
		featureWord = new Word("platform", POS.NOUN);
		featureWords.add(featureWord);
		
		featureWord = new Word("structure", POS.NOUN);
		featureWords.add(featureWord);
		
		long t = System.currentTimeMillis();
		
		wsdMethod.getBestWordId(line, featureWords);
		
		System.out.printf("Finished in (%1d msec )" , System.currentTimeMillis()-t ) ;
	}

}
