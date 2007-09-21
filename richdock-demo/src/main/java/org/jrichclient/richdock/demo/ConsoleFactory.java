/*
 * JRichClient -- Libraries for Java Rich Client Applications
 * 
 * Copyright 2007 CompuLink, Ltd. 409 Vandiver Drive #4-200,
 * Columbia, Missouri 65202-1562, All Rights Reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.jrichclient.richdock.demo;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import org.jrichclient.richdock.dockable.BasicDockable;
import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.dockingport.ViewDockingPort;
import org.jrichclient.richdock.icons.ImageResources;
import org.jrichclient.richdock.utils.ActionUtils;

public final class ConsoleFactory {
	private static final String VIEW_ICON = "org/jrichclient/richdock/icons/console_view.gif";
	private static final String CLEAR_OUTPUT_ICON = "org/jrichclient/richdock/icons/clear_co.gif";
	private static final String SAVE_ICON = "org/jrichclient/richdock/icons/save.gif";
	
	private static final TextAreaOutputStream OUTPUT_STREAM = 
		new TextAreaOutputStream(5000, 500);
	private static final TextAreaOutputStream ERROR_STREAM = 
		new TextAreaOutputStream(5000, 500);
	
	static {
		System.setOut(new PrintStream(OUTPUT_STREAM));
		System.setErr(new PrintStream(ERROR_STREAM));
	}
		
// Factory methods *************************************************************
	
	public static Dockable createOutputConsole() {
		return createConsoleDockable(new OutputConsole());
	}
	
	public static Dockable createErrorConsole() {
		return createConsoleDockable(new ErrorConsole());
	}
	
	private static Dockable createConsoleDockable(ConsoleDockable consoleDockable) {
		JButton clearConsoleButton = new JButton();
		clearConsoleButton.addActionListener(EventHandler.create(
			ActionListener.class, consoleDockable, "clearConsole"));
		clearConsoleButton.setFocusPainted(false);
		clearConsoleButton.setText(null);
		clearConsoleButton.setMargin(new Insets(0, 0, 0, 0));
		clearConsoleButton.setIcon(ImageResources.createIcon(CLEAR_OUTPUT_ICON));
		
		JButton saveConsoleButton = new JButton();
		saveConsoleButton.addActionListener(EventHandler.create(
			ActionListener.class, consoleDockable, "saveConsole"));
		saveConsoleButton.setFocusPainted(false);
		saveConsoleButton.setMargin(new Insets(0, 0, 0, 0));
		saveConsoleButton.setIcon(ImageResources.createIcon(SAVE_ICON));
		
		JToolBar toolBar = ActionUtils.createEmptyToolBar();
		toolBar.add(saveConsoleButton);
		toolBar.add(clearConsoleButton);
		toolBar.add(ActionUtils.createCloseToolBarButton(consoleDockable));

		ViewDockingPort viewDockingPort = new ViewDockingPort();
		viewDockingPort.dock(consoleDockable, ViewDockingPort.LOCATIONNAME_CONTENT);
		viewDockingPort.setToolBar(toolBar);
		
		return viewDockingPort;
	}
	
	public static abstract class ConsoleDockable extends BasicDockable {
		private final JTextArea textArea;
		private final TextAreaOutputStream stream;
		
		public ConsoleDockable(String title, TextAreaOutputStream stream) {
			this(new JScrollPane(), title, stream);
		}
		
		public ConsoleDockable(JScrollPane scrollPane, String title, TextAreaOutputStream stream) {
			super(scrollPane, title, VIEW_ICON);
			
			textArea = new JTextArea(10, 50);
			textArea.setBackground(Color.WHITE);
			scrollPane.setBorder(BorderFactory.createEmptyBorder(-1, 0, -1, -1));
			scrollPane.setViewportView(textArea);
			
			this.stream = stream;
			stream.addTextArea(textArea);
		}
		
		public void clearConsole() {
			textArea.setText("");
		}
		
    	// file filter for file save dialog
		private static class TextFileFilter extends FileFilter {
			public String getDescription() {
				return "Text Files";
			}
			
			public boolean accept(File file) {
				if (file.isDirectory())
					return true;
				String ext = getExtension(file);
				if (ext != null && ext.equals("txt"))
					return true;
				return false;
			}
			
			private String getExtension(File file) {
				String ext = null;
				String s = file.getName();
				int i = s.lastIndexOf('.');
				
				if (i > 0 && i < s.length() - 1)
					ext = s.substring(i+1).toLowerCase();
				return ext;
			}
		}
		
		public void saveConsole() {
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new TextFileFilter());
			if (fc.showSaveDialog(textArea) == JFileChooser.APPROVE_OPTION) {
				PrintWriter out = null;
				try {
					out = new PrintWriter(new FileWriter(fc.getSelectedFile()));
					out.append(textArea.getText());
				} catch (IOException ex) {
					ex.printStackTrace();
				} finally {
					if (out != null)
						out.close();
				}
			}
		}
		
		public void dispose() {
			super.dispose();
			
			stream.removeTextArea(textArea);
		}
	}
	
	@SuppressWarnings("serial")
	public static class OutputConsole extends ConsoleDockable {
		public OutputConsole() {
			super("System Output", OUTPUT_STREAM);
		}
	}
	
	@SuppressWarnings("serial")
	public static class ErrorConsole extends ConsoleDockable {
		public ErrorConsole() {
			super("System Error", ERROR_STREAM);
		}
	}
	
	private static class TextAreaOutputStream extends OutputStream {
		private final int idealSize;
		private final int maxExcess;
		
		public TextAreaOutputStream(int idealSize, int maxExcess) {
			this.idealSize = idealSize;
			this.maxExcess = maxExcess;
		}
		
		private final List<JTextArea> textAreaList = new ArrayList<JTextArea>();
		
	    // we keep a buffer around for creating 1-char strings, to
	    // avoid the potential horror of thousands of array allocations
	    // per second
	    private byte littlebuf[] = new byte[1];
	    	    
	    public void addTextArea(JTextArea textArea) {
	    	textAreaList.add(textArea);
	    }
	    
	    public void removeTextArea(JTextArea textArea) {
	    	textAreaList.remove(textArea);
	    }

	    // Redirect output to the JTextArea
	    @Override
	    public void write(int b) throws IOException {
	      littlebuf[0] = (byte)b;
	      print(new String(littlebuf, 0, 1 ));
	    }

	    // Redirect output to the JTextArea
	    @Override
	    public void write(byte b[]) throws IOException {
	      print(new String(b, 0, b.length));
	    }

	    // Redirect output to the JTextArea
	    @Override
	    public void write(byte b[], int off, int len) throws IOException {
	      print(new String(b, off, len));
	    }
	    
	    @Override
	    public void flush() throws IOException {
	  	  // nothing need be done here
	    }
	    
	    @Override
	    public void close() throws IOException {
	  	  textAreaList.clear();
	    }
	    
	    private void print(final String str) {
	  	  if (SwingUtilities.isEventDispatchThread()) {
	  		  doPrint(str);
	  		  return;
	  	  }
	  	  
	  	  SwingUtilities.invokeLater(new Runnable() {
	  		  public void run() {
	  			  doPrint(str);
	  		  }
	  	  });
	    }
	    
	    private void doPrint(final String str) {
	    	for (JTextArea textArea : textAreaList) {
		        textArea.append(str);
	
		        // Make sure the last line is always visible
		        textArea.setCaretPosition(textArea.getDocument().getLength());
	
		        // Keep the text area down to a certain character size
		        int excess = textArea.getDocument().getLength() - idealSize;
		        if (excess >= maxExcess) 
		            textArea.replaceRange("", 0, excess);   
	    	}
	    }
	}
}
