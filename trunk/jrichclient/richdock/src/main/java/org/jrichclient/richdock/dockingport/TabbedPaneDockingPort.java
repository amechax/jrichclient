/*
 * JRichClient -- Java libraries for rich client applications.
 * Copyright (C) 2007 CompuLink, Ltd. 409 Vandiver Drive #4-200,
 * Columbia, Missouri 65202-1562, All Rights Reserved.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jrichclient.richdock.dockingport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.awt.dnd.DropTargetDropEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;
import org.jrichclient.richdock.helper.DragHelper;
import org.jrichclient.richdock.helper.DropHelper;
import org.jrichclient.richdock.helper.IndexedLocationDockingPortHelper;
import org.jrichclient.richdock.icons.ImageResources;
import org.jrichclient.richdock.utils.XMLUtils;

@SuppressWarnings("serial")
public class TabbedPaneDockingPort extends JPanel implements DockingPort<Integer> {
// Property names **************************************************************
	
	public static final String PROPERTYNAME_TAB_PLACEMENT = "tabPlacement";
	public static final String PROPERTYNAME_TAB_LAYOUT_POLICY = "tabLayoutPolicy";
	public static final String PROPERTYNAME_SINGLE_TABS_ALLOWED = "singleTabsAllowed";
	public static final String PROPERTYNAME_SELECTED_DOCKABLE = "selectedDockable";

// Private fields **************************************************************
	
	private final JTabbedPane tabbedPane;
	private final TabbedHelper helper;
	private final TabbedDragHelper dragHelper;
	private final TabbedDropHelper dropHelper;
	private final PropertyChangeListener dockableListener;
	private final ChangeListener tabChangeListener;
	private boolean singleTabsAllowed;
	private Dockable selectedDockable;
	private Component content;
	
// Constructors ****************************************************************
	
	public TabbedPaneDockingPort() {
		this(JTabbedPane.BOTTOM, JTabbedPane.SCROLL_TAB_LAYOUT, false);
	}
	
	public TabbedPaneDockingPort(int tabPlacement, int tabLayoutPolicy, boolean singleTabsAllowed) {
		super(new BorderLayout());
		
		tabbedPane = new JTabbedPane(tabPlacement, tabLayoutPolicy);
		this.singleTabsAllowed = singleTabsAllowed;
		
		helper = new TabbedHelper("", null, null, null);
		tabbedPane.addMouseListener(helper.getPopupMouseListener());
		
		dragHelper = new TabbedDragHelper(this, tabbedPane);
		dropHelper = new TabbedDropHelper(this);
		dropHelper.setDropable(true);
		
		dockableListener = new DockableListener();
		
		tabChangeListener = new TabChangeListener();
		tabbedPane.addChangeListener(tabChangeListener);
	}
	
// Clone ***********************************************************************
	
	@Override
	public TabbedPaneDockingPort clone() throws CloneNotSupportedException {
		return (TabbedPaneDockingPort)XMLUtils.duplicate(this, false);
	}
	
// JTabbedPane *****************************************************************
	
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
// Title ***********************************************************************
	
	public String getTitle() {
		return helper.getTitle();
	}
	
	public void setTitle(String title) {
		helper.setTitle(title);
	}
	
// IconImage *******************************************************************
	
	public String getIconFile() {
		return helper.getIconFile();
	}

	public void setIconFile(String image) {
		helper.setIconFile(image);
	}
	
// ToolTipText *****************************************************************
	
	public String getToolTipText() {
		return helper.getToolTipText();
	}
	
	public void setToolTipText(String toolTipText) {
		helper.setToolTipText(toolTipText);
	}
	
// Popup menu ******************************************************************
	
	public JPopupMenu getPopupMenu() {
		return helper.getPopupMenu();
	}

	public void setPopupMenu(JPopupMenu popupMenu) {
		helper.setPopupMenu(popupMenu);
	}
	
// Dragable ********************************************************************

	public boolean isDragable() {
		return helper.isDragable();
	}

	public void setDragable(boolean dragable) {
		helper.setDragable(dragable);
	}

// Floatable *******************************************************************
	
	public boolean isFloatable() {
		return helper.isFloatable();
	}

	public void setFloatable(boolean floatable) {
		helper.setFloatable(floatable);
	}

// Dropable ********************************************************************

	public boolean isDropable() {
		return dropHelper.isDropable();
	}
	
	public void setDropable(boolean dropable) {
		dropHelper.setDropable(dropable);
	}

// DockingPort *****************************************************************

	public DockingPort<?> getDockingPort() {
		return helper.getDockingPort();
	}

	public void setDockingPort(DockingPort<?> dockingPort) {
		helper.setDockingPort(dockingPort);
	}
	
// Component *******************************************************************
	
	public JPanel getComponent() {
		return this;
	}
	
// Dock/Undock *****************************************************************
	
	public void dock(Dockable dockable, Integer location) {
		helper.dock(dockable, location);
	}

	public void undock(Dockable dockable, boolean disposeOnEmpty) {
		helper.undock(dockable, disposeOnEmpty);
	}
	
// Tab placement ***************************************************************
	
	public int getTabPlacement() {
		return tabbedPane.getTabPlacement();
	}
	
	public void setTabPlacement(int tabPlacement) {
		int oldTabPlacement = getTabPlacement();
		tabbedPane.setTabPlacement(tabPlacement);
		
		firePropertyChange(PROPERTYNAME_TAB_PLACEMENT, 
			oldTabPlacement, getTabPlacement());
	}
		
// Tab layout policy ***********************************************************
	
	public int getTabLayoutPolicy() {
		return tabbedPane.getTabLayoutPolicy();
	}
	
	public void setTabLayoutPolicy(int tabLayoutPolicy) {
		int oldTabLayoutPolicy = getTabLayoutPolicy();
		tabbedPane.setTabLayoutPolicy(tabLayoutPolicy);
		
		firePropertyChange(PROPERTYNAME_TAB_LAYOUT_POLICY, 
			oldTabLayoutPolicy, getTabLayoutPolicy());
	}
	
// SingleTabsAllowed ***********************************************************
	
	public boolean getSingleTabsAllowed() {
		return singleTabsAllowed;
	}
	
	public void setSingleTabsAllowed(boolean singleTabsAllowed) {
		boolean oldSingleTabsAllowed = getSingleTabsAllowed();
		this.singleTabsAllowed = singleTabsAllowed;
		firePropertyChange(PROPERTYNAME_SINGLE_TABS_ALLOWED, oldSingleTabsAllowed, getSingleTabsAllowed());
	}
	
// Selected dockable ***********************************************************
	
	public Dockable getSelectedDockable() {
		return selectedDockable;
	}
	
	public void setSelectedDockable(Dockable selectedDockable) {
		Dockable oldSelectedDockable = getSelectedDockable();
		if (selectedDockable == oldSelectedDockable)
			return;
		
		this.selectedDockable = selectedDockable;			
		firePropertyChange(PROPERTYNAME_SELECTED_DOCKABLE, selectedDockable, getSelectedDockable());
		
		setTitle(selectedDockable == null ? "" : selectedDockable.getTitle());
		setIconFile(selectedDockable == null ? null : selectedDockable.getIconFile());
		setToolTipText(selectedDockable == null ? null : selectedDockable.getToolTipText());
		setPopupMenu(selectedDockable == null ? null : selectedDockable.getPopupMenu());
	}
	
	private Dockable computeSelectedDockable() {
		int dockableCount = getDockableCount();
		if (dockableCount == 1)
			return getDockable(0);
		
		int selectedIndex = tabbedPane.getSelectedIndex();
		if (0 <= selectedIndex && selectedIndex < dockableCount)
			return getDockable(selectedIndex);
		
		return null;
	}

// Install *********************************************************************
	
	private void install(Dockable dockable, Integer location) {
		dockable.addPropertyChangeListener(dockableListener);
		if (getSingleTabsAllowed()) {
			installTab(dockable, location);
			tabbedPane.setSelectedIndex(location);
		} else {
			switch (getDockableCount()) {
				case 1:
					setContent(dockable.getComponent());
					break;
				case 2:
					setContent(tabbedPane);
					installTab(getDockable(1 - location), 0);
					installTab(dockable, location);
					tabbedPane.setSelectedIndex(location);
					break;
				default:
					installTab(dockable, location);
					tabbedPane.setSelectedIndex(location);
					break;
			}
		}
		
		setSelectedDockable(computeSelectedDockable());
	}
	
	private void installTab(Dockable dockable, int location) {
		tabbedPane.insertTab(dockable.getTitle(), 
			ImageResources.createIcon(dockable.getIconFile()), 
			createTabComponent(dockable), dockable.getToolTipText(), location);
	}
	
	private Component createTabComponent(Dockable dockable) {
		JPanel container = new JPanel(new BorderLayout());
		container.add(dockable.getComponent(), BorderLayout.CENTER);
		container.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		return container;
	}
	
// Uninstall *******************************************************************
	
	private void uninstall(Dockable dockable, Integer location) {
		dockable.removePropertyChangeListener(dockableListener);
		
		if (getSingleTabsAllowed()) {
				tabbedPane.removeTabAt(location);
				if (getDockableCount() == 0)
					setContent(null);
		} else {
			switch (getDockableCount()) {
				case 0:
					setContent(null);
					break;
				case 1:
					setContent(null);
					tabbedPane.removeTabAt(1);
					tabbedPane.removeTabAt(0);
					setContent(getDockable(0).getComponent());
					break;
				default:
					tabbedPane.removeTabAt(location);
					break;
			}
		}
				
		setSelectedDockable(computeSelectedDockable());
	}
				
// Content *********************************************************************
	
	private Component getContent() {
		return content;
	}
	
	private void setContent(Component content) {
		Component oldContent = getContent();
		if (content == oldContent)
			return;
		
		if (oldContent != null)
			remove(oldContent);
		
		this.content = content;
		if (content != null) {
			add(content, BorderLayout.CENTER);
			content.setVisible(true);
		}
		
		validate();
		repaint();
	}
	
// Lookups *********************************************************************

	public int getDockableCount() {
		return helper.getDockableCount();
	}

	public Dockable getDockable(Integer location) {
		return helper.getDockable(location);
	}

	public Integer getLocation(Dockable dockable) {
		return helper.getLocation(dockable);
	}

	public Iterator<Dockable> iterator() {
		return helper.iterator();
	}
	
// TabbedHelper ****************************************************************

	private class TabbedHelper extends IndexedLocationDockingPortHelper {

		protected TabbedHelper(String title, String iconFile, String toolTipText, JPopupMenu popupMenu) {
			super(TabbedPaneDockingPort.this, title, iconFile, toolTipText, popupMenu);
		}

		@Override
		protected void install(final Dockable dockable, final Integer location) {
			TabbedPaneDockingPort.this.install(dockable, location);
		}

		@Override
		protected void uninstall(final Dockable dockable, final Integer location) {
			TabbedPaneDockingPort.this.uninstall(dockable, location);
		}

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			TabbedPaneDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
		
		@Override
		protected Dockable findDockable(Point pt) {
			Dockable dockable = getDockable(tabbedPane.indexAtLocation(pt.x, pt.y));
			return dockable == null ? TabbedPaneDockingPort.this : dockable;
		}
	}
	
// TabbedDragHelper ************************************************************
	
	private class TabbedDragHelper extends DragHelper {

		public TabbedDragHelper(Dockable dockable, Component dragComponent) {
			super(dockable, dragComponent);
		}
		
		@Override
		protected Dockable findDockable(Point pt) {
			return getDockable(tabbedPane.indexAtLocation(pt.x, pt.y));
		}
	}
	
// TabbedDropHelper ************************************************************
	
	private class TabbedDropHelper extends DropHelper {

		public TabbedDropHelper(Component component) {
			super(component);
		}

		@Override
		protected boolean dropDockable(Dockable dockable, DropTargetDropEvent event) {
			if (dockable.getDockingPort() == TabbedPaneDockingPort.this) {
				int dropLocation = getDropLocation(event.getLocation());
				dock(dockable, dropLocation);
				tabbedPane.setSelectedIndex(dropLocation);
			} else {
				dock(dockable, 0);
			}
			
			return true;
		}
		
		private int getDropLocation(Point dropPoint) {
			int mouseIndex = tabbedPane.indexAtLocation(dropPoint.x, dropPoint.y);
			
			int dropIndex = 0;
			if (0 <= mouseIndex && mouseIndex < getDockableCount())
				dropIndex = mouseIndex;
			else if (getDockableCount() > 0)
				dropIndex = getDockableCount() - 1;
			
			return dropIndex;
		}

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			TabbedPaneDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
		
// Dockable Listener ***********************************************************
	
	/**
	 * This PropertyChangeListener observes changes to the title, icon and
	 * toolTipText properties of a Dockable, and updates its assocated tab
	 * on the JTabbedPane accordingly.
	 */
	private class DockableListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent event) {
			String propertyName = event.getPropertyName();
			Dockable dockable = (Dockable)event.getSource();
			int location = getLocation(dockable);
			
			if (PROPERTYNAME_TITLE.equals(propertyName)) {
				String title = (String)event.getNewValue();
				
				if (0 <= location && location < tabbedPane.getTabCount())
					tabbedPane.setTitleAt(location, title);
				
				if (dockable == getSelectedDockable())
					setTitle(title);
			} else if (PROPERTYNAME_ICON_FILE.equals(propertyName)) {
				String iconFile = (String)event.getNewValue();
				Icon icon = ImageResources.createIcon(iconFile);
				
				if (0 <= location && location < tabbedPane.getTabCount())
					tabbedPane.setIconAt(location, icon);
				
				if (dockable == getSelectedDockable())
					setIconFile(iconFile);
			} else if (PROPERTYNAME_TOOL_TIP_TEXT.equals(propertyName)) {
				String toolTipText = (String)event.getNewValue();
				
				if (0 <= location && location < tabbedPane.getTabCount())
					tabbedPane.setToolTipTextAt(location, toolTipText);
				
				if (dockable == getSelectedDockable())
					setToolTipText(toolTipText);
			} else if (PROPERTYNAME_POPUP_MENU.equals(propertyName) && 
						dockable == getSelectedDockable()) {
				setPopupMenu((JPopupMenu)event.getNewValue());
			}
		}
	}	
	
