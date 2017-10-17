/**
 * 
 */
package com.mycompany.test.automation_framework.testCases;
import java.util.HashMap;

/**
 * @author Sarath
 * Email Sarath.qaengineer@gmail.com
 * The following program prints Vowels and count of its occurrences from a String Using a hashmap
 */
public class collectVowels {

	public static void printVowels(String s)
	{
		HashMap<Character,Integer> map = new HashMap<Character,Integer>();
		char[] vowels = {'a','e','i','o','u','A','E','I','O','U'};
		char ch;
		boolean found=Boolean.FALSE;
		for(int i=0;i<s.length();i++)
		{
			ch = s.charAt(i);
			for(int j=0;j<vowels.length;j++)
			{
				if(vowels[j]==ch)
				{	
					found=Boolean.TRUE;
					if(map.containsKey(ch))
					{
						map.put(ch,map.get(ch)+1);
					} 
					else
					{
						map.put(ch,1);
					}
				}
			}
		}
		if(found)
		System.out.println(map);
		else
			System.out.println("No Vowels Found !");
	}
	
	public static void main(String s[])
	{	
		String mystring = "California";
		printVowels(mystring);
	}
}
