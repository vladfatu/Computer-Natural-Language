package com.cnl.conversion.classification.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnl.conversion.data.Data;

/**
 * @author vlad
 *
 */
public class Feature {
	
	private Map<String, Integer> frequencies;
	
	public Feature(List<ClassificationClass> classes)
	{
		frequencies = new HashMap<String, Integer>();
		for (ClassificationClass classificationClass : classes)
		{
			frequencies.put(classificationClass.getClassName(), 0);
		}
	}
	
	public float getProbabilityForClassificationClass(ClassificationClass classificationClass, Data data)
	{
		return (frequencies.get(classificationClass.getClassName())+1)/(data.getWordCounts() + data.getVocabulary().size());
	}
	
	public void addFrequencyForClass(ClassificationClass classificationClass, int count)
	{
		int frequency = frequencies.get(classificationClass.getClassName());
		frequencies.put(classificationClass.getClassName(), frequency + count);
	}

}
