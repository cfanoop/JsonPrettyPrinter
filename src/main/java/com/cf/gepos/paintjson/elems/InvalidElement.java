package com.cf.gepos.paintjson.elems;

public class InvalidElement extends Element {

	@Override
	public Token getExpectedStartToken() {
		return null;
	}

	@Override
	public Token getExpectedEndToken() {
		return null;
	}

	@Override
	public Token getExpectedDelimitter() {
		return null;
	}

	@Override
	public Element addComponent(Token tch) {
		return null;
	}

	public void validateComponent() {
		this.getParent().e_errors.put(this, new TokenError(
				"Invalid " + (this.getStartToken() != Token.KVAL ? this.getStartToken().getSym() : " character ")));
	}

	protected void setStartToken(Token startToken) {
		this.startToken = startToken;
	}

	protected void setEndToken(Token endToken) {
		this.endToken = endToken;
	}

	@Override
	public String toString() {
		return "\n" + this.getStartToken().getSym();
	}

}
