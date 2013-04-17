package com.cnl.conversion.wsd.supervised;

import com.cnl.conversion.wsd.pojo.WsdMethod;
import com.cnl.utils.StringUtils;

public class WsdSupervised extends WsdMethod{

	@Override
	protected int getScore(String text1, String text2) {
		System.out.println("Text1 is : " + text1);
		System.out.println("Text2 is : " + text2);
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
							while (S1[i + x].equals(S2[j + x]))
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
