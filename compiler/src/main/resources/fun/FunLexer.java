// Generated from Fun.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FunLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BOOL=1, ELSE=2, FALSE=3, FUNC=4, IF=5, INT=6, PROC=7, RETURN=8, TRUE=9, 
		WHILE=10, EQ=11, LT=12, GT=13, PLUS=14, MINUS=15, TIMES=16, DIV=17, NOT=18, 
		ASSN=19, LPAR=20, RPAR=21, COLON=22, DOT=23, NUM=24, ID=25, SPACE=26, 
		EOL=27, COMMENT=28;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"BOOL", "ELSE", "FALSE", "FUNC", "IF", "INT", "PROC", "RETURN", "TRUE", 
		"WHILE", "EQ", "LT", "GT", "PLUS", "MINUS", "TIMES", "DIV", "NOT", "ASSN", 
		"LPAR", "RPAR", "COLON", "DOT", "NUM", "ID", "SPACE", "EOL", "COMMENT", 
		"LETTER", "DIGIT"
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


	public FunLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Fun.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\36\u00bc\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\3\2"+
		"\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3"+
		"\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f"+
		"\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23"+
		"\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31"+
		"\6\31\u0091\n\31\r\31\16\31\u0092\3\32\3\32\3\32\7\32\u0098\n\32\f\32"+
		"\16\32\u009b\13\32\3\33\6\33\u009e\n\33\r\33\16\33\u009f\3\33\3\33\3\34"+
		"\5\34\u00a5\n\34\3\34\3\34\3\34\3\34\3\35\3\35\7\35\u00ad\n\35\f\35\16"+
		"\35\u00b0\13\35\3\35\5\35\u00b3\n\35\3\35\3\35\3\35\3\35\3\36\3\36\3\37"+
		"\3\37\2\2 \3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33"+
		"\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67"+
		"\359\36;\2=\2\3\2\5\4\2\13\13\"\"\4\2\f\f\17\17\4\2C\\c|\u00c0\2\3\3\2"+
		"\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17"+
		"\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2"+
		"\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3"+
		"\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3"+
		"\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\3?\3\2\2\2\5"+
		"D\3\2\2\2\7I\3\2\2\2\tO\3\2\2\2\13T\3\2\2\2\rW\3\2\2\2\17[\3\2\2\2\21"+
		"`\3\2\2\2\23g\3\2\2\2\25l\3\2\2\2\27r\3\2\2\2\31u\3\2\2\2\33w\3\2\2\2"+
		"\35y\3\2\2\2\37{\3\2\2\2!}\3\2\2\2#\177\3\2\2\2%\u0081\3\2\2\2\'\u0085"+
		"\3\2\2\2)\u0087\3\2\2\2+\u0089\3\2\2\2-\u008b\3\2\2\2/\u008d\3\2\2\2\61"+
		"\u0090\3\2\2\2\63\u0094\3\2\2\2\65\u009d\3\2\2\2\67\u00a4\3\2\2\29\u00aa"+
		"\3\2\2\2;\u00b8\3\2\2\2=\u00ba\3\2\2\2?@\7d\2\2@A\7q\2\2AB\7q\2\2BC\7"+
		"n\2\2C\4\3\2\2\2DE\7g\2\2EF\7n\2\2FG\7u\2\2GH\7g\2\2H\6\3\2\2\2IJ\7h\2"+
		"\2JK\7c\2\2KL\7n\2\2LM\7u\2\2MN\7g\2\2N\b\3\2\2\2OP\7h\2\2PQ\7w\2\2QR"+
		"\7p\2\2RS\7e\2\2S\n\3\2\2\2TU\7k\2\2UV\7h\2\2V\f\3\2\2\2WX\7k\2\2XY\7"+
		"p\2\2YZ\7v\2\2Z\16\3\2\2\2[\\\7r\2\2\\]\7t\2\2]^\7q\2\2^_\7e\2\2_\20\3"+
		"\2\2\2`a\7t\2\2ab\7g\2\2bc\7v\2\2cd\7w\2\2de\7t\2\2ef\7p\2\2f\22\3\2\2"+
		"\2gh\7v\2\2hi\7t\2\2ij\7w\2\2jk\7g\2\2k\24\3\2\2\2lm\7y\2\2mn\7j\2\2n"+
		"o\7k\2\2op\7n\2\2pq\7g\2\2q\26\3\2\2\2rs\7?\2\2st\7?\2\2t\30\3\2\2\2u"+
		"v\7>\2\2v\32\3\2\2\2wx\7@\2\2x\34\3\2\2\2yz\7-\2\2z\36\3\2\2\2{|\7/\2"+
		"\2| \3\2\2\2}~\7,\2\2~\"\3\2\2\2\177\u0080\7\61\2\2\u0080$\3\2\2\2\u0081"+
		"\u0082\7p\2\2\u0082\u0083\7q\2\2\u0083\u0084\7v\2\2\u0084&\3\2\2\2\u0085"+
		"\u0086\7?\2\2\u0086(\3\2\2\2\u0087\u0088\7*\2\2\u0088*\3\2\2\2\u0089\u008a"+
		"\7+\2\2\u008a,\3\2\2\2\u008b\u008c\7<\2\2\u008c.\3\2\2\2\u008d\u008e\7"+
		"\60\2\2\u008e\60\3\2\2\2\u008f\u0091\5=\37\2\u0090\u008f\3\2\2\2\u0091"+
		"\u0092\3\2\2\2\u0092\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093\62\3\2\2"+
		"\2\u0094\u0099\5;\36\2\u0095\u0098\5;\36\2\u0096\u0098\5=\37\2\u0097\u0095"+
		"\3\2\2\2\u0097\u0096\3\2\2\2\u0098\u009b\3\2\2\2\u0099\u0097\3\2\2\2\u0099"+
		"\u009a\3\2\2\2\u009a\64\3\2\2\2\u009b\u0099\3\2\2\2\u009c\u009e\t\2\2"+
		"\2\u009d\u009c\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u009d\3\2\2\2\u009f\u00a0"+
		"\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a2\b\33\2\2\u00a2\66\3\2\2\2\u00a3"+
		"\u00a5\7\17\2\2\u00a4\u00a3\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a6\3"+
		"\2\2\2\u00a6\u00a7\7\f\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00a9\b\34\2\2\u00a9"+
		"8\3\2\2\2\u00aa\u00ae\7%\2\2\u00ab\u00ad\n\3\2\2\u00ac\u00ab\3\2\2\2\u00ad"+
		"\u00b0\3\2\2\2\u00ae\u00ac\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b2\3\2"+
		"\2\2\u00b0\u00ae\3\2\2\2\u00b1\u00b3\7\17\2\2\u00b2\u00b1\3\2\2\2\u00b2"+
		"\u00b3\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b5\7\f\2\2\u00b5\u00b6\3\2"+
		"\2\2\u00b6\u00b7\b\35\2\2\u00b7:\3\2\2\2\u00b8\u00b9\t\4\2\2\u00b9<\3"+
		"\2\2\2\u00ba\u00bb\4\62;\2\u00bb>\3\2\2\2\n\2\u0092\u0097\u0099\u009f"+
		"\u00a4\u00ae\u00b2\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}