import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.io.*;
public class Tester extends FractalHash {

	private final String WORDS_FILE_NAME = "words.txt";

	private String[] stringArray = null;
	public void longestLookup() {
		NumberFormat formatter = new DecimalFormat("#0.0000");
		double totalTime = 0;
		int longestTimeIndex = -1;
		long startTime = 0;
		long endTime = 0;
		long timeTaken = 0;
		long longestTime = 0;

		for(int i = 0; i < numberOfNodes(); i++) {
			startTime = System.nanoTime();
			find(stringArray[i]);
			endTime = System.nanoTime();
			timeTaken = endTime - startTime;
			totalTime += (double)timeTaken/1000000;
			if(timeTaken > longestTime){
				longestTime = timeTaken;
				longestTimeIndex = i;
			}
		}
		System.out.println("Longest Lookup: "+stringArray[longestTimeIndex]+" "+formatter.format((double)longestTime/1000000)+" Miliseconds");
		System.out.println("Total Lookup Time: "+formatter.format(totalTime)+" Miliseconds");
		
	}
	public void insertFromFile(int lines) {
		try (BufferedReader reader = new BufferedReader(new FileReader(WORDS_FILE_NAME))) {
		    String line;
		    int i = 0;
		    stringArray = new String[lines];
		    while ((line = reader.readLine()) != null && i < lines) {
		       	stringArray[i] = line;
		       	insert(line);
		       	i++;
		    }
		} catch(IOException e) { e.printStackTrace(); }
	}
	public void populateArrayOfWordsFromFile(int lines) {
		try (BufferedReader reader = new BufferedReader(new FileReader(WORDS_FILE_NAME))) {
		    String line;
		    int i = 0;
		    stringArray = new String[lines];
		    while ((line = reader.readLine()) != null && i < lines) {
		       	stringArray[i] = line;
		       	i++;
		    }
		} catch(IOException e) { e.printStackTrace(); }
	}
	public void generateSpaceEfficiencyResults(int maximum, int granularity){
		if(granularity > maximum) { System.out.println("paramater 2 cannot be larger than the paramater 1"); return; }

		populateArrayOfWordsFromFile(maximum);
		for(int i = 0; i<maximum/granularity; i++) {
			for(int e = i*granularity; e < ((i+1)*granularity); e++) {
				insert(stringArray[e]);
			}

			try (BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt", true))) {
			    writer.write("="+numberOfNodes()+"/"+getNumberOfSlots()+"*100"+"\n");
			} catch(IOException e) { e.printStackTrace(); }
		}
	}
}