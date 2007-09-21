package org.jrichclient.richdock.dockingport;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import static org.jrichclient.richdock.UnitTestUtils.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

import junit.framework.JUnit4TestAdapter;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPortTester;
import org.jrichclient.richdock.dockable.BasicDockable;
import org.jrichclient.richdock.icons.ImageResources;
import org.junit.Test;

public class TestTabbedPaneDockingPort extends DockingPortTester<Integer> {
	private final TabbedPaneDockingPort dockingPort;
	
// Constructor *****************************************************************
	
	public TestTabbedPaneDockingPort() {
		this(new TabbedPaneDockingPort());
	}

	public TestTabbedPaneDockingPort(TabbedPaneDockingPort dockingPort) {
		super(dockingPort);
		
		this.dockingPort = dockingPort;
	}
	
// Test Constructors ***********************************************************
	
	@Test
	public void testConstructor1() {
		assertEquals("", dockingPort.getTitle());
		assertNull(dockingPort.getIconFile());
		assertNull(dockingPort.getToolTipText());
		assertNull(dockingPort.getPopupMenu());
		assertTrue(dockingPort.isDragable());
		assertTrue(dockingPort.isFloatable());
		assertTrue(dockingPort.isDropable());
		assertNull(dockingPort.getDockingPort());
		assertSame(JTabbedPane.BOTTOM, dockingPort.getTabPlacement());
		assertSame(JTabbedPane.SCROLL_TAB_LAYOUT, dockingPort.getTabLayoutPolicy());
		assertFalse(dockingPort.getSingleTabsAllowed());
		assertEquals(0, dockingPort.getDockableCount());
		assertTrue(dockingPort.canClose());
		assertFalse(dockingPort.getDisposeOnEmpty());
		assertFalse(dockingPort.isDisposed());
	}
	
	@Test
	public void testConstructor2() {
		TabbedPaneDockingPort dockingPort = new TabbedPaneDockingPort(
			JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT, true);
		
		assertEquals("", dockingPort.getTitle());
		assertNull(dockingPort.getIconFile());
		assertNull(dockingPort.getToolTipText());
		assertNull(dockingPort.getPopupMenu());
		assertTrue(dockingPort.isDragable());
		assertTrue(dockingPort.isFloatable());
		assertTrue(dockingPort.isDropable());
		assertNull(dockingPort.getDockingPort());
		assertSame(JTabbedPane.TOP, dockingPort.getTabPlacement());
		assertSame(JTabbedPane.WRAP_TAB_LAYOUT, dockingPort.getTabLayoutPolicy());
		assertTrue(dockingPort.getSingleTabsAllowed());
		assertEquals(0, dockingPort.getDockableCount());
		assertTrue(dockingPort.canClose());
		assertFalse(dockingPort.getDisposeOnEmpty());
		assertFalse(dockingPort.isDisposed());
	}
	
// Clone ***********************************************************************
	
	@Test
	public void testClone() throws CloneNotSupportedException {
		TabbedPaneDockingPort original = new TabbedPaneDockingPort(
			JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT, true);
		original.dock(createTestDockable("Dockable 0"), 0);
		original.dock(createTestDockable("Dockable 1"), 1);
		original.dock(createTestDockable("Dockable 2"), 2);
		String title = original.getTitle();
		String iconFile = original.getIconFile();
		String toolTipText = original.getToolTipText();
		
		TabbedPaneDockingPort copy = original.clone();
		assertEquals(3, copy.getDockableCount());
		
		Dockable dockable0 = copy.getDockable(0);
		assertEquals("Dockable 0", dockable0.getTitle());
		
		Dockable dockable1 = copy.getDockable(1);
		assertEquals("Dockable 1", dockable1.getTitle());
		
		Dockable dockable2 = copy.getDockable(2);
		assertEquals("Dockable 2", dockable2.getTitle());
		
		assertEquals(title, copy.getTitle());
		assertEquals(iconFile, copy.getIconFile());
		assertEquals(toolTipText, copy.getToolTipText());
		
		original.dispose();
		copy.dispose();
	}
	
	private Dockable createTestDockable(String title) {
		JButton testContent = new JButton(title);
		String testIconFile = ImageResources.GLOBE_IMAGE;
		String testToolTipText = title + " ToolTipText";
		JPopupMenu testPopupMenu = new JPopupMenu();
		
		Dockable testDockable = new BasicDockable(testContent, title, 
			testIconFile, testToolTipText, testPopupMenu);
		
		return testDockable;
	}
	
// TestDockTitle ***************************************************************
	
