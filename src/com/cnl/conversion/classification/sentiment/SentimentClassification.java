package com.cnl.conversion.classification.sentiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cnl.conversion.classification.pojo.Classification;
import com.cnl.conversion.classification.pojo.ClassificationClass;
import com.cnl.conversion.classification.pojo.ClassificationMethod;
import com.cnl.conversion.classification.pojo.Feature;
import com.cnl.conversion.data.Data;

/**
 * @author vlad
 *
 */
public class SentimentClassification extends Classification{
	
	private List<ClassificationClass> classes;
	private ClassificationClass posClassificationClass;
	private ClassificationClass negClassificationClass;
	
	public SentimentClassification(Data data, ClassificationMethod classificationMethod)
	{
		super(data, classificationMethod);
		initialize();
		
	}
	
	private void initialize()
	{
		classes = new ArrayList<ClassificationClass>();
		posClassificationClass = new ClassificationClass("Positive", 0.5f);
		classes.add(posClassificationClass);
		negClassificationClass = new ClassificationClass("Negative", 0.5f);
		classes.add(negClassificationClass);
		data.setClassCount(new HashMap<String, Integer>());
		data.setVocabulary(new HashMap<String, Feature>());
		data.init(classes);
	}
	
	@Override
	public void trainAndTest()
	{
		double accuracy = 0;
		for (int i=0; i< 10; i++)
		{
			System.out.println("Testing data: " + i*100 + ", " + (i+1)*100);
			initialize();
			if (i>0)
			{
				train(0, (i-1)*100);
			}
			if (i<9)
			{
				train((i+1)*100, 1000);
			}
			accuracy += test(i*100, (i+1)*100);
		}
		System.out.println("Total Accuracy is: " + accuracy/10 + "%");
	}
	
	public void train(int startingPosition, int lastPosition)
	{
		data.trainFromClass(classes, posClassificationClass, startingPosition, lastPosition);
		data.trainFromClass(classes, negClassificationClass, startingPosition, lastPosition);
		//data.printVocabulary();
	}
	
	public double test(int startingPosition, int lastPosition)
	{
		int truePos = 0, trueNeg = 0, falsePos = 0, falseNeg = 0; 
		List<List<String>> posDocuments = data.getTestDataForClass(posClassificationClass, startingPosition, lastPosition);
		List<List<String>> negDocuments = data.getTestDataForClass(negClassificationClass, startingPosition, lastPosition);
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
		double accuracy = 0;
		if (falsePos+trueNeg > 0)
		{
			accuracy = (double)((truePos+falseNeg) * 100)/(posDocuments.size()+negDocuments.size());
			System.out.println("Accuracy: " + accuracy + "%");
		}
		return accuracy;
	}
	
	public ClassificationClass getClassForString(List<String> stems)
	{
		return classificationMethod.getClassificationClass(classes, data, stems);
	}

}
