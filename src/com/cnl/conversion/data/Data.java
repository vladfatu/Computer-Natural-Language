package com.cnl.conversion.data;

import java.util.List;
import java.util.Map;

import com.cnl.conversion.classification.pojo.ClassificationClass;
import com.cnl.conversion.classification.pojo.Feature;

/**
 * @author vlad
 *
 */
public abstract class Data {

	private Map<String, Feature> vocabulary;
	private int wordCounts;

	public int getWordCounts() {
		return wordCounts;
	}

	public void setWordCounts(int wordCounts) {
		this.wordCounts = wordCounts;
	}

	public Map<String, Feature> getVocabulary() {
		return vocabulary;
	}

	public void setVocabulary(Map<String, Feature> vocabulary) {
		this.vocabulary = vocabulary;
	}
	
	public abstract void trainFroClass(ClassificationClass classificationClass);
	
	public abstract List<String> getTestDataForClass(ClassificationClass classificationClass);
	
}
