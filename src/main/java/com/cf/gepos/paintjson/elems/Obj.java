package com.cf.gepos.paintjson.elems;

public class Obj extends Element {

	public Element addComponent(Token tch) {
		Element elem = null;
		switch (tch) {
		case CURLY_START:
			if (this.getStartToken() == null) {
				this.setStartToken(tch);
				return this;
			}
			addInvalid(tch);
			return this;
		case DQUARTZ:
			elem = new Elem();
			elem.childIntend = this.childIntend + 1;
			elem.setParent(this);
			this.getParts().add(elem);
			return elem.addComponent(tch);
		case CURLY_END:
			if (this.getEndToken() == null) {
				this.setEndToken(tch);
				this.getParts().getLast().setLast(true);
				return this.getParent() == null ? this : this.getParent();
			}
			addInvalid(tch);
			return this;
		case COMMA:
			if (!delimitters.containsKey(this.getParts().getLast())) {
				delimitters.put(this.getParts().getLast(), tch);
				return this;
			}
			addInvalid(tch);
			return this;
		case SQUARE_START:
		case COLON:
		case SQUARE_END:
		case KVAL:
			addInvalid(tch);
		default:
			return this;
		}
	}

	@Override
	public Token getExpectedStartToken() {
		return Token.CURLY_START;
	}

	@Override
	public Token getExpectedEndToken() {
		return Token.CURLY_END;
	}

	@Override
	public Token getExpectedDelimitter() {
		return Token.COMMA;
	}

	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		if (e_errors.get(this) != null) {
			strb.append(e_errors.get(this));
			strb.append("\n");
		}
		strb.append(this.getStartToken().getSym());
		this.getParts().forEach((v) -> {
			if (e_errors.get(v) != null) {
				strb.append("\n");
				strb.append(e_errors.get(v));
			}
			strb.append(v);
			if (delimitters.get(v) != null)
				strb.append(delimitters.get(v).getSym());
		});
		if (this.getEndToken() != null) {
			strb.append("\n");
			for (int i = 0; i < childIntend; i++)
				strb.append("\t");
			strb.append(this.getEndToken().getSym());

		}
		return strb.toString();
	}

}
