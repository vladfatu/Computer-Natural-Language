package com.cnl.conversion.classification.NaiveBayes;

import java.util.List;

import com.cnl.conversion.classification.ClassificationClass;
import com.cnl.conversion.classification.Feature;

/**
 * @author vlad
 * 
 */
public class NaiveBayes {

	public ClassificationClass getClassificationClass(List<ClassificationClass> classes, List<Feature> features)
	{
		if (classes.size() > 0)
		{
			float max = 0;
			ClassificationClass bestClassificationClass = classes.get(0);
			for (ClassificationClass classificationClass : classes)
			{
				float probability = getProbabilityForClassificationClass(classificationClass, features);
				if (probability > max)
				{
					max = probability;
					bestClassificationClass = classificationClass;
				}
			}
			return bestClassificationClass;
		}
		return null;
	}

	private float getProbabilityForClassificationClass(ClassificationClass classificationClass, List<Feature> features)
	{
		if (features.size() > 1)
		{
			float classProbability = classificationClass.getClassProbability();
			for (Feature feature : features)
			{
				classProbability *= feature.getProbabilityForClassificationClass(classificationClass);
			}
			return classProbability;
		}
		else
		{
			return 0;
		}
	}

}
