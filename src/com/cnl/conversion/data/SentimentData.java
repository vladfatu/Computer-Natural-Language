package com.cnl.conversion.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import com.cnl.conversion.classification.pojo.ClassificationClass;
import com.cnl.conversion.classification.pojo.Feature;

/**
 * @author vlad
 * 
 */
public class SentimentData extends Data {
	
	public void updateFeature(String word, List<ClassificationClass> classes, ClassificationClass classificationClass)
	{
		Feature feature = this.getVocabulary().get(word);
		if (feature == null)
		{
			feature = new Feature(classes);
		}
		feature.addFrequencyForClass(classificationClass, 1);
		this.getVocabulary().put(word, feature);
	}

	public void trainFromClass(List<ClassificationClass> classes, ClassificationClass classificationClass)
	{
		int startingNumber = 0, lastNumber = 100;
		String path = null;
		if (classificationClass.getClassName().equals("positive"))
		{
			path = "data/movie_review_data/pos";
		}
		else
		{
			path = "data/movie_review_data/neg";
		}
		File file = new File(path);
		String[] files = file.list();
		Arrays.sort(files);
		for (int i = startingNumber; i < lastNumber; i++)
		{
			String document = readfile(path+"/"+files[i]);
			StringTokenizer tokenizer = new StringTokenizer(document);
			while (tokenizer.hasMoreTokens())
			{
				updateFeature(tokenizer.nextToken(), classes, classificationClass);
			}
		}
	}

	public List<String> getTestDataForClass(ClassificationClass classificationClass)
	{
		List<String> documents = new ArrayList<String>();
		return documents;
	}

}
