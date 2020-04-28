package com.cf.gepos.paintjson.elems;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public abstract class Element {

	protected Token startToken, endToken;

	protected Map<Element, Token> delimitters = new HashMap<>();

	private Deque<Element> parts = new LinkedList<>();

	protected Map<Object, TokenError> e_errors = new HashMap<>();

	private Element parent;
	protected boolean isLast;

	protected int childIntend = 0;

	public abstract Token getExpectedStartToken();

	public abstract Token getExpectedEndToken();

	public abstract Token getExpectedDelimitter();
	
	public Element getParent() {
		return parent;
	}

	public void setParent(Element parent) {
		this.parent = parent;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public Deque<Element> getParts() {
		return parts;
	}

	public void setParts(Deque<Element> parts) {
		this.parts = parts;
	}

	public Token getStartToken() {
		return startToken;
	}

	protected void setStartToken(Token startToken) {
		if (this.getExpectedStartToken() == startToken)
			this.startToken = startToken;
	}

	public Token getEndToken() {
		return endToken;
	}

	protected void setEndToken(Token endToken) {
		if (this.getExpectedEndToken() == endToken)
			this.endToken = endToken;
	}

	public Element addComponent(Character c) {
		Token tch = Token.readToken(c);
		if (!(this instanceof Value) && Character.isWhitespace(c)) {
			return this;
		}
		Element e = addComponent(tch);
		if (e instanceof Value && tch != Token.DQUARTZ) {
			((Value) e).appendText(c);
		}
		return e;
	}

	public abstract Element addComponent(Token tch);

	public static Element Nil = new Obj();

	public void validateComponent() {
		final Map<Object, TokenError> etokenErr = (this.parent != null && this.childIntend == this.parent.childIntend)
				? this.parent.e_errors
				: this.e_errors;
		if ((getExpectedStartToken() != null && !getExpectedStartToken().equals(getStartToken()))
				|| (getExpectedEndToken() != null && !getExpectedEndToken().equals(getEndToken())))
			etokenErr.put(this, new TokenError("Missing " + getExpectedStartToken() + " or " + getExpectedEndToken()));
		this.parts.forEach((v) -> {
			if (!this.parts.isEmpty())
				this.parts.getLast().setLast(true);
			if (getExpectedDelimitter() != null && !v.isLast
					&& !getExpectedDelimitter().equals(this.delimitters.get(v)))
				etokenErr.put(v, new TokenError("Missing " + getExpectedDelimitter()));
			if (v.isLast && this.delimitters.containsKey(v))
				etokenErr.put(v, new TokenError("Invalid " + delimitters.get(v).getSym()));
			v.validateComponent();
		});
	}

	public abstract String toString();
	
	public void addInvalid(Token tch) {
		Element elem = new InvalidElement();
		elem.childIntend = this.childIntend + 1;
		elem.setStartToken(tch);
		elem.setParent(this);
		this.getParts().add(elem);

	}
}
