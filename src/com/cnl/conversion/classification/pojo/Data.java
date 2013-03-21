package com.cnl.conversion.classification.pojo;

import java.util.Map;

/**
 * @author vlad
 *
 */
public class Data {

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
	
}
