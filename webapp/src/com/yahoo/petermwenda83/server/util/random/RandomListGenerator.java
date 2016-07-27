/**
 * 
 */
package com.yahoo.petermwenda83.server.util.random;

import java.io.File;

import java.io.IOException;
import org.apache.commons.io.FileUtils;

import org.apache.commons.math3.random.RandomDataGenerator;
/**
 * 
 * @author peter
 *
 */
public class RandomListGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Have started list generator.");
		File outFile = new File("/home/peter/Documents/radom/randomList.txt");

		RandomDataGenerator generator = new RandomDataGenerator();
		
		int listSize = 503;	// The size of the array / list to be generated
		
		// The Strings to be randomized
		String[] strings = {"0DE968C9-7309-C481-58F7-AB6CDB1011EF", "5C1D9939-136A-55DE-FD0E-61D8204E17C9",
				"B936DA83-8A45-E9F0-2EAE-D75F5C232E78"};
				
		try {
		 	
			for(int j=0; j<listSize; j++) {
				FileUtils.write(outFile,						 
						strings[generator.nextInt(0, strings.length - 1)] 
						+ "\n",					
						true); // Append to file                              
			}
			
		} catch(IOException e) {
			System.err.println("IOException in main.");
			e.printStackTrace();
		}			
		
		System.out.println("Have finished list generator.");
	}

}
