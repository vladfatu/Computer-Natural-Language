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

	public ClassificationClass getClassificationClass(List<ClassificationClass> classes, Data data)
	{
		if (classes.size() > 0)
		{
			float max = 0;
			ClassificationClass bestClassificationClass = classes.get(0);
			for (ClassificationClass classificationClass : classes)
			{
				float probability = getProbabilityForClassificationClass(classificationClass, data);
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

	private float getProbabilityForClassificationClass(ClassificationClass classificationClass, Data data)
	{
		if (data.getVocabulary().size() > 1)
		{
			float classProbability = classificationClass.getClassProbability();
			for (Feature feature : data.getVocabulary().values())
			{
				classProbability *= feature.getProbabilityForClassificationClass(classificationClass, data);
			}
			return classProbability;
		}
		else
		{
			return 0;
		}
	}

}
