package api;

public class SVM {

	// code limit
	private static int cl = 0;

	// Add a 1 byte instruction to the code.
	public static void emit1 () {
		cl += 1;
	}

	// Add a 1+1 byte instruction to the code.
	public static void emit11 () {
		cl += 2;
	}

	// Add a 1+2 byte instruction to the code.
	public static void emit12 () {
		cl += 3;
	}

	// Return the offset of the next instruction to be added.
	public static int currentOffset () {
		return cl;
	}

}
