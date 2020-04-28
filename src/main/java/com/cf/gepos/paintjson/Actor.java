package com.cf.gepos.paintjson;

import java.util.List;

import com.cf.gepos.paintjson.elems.Element;

public class Actor {

	private Element startEle = Element.Nil;

	public void buildElement() {

		Context ctx = Context.getInstance();
		List<String> lines = ctx.getLines();

		Element curr = startEle;

		for (String ll : lines) {
			char[] chs = ll.toCharArray();
			for (char ch : chs) {
				curr = curr.addComponent(ch);
			}
		}

		ctx.setElement(startEle);
		validate();
	}

	public void validate() {
		startEle.validateComponent();
	}
}
