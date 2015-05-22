package com.studlymen;

import java.util.Arrays;
import java.util.Scanner;

public class NGramBuffer 
{
	private int mCapacity;
	private int mSize;
	private int mRdWr;
	private String mBuffer[];
	
	/*
	0	1	2
	W
	a	W
	a	b	W
	aW	b	c
	c+W=0
		c+W=1
			c+W=2
	d	bW	c
		c+W=1
			c+W=2
	c+W=3%3=0
	d	e	cW
			c+W%3=2
	c+W%3=0
		c+W%3=1
	dW	e	f
	g	eW	f						
	*/
	
	public NGramBuffer(int capacity)
	{
		mCapacity = capacity;
		mBuffer = new String[mCapacity];
		mRdWr = 0;
		mSize = 0;
	}
	
	public void append(String word)
	{
		mBuffer[mRdWr] = word;
		mRdWr = (mRdWr + 1) % mCapacity;
		if(mSize < mCapacity)
			mSize++;
	}
	
	public String[] getBuffer()
	{
		if(mSize < mCapacity)
			return Arrays.copyOf(mBuffer, mSize);
	
		String buffer[] = new String[mCapacity];
		for(int i = 0; i < mCapacity; i++)
			buffer[i] = mBuffer[(i + mRdWr) % mCapacity];
		
		return buffer;
	}
	
	public int getCapacity()
	{
		return mCapacity;
	}
	
	public int getSize()
	{
		return mSize;
	}
	
	public String toString()
	{
		String output = "";
		for(String word : getBuffer())
			output += (word + " ");
		return output;
	}
	
	public static void main(String args[])
	{
		NGramBuffer buffer = new NGramBuffer(4);
		String text = "And it came to pass that I Nephi was born of goodly parents";
		Scanner s = new Scanner(text);
		while(s.hasNext())
		{
			buffer.append(s.next());
			System.out.println(buffer + " " + buffer.getSize());
		}
		s.close();
	}
}