// Generated from Fun.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FunParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BOOL=1, ELSE=2, FALSE=3, FUNC=4, IF=5, INT=6, PROC=7, RETURN=8, TRUE=9, 
		WHILE=10, EQ=11, LT=12, GT=13, PLUS=14, MINUS=15, TIMES=16, DIV=17, NOT=18, 
		ASSN=19, LPAR=20, RPAR=21, COLON=22, DOT=23, NUM=24, ID=25, SPACE=26, 
		EOL=27, COMMENT=28;
	public static final int
		RULE_program = 0, RULE_proc_decl = 1, RULE_formal_decl = 2, RULE_var_decl = 3, 
		RULE_type = 4, RULE_com = 5, RULE_seq_com = 6, RULE_expr = 7, RULE_sec_expr = 8, 
		RULE_prim_expr = 9, RULE_actual = 10;
	public static final String[] ruleNames = {
		"program", "proc_decl", "formal_decl", "var_decl", "type", "com", "seq_com", 
		"expr", "sec_expr", "prim_expr", "actual"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'bool'", "'else'", "'false'", "'func'", "'if'", "'int'", "'proc'", 
		"'return'", "'true'", "'while'", "'=='", "'<'", "'>'", "'+'", "'-'", "'*'", 
		"'/'", "'not'", "'='", "'('", "')'", "':'", "'.'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "BOOL", "ELSE", "FALSE", "FUNC", "IF", "INT", "PROC", "RETURN", 
		"TRUE", "WHILE", "EQ", "LT", "GT", "PLUS", "MINUS", "TIMES", "DIV", "NOT", 
		"ASSN", "LPAR", "RPAR", "COLON", "DOT", "NUM", "ID", "SPACE", "EOL", "COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Fun.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public FunParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	 
		public ProgramContext() { }
		public void copyFrom(ProgramContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ProgContext extends ProgramContext {
		public TerminalNode EOF() { return getToken(FunParser.EOF, 0); }
		public List<Var_declContext> var_decl() {
			return getRuleContexts(Var_declContext.class);
		}
		public Var_declContext var_decl(int i) {
			return getRuleContext(Var_declContext.class,i);
		}
		public List<Proc_declContext> proc_decl() {
			return getRuleContexts(Proc_declContext.class);
		}
		public Proc_declContext proc_decl(int i) {
			return getRuleContext(Proc_declContext.class,i);
		}
		public ProgContext(ProgramContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			_localctx = new ProgContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==BOOL || _la==INT) {
				{
				{
				setState(22);
				var_decl();
				}
				}
				setState(27);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(29); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(28);
				proc_decl();
				}
				}
				setState(31); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==FUNC || _la==PROC );
			setState(33);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Proc_declContext extends ParserRuleContext {
		public Proc_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_proc_decl; }
	 
		public Proc_declContext() { }
		public void copyFrom(Proc_declContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ProcContext extends Proc_declContext {
		public TerminalNode PROC() { return getToken(FunParser.PROC, 0); }
		public TerminalNode ID() { return getToken(FunParser.ID, 0); }
		public TerminalNode LPAR() { return getToken(FunParser.LPAR, 0); }
		public Formal_declContext formal_decl() {
			return getRuleContext(Formal_declContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(FunParser.RPAR, 0); }
		public TerminalNode COLON() { return getToken(FunParser.COLON, 0); }
		public Seq_comContext seq_com() {
			return getRuleContext(Seq_comContext.class,0);
		}
		public TerminalNode DOT() { return getToken(FunParser.DOT, 0); }
		public List<Var_declContext> var_decl() {
			return getRuleContexts(Var_declContext.class);
		}
		public Var_declContext var_decl(int i) {
			return getRuleContext(Var_declContext.class,i);
		}
		public ProcContext(Proc_declContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitProc(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FuncContext extends Proc_declContext {
		public TerminalNode FUNC() { return getToken(FunParser.FUNC, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(FunParser.ID, 0); }
		public TerminalNode LPAR() { return getToken(FunParser.LPAR, 0); }
		public Formal_declContext formal_decl() {
			return getRuleContext(Formal_declContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(FunParser.RPAR, 0); }
		public TerminalNode COLON() { return getToken(FunParser.COLON, 0); }
		public Seq_comContext seq_com() {
			return getRuleContext(Seq_comContext.class,0);
		}
		public TerminalNode RETURN() { return getToken(FunParser.RETURN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode DOT() { return getToken(FunParser.DOT, 0); }
		public List<Var_declContext> var_decl() {
			return getRuleContexts(Var_declContext.class);
		}
		public Var_declContext var_decl(int i) {
			return getRuleContext(Var_declContext.class,i);
		}
		public FuncContext(Proc_declContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitFunc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Proc_declContext proc_decl() throws RecognitionException {
		Proc_declContext _localctx = new Proc_declContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_proc_decl);
		int _la;
		try {
			setState(68);
			switch (_input.LA(1)) {
			case PROC:
				_localctx = new ProcContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(35);
				match(PROC);
				setState(36);
				match(ID);
				setState(37);
				match(LPAR);
				setState(38);
				formal_decl();
				setState(39);
				match(RPAR);
				setState(40);
				match(COLON);
				setState(44);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==BOOL || _la==INT) {
					{
					{
					setState(41);
					var_decl();
					}
					}
					setState(46);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(47);
				seq_com();
				setState(48);
				match(DOT);
				}
				break;
			case FUNC:
				_localctx = new FuncContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(50);
				match(FUNC);
				setState(51);
				type();
				setState(52);
				match(ID);
				setState(53);
				match(LPAR);
				setState(54);
				formal_decl();
				setState(55);
				match(RPAR);
				setState(56);
				match(COLON);
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==BOOL || _la==INT) {
					{
					{
					setState(57);
					var_decl();
					}
					}
					setState(62);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(63);
				seq_com();
				setState(64);
				match(RETURN);
				setState(65);
				expr();
				setState(66);
				match(DOT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Formal_declContext extends ParserRuleContext {
		public Formal_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formal_decl; }
	 
		public Formal_declContext() { }
		public void copyFrom(Formal_declContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FormalContext extends Formal_declContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(FunParser.ID, 0); }
		public FormalContext(Formal_declContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitFormal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Formal_declContext formal_decl() throws RecognitionException {
		Formal_declContext _localctx = new Formal_declContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_formal_decl);
		int _la;
		try {
			_localctx = new FormalContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			_la = _input.LA(1);
			if (_la==BOOL || _la==INT) {
				{
				setState(70);
				type();
				setState(71);
				match(ID);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Var_declContext extends ParserRuleContext {
		public Var_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_decl; }
	 
		public Var_declContext() { }
		public void copyFrom(Var_declContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class VarContext extends Var_declContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(FunParser.ID, 0); }
		public TerminalNode ASSN() { return getToken(FunParser.ASSN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public VarContext(Var_declContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_declContext var_decl() throws RecognitionException {
		Var_declContext _localctx = new Var_declContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_var_decl);
		try {
			_localctx = new VarContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			type();
			setState(76);
			match(ID);
			setState(77);
			match(ASSN);
			setState(78);
			expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BoolContext extends TypeContext {
		public TerminalNode BOOL() { return getToken(FunParser.BOOL, 0); }
		public BoolContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitBool(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntContext extends TypeContext {
		public TerminalNode INT() { return getToken(FunParser.INT, 0); }
		public IntContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitInt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_type);
		try {
			setState(82);
			switch (_input.LA(1)) {
			case BOOL:
				_localctx = new BoolContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(80);
				match(BOOL);
				}
				break;
			case INT:
				_localctx = new IntContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				match(INT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComContext extends ParserRuleContext {
		public ComContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_com; }
	 
		public ComContext() { }
		public void copyFrom(ComContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AssnContext extends ComContext {
		public TerminalNode ID() { return getToken(FunParser.ID, 0); }
		public TerminalNode ASSN() { return getToken(FunParser.ASSN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssnContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitAssn(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ProccallContext extends ComContext {
		public TerminalNode ID() { return getToken(FunParser.ID, 0); }
		public TerminalNode LPAR() { return getToken(FunParser.LPAR, 0); }
		public ActualContext actual() {
			return getRuleContext(ActualContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(FunParser.RPAR, 0); }
		public ProccallContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitProccall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WhileContext extends ComContext {
		public TerminalNode WHILE() { return getToken(FunParser.WHILE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode COLON() { return getToken(FunParser.COLON, 0); }
		public Seq_comContext seq_com() {
			return getRuleContext(Seq_comContext.class,0);
		}
		public TerminalNode DOT() { return getToken(FunParser.DOT, 0); }
		public WhileContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitWhile(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IfContext extends ComContext {
		public Seq_comContext c1;
		public Seq_comContext c2;
		public TerminalNode IF() { return getToken(FunParser.IF, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<TerminalNode> COLON() { return getTokens(FunParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(FunParser.COLON, i);
		}
		public List<Seq_comContext> seq_com() {
			return getRuleContexts(Seq_comContext.class);
		}
		public Seq_comContext seq_com(int i) {
			return getRuleContext(Seq_comContext.class,i);
		}
		public TerminalNode DOT() { return getToken(FunParser.DOT, 0); }
		public TerminalNode ELSE() { return getToken(FunParser.ELSE, 0); }
		public IfContext(ComContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitIf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComContext com() throws RecognitionException {
		ComContext _localctx = new ComContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_com);
		try {
			setState(110);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				_localctx = new AssnContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(84);
				match(ID);
				setState(85);
				match(ASSN);
				setState(86);
				expr();
				}
				break;
			case 2:
				_localctx = new ProccallContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(87);
				match(ID);
				setState(88);
				match(LPAR);
				setState(89);
				actual();
				setState(90);
				match(RPAR);
				}
				break;
			case 3:
				_localctx = new IfContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(92);
				match(IF);
				setState(93);
				expr();
				setState(94);
				match(COLON);
				setState(95);
				((IfContext)_localctx).c1 = seq_com();
				setState(102);
				switch (_input.LA(1)) {
				case DOT:
					{
					setState(96);
					match(DOT);
					}
					break;
				case ELSE:
					{
					setState(97);
					match(ELSE);
					setState(98);
					match(COLON);
					setState(99);
					((IfContext)_localctx).c2 = seq_com();
					setState(100);
					match(DOT);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 4:
				_localctx = new WhileContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(104);
				match(WHILE);
				setState(105);
				expr();
				setState(106);
				match(COLON);
				setState(107);
				seq_com();
				setState(108);
				match(DOT);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Seq_comContext extends ParserRuleContext {
		public Seq_comContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_seq_com; }
	 
		public Seq_comContext() { }
		public void copyFrom(Seq_comContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SeqContext extends Seq_comContext {
		public List<ComContext> com() {
			return getRuleContexts(ComContext.class);
		}
		public ComContext com(int i) {
			return getRuleContext(ComContext.class,i);
		}
		public SeqContext(Seq_comContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitSeq(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Seq_comContext seq_com() throws RecognitionException {
		Seq_comContext _localctx = new Seq_comContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_seq_com);
		int _la;
		try {
			_localctx = new SeqContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IF) | (1L << WHILE) | (1L << ID))) != 0)) {
				{
				{
				setState(112);
				com();
				}
				}
				setState(117);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public Sec_exprContext e1;
		public Token op;
		public Sec_exprContext e2;
		public List<Sec_exprContext> sec_expr() {
			return getRuleContexts(Sec_exprContext.class);
		}
		public Sec_exprContext sec_expr(int i) {
			return getRuleContext(Sec_exprContext.class,i);
		}
		public TerminalNode EQ() { return getToken(FunParser.EQ, 0); }
		public TerminalNode LT() { return getToken(FunParser.LT, 0); }
		public TerminalNode GT() { return getToken(FunParser.GT, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			((ExprContext)_localctx).e1 = sec_expr();
			setState(121);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ) | (1L << LT) | (1L << GT))) != 0)) {
				{
				setState(119);
				((ExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ) | (1L << LT) | (1L << GT))) != 0)) ) {
					((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(120);
				((ExprContext)_localctx).e2 = sec_expr();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Sec_exprContext extends ParserRuleContext {
		public Prim_exprContext e1;
		public Token op;
		public Sec_exprContext e2;
		public Prim_exprContext prim_expr() {
			return getRuleContext(Prim_exprContext.class,0);
		}
		public Sec_exprContext sec_expr() {
			return getRuleContext(Sec_exprContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(FunParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(FunParser.MINUS, 0); }
		public TerminalNode TIMES() { return getToken(FunParser.TIMES, 0); }
		public TerminalNode DIV() { return getToken(FunParser.DIV, 0); }
		public Sec_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sec_expr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitSec_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Sec_exprContext sec_expr() throws RecognitionException {
		Sec_exprContext _localctx = new Sec_exprContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_sec_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			((Sec_exprContext)_localctx).e1 = prim_expr();
			setState(126);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PLUS) | (1L << MINUS) | (1L << TIMES) | (1L << DIV))) != 0)) {
				{
				setState(124);
				((Sec_exprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PLUS) | (1L << MINUS) | (1L << TIMES) | (1L << DIV))) != 0)) ) {
					((Sec_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(125);
				((Sec_exprContext)_localctx).e2 = sec_expr();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Prim_exprContext extends ParserRuleContext {
		public Prim_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prim_expr; }
	 
		public Prim_exprContext() { }
		public void copyFrom(Prim_exprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NotContext extends Prim_exprContext {
		public TerminalNode NOT() { return getToken(FunParser.NOT, 0); }
		public Prim_exprContext prim_expr() {
			return getRuleContext(Prim_exprContext.class,0);
		}
		public NotContext(Prim_exprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitNot(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParensContext extends Prim_exprContext {
		public TerminalNode LPAR() { return getToken(FunParser.LPAR, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(FunParser.RPAR, 0); }
		public ParensContext(Prim_exprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitParens(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunccallContext extends Prim_exprContext {
		public TerminalNode ID() { return getToken(FunParser.ID, 0); }
		public TerminalNode LPAR() { return getToken(FunParser.LPAR, 0); }
		public ActualContext actual() {
			return getRuleContext(ActualContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(FunParser.RPAR, 0); }
		public FunccallContext(Prim_exprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitFunccall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NumContext extends Prim_exprContext {
		public TerminalNode NUM() { return getToken(FunParser.NUM, 0); }
		public NumContext(Prim_exprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitNum(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FalseContext extends Prim_exprContext {
		public TerminalNode FALSE() { return getToken(FunParser.FALSE, 0); }
		public FalseContext(Prim_exprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitFalse(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TrueContext extends Prim_exprContext {
		public TerminalNode TRUE() { return getToken(FunParser.TRUE, 0); }
		public TrueContext(Prim_exprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitTrue(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdContext extends Prim_exprContext {
		public TerminalNode ID() { return getToken(FunParser.ID, 0); }
		public IdContext(Prim_exprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Prim_exprContext prim_expr() throws RecognitionException {
		Prim_exprContext _localctx = new Prim_exprContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_prim_expr);
		try {
			setState(143);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				_localctx = new FalseContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(128);
				match(FALSE);
				}
				break;
			case 2:
				_localctx = new TrueContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(129);
				match(TRUE);
				}
				break;
			case 3:
				_localctx = new NumContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(130);
				match(NUM);
				}
				break;
			case 4:
				_localctx = new IdContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(131);
				match(ID);
				}
				break;
			case 5:
				_localctx = new FunccallContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(132);
				match(ID);
				setState(133);
				match(LPAR);
				setState(134);
				actual();
				setState(135);
				match(RPAR);
				}
				break;
			case 6:
				_localctx = new NotContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(137);
				match(NOT);
				setState(138);
				prim_expr();
				}
				break;
			case 7:
				_localctx = new ParensContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(139);
				match(LPAR);
				setState(140);
				expr();
				setState(141);
				match(RPAR);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActualContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ActualContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actual; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FunVisitor ) return ((FunVisitor<? extends T>)visitor).visitActual(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActualContext actual() throws RecognitionException {
		ActualContext _localctx = new ActualContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_actual);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FALSE) | (1L << TRUE) | (1L << NOT) | (1L << LPAR) | (1L << NUM) | (1L << ID))) != 0)) {
				{
				setState(145);
				expr();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\36\u0097\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\3\2\7\2\32\n\2\f\2\16\2\35\13\2\3\2\6\2 \n\2\r\2\16\2!\3"+
		"\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3-\n\3\f\3\16\3\60\13\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3=\n\3\f\3\16\3@\13\3\3\3\3\3\3\3"+
		"\3\3\3\3\5\3G\n\3\3\4\3\4\3\4\5\4L\n\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\5\6"+
		"U\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\5\7i\n\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7q\n\7\3\b\7\bt\n\b\f\b\16"+
		"\bw\13\b\3\t\3\t\3\t\5\t|\n\t\3\n\3\n\3\n\5\n\u0081\n\n\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u0092"+
		"\n\13\3\f\5\f\u0095\n\f\3\f\2\2\r\2\4\6\b\n\f\16\20\22\24\26\2\4\3\2\r"+
		"\17\3\2\20\23\u00a0\2\33\3\2\2\2\4F\3\2\2\2\6K\3\2\2\2\bM\3\2\2\2\nT\3"+
		"\2\2\2\fp\3\2\2\2\16u\3\2\2\2\20x\3\2\2\2\22}\3\2\2\2\24\u0091\3\2\2\2"+
		"\26\u0094\3\2\2\2\30\32\5\b\5\2\31\30\3\2\2\2\32\35\3\2\2\2\33\31\3\2"+
		"\2\2\33\34\3\2\2\2\34\37\3\2\2\2\35\33\3\2\2\2\36 \5\4\3\2\37\36\3\2\2"+
		"\2 !\3\2\2\2!\37\3\2\2\2!\"\3\2\2\2\"#\3\2\2\2#$\7\2\2\3$\3\3\2\2\2%&"+
		"\7\t\2\2&\'\7\33\2\2\'(\7\26\2\2()\5\6\4\2)*\7\27\2\2*.\7\30\2\2+-\5\b"+
		"\5\2,+\3\2\2\2-\60\3\2\2\2.,\3\2\2\2./\3\2\2\2/\61\3\2\2\2\60.\3\2\2\2"+
		"\61\62\5\16\b\2\62\63\7\31\2\2\63G\3\2\2\2\64\65\7\6\2\2\65\66\5\n\6\2"+
		"\66\67\7\33\2\2\678\7\26\2\289\5\6\4\29:\7\27\2\2:>\7\30\2\2;=\5\b\5\2"+
		"<;\3\2\2\2=@\3\2\2\2><\3\2\2\2>?\3\2\2\2?A\3\2\2\2@>\3\2\2\2AB\5\16\b"+
		"\2BC\7\n\2\2CD\5\20\t\2DE\7\31\2\2EG\3\2\2\2F%\3\2\2\2F\64\3\2\2\2G\5"+
		"\3\2\2\2HI\5\n\6\2IJ\7\33\2\2JL\3\2\2\2KH\3\2\2\2KL\3\2\2\2L\7\3\2\2\2"+
		"MN\5\n\6\2NO\7\33\2\2OP\7\25\2\2PQ\5\20\t\2Q\t\3\2\2\2RU\7\3\2\2SU\7\b"+
		"\2\2TR\3\2\2\2TS\3\2\2\2U\13\3\2\2\2VW\7\33\2\2WX\7\25\2\2Xq\5\20\t\2"+
		"YZ\7\33\2\2Z[\7\26\2\2[\\\5\26\f\2\\]\7\27\2\2]q\3\2\2\2^_\7\7\2\2_`\5"+
		"\20\t\2`a\7\30\2\2ah\5\16\b\2bi\7\31\2\2cd\7\4\2\2de\7\30\2\2ef\5\16\b"+
		"\2fg\7\31\2\2gi\3\2\2\2hb\3\2\2\2hc\3\2\2\2iq\3\2\2\2jk\7\f\2\2kl\5\20"+
		"\t\2lm\7\30\2\2mn\5\16\b\2no\7\31\2\2oq\3\2\2\2pV\3\2\2\2pY\3\2\2\2p^"+
		"\3\2\2\2pj\3\2\2\2q\r\3\2\2\2rt\5\f\7\2sr\3\2\2\2tw\3\2\2\2us\3\2\2\2"+
		"uv\3\2\2\2v\17\3\2\2\2wu\3\2\2\2x{\5\22\n\2yz\t\2\2\2z|\5\22\n\2{y\3\2"+
		"\2\2{|\3\2\2\2|\21\3\2\2\2}\u0080\5\24\13\2~\177\t\3\2\2\177\u0081\5\22"+
		"\n\2\u0080~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\23\3\2\2\2\u0082\u0092\7"+
		"\5\2\2\u0083\u0092\7\13\2\2\u0084\u0092\7\32\2\2\u0085\u0092\7\33\2\2"+
		"\u0086\u0087\7\33\2\2\u0087\u0088\7\26\2\2\u0088\u0089\5\26\f\2\u0089"+
		"\u008a\7\27\2\2\u008a\u0092\3\2\2\2\u008b\u008c\7\24\2\2\u008c\u0092\5"+
		"\24\13\2\u008d\u008e\7\26\2\2\u008e\u008f\5\20\t\2\u008f\u0090\7\27\2"+
		"\2\u0090\u0092\3\2\2\2\u0091\u0082\3\2\2\2\u0091\u0083\3\2\2\2\u0091\u0084"+
		"\3\2\2\2\u0091\u0085\3\2\2\2\u0091\u0086\3\2\2\2\u0091\u008b\3\2\2\2\u0091"+
		"\u008d\3\2\2\2\u0092\25\3\2\2\2\u0093\u0095\5\20\t\2\u0094\u0093\3\2\2"+
		"\2\u0094\u0095\3\2\2\2\u0095\27\3\2\2\2\20\33!.>FKThpu{\u0080\u0091\u0094";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}