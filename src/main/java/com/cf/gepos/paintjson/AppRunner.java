package com.cf.gepos.paintjson;

public class AppRunner {

	private InOutHandler fh = new InOutHandler();
	private Actor jp = new Actor();

	public static void main(String[] args) {
		AppRunner appRunner = new AppRunner();
		appRunner.start();
	}

	private void start() {

		try {
			fh.readFiles();
			jp.buildElement();
			fh.formatAndPrint();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
