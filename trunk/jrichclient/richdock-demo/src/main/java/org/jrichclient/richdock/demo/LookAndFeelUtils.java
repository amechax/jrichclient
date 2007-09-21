package org.jrichclient.richdock.demo;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LookAndFeelUtils {
	public static void setLookAndFeel(String lookAndFeelClassName) throws ClassNotFoundException, 
			InstantiationException, UnsupportedLookAndFeelException, IllegalAccessException {
		UIManager.setLookAndFeel(lookAndFeelClassName);
	}

	public static void setSystemLookAndFeel() {
		try {
			setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
