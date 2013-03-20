package com.cnl.conversion.classification;

public class ClassificationClass {
	
	private float classProbability;
	
	public ClassificationClass(float classProbability)
	{
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

}
