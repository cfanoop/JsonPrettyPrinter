package com.cf.gepos.paintjson.elems;

public class TokenError {

	private String errorString;

	public TokenError(String errorString) {
		this.errorString = errorString;
	}

	@Override
	public String toString() {
		return "< " + errorString + " >";
	}

}
