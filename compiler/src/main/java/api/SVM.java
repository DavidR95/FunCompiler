package api;

import com.google.gson.JsonArray;

public class SVM {

	public static final byte        // opcodes
	   LOADG   =  0,  STOREG  =  1,
	   LOADL   =  2,  STOREL  =  3,
	   LOADC   =  4,
	   ADD     =  6,  SUB     =  7,
	   MUL     =  8,  DIV     =  9,
	   CMPEQ   = 10,
	   CMPLT   = 12,  CMPGT   = 13,
	   INV     = 14,  INC     = 15,
	   HALT    = 16,  JUMP    = 17,
	   JUMPF   = 18,  JUMPT   = 19,
	   CALL    = 20,  RETURN  = 21,
	   COPYARG = 22;

	private static final String[] mnemonic = {
	   "LOADG",    "STOREG",
	   "LOADL",    "STOREL",
	   "LOADC",    "???",
	   "ADD",    "SUB",
	   "MUL",    "DIV",
	   "CMPEQ",    "???",
	   "CMPLT",    "CMPGT",
	   "INV",    "INC",
	   "HALT",    "JUMP",
	   "JUMPF",    "JUMPT",
	   "CALL",    "RETURN",
	   "COPYARG"                };

	private static final int[] bytes = {
	   3,             3,
	   3,             3,
	   3,             1,
	   1,             1,
	   1,             1,
	   1,             1,
	   1,             1,
	   1,             1,
	   1,             3,
	   3,             3,
	   3,             2,
	   2                };

	public static final int         // offsets of IO routines
	   READOFFSET    = 32766,
	   WRITEOFFSET   = 32767;

	// MACHINE STATE

	protected byte[] code;     // code store
	protected int cl;          // code limit

	// CONSTRUCTOR

	public SVM () {
		code = new byte[32768];
		cl = 0;
	}

	// CODE DISPLAY

	public JsonArray showCode() {
		JsonArray assembly = new JsonArray();
		for (int c = 0; c < cl;) {
			assembly.add(showInstruction(c));
			c += bytes[code[c]];
		}
        return assembly;
	}

	private String showInstruction (int c) {
		byte opcode = code[c++];
		String line =
		   String.format("%d: %s ", c-1, mnemonic[opcode]);
		switch (bytes[opcode]) {
			case 1:
				break;
			case 2: {
				byte operand = code[c++];
				line += operand;
				break;
			}
			case 3: {
				int operand =
				   code[c++]<<8 | (code[c++]&0xFF);
				line += operand;
				break;
			}
		}
		return line;
	}

	// CODE EMISSION

	public void emit1 (byte opcode) {
	// Add a 1 byte instruction to the code.
		code[cl++] = opcode;
	}

	public void emit11 (byte opcode,
	                     int operand) {
	// Add a 1+1 byte instruction to the code.
		code[cl++] = opcode;
		code[cl++] = (byte) operand;
	}

	public void emit12 (byte opcode,
	                    int operand) {
	// Add a 1+2 byte instruction to the code.
		code[cl++] = opcode;
		code[cl++] = (byte) (operand >> 8);
		code[cl++] = (byte) (operand & 0xFF);
	}

	public void patch12 (int addr, int operand) {
	// Patch an operand into a 1+2 byte instruction.
		code[addr+1] = (byte) (operand >> 8);
		code[addr+2] = (byte) (operand & 0xFF);
	}

	public int currentOffset () {
	// Return the offset of the next instruction to be added.
		return cl;
	}

}
