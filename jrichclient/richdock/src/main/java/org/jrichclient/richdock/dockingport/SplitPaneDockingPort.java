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

import java.awt.Component;
import java.awt.Point;
import java.awt.dnd.DropTargetDropEvent;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.plaf.SplitPaneUI;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;
import org.jrichclient.richdock.helper.NamedLocationDockingPortHelper;
import org.jrichclient.richdock.helper.NamedLocationDropHelper;
import org.jrichclient.richdock.utils.XMLUtils;

@SuppressWarnings("serial")
public class SplitPaneDockingPort extends JSplitPane implements DockingPort<String> {
	private final SplitHelper helper;
	private final SplitDropHelper dropHelper;
		
// Constructors ****************************************************************
	
	public SplitPaneDockingPort() {
		this("", null, null, null);
	}
	
	public SplitPaneDockingPort(String title) {
		this(title, null, null, null);
	}
	
	public SplitPaneDockingPort(String title, String iconFile) {
		this(title, iconFile, null, null);
	}
	
	public SplitPaneDockingPort(String title, String iconFile, String toolTipText) {
		this(title, iconFile, toolTipText, null);
	}
	
	public SplitPaneDockingPort(String title, String iconFile, String toolTipText, JPopupMenu popupMenu) {
		super(JSplitPane.HORIZONTAL_SPLIT, true);
		
		setBorder(BorderFactory.createEmptyBorder());
		SplitPaneUI splitPaneUI = getUI();
        if (splitPaneUI instanceof BasicSplitPaneUI) {
            BasicSplitPaneUI basicUI = (BasicSplitPaneUI)splitPaneUI;
            basicUI.getDivider().setBorder(BorderFactory.createEmptyBorder());
        }
        
		helper = new SplitHelper(title, iconFile, toolTipText, popupMenu);
		setToolTipText(toolTipText);
		addMouseListener(helper.getPopupMouseListener());
		
		dropHelper = new SplitDropHelper(this);
	}
	
// Clone ***********************************************************************
	
	@Override
	public SplitPaneDockingPort clone() throws CloneNotSupportedException {
		return (SplitPaneDockingPort)XMLUtils.duplicate(this, false);
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
	
	@Override
	public void setToolTipText(String toolTipText) {
		super.setToolTipText(toolTipText);
		
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

// Dock/Undock *****************************************************************

	public void dock(Dockable dockable, String location) {
		helper.dock(dockable, location);

	}

	public void undock(Dockable dockable, boolean disposeOnEmpty) {
		helper.undock(dockable, disposeOnEmpty);
	}
	
// Install/Uninstall ***********************************************************
	
	private void install(Dockable dockable, String location) {
		if (JSplitPane.LEFT.equals(location)) {
			setLeftComponent((Component)dockable);
		} else if (JSplitPane.RIGHT.equals(location)){
			setRightComponent((Component)dockable);
		} else if (JSplitPane.TOP.equals(location)) {
			setTopComponent((Component)dockable);
		} else if (JSplitPane.BOTTOM.equals(location)) {
			setBottomComponent((Component)dockable);
		}
		
		validate();
		repaint();
	}
	
	private void uninstall(Dockable dockable) {
		remove((Component)dockable);
		validate();
		repaint();
	}
	
// Lookups *********************************************************************

	public int getDockableCount() {
		return helper.getDockableCount();
	}

	public Dockable getDockable(String location) {
		return helper.getDockable(location);
	}

	public String getLocation(Dockable dockable) {
		return helper.getLocation(dockable);
	}

	public Iterator<Dockable> iterator() {
		return helper.iterator();
	}
	
// SplitDockingPortHelper ******************************************************

	private class SplitHelper extends NamedLocationDockingPortHelper {

		public SplitHelper(String title,  String iconFile, 
				String toolTipText, JPopupMenu popupMenu) {
			super(SplitPaneDockingPort.this, title, iconFile, toolTipText, popupMenu);
		}

		@Override
		protected void install(Dockable dockable, String location) {
			SplitPaneDockingPort.this.install(dockable, location);
		}

		@Override
		protected void uninstall(Dockable dockable, String location) {
			SplitPaneDockingPort.this.uninstall(dockable);
		}

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			SplitPaneDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
	
// SplitDropHelper *************************************************************
	
	private class SplitDropHelper extends NamedLocationDropHelper {

		public SplitDropHelper(Component component) {
			super(SplitPaneDockingPort.this, component);
		}

		@Override
		protected boolean dropDockable(Dockable dockable, DropTargetDropEvent event) {
			String dropLocation = getDropLocation(event.getLocation());
			
			if (dropLocation == null)
				return false;
			
			return dropDockable(dockable, dropLocation);
		}

		private String getDropLocation(Point point) {
			Component comp = getComponentAt(point);
			
			if (getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
				if (comp == getLeftComponent())
					return JSplitPane.LEFT;
				if (comp == getRightComponent())
					return JSplitPane.RIGHT;
			} else {
				if (comp == getTopComponent())
					return JSplitPane.TOP;
				if (comp == getBottomComponent())
					return JSplitPane.BOTTOM;
			}
			
			return null;
		}
		
		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			SplitPaneDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
	
// CanClose ********************************************************************
	
	public boolean canClose() {
		return helper.canClose();
	}

// DisposeOnEmpty **************************************************************

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
		dropHelper.dispose();
		removeMouseListener(helper.getPopupMouseListener());
		helper.dispose();
	}
}
