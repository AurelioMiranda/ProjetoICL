/* Generated By:JavaCC: Do not edit this line. Parser.java */
package parser;
import ast.*;
import ast.arithmetic.*;
import ast.control_flow.*;
import ast.identifiers.*;
import ast.logical.*;
import ast.references.*;
import ast.tuples.*;
import ast.string.*;
import ast.print.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Parser implements ParserConstants {

  final public Exp Start() throws ParseException {
  Exp e;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Num:
    case MINUS:
    case NEG:
    case DER:
    case LPAR:
    case TRUE:
    case FALSE:
    case WHILE:
    case NEW:
    case UNIT:
    case LET:
    case IF:
    case FUN:
    case LBRAC:
    case FIRST:
    case SECOND:
    case LAST:
    case MATCH:
    case CONCAT:
    case PRINT:
    case PRINTLN:
    case SPLIT:
    case ID:
    case STRING:
      e = decl();
      jj_consume_token(EOL);
                      {if (true) return e;}
      break;
    case 0:
      jj_consume_token(0);
            {if (true) throw new ParseException("End of File encountered. Stopping Parsing.");}
      break;
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

/*Exp sequence() :
 { Exp e1, e2; }
 {
   e1 = decl() ( <SEMICOLON> e2 = sequence() { e1 = new ASTSeq(e1, e2); } )* { return e1; }
 }*/
  final public Exp decl() throws ParseException {
  Exp e, b; List<ASTParameter> params = new ArrayList<ASTParameter>();
 Map<String, Exp> variables = new HashMap<String, Exp>(); Token x, id, type;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case FUN:
      jj_consume_token(FUN);
      label_1:
      while (true) {
        jj_consume_token(LPAR);
        id = jj_consume_token(ID);
        jj_consume_token(COLON);
        type = jj_consume_token(ID);
        jj_consume_token(RPAR);
                                                        params.add(new ASTParameter(id.image, type.image));
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LPAR:
          ;
          break;
        default:
          jj_la1[1] = jj_gen;
          break label_1;
        }
      }
      jj_consume_token(ARROW);
      e = decl();
                         {if (true) return new ASTClosure(params, e);}
      break;
    case LET:
      jj_consume_token(LET);
      label_2:
      while (true) {
        x = jj_consume_token(ID);
        jj_consume_token(EQ);
        e = decl();
                                           variables.put(x.image, e);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case ID:
          ;
          break;
        default:
          jj_la1[2] = jj_gen;
          break label_2;
        }
      }
      jj_consume_token(IN);
      b = decl();
                           {if (true) return new ASTLet(variables, b);}
      break;
    case Num:
    case MINUS:
    case NEG:
    case DER:
    case LPAR:
    case TRUE:
    case FALSE:
    case WHILE:
    case NEW:
    case UNIT:
    case IF:
    case LBRAC:
    case FIRST:
    case SECOND:
    case LAST:
    case MATCH:
    case CONCAT:
    case PRINT:
    case PRINTLN:
    case SPLIT:
    case ID:
    case STRING:
      e = control_flow();
                         {if (true) return e;}
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp control_flow() throws ParseException {
  Exp e,b,eb, pair, match; Token id, d, x;
 List<String> ids = new ArrayList<String>();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IF:
      jj_consume_token(IF);
      e = decl();
      jj_consume_token(THEN);
      b = decl();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ELSE:
        jj_consume_token(ELSE);
        eb = decl();
        jj_consume_token(END);
                                                                 {if (true) return new ASTElse(e, b, eb);}
        break;
      default:
        jj_la1[4] = jj_gen;
        ;
      }
      jj_consume_token(END);
             {if (true) return new ASTIf(e, b);}
      break;
    case WHILE:
      jj_consume_token(WHILE);
      e = decl();
      jj_consume_token(DO);
      b = decl();
      jj_consume_token(END);
                                               {if (true) return new ASTWhile(e, b);}
      break;
    case LBRAC:
      jj_consume_token(LBRAC);
      e = decl();
                        pair = new ASTPair(e);
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[5] = jj_gen;
          break label_3;
        }
        jj_consume_token(COMMA);
        b = decl();
                             ((ASTPair)pair).addValue(b);
      }
      jj_consume_token(RBRAC);
                                                                        {if (true) return pair;}
      break;
    case MATCH:
      jj_consume_token(MATCH);
      id = jj_consume_token(ID);
                        match = new ASTMatch(id.image);
      jj_consume_token(WITH);
      d = jj_consume_token(ID);
                          ((ASTMatch)match).addValue(d.image);
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[6] = jj_gen;
          break label_4;
        }
        jj_consume_token(COMMA);
        x = jj_consume_token(ID);
                           { ((ASTMatch)match).addValue(x.image); }
      }
      jj_consume_token(ARROW);
      e = decl();
                             ((ASTMatch)match).addBody(e);
                                                               {if (true) return match;}
      break;
    case FIRST:
      jj_consume_token(FIRST);
      e = decl();
                         {if (true) return new ASTFirst(e);}
      break;
    case SECOND:
      jj_consume_token(SECOND);
      e = decl();
                          {if (true) return new ASTSecond(e);}
      break;
    case LAST:
      jj_consume_token(LAST);
      e = decl();
                        {if (true) return new ASTLast(e);}
      break;
    case CONCAT:
      jj_consume_token(CONCAT);
      jj_consume_token(LPAR);
      e = decl();
      jj_consume_token(COMMA);
      b = decl();
      jj_consume_token(RPAR);
                                                           {if (true) return new ASTConcat(e, b);}
      break;
    case SPLIT:
      jj_consume_token(SPLIT);
      jj_consume_token(LPAR);
      e = decl();
      jj_consume_token(COMMA);
      b = decl();
      jj_consume_token(RPAR);
                                                          {if (true) return new ASTSplit(e, b);}
      break;
    case PRINTLN:
      jj_consume_token(PRINTLN);
      jj_consume_token(LPAR);
      e = decl();
      jj_consume_token(RPAR);
                                         {if (true) return new ASTPrintln(e);}
      break;
    case PRINT:
      jj_consume_token(PRINT);
      jj_consume_token(LPAR);
      e = decl();
      jj_consume_token(RPAR);
                                       {if (true) return new ASTPrint(e);}
      break;
    case Num:
    case MINUS:
    case NEG:
    case DER:
    case LPAR:
    case TRUE:
    case FALSE:
    case NEW:
    case UNIT:
    case ID:
    case STRING:
      e = assignment();
                        {if (true) return e;}
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp assignment() throws ParseException {
  Exp e, v;
    e = logic();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ASSIGN:
      jj_consume_token(ASSIGN);
      v = assignment();
                                            {if (true) return new ASTAssign(e, v);}
      break;
    default:
      jj_la1[8] = jj_gen;
      ;
    }
       {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  final public Exp logic() throws ParseException {
  Exp e1,e2;
    e1 = eqOp();
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
      case OR:
        ;
        break;
      default:
        jj_la1[9] = jj_gen;
        break label_5;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
        jj_consume_token(AND);
        e2 = eqOp();
                                  e1 = new ASTAnd(e1,e2);
        break;
      case OR:
        jj_consume_token(OR);
        e2 = eqOp();
                     e1 = new ASTOr(e1,e2);
        break;
      default:
        jj_la1[10] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
       {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp eqOp() throws ParseException {
  Exp e1,e2;
    e1 = boolOp();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EQ:
      case NEQ:
        ;
        break;
      default:
        jj_la1[11] = jj_gen;
        break label_6;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EQ:
        jj_consume_token(EQ);
        e2 = boolOp();
                                     e1 = new ASTEq(e1,e2);
        break;
      case NEQ:
        jj_consume_token(NEQ);
        e2 = boolOp();
                       e1 = new ASTNEq(e1, e2);
        break;
      default:
        jj_la1[12] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
        {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp boolOp() throws ParseException {
  Exp e1,e2;
    e1 = Expr();
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case GT:
      case LT:
      case GTOEQ:
      case LTOEQ:
        ;
        break;
      default:
        jj_la1[13] = jj_gen;
        break label_7;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case GT:
        jj_consume_token(GT);
        e2 = Expr();
                                 e1 = new ASTGr(e1,e2);
        break;
      case LT:
        jj_consume_token(LT);
        e2 = Expr();
                     e1 = new ASTLt(e1,e2);
        break;
      case GTOEQ:
        jj_consume_token(GTOEQ);
        e2 = Expr();
                        e1 = new ASTGrOrEq(e1,e2);
        break;
      case LTOEQ:
        jj_consume_token(LTOEQ);
        e2 = Expr();
                        e1 = new ASTLTOrEq(e1,e2);
        break;
      default:
        jj_la1[14] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
        {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp Expr() throws ParseException {
  Exp e1, e2;
    e1 = Term();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PLUS:
    case MINUS:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MINUS:
        jj_consume_token(MINUS);
        e2 = Expr();
                           e1 = new ASTSub(e1,e2);
        break;
      case PLUS:
        jj_consume_token(PLUS);
        e2 = Expr();
                         e1 = new ASTAdd(e1,e2);
        break;
      default:
        jj_la1[15] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[16] = jj_gen;
      ;
    }
      {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp Term() throws ParseException {
  Exp e1, e2;
    e1 = fcall();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TIMES:
    case DIV:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DIV:
        jj_consume_token(DIV);
        e2 = Term();
                          e1 = new ASTDiv(e1,e2);
        break;
      case TIMES:
        jj_consume_token(TIMES);
        e2 = Term();
                          e1 = new ASTMult(e1,e2);
        break;
      default:
        jj_la1[17] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[18] = jj_gen;
      ;
    }
      {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp Fact() throws ParseException {
  Token x; Exp e;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Num:
      x = jj_consume_token(Num);
      {if (true) return new ASTInt(Integer.parseInt(x.image));}
      break;
    case STRING:
      x = jj_consume_token(STRING);
                  {if (true) return new ASTString(x.image);}
      break;
    case TRUE:
      jj_consume_token(TRUE);
            {if (true) return new ASTBool(true);}
      break;
    case FALSE:
      jj_consume_token(FALSE);
             {if (true) return new ASTBool(false);}
      break;
    case MINUS:
      jj_consume_token(MINUS);
      e = Fact();
                        {if (true) return new ASTSub(new ASTInt(0),e);}
      break;
    case NEG:
      jj_consume_token(NEG);
      e = Fact();
                      {if (true) return new ASTNeg(e);}
      break;
    case LPAR:
      jj_consume_token(LPAR);
      e = logic();
      jj_consume_token(RPAR);
                               {if (true) return e;}
      break;
    case UNIT:
      jj_consume_token(UNIT);
            {if (true) return new ASTBool(true);}
      break;
    case NEW:
      jj_consume_token(NEW);
      jj_consume_token(LPAR);
      e = logic();
      jj_consume_token(RPAR);
                                     {if (true) return new ASTNew(e);}
      break;
    case DER:
      jj_consume_token(DER);
      e = Fact();
                      {if (true) return new ASTDeref(e);}
      break;
    case ID:
      x = jj_consume_token(ID);
              {if (true) return new ASTIdentifier(x.image);}
      break;
    default:
      jj_la1[19] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp fcall() throws ParseException {
  Exp e;
    e = Fact();
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LPAR:
        ;
        break;
      default:
        jj_la1[20] = jj_gen;
        break label_8;
      }
      e = gets(e);
    }
   {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  final public Exp gets(Exp exp) throws ParseException {
  Exp e1,e2;
    jj_consume_token(LPAR);
          e1 = new ASTCall(exp);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Num:
    case MINUS:
    case NEG:
    case DER:
    case LPAR:
    case TRUE:
    case FALSE:
    case WHILE:
    case NEW:
    case UNIT:
    case IF:
    case LBRAC:
    case FIRST:
    case SECOND:
    case LAST:
    case MATCH:
    case CONCAT:
    case PRINT:
    case PRINTLN:
    case SPLIT:
    case ID:
    case STRING:
      e2 = control_flow();
                       ((ASTCall)e1).addArg(e2);
      label_9:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[21] = jj_gen;
          break label_9;
        }
        jj_consume_token(COMMA);
        e2 = control_flow();
                         ((ASTCall)e1).addArg(e2);
      }
      break;
    default:
      jj_la1[22] = jj_gen;
      ;
    }
    jj_consume_token(RPAR);
      {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public ParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[23];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0xa406a191,0x8000,0x0,0xa406a190,0x0,0x0,0x0,0xa406a190,0x40000000,0x600000,0x600000,0x180000,0x180000,0x1e00,0x1e00,0xa0,0xa0,0x4040,0x4040,0xa006a190,0x8000,0x0,0xa406a190,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x49e794a,0x0,0x800000,0x49e794a,0x20,0x400,0x400,0x49e7908,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x4800000,0x0,0x400,0x49e7908,};
   }

  /** Constructor with InputStream. */
  public Parser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Parser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Parser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Parser(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List jj_expentries = new java.util.ArrayList();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[59];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 23; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 59; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

                      }
