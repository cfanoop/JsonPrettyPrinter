package com.cf.gepos.paintjson.elems;

public class Elem extends Element {

	@Override
	public Element addComponent(Token tch) {
		Element elem = null;
		switch (tch) {
		case DQUARTZ:
			if (this.getParts().size() == 2) {
				addInvalid(tch);
				return this.getParent().addComponent(tch);
			}
			elem = new Value("String");
			elem.setParent(this);
			this.getParts().add(elem);
			return elem.addComponent(tch);
		case COLON:
			if (this.getParts().isEmpty()) {
				addInvalid(tch);
			}
			delimitters.put(this.getParts().getLast(), tch);
			return this;
		case COMMA:
			if (this.getParts().size() < 2) {
				addInvalid(tch);
			}
			return this.getParent().addComponent(tch);
		case KVAL:
			if (this.getParts().isEmpty()) {
				addInvalid(tch);
				return this;
			}
			if (this.getParts().size() == 2) {
				addInvalid(tch);
				return this.getParent();
			}
			elem = new Value("NString");
			elem.setParent(this);
			this.getParts().add(elem);
			return elem.addComponent(tch);
		case CURLY_START:
			if (this.getParts().isEmpty()) {
				addInvalid(tch);
				return this;
			}
			elem = new Obj();
			elem.childIntend = this.childIntend;
			elem.setParent(this);
			this.getParts().add(elem);
			return elem.addComponent(tch);
		case SQUARE_START:
			if (this.getParts().isEmpty()) {
				addInvalid(tch);
				return this;
			}
			elem = new ArryElem();
			elem.childIntend = this.childIntend + 1;
			elem.setParent(this);
			this.getParts().add(elem);
			return elem.addComponent(tch);
		case CURLY_END:
			if (this.getParts().size() < 2) {
				addInvalid(tch);
			}
			return this.getParent().addComponent(tch);
		case SQUARE_END:
			addInvalid(tch);
		default:
			return this;
		}
	}

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
		return Token.COLON;
	}

	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		if (e_errors.get(this) != null) {
			strb.append("\n");
			strb.append(e_errors.get(this));
		}
		this.getParts().forEach((v) -> {
			if (e_errors.get(v) != null) {
				strb.append("\n");
				strb.append(e_errors.get(v));
			}
		});
		strb.append("\n");
		for (int i = 0; i < childIntend; i++)
			strb.append("\t");
		this.getParts().forEach((v) -> {
			strb.append(v);
			if (delimitters.get(v) != null)
				strb.append(delimitters.get(v).getSym());

		});
		return strb.toString();
	}

}
