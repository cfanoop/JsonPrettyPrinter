package com.cf.gepos.paintjson.elems;

public class ArryElem extends Element {

	@Override
	public Element addComponent(Token tch) {
		Element elem = null;
		switch (tch) {
		case SQUARE_START:
			if (this.getStartToken() == null) {
				this.setStartToken(tch);
				return this;
			}
			elem = new ArryElem();
			elem.childIntend = this.childIntend + 1;
			elem.setParent(this);
			this.getParts().add(elem);
			return elem.addComponent(tch);
		case DQUARTZ:
			elem = new Value("String");
			elem.setParent(this);
			this.getParts().add(elem);
			return elem.addComponent(tch);
		case COMMA:
			if (this.getParts().isEmpty()) {
				addInvalid(tch);
				return this;
			}
			delimitters.put(this.getParts().getLast(), tch);
			return this;
		case CURLY_START:
			elem = new Obj();
			elem.setParent(this);
			this.getParts().add(elem);
			elem.childIntend = this.childIntend;
			return elem.addComponent(tch);
		case KVAL:
			elem = new Value("NString");
			elem.setParent(this);
			this.getParts().add(elem);
			return elem.addComponent(tch);
		case SQUARE_END:
			if (this.getEndToken() == null) {
				this.setEndToken(tch);
				return this.getParent();
			}
		case CURLY_END:
		case COLON:
			addInvalid(tch);
		default:
			return this;
		}
	}

	@Override
	public Token getExpectedStartToken() {
		return Token.SQUARE_START;
	}

	@Override
	public Token getExpectedEndToken() {
		return Token.SQUARE_END;
	}

	@Override
	public Token getExpectedDelimitter() {
		return Token.COMMA;
	}

	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		if (e_errors.get(this) != null) {
			strb.append("\n");
			strb.append(e_errors.get(this));
		}
		strb.append(this.getStartToken().getSym());
		this.getParts().forEach((v) -> {
			if (e_errors.get(v) != null) {
				strb.append("\n");
				strb.append(e_errors.get(v));
			}
			strb.append("\n");
			for (int i = 0; i < childIntend; i++)
				strb.append("\t");
			strb.append(v);
			if (delimitters.get(v) != null)
				strb.append(delimitters.get(v).getSym());
		});
		if (this.getEndToken() != null) {
			strb.append("\n");
			for (int i = 0; i < childIntend - 1; i++)
				strb.append("\t");
			strb.append(this.getEndToken().getSym());

		}
		return strb.toString();
	}

}
