package ast;



public interface Exp {
	public interface Visitor<T> {
		public T visit(ASTAdd i);
		public T visit(ASTDiv i);
		public T visit(ASTInt i);
		public T visit(ASTMult e);
		public T visit(ASTSub e);
        public T visit(ASTEq astEq);
		public T visit(ASTAnd astAnd);
		public T visit(ASTOr astOr);
		public T visit(ASTGr astGr);
        public T visit(ASTLt astLt);
    }
	
    //	public int eval();
	
	public <T> T accept(Visitor<T> v);
	
	
}

