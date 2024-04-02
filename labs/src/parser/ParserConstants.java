/* Generated By:JavaCC: Do not edit this line. ParserConstants.java */
package parser;


/** 
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int Num = 4;
  /** RegularExpression Id. */
  int PLUS = 5;
  /** RegularExpression Id. */
  int TIMES = 6;
  /** RegularExpression Id. */
  int MINUS = 7;
  /** RegularExpression Id. */
  int GT = 8;
  /** RegularExpression Id. */
  int LT = 9;
  /** RegularExpression Id. */
  int NEG = 10;
  /** RegularExpression Id. */
  int DIV = 11;
  /** RegularExpression Id. */
  int LPAR = 12;
  /** RegularExpression Id. */
  int RPAR = 13;
  /** RegularExpression Id. */
  int TRUE = 14;
  /** RegularExpression Id. */
  int FALSE = 15;
  /** RegularExpression Id. */
  int EQ = 16;
  /** RegularExpression Id. */
  int AND = 17;
  /** RegularExpression Id. */
  int OR = 18;
  /** RegularExpression Id. */
  int IFQ = 19;
  /** RegularExpression Id. */
  int COLON = 20;
  /** RegularExpression Id. */
  int EOL = 21;
  /** RegularExpression Id. */
  int LET = 22;
  /** RegularExpression Id. */
  int IN = 23;
  /** RegularExpression Id. */
  int INT = 24;
  /** RegularExpression Id. */
  int DIGIT = 25;
  /** RegularExpression Id. */
  int ID = 26;
  /** RegularExpression Id. */
  int LETTER = 27;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\r\"",
    "<Num>",
    "\"+\"",
    "\"*\"",
    "\"-\"",
    "\">\"",
    "\"<\"",
    "\"!\"",
    "\"/\"",
    "\"(\"",
    "\")\"",
    "\"true\"",
    "\"false\"",
    "\"=\"",
    "\"and\"",
    "\"or\"",
    "\"?\"",
    "\":\"",
    "\"\\n\"",
    "\"let\"",
    "\"in\"",
    "<INT>",
    "<DIGIT>",
    "<ID>",
    "<LETTER>",
  };

}
