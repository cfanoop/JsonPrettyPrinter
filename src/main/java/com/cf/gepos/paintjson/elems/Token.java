package com.cf.gepos.paintjson.elems;

public enum Token {

	CURLY_START('{'), CURLY_END('}'), SQUARE_START('['), 
	SQUARE_END(']'), DQUARTZ('"'), COLON(':'), COMMA(','),
	KVAL(' ');

	private char sym;

	private Token(char sym) {
		this.sym = sym;
	}

	public char getSym() {
		return sym;
	}

	public void setSym(char sym) {
		this.sym = sym;
	}

	public static Token readToken(char value) {

		for (Token tk : Token.values())
			if (tk.sym == value)
				return tk;
		return KVAL;
	}
	
	

}
