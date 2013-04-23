package com.cnl.conversion.wsd.pojo;

import java.util.List;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.morph.WordnetStemmer;

/**
 * @author vlad
 *
 */
public abstract class WsdMethod {
	
	public abstract void test(Word targetWord, List<WsdContext> wsdContexts);
	
	public abstract IWordID getBestWordId(IDictionary dict, WordnetStemmer stemmer, Word targetWord,
			WsdContext wsdContext);

}
