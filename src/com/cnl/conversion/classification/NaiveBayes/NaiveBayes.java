package com.cnl.conversion.classification.NaiveBayes;

import java.util.List;

import com.cnl.conversion.classification.pojo.ClassificationClass;
import com.cnl.conversion.classification.pojo.Feature;
import com.cnl.conversion.data.Data;

/**
 * @author vlad
 * 
 */
public class NaiveBayes {

	public ClassificationClass getClassificationClass(List<ClassificationClass> classes, Data data, List<String> stems)
	{
		if (classes.size() > 0)
		{
			double max = 2;
			ClassificationClass bestClassificationClass = classes.get(0);
			for (ClassificationClass classificationClass : classes)
			{
				double probability = getProbabilityForClassificationClass(classificationClass, data, stems);
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

	private double getProbabilityForClassificationClass(ClassificationClass classificationClass, Data data, List<String> stems)
	{
		if (data.getVocabulary().size() > 1)
		{
			double classProbability = classificationClass.getClassProbability();
			for (String stem : stems)
			{
				Feature feature = data.getVocabulary().get(stem);
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
