package com.cnl.conversion.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import com.cnl.conversion.classification.pojo.ClassificationClass;
import com.cnl.conversion.classification.pojo.Feature;
import com.cnl.conversion.parsing.Stemmer;

/**
 * @author vlad
 * 
 */
public class SentimentData extends Data {

	private static final boolean STEMMING = false;

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
	
	public void trainFromClass(List<ClassificationClass> classes, ClassificationClass classificationClass, int startingNumber, int lastNumber)
	{
		String path = getPathForClass(classificationClass);
		File file = new File(path);
		String[] files = file.list();
		Arrays.sort(files);
		for (int i = startingNumber; i < lastNumber; i++)
		{
			if (STEMMING)
			{
				for (String stem : Stemmer.getStems(path + "/" + files[i]))
				{
					updateFeature(stem, classes, classificationClass);
					int count = getClassCount().get(classificationClass.getClassName());
					getClassCount().put(classificationClass.getClassName(), count+1);
				}
			}
			else
			{
				String document = readfile(path + "/" + files[i]);
				StringTokenizer tokenizer = new StringTokenizer(document);
				while (tokenizer.hasMoreTokens())
				{
					updateFeature(tokenizer.nextToken(), classes, classificationClass);
					int count = getClassCount().get(classificationClass.getClassName());
					getClassCount().put(classificationClass.getClassName(), count+1);
				}
			}

		}
	}

	private String getPathForClass(ClassificationClass classificationClass)
	{
		if (classificationClass.getClassName().equalsIgnoreCase(("positive")))
		{
			return "data/movie_review_data/pos";
		}
		else
		{
			return "data/movie_review_data/neg";
		}
	}
	
	public List<List<String>> getTestDataForClass(ClassificationClass classificationClass, int startingNumber, int lastNumber)
	{
		List<List<String>> documents = new ArrayList<List<String>>();

		String path = getPathForClass(classificationClass);
		File file = new File(path);
		String[] files = file.list();
		Arrays.sort(files);
		for (int i = startingNumber; i < lastNumber; i++)
		{
			if (STEMMING)
			{
				documents.add(Stemmer.getStems(path + "/" + files[i]));
			}
			else
			{
				 String document = readfile(path+"/"+files[i]);
				 StringTokenizer tokenizer = new StringTokenizer(document);
				 List<String> stems = new ArrayList<String>();
				 while (tokenizer.hasMoreTokens())
				 {
				 stems.add(tokenizer.nextToken());
				 }
				 documents.add(stems);
			}
		}

		return documents;
	}

}
