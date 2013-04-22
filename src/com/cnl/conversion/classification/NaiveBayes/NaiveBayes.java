package com.cnl.conversion.classification.NaiveBayes;

import java.util.List;

import com.cnl.conversion.classification.pojo.ClassificationClass;
import com.cnl.conversion.classification.pojo.ClassificationMethod;
import com.cnl.conversion.classification.pojo.Feature;
import com.cnl.conversion.data.Data;

/**
 * @author vlad
 * 
 */
public class NaiveBayes extends ClassificationMethod{

	@Override
	protected double getProbabilityForClassificationClass(ClassificationClass classificationClass, Data data, List<String> stems)
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
