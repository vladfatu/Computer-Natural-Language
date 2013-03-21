package com.cnl.conversion.classification.pojo;

/**
 * @author vlad
 *
 */
public class ClassificationClass {
	
	private float classProbability;
	private String className;
	
	public ClassificationClass(String className, float classProbability)
	{
		this.className = className;
		this.classProbability = classProbability;
	}

	public float getClassProbability()
	{
		return classProbability;
	}

	public void setClassProbability(float classProbability)
	{
		this.classProbability = classProbability;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
