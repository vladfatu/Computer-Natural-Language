package com.cnl.conversion.wsd.supervised;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnl.constants.Constants;
import com.cnl.conversion.wsd.pojo.Word;
import com.cnl.conversion.wsd.pojo.WsdContext;
import com.cnl.conversion.wsd.pojo.WsdMethod;
import com.cnl.utils.StringUtils;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.Pointer;
import edu.mit.jwi.item.SynsetID;
import edu.mit.jwi.morph.WordnetStemmer;

/**
 * @author vlad
 * 
 */
public class WsdSupervised extends WsdMethod {
	
	private Map<String, String> senseMap;
	
	public void test(Word targetWord, List<WsdContext> wsdContexts)
	{
		senseMap = new HashMap<String, String>();
		senseMap.put("cord", "WID-03670849-N-??-line");
		senseMap.put("division", "WID-05748786-N-??-line");
		senseMap.put("phone", "WID-04402057-N-??-line");
		senseMap.put("product", "WID-03671668-N-??-line");
		senseMap.put("text", "WID-07012534-N-??-line");
		senseMap.put("formation", "WID-08430568-N-??-line");
		
		try
		{
			URL url = new URL("file", null, Constants.WORDNET_PATH);
			// construct the dictionary object and open it
			IRAMDictionary dict = new RAMDictionary(url, ILoadPolicy.NO_LOAD);
			dict.open();

			WordnetStemmer stemmer = new WordnetStemmer(dict);
			// System.out.println ("Loading Wordnet into memory ... ") ;
			// long t = System.currentTimeMillis();
			// dict.load(true) ;
			// System.out.printf("done (%1d msec )" ,
			// System.currentTimeMillis()-t ) ;

			long t = System.currentTimeMillis();
			int goodWordSense = 0;
			int numberOfContextsToRead = 4000;

			for (int i = 0; i < numberOfContextsToRead; i++)
			{
				System.out.println(i + " sense: " + wsdContexts.get(i).getSenseId());
				IWordID bestWordID = getBestWordId(dict, stemmer, targetWord, wsdContexts.get(i));
				if (bestWordID != null)
				{
					if (bestWordID.toString().equals(senseMap.get(wsdContexts.get(i).getSenseId())))
					{
						System.out.println("Should be " +wsdContexts.get(i).getSenseId() + " true");
						goodWordSense++;
					}
					else
					{
						System.out.println("Should be " +wsdContexts.get(i).getSenseId() + " false");
					}
				}
			}
			
			System.out.println("Accuracy: " + (((double)goodWordSense/numberOfContextsToRead)*100) + "%");

			System.out.printf("Finished in (%1d msec )",
					System.currentTimeMillis() - t);

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

	}

	private List<Word> getFeatureWords(IDictionary dict,
			WordnetStemmer stemmer, WsdContext wsdContext)
	{
		int CONTEXT_SIZE = 3;
		List<Word> featureWords = new ArrayList<Word>();
		int index = wsdContext.getWords().indexOf(null);

		int i = index + 1;
		while (i < wsdContext.getWords().size()
				&& featureWords.size() < CONTEXT_SIZE)
		{
			Word word = wsdContext.getWords().get(i);
			if (word != null)
			{
				List<String> stems = stemmer.findStems(word.getWord(),
						word.getWordType());
				if (stems.size() > 0)
				{
					word.setWord(stems.get(0));
					featureWords.add(word);
				}
			}
			i++;
		}

		i = index - 1;
		while (i >= 0 && featureWords.size() < CONTEXT_SIZE * 2)
		{
			Word word = wsdContext.getWords().get(i);
			if (word != null)
			{
				List<String> stems = stemmer.findStems(word.getWord(),
						word.getWordType());
				if (stems.size() > 0)
				{
					word.setWord(stems.get(0));
					featureWords.add(word);
				}
			}
			i--;
		}
		
		System.out.print("Feature words are :");
		for (Word word : featureWords)
		{
			System.out.print(" " + word.getWord());
		}
		System.out.println();

		return featureWords;
	}

	@Override
	public IWordID getBestWordId(IDictionary dict, WordnetStemmer stemmer,
			Word targetWord, WsdContext wsdContext)
	{
		int maxScore = 0;
		IWordID bestWordID = null;

		List<Word> featureWords = getFeatureWords(dict, stemmer, wsdContext);

		IIndexWord idxWord = dict.getIndexWord(targetWord.getWord(),
				targetWord.getWordType());

		for (IWordID wordID : idxWord.getWordIDs())
		{
			int score = 0;
			for (Word featureWord : featureWords)
			{
				IIndexWord idxFeatureWord = dict.getIndexWord(
						featureWord.getWord(), featureWord.getWordType());
				if (idxFeatureWord != null)
				{
					for (IWordID featureWordID : idxFeatureWord.getWordIDs())
					{
						score += getLinkedScore(dict, dict.getWord(wordID),
								dict.getWord(featureWordID));
					}
				}
			}
			if (score > maxScore)
			{
				bestWordID = wordID;
				maxScore = score;
			}
		}

		if (bestWordID != null)
		{
			System.out.println("Best sense is : "
					+ dict.getWord(bestWordID).getSynset().getGloss());
		}

		return bestWordID;
	}

	private int getLinkedScore(IDictionary dict, IWord targetWord,
			IWord featureWord)
	{
		StringBuilder targetCombinedGloss = new StringBuilder(targetWord
				.getSynset().getGloss());
		List<ISynsetID> synsets = new ArrayList<ISynsetID>();
		synsets.addAll(targetWord.getSynset().getRelatedSynsets(Pointer.MERONYM_PART));
		synsets.addAll(targetWord.getSynset().getRelatedSynsets(Pointer.HYPERNYM));
		for (ISynsetID synetID : synsets)
		{
			targetCombinedGloss.append(" ").append(
					dict.getSynset(synetID).getGloss());
		}

		StringBuilder featureCombinedGloss = new StringBuilder(featureWord
				.getSynset().getGloss());
		synsets = new ArrayList<ISynsetID>();
		synsets.addAll(featureWord.getSynset().getRelatedSynsets(Pointer.MERONYM_PART));
		synsets.addAll(featureWord.getSynset().getRelatedSynsets(Pointer.HYPERNYM));
		for (ISynsetID synetID : synsets)
		{
			featureCombinedGloss.append(" ").append(
					dict.getSynset(synetID).getGloss());
		}

		return getGlossScore(targetCombinedGloss.toString(),
				featureCombinedGloss.toString());
	}

	protected int getGlossScore(String text1, String text2)
	{
		// System.out.println("Text1 is : " + text1);
		// System.out.println("Text2 is : " + text2);
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
							while (S1[i + x] != null
									&& S1[i + x].equals(S2[j + x]))
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
