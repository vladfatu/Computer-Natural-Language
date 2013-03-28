package com.cnl.conversion.classification.pojo;

import java.util.List;

import com.cnl.conversion.data.Data;

public interface ClassificationMethod {
	
	public ClassificationClass getClassificationClass(List<ClassificationClass> classes, Data data, List<String> stems);

}
