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
		List<List<String>> posDocuments = data.getTestDataForClass(posClassificationClass);
		List<List<String>> negDocuments = data.getTestDataForClass(negClassificationClass);
		for (List<String> document : posDocuments)
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
		for (List<String> document : negDocuments)
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
		System.out.println("True Positive: " + truePos);
		System.out.println("False Negative: " + falseNeg);
		if (falsePos+trueNeg > 0)
		{
			System.out.println("Accuracy: " + (double)(truePos+falseNeg)/(posDocuments.size()+negDocuments.size()));
		}
	}
	
	public ClassificationClass getClassForString(List<String> stems)
	{
		NaiveBayes naiveBayes = new NaiveBayes();
		return naiveBayes.getClassificationClass(classes, data, stems);
	}

}
