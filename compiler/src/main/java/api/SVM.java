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

	// Each SVM object is a simple virtual machine.
	// This comprises a code store, a data store, and
	// registers pc (program counter), sp (stack pointer),
	// fp (frame pointer), and status (initially RUNNING).

	// The data store contains a stack of words. Register sp
	// points to the first free word above the stack top.

	// The code store contains byte-codes.
	// Each instruction occupies 1-3 bytes, in which the
	// first byte contains the opcode.
	// Register pc points to the first byte of the next
	// instruction to be executed.
	// The instruction set is as follows:
	//
	// Opcode Bytes Mnemonic	Behaviour
	//    0    1+2  LOADG d    w <- word at global address d;
	//                         push w.
	//    1    1+2  STOREG d   pop w;
	//                         word at global address d <- w.
	//    2    1+2  LOADL d    w <- word at local address d;
	//                         push w.
	//    3    1+2  STOREL d   pop w;
	//                         word at local address d <- w.
	//    4    1+2  LOADC w    push w.
	//    6    1    ADD        pop w2; pop w1; push (w1+w2).
	//    7    1    SUB        pop w2; pop w1; push (w1-w2).
	//    8    1    MUL        pop w2; pop w1; push (w1*w2).
	//    9    1    DIV        pop w2; pop w1; push (w1/w2).
	//   10    1    CMPEQ      pop w2; pop w1;
	//                         push (if w1=w2 then 1 else 0).
	//   12    1    CMPLT      pop w2; pop w1;
	//                         push (if w1<w2 then 1 else 0).
	//   13    1    CMPGT      pop w2; pop w1;
	//                         push (if w1>w2 then 1 else 0).
	//   14    1    INV        pop w;
	//                         push (if w==0 then 1 else 0).
	//   15    1    INC        pop w; push w+1.
	//   16    1    HALT       status <- HALTED.
	//   17    1+2  JUMP c     pc <- c.
	//   18    1+2  JUMPF c    pop w; if w=0 then pc <- c.
	//   19    1+2  JUMPT c    pop w; if w!=0 then pc <- c.
	//   20    1+2  CALL c     push a new frame initially
	//                         containing only
	//                         dynamic link <- fp and
	//                         return address <- pc;
	//                         fp <- base address of frame;
	//                         pc <- c.
	//   21    1+1  RETURN r   pop the result (r words);
	//                         pop topmost frame;
	//                         push the result (r words);
	//                         fp <- dynamic link;
	//                         pc <- return address.
	//   22    1+1  COPYARG s  swap arguments (s words) into
	//                         the topmost frame, just above
	//                         the return address.

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

	protected byte[] code;     // code store
	protected int cl;          // code limit

	// CONSTRUCTOR

	public SVM () {
		code = new byte[32768];
		cl = 0;
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
