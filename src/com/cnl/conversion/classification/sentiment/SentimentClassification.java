package com.cnl.conversion.classification.sentiment;

import java.util.ArrayList;
import java.util.List;

import com.cnl.conversion.classification.pojo.ClassificationClass;

public class SentimentClassification {
	
	public static void main(String[] args)
	{
		List<ClassificationClass> classes = new ArrayList<ClassificationClass>();
		ClassificationClass classificationClass = new ClassificationClass("Positive", 0.5f);
		classes.add(classificationClass);
		classificationClass = new ClassificationClass("Negative", 0.5f);
		classes.add(classificationClass);
	}

}
