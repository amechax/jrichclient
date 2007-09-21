package org.jrichclient.richdock.dockingport.tabbar;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import static org.jrichclient.richdock.UnitTestUtils.*;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;

import org.jrichclient.richdock.icons.ImageResources;
import org.junit.Before;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;

public class TestTabComponent {
	private static final String TEST_TEXT = "Test text";
	private static Icon TEST_ICON;
	
	private TabComponent tabComponent;

	@Before
	public void setUp() throws Exception {
		TEST_ICON = ImageResources.createIcon(ImageResources.GLOBE_IMAGE);
		tabComponent = new TabComponent(TEST_TEXT, TEST_ICON);
	}
	
	@Test
	public void testText() {
		PropertyChangeListener mock = createStrictMock(PropertyChangeListener.class);
		tabComponent.addPropertyChangeListener(TabComponent.PROPERTYNAME_TEXT, mock);
		
		propertyChange(mock, new PropertyChangeEvent(
			tabComponent, TabComponent.PROPERTYNAME_TEXT, TEST_TEXT, null));
		propertyChange(mock, new PropertyChangeEvent(
			tabComponent, TabComponent.PROPERTYNAME_TEXT, null, TEST_TEXT));
				
		replay(mock);
		assertSame(TEST_TEXT, tabComponent.getText());
		
		tabComponent.setText(null);
		assertNull(tabComponent.getText());
		
		tabComponent.setText(TEST_TEXT);
		assertSame(TEST_TEXT, tabComponent.getText());
		verify(mock);
	}
	
	@Test
	public void testIcon() {
		PropertyChangeListener mock = createStrictMock(PropertyChangeListener.class);
		tabComponent.addPropertyChangeListener(TabComponent.PROPERTYNAME_ICON, mock);
		
		propertyChange(mock, new PropertyChangeEvent(
			tabComponent, TabComponent.PROPERTYNAME_ICON, TEST_ICON, null));
		propertyChange(mock, new PropertyChangeEvent(
			tabComponent, TabComponent.PROPERTYNAME_ICON, null, TEST_ICON));
		
		replay(mock);
		assertSame(TEST_ICON, tabComponent.getIcon());
		
		tabComponent.setIcon(null);
		assertNull(tabComponent.getIcon());
		
		tabComponent.setIcon(TEST_ICON);
		assertSame(TEST_ICON, tabComponent.getIcon());
		verify(mock);
	}
	
	@Test
	public void testFont() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font oldFont = tabComponent.getFont();
		Font newFont = env.getAllFonts()[0];
		
		PropertyChangeListener mock = createStrictMock(PropertyChangeListener.class);
		tabComponent.addPropertyChangeListener(TabComponent.PROPERTYNAME_FONT, mock);
		
		propertyChange(mock, new PropertyChangeEvent(
			tabComponent, TabComponent.PROPERTYNAME_FONT, oldFont, newFont));
		propertyChange(mock, new PropertyChangeEvent(
			tabComponent, TabComponent.PROPERTYNAME_FONT, newFont, oldFont));
			
		replay(mock);
		assertSame(oldFont, tabComponent.getFont());
		
		tabComponent.setFont(newFont);
		assertSame(newFont, tabComponent.getFont());
		
		tabComponent.setFont(oldFont);
		assertSame(oldFont, tabComponent.getFont());
		verify(mock);
	}
	
	@Test
	public void testSelected() {
		PropertyChangeListener mock = createStrictMock(PropertyChangeListener.class);
		tabComponent.addPropertyChangeListener(TabComponent.PROPERTYNAME_SELECTED, mock);
		
		propertyChange(mock, new PropertyChangeEvent(
			tabComponent, TabComponent.PROPERTYNAME_SELECTED, false, true));
		propertyChange(mock, new PropertyChangeEvent(
			tabComponent, TabComponent.PROPERTYNAME_SELECTED, true, false));
		
		replay(mock);
		assertFalse(tabComponent.isSelected());
		
		tabComponent.setSelected(true);
		assertTrue(tabComponent.isSelected());
		
		tabComponent.setSelected(false);
		assertFalse(tabComponent.isSelected());
		verify(mock);
	}
	
	@Test
	public void testSizeSet() {
		assertTrue(tabComponent.isMinimumSizeSet());
		assertTrue(tabComponent.isMaximumSizeSet());
		assertTrue(tabComponent.isPreferredSizeSet());
	}
	
	@Test
	public void testSizes() {
		assertNotNull(tabComponent.getMinimumSize());
		assertNotNull(tabComponent.getMaximumSize());
		assertNotNull(tabComponent.getPreferredSize());
	}
	
	@Test
	public void testPaintComponent() {
		Dimension size = tabComponent.getPreferredSize();
		tabComponent.setSize(size);
		tabComponent.setSelected(true);
		
		BufferedImage bi = createWhiteImage(size.width, size.height);
		tabComponent.paintComponent(bi.createGraphics());
		assertIsNotTotallyWhite(bi);
	}
	
	// Allow the test to be run with JUnit 3 test runners
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestTabComponent.class);
	}

}
