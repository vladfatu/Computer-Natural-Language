package com.cnl.conversion.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import com.cnl.conversion.wsd.pojo.Word;
import com.cnl.conversion.wsd.pojo.WsdContext;

import edu.mit.jwi.item.POS;

public class WsdData {
	
	private List<WsdContext> contexts;
	
	public WsdData()
	{
		contexts = new ArrayList<WsdContext>();
		System.out.println("Parsing files..");
		readDataForTargetWord("line");
		System.out.println("Finished parsing files");
	}
	
	private void readDataForTargetWord(String targetWord)
	{
		File file = new File(getPathForTargetWord(targetWord));
		try
		{
			Document doc = Jsoup.parse(file, null);
			Node tempNode = doc.childNode(0).childNode(1).childNode(0).childNode(1);
			for (Node node : tempNode.childNodes())
			{
				if (node.nodeName().equals("instance"))
				{
					Node senseNode = node.childNode(1);
					WsdContext wsdContext = new WsdContext();
					wsdContext.setSenseId(senseNode.attr("senseId"));
					Node contextNode = node.childNode(3);
					for (int i=0; i<contextNode.childNodeSize();i=i+2)
					{
						if (contextNode.childNode(i).toString().equals(" "))
						{
							i++;
							wsdContext.getWords().add(null);
						}
						else
						{
							Word word = new Word(contextNode.childNode(i).toString().trim(), POS.NOUN);
							wsdContext.getWords().add(word);
						}
					}
					contexts.add(wsdContext);
				}
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getPathForTargetWord(String targetWord)
	{
		String path = "data/wsd/"+targetWord+".pos";
		return path;
	}

	public List<WsdContext> getContexts()
	{
		return contexts;
	}

	public void setContexts(List<WsdContext> contexts)
	{
		this.contexts = contexts;
	}

}
