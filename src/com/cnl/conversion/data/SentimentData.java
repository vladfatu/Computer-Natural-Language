package com.cnl.conversion.data;

import java.util.ArrayList;
import java.util.List;

import com.cnl.conversion.classification.pojo.ClassificationClass;

/**
 * @author vlad
 *
 */
public class SentimentData extends Data{
	
	public void trainFroClass(ClassificationClass classificationClass)
	{
		
	}
	
	public List<String> getTestDataForClass(ClassificationClass classificationClass)
	{
		List<String> documents = new ArrayList<String>();
		return documents;
	}

}
