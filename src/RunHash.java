import java.io.*;

public class RunHash {
	public static void main(String[] args) {

		Tester user01 = new Tester();

		// user01.generateSpaceEfficiencyResults(10000, 100);

		user01.insertFromFile(10000);
		// user01.numberOfSlots();
		user01.longestLookup();
	}
}