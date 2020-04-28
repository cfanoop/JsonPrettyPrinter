package com.cf.gepos.paintjson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class InOutHandler {

	public void readFiles() throws IOException {

		Context ctx = Context.getInstance();
		List<String> lss = Files.readAllLines(Paths.get(ctx.getReadPath()));
		ctx.setLines(lss);
	}

	public void formatAndPrint() {
		Context ctx = Context.getInstance();
		System.out.println(ctx.getElement());
	}
}
