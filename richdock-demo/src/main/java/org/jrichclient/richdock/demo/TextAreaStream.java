package org.jrichclient.richdock.demo;

import java.io.OutputStream;
import java.io.PrintStream;

public class TextAreaStream extends PrintStream {

	public TextAreaStream(OutputStream out) {
		super(out);
	}

}
