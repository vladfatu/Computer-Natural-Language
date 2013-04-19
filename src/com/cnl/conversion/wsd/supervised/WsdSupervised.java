package com.cnl.conversion.wsd.supervised;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.cnl.constants.Constants;
import com.cnl.conversion.wsd.pojo.Word;
import com.cnl.conversion.wsd.pojo.WsdMethod;
import com.cnl.utils.StringUtils;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;

/**
 * @author vlad
 *
 */
public class WsdSupervised extends WsdMethod{
	
	@Override
	public IWordID getBestWordId(Word targetWord, List<Word> featureWords)
	{
		int maxScore = 0;
		IWordID bestWordID = null;
		
		try
		{
			URL url = new URL("file", null, Constants.WORDNET_PATH);
			
			// construct the dictionary object and open it
			IRAMDictionary dict = new RAMDictionary(url, ILoadPolicy.NO_LOAD);
			dict.open();
//			System.out.println ("Loading Wordnet into memory ... ") ;
//			long t = System.currentTimeMillis();
//			dict.load(true) ;
//			System.out.printf("done (%1d msec )" , System.currentTimeMillis()-t ) ;


			IIndexWord idxWord = dict.getIndexWord(targetWord.getWord(), targetWord.getWordType());
			
			for (IWordID wordID : idxWord.getWordIDs())
			{
				int score = 0;
				for (Word featureWord : featureWords)
				{
					IIndexWord idxFeatureWord = dict.getIndexWord(featureWord.getWord(), featureWord.getWordType());
					if (idxFeatureWord != null)
					{
						for (IWordID featureWordID : idxFeatureWord.getWordIDs())
						{
							score += getLinkedScore(dict, dict.getWord(wordID), dict.getWord(featureWordID));
						}
					}
				}
				if (score > maxScore)
				{
					bestWordID = wordID;
					maxScore = score;
				}
			}
			
			System.out.println("Best sense is : " + dict.getWord(bestWordID).getSynset().getGloss());
			
			dict.close();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return bestWordID;
	}
	
	private int getLinkedScore(IDictionary dict, IWord targetWord, IWord featureWord)
	{
		StringBuilder targetCombinedGloss = new StringBuilder(targetWord.getSynset().getGloss());
		for (ISynsetID synetID : targetWord.getSynset().getRelatedSynsets())
		{
			targetCombinedGloss.append(" ").append(dict.getSynset(synetID).getGloss());
		}
		
		StringBuilder featureCombinedGloss = new StringBuilder(featureWord.getSynset().getGloss());
		for (ISynsetID synetID : featureWord.getSynset().getRelatedSynsets())
		{
			featureCombinedGloss.append(" ").append(dict.getSynset(synetID).getGloss());
		}
		
		return getGlossScore(targetCombinedGloss.toString(), featureCombinedGloss.toString());
	}
	
	protected int getGlossScore(String text1, String text2) {
		//System.out.println("Text1 is : " + text1);
		//System.out.println("Text2 is : " + text2);
		text1 = StringUtils.removeAllNonAlphaNumeric(text1);
		text2 = StringUtils.removeAllNonAlphaNumeric(text2);
		return getWordsScore(text1.split(" "), text2.split(" "));
	}
	
	private int getWordsScore(String[] S1, String[] S2)
	{
		int score = 0;
		boolean found = true;
		while (found)
		{
			found = false;
			int start1 = 0;
			int start2 = 0;
			int max = 0;
			for (int i = 0; i < S1.length; i++)
			{
				if (S1[i] != null)
				{
					for (int j = 0; j < S2.length; j++)
					{
						if (S2[j] != null)
						{
							int x = 0;
							while (S1[i + x] != null && S1[i + x].equals(S2[j + x]))
							{
								found = true;
								x++;
								if (((i + x) >= S1.length)
										|| ((j + x) >= S2.length))
									break;
							}
							if (x > max)
							{
								max = x;
								start1 = i;
								start2 = j;
							}
						}
					}
				}
			}
			for (int i = start1; i < start1 + max; i++)
			{
				S1[i] = null;
			}
			for (int i = start2; i < start2 + max; i++)
			{
				S2[i] = null;
			}
			score += (max * max);
		}
		return score;
	}

}
