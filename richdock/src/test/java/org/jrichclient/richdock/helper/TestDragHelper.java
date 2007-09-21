package org.jrichclient.richdock.helper;

import static org.junit.Assert.*;

import java.awt.Container;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.jrichclient.richdock.dockable.BasicDockable;
import org.jrichclient.richdock.Dockable;
import org.junit.Before;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;

public class TestDragHelper {
	private Dockable dockable;
	private Transferable transferable;

	@Before
	public void setUp() throws Exception {
		dockable = new BasicDockable(new Container());
		transferable = new DragHelper.LocalObjectTransferable(dockable);
	}
	
	@Test
	public final void testSupportedFlavor() {
		DataFlavor[] flavors = transferable.getTransferDataFlavors();
		assertTrue(transferable.isDataFlavorSupported(flavors[0]));
	}

	@Test
	public final void testUnsupportedFlavor() {
		assertFalse(transferable.isDataFlavorSupported(DataFlavor.stringFlavor));
	}
	
	@Test
	public final void testGetTransferData() throws UnsupportedFlavorException, IOException {
		DataFlavor[] flavors = transferable.getTransferDataFlavors();
		assertSame(dockable, transferable.getTransferData(flavors[0]));
	}
	
	@Test(expected=UnsupportedFlavorException.class)
	public final void testBadGetTransferData() throws UnsupportedFlavorException, IOException {
		transferable.getTransferData(DataFlavor.stringFlavor);
	}
		
	// Allow the test to be run with JUnit 3 test runners
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestDragHelper.class);
	}
}
