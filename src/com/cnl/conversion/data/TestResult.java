package com.cnl.conversion.data;

/**
 * @author vlad
 *
 */
public class TestResult {
	
	private int truePos;
	private int trueNeg;
	private int falsePos;
	private int falseNeg;
	
	public TestResult(int truePos, int trueNeg, int falsePos, int falseNeg)
	{
		this.truePos = truePos;
		this.trueNeg = trueNeg;
		this.falsePos = falsePos;
		this.falseNeg = falseNeg;
	}
	
	public double getAccuracy()
	{
		return (double)(truePos+falseNeg)/(trueNeg+falsePos);
	}

}
