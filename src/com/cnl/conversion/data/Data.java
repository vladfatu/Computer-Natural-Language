package com.cnl.conversion.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
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
	
	public Data()
	{
		this.vocabulary = new HashMap<String, Feature>();
	}

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
	
	public void printVocabulary()
	{
		for (Map.Entry<String, Feature> word : getVocabulary().entrySet())
		{
			System.out.println(word.getKey() + " : " + word.getValue().print());
		}
	}
	
	public abstract void trainFromClass(List<ClassificationClass> classes, ClassificationClass classificationClass);
	
	public abstract List<List<String>> getTestDataForClass(ClassificationClass classificationClass);
	
	protected String readfile(String path)
	{
		File file = new File(path);
        StringBuilder contents = new StringBuilder();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            // repeat until all lines is read
            while ((text = reader.readLine()) != null) {
                contents.append(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

       return contents.toString();
	}
	
}
