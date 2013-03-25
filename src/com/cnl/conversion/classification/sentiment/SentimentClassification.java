package com.cnl.conversion.classification.sentiment;

import java.util.ArrayList;
import java.util.List;

import com.cnl.conversion.classification.NaiveBayes.NaiveBayes;
import com.cnl.conversion.classification.pojo.ClassificationClass;
import com.cnl.conversion.data.Data;

public class SentimentClassification {
	
	private List<ClassificationClass> classes;
	private ClassificationClass posClassificationClass;
	private ClassificationClass negClassificationClass;
	private Data data;
	
	public SentimentClassification(Data data)
	{
		classes = new ArrayList<ClassificationClass>();
		posClassificationClass = new ClassificationClass("Positive", 0.5f);
		classes.add(posClassificationClass);
		negClassificationClass = new ClassificationClass("Negative", 0.5f);
		classes.add(negClassificationClass);
		
		this.data = data;
		
	}
	
	public void train()
	{
		data.trainFromClass(classes, posClassificationClass);
		data.trainFromClass(classes, negClassificationClass);
		data.printVocabulary();
	}
	
	public void test()
	{
		int truePos = 0, trueNeg = 0, falsePos = 0, falseNeg = 0; 
		List<String> posDocuments = data.getTestDataForClass(posClassificationClass);
		List<String> negDocuments = data.getTestDataForClass(negClassificationClass);
		for (String document : posDocuments)
		{
			if (getClassForString(document) == posClassificationClass)
			{
				truePos++;
			}
			else
			{
				trueNeg++;
			}
		}
		for (String document : negDocuments)
		{
			if (getClassForString(document) == posClassificationClass)
			{
				falsePos++;
			}
			else
			{
				falseNeg++;
			}
		}
		if (falsePos+trueNeg > 0)
		{
			System.out.println("Accuracy: " + (truePos+falseNeg)/(falsePos+trueNeg));
		}
	}
	
	public ClassificationClass getClassForString(String document)
	{
		NaiveBayes naiveBayes = new NaiveBayes();
		return naiveBayes.getClassificationClass(classes, data);
	}

}
