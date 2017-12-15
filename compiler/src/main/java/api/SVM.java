package api;

//////////////////////////////////////////////////////////////
//
// Representation and interpretation of FunVM code.
//
// Developed June 2012 by David Watt (University of Glasgow).
//
//////////////////////////////////////////////////////////////

import java.io.*;
import java.util.*;

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

	public static final int         // offsets of IO routines
	   READOFFSET    = 32766,
	   WRITEOFFSET   = 32767,
	   IOBASE        = 32766;


	// MACHINE STATE

	protected int cl;          // code limit

	// CONSTRUCTOR

	public SVM () {
		cl = 0;
	}

	// CODE EMISSION

	public void emit1 (byte opcode) {
		// Add a 1 byte instruction to the code.
		cl += 1;
	}

	public void emit11 (byte opcode,
	                     int operand) {
		// Add a 1+1 byte instruction to the code.
		cl += 2;
	}

	public void emit12 (byte opcode,
	                    int operand) {
		// Add a 1+2 byte instruction to the code.
		cl += 3;
	}

	public void patch12 (int addr, int operand) {
		// Patch an operand into a 1+2 byte instruction.
	}

	public int currentOffset () {
		// Return the offset of the next instruction to be added.
		return cl;
	}

}
