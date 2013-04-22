package com.cnl.conversion.classification.pojo;

import java.util.List;

import com.cnl.conversion.data.Data;

public abstract class ClassificationMethod {
	
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
	
	protected abstract double getProbabilityForClassificationClass(ClassificationClass classificationClass, Data data, List<String> stems);

}