	@Test
	public void testDockTitle() {
		boolean oldSingleTabsAllowed = dockingPort.getSingleTabsAllowed();
		dockingPort.setSingleTabsAllowed(true);
		
		String oldTitle = dockingPort.getTitle();
		String testTitle1 = "Test Title 1";
		String testTitle2 = "Test Title 2";
		Dockable testDockable = createTestDockable(testTitle1);
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(Dockable.PROPERTYNAME_TITLE, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			Dockable.PROPERTYNAME_TITLE, oldTitle, testTitle1));
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			Dockable.PROPERTYNAME_TITLE, testTitle1, testTitle2));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			Dockable.PROPERTYNAME_TITLE, testTitle2, oldTitle));
		
		replay(listener);
		assertEquals(oldTitle, dockingPort.getTitle());
		
		dockingPort.dock(testDockable, 0);
		assertEquals(testTitle1, dockingPort.getTitle());
		assertEquals(testTitle1, dockingPort.getTabbedPane().getTitleAt(0));
		
		testDockable.setTitle(testTitle2);
		assertEquals(testTitle2, dockingPort.getTitle());
		assertEquals(testTitle2, dockingPort.getTabbedPane().getTitleAt(0));
		
		dockingPort.undock(testDockable, false);
		assertEquals(oldTitle, dockingPort.getTitle());
		
		verify(listener);
		dockingPort.removePropertyChangeListener(Dockable.PROPERTYNAME_TITLE, listener);
		dockingPort.setSingleTabsAllowed(oldSingleTabsAllowed);
	}
	
// DockIconFile ****************************************************************
	
	@Test
	public void testDockIconFile() {
		String oldIconFile = dockingPort.getIconFile();
		String testIconFile1 = ImageResources.UP_ARROW_IMAGE;
		String testIconFile2 = ImageResources.DOWN_ARROW_IMAGE;
		Dockable testDockable = createTestDockable("Test");
		testDockable.setIconFile(testIconFile1);
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(Dockable.PROPERTYNAME_ICON_FILE, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			Dockable.PROPERTYNAME_ICON_FILE, oldIconFile, testIconFile1));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			Dockable.PROPERTYNAME_ICON_FILE, testIconFile1, testIconFile2));
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			Dockable.PROPERTYNAME_ICON_FILE, testIconFile2, oldIconFile));
		
		replay(listener);
		assertEquals(oldIconFile, dockingPort.getIconFile());
		
		dockingPort.dock(testDockable, 0);
		assertEquals(testIconFile1, dockingPort.getIconFile());
		
		testDockable.setIconFile(testIconFile2);
		assertEquals(testIconFile2, dockingPort.getIconFile());
		
		dockingPort.undock(testDockable, false);
		assertEquals(oldIconFile, dockingPort.getIconFile());
		
		verify(listener);
		dockingPort.removePropertyChangeListener(Dockable.PROPERTYNAME_TITLE, listener);
	}
	
// DockToolTipText *************************************************************
	
	@Test
	public void testDockToolTipText() {
		boolean oldSingleTabsAllowed = dockingPort.getSingleTabsAllowed();
		dockingPort.setSingleTabsAllowed(true);
		
		String oldToolTipText = dockingPort.getToolTipText();
		String testToolTipText1 = "Test ToolTipText1";
		String testToolTipText2 = "Test ToolTipText2";
		Dockable testDockable = createTestDockable("Test");
		testDockable.setToolTipText(testToolTipText1);
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(Dockable.PROPERTYNAME_TOOL_TIP_TEXT, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			Dockable.PROPERTYNAME_TOOL_TIP_TEXT, oldToolTipText, testToolTipText1));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			Dockable.PROPERTYNAME_TOOL_TIP_TEXT, testToolTipText1, testToolTipText2));
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			Dockable.PROPERTYNAME_TOOL_TIP_TEXT, testToolTipText2, oldToolTipText));
		
		replay(listener);
		assertEquals(oldToolTipText, dockingPort.getToolTipText());
		
		dockingPort.dock(testDockable, 0);
		assertEquals(testToolTipText1, dockingPort.getToolTipText());
		assertEquals(testToolTipText1, dockingPort.getTabbedPane().getToolTipTextAt(0));
		
		testDockable.setToolTipText(testToolTipText2);
		assertEquals(testToolTipText2, dockingPort.getToolTipText());
		assertEquals(testToolTipText2, dockingPort.getTabbedPane().getToolTipTextAt(0));
		
		dockingPort.undock(testDockable, false);
		assertEquals(oldToolTipText, dockingPort.getToolTipText());
		
		verify(listener);
		dockingPort.removePropertyChangeListener(Dockable.PROPERTYNAME_TOOL_TIP_TEXT, listener);
		dockingPort.setSingleTabsAllowed(oldSingleTabsAllowed);
	}
	
