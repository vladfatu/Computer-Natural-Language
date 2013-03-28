package com.cnl.conversion.classification.pojo;

import com.cnl.conversion.data.Data;

public abstract class Classification {
	
	protected Data data;
	protected ClassificationMethod classificationMethod;
	
	public Classification(Data data, ClassificationMethod classificationMethod)
	{
		this.classificationMethod = classificationMethod;
		this.data = data;
	}

	public abstract void trainAndTest();
	
}
