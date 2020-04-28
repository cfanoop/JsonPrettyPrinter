package com.cf.gepos.paintjson.elems;

public class Value extends Element {

	private StringBuilder text = new StringBuilder();
	private String textType = "NString";

	public Value(String textType) {
		this.textType = textType;
	}

	public String getTextType() {
		return textType;
	}

	public String getText() {
		return text.toString();
	}

	public void appendText(char text) {
		this.text.append(text);
	}

	@Override
	public Element addComponent(Token tch) {

		switch (tch) {
		case DQUARTZ:
			if (!getTextType().equals("String")) {
				addInvalid(tch);
				return this.getParent().addComponent(tch);
			}
			if (this.getStartToken() == null)
				this.setStartToken(tch);
			else if (this.getEndToken() == null) {
				this.setEndToken(tch);
				return this.getParent();
			}
			return this;
		case KVAL:
			return this;
		case COMMA:
			if (getTextType().equals("String"))
				return this;
			return this.getParent().addComponent(tch);
		case CURLY_END:
		case SQUARE_END:
			if (getTextType().equals("String"))
				return this;
			return this.getParent().addComponent(tch);
		case COLON:
		case SQUARE_START:
		case CURLY_START:
			if (getTextType().equals("String"))
				return this;
		default:
			return this;
		}

	}

	@Override
	public Token getExpectedStartToken() {
		return textType.equals("String") ? Token.DQUARTZ : null;
	}

	@Override
	public Token getExpectedEndToken() {
		return textType.equals("String") ? Token.DQUARTZ : null;
	}

	@Override
	public Token getExpectedDelimitter() {
		return null;
	}

	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		if (this.getStartToken() != null)
			strb.append(this.getStartToken().getSym());
		strb.append(text);
		if (this.getEndToken() != null)
			strb.append(this.getEndToken().getSym());
		return strb.toString();
	}
}
