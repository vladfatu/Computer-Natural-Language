package com.cnl.conversion.wsd.pojo;

import java.util.List;

import edu.mit.jwi.item.IWordID;

/**
 * @author vlad
 *
 */
public abstract class WsdMethod {
	
	public abstract IWordID getBestWordId(Word targetWord, List<Word> featureWords);

}
