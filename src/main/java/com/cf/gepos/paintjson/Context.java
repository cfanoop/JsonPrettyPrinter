package com.cf.gepos.paintjson;

import java.util.List;

import com.cf.gepos.paintjson.elems.Element;

public class Context {

	private static Context cxt;
	private String rpath = "/Users/anoopchiramel/Files/jsons/a.json", wpath;
	private List<String> lines;
	private Element curr;

	private Context() {
	}

	public static Context getInstance() {
		if (cxt == null)
			cxt = new Context();
		return cxt;
	}

	public String getReadPath() {
		return rpath;
	}

	public void setLines(List<String> lss) {
		lines = lss;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setElement(Element curr) {
		this.curr = curr;
	}

	public Element getElement() {
		return curr;
	}
}