// Tab Change Listener *********************************************************
	
	/**
	 * This ChangeListener observes changes in the selected index of the
	 * JTabbedPane and sets the selected Dockable accordingly.
	 */
	private class TabChangeListener implements ChangeListener {
		private int selectedIndex = -1;

		public void stateChanged(ChangeEvent event) {
			int newSelectedIndex = tabbedPane.getSelectedIndex();
			if (newSelectedIndex != selectedIndex) {
				this.selectedIndex = newSelectedIndex;
				
				if (0 <= newSelectedIndex && newSelectedIndex < getDockableCount())
					setSelectedDockable(getDockable(Integer.valueOf(newSelectedIndex)));
				else setSelectedDockable(null);
			}
		}
	}
	
// CanClose ********************************************************************
	
	public boolean canClose() {
		return helper.canClose();
	}

// DisposeWhenEmpty ************************************************************
	
	public boolean getDisposeOnEmpty() {
		return helper.getDisposeOnEmpty();
	}
	
	public void setDisposeOnEmpty(boolean disposeOnEmpty) {
		helper.setDisposeOnEmpty(disposeOnEmpty);
	}

// Dispose *********************************************************************
	
	public boolean isDisposed() {
		return helper.isDisposed();
	}

	public void dispose() {
		tabbedPane.removeChangeListener(tabChangeListener);
		tabbedPane.removeMouseListener(helper.getPopupMouseListener());
		dropHelper.dispose();
		dragHelper.dispose();
		helper.dispose();
	}
}
