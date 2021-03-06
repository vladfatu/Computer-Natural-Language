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
	
	public double getProbabilityForClassificationClass(ClassificationClass classificationClass, Data data)
	{
		return ((double)(frequencies.get(classificationClass.getClassName())+1))/(data.getClassCount().get(classificationClass.getClassName()) + data.getVocabulary().size());
	}
	
	public void addFrequencyForClass(ClassificationClass classificationClass, int count)
	{
		int frequency = frequencies.get(classificationClass.getClassName());
		frequencies.put(classificationClass.getClassName(), frequency + count);
	}
	
	public String print()
	{
		StringBuilder string = new StringBuilder();
		for (Map.Entry<String,Integer> frequency : frequencies.entrySet())
		{
			string.append(frequency.getKey()).append(" : ").append(frequency.getValue()).append("  ");
		}
		return string.toString();
	}

}
