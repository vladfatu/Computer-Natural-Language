package com.cnl.conversion.classification.NaiveBayes;

import java.util.List;
import java.util.StringTokenizer;

import com.cnl.conversion.classification.pojo.ClassificationClass;
import com.cnl.conversion.classification.pojo.Feature;
import com.cnl.conversion.data.Data;

/**
 * @author vlad
 * 
 */
public class NaiveBayes {

	public ClassificationClass getClassificationClass(List<ClassificationClass> classes, Data data, String document)
	{
		if (classes.size() > 0)
		{
			double max = 2;
			ClassificationClass bestClassificationClass = classes.get(0);
			for (ClassificationClass classificationClass : classes)
			{
				double probability = getProbabilityForClassificationClass(classificationClass, data, document);
				if (probability > max || max == 2)
				{
					max = probability;
					bestClassificationClass = classificationClass;
				}
			}
			return bestClassificationClass;
		}
		return null;
	}

	private double getProbabilityForClassificationClass(ClassificationClass classificationClass, Data data, String document)
	{
		if (data.getVocabulary().size() > 1)
		{
			double classProbability = classificationClass.getClassProbability();
			StringTokenizer tokenizer = new StringTokenizer(document);
			while (tokenizer.hasMoreTokens())
			{
				Feature feature = data.getVocabulary().get(tokenizer.nextToken());
				if (feature != null)
				{
					classProbability = classProbability + Math.log(feature.getProbabilityForClassificationClass(classificationClass, data));
				}
			}
			return classProbability;
		}
		else
		{
			return 0;
		}
	}

}
