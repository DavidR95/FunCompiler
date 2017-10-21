//////////////////////////////////////////////////////////////
//
// Representation of FunVM code, global, and local addresses.
//
// Developed June 2012 by David Watt (University of Glasgow).
//
//////////////////////////////////////////////////////////////

public class Address {

	public static final int
	   CODE   = 0,
	   GLOBAL = 1,
	   LOCAL  = 2;

	public int offset;
	public int locale;  // CODE, GLOBAL, or LOCAL

	public Address (int off, int loc) {
		offset = off;  locale = loc;
	}

}