// DockPopupMenu ***************************************************************
	
	@Test
	public void testDockPopupMenu() {
		JPopupMenu oldPopupMenu = dockingPort.getPopupMenu();
		JPopupMenu testPopupMenu1 = new JPopupMenu("Test PopupMenu1");
		JPopupMenu testPopupMenu2 = new JPopupMenu("Test PopupMenu2");
		Dockable testDockable = createTestDockable("Test");
		testDockable.setPopupMenu(testPopupMenu1);
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(Dockable.PROPERTYNAME_POPUP_MENU, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			Dockable.PROPERTYNAME_POPUP_MENU, oldPopupMenu, testPopupMenu1));
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			Dockable.PROPERTYNAME_POPUP_MENU, testPopupMenu1, testPopupMenu2));
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			Dockable.PROPERTYNAME_POPUP_MENU, testPopupMenu2, oldPopupMenu));
		
		replay(listener);
		assertSame(oldPopupMenu, dockingPort.getPopupMenu());
		
		dockingPort.dock(testDockable, 0);
		assertSame(testPopupMenu1, dockingPort.getPopupMenu());
		
		testDockable.setPopupMenu(testPopupMenu2);
		assertSame(testPopupMenu2, dockingPort.getPopupMenu());
		
		dockingPort.undock(testDockable, false);
		assertSame(oldPopupMenu, dockingPort.getPopupMenu());
		
		verify(listener);
		dockingPort.removePropertyChangeListener(Dockable.PROPERTYNAME_POPUP_MENU, listener);
	}
	
// TabPlacement ****************************************************************
	
	@Test
	public void testTabPlacement() {
		int oldTabPlacement = JTabbedPane.BOTTOM;
		int newTabPlacement = JTabbedPane.TOP;
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(
			TabbedPaneDockingPort.PROPERTYNAME_TAB_PLACEMENT, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			TabbedPaneDockingPort.PROPERTYNAME_TAB_PLACEMENT, 
			oldTabPlacement, newTabPlacement));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			TabbedPaneDockingPort.PROPERTYNAME_TAB_PLACEMENT, 
			newTabPlacement, oldTabPlacement));
		
		replay(listener);
		dockingPort.setTabPlacement(newTabPlacement);
		assertSame(newTabPlacement, dockingPort.getTabPlacement());
		
		dockingPort.setTabPlacement(oldTabPlacement);
		assertSame(oldTabPlacement, dockingPort.getTabPlacement());
		
		dockingPort.removePropertyChangeListener(
			TabbedPaneDockingPort.PROPERTYNAME_TAB_PLACEMENT, listener);
		verify(listener);
	}
	
// TabLayoutPolicy *************************************************************
	
	@Test
	public void testTabLayoutPolicy() {
		int oldTabLayoutPolicy = JTabbedPane.SCROLL_TAB_LAYOUT;
		int newTabLayoutPolicy = JTabbedPane.WRAP_TAB_LAYOUT;
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(
			TabbedPaneDockingPort.PROPERTYNAME_TAB_LAYOUT_POLICY, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			TabbedPaneDockingPort.PROPERTYNAME_TAB_LAYOUT_POLICY, 
			oldTabLayoutPolicy, newTabLayoutPolicy));
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			TabbedPaneDockingPort.PROPERTYNAME_TAB_LAYOUT_POLICY, 
			newTabLayoutPolicy, oldTabLayoutPolicy));

		replay(listener);
		dockingPort.setTabLayoutPolicy(newTabLayoutPolicy);
		assertSame(newTabLayoutPolicy, dockingPort.getTabLayoutPolicy());
		
		dockingPort.setTabLayoutPolicy(oldTabLayoutPolicy);
		assertSame(oldTabLayoutPolicy, dockingPort.getTabLayoutPolicy());
		
		dockingPort.removePropertyChangeListener(
			TabbedPaneDockingPort.PROPERTYNAME_TAB_LAYOUT_POLICY, listener);
		verify(listener);
	}
	
// SingleTabsAllowed ***********************************************************
	
	@Test
	public void testSingleTabsAllowed() {
		boolean oldSingleTabsAllowed = dockingPort.getSingleTabsAllowed();
		boolean newSingleTabsAllowed = !oldSingleTabsAllowed;
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(
			TabbedPaneDockingPort.PROPERTYNAME_SINGLE_TABS_ALLOWED, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			TabbedPaneDockingPort.PROPERTYNAME_SINGLE_TABS_ALLOWED, 
			oldSingleTabsAllowed, newSingleTabsAllowed));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			TabbedPaneDockingPort.PROPERTYNAME_SINGLE_TABS_ALLOWED,
			newSingleTabsAllowed, oldSingleTabsAllowed));
		
		replay(listener);
		dockingPort.setSingleTabsAllowed(newSingleTabsAllowed);
		assertSame(newSingleTabsAllowed, dockingPort.getSingleTabsAllowed());
		
		dockingPort.setSingleTabsAllowed(oldSingleTabsAllowed);
		assertSame(oldSingleTabsAllowed, dockingPort.getSingleTabsAllowed());
		
		dockingPort.removePropertyChangeListener(
			TabbedPaneDockingPort.PROPERTYNAME_TAB_LAYOUT_POLICY, listener);
		verify(listener);
	}
	
// Dock/Undock *****************************************************************
	
	@Test
	public void testDockUndock() {
		testDockUndock(Integer.valueOf(0));
	}
	
	// Allow the test to be run with JUnit 3 test runners
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestTabbedPaneDockingPort.class);
	}
}
