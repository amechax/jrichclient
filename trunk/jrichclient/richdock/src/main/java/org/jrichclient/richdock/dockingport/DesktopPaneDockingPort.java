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
import java.awt.Dimension;
import java.awt.Point;
import java.awt.dnd.DropTargetDropEvent;
import java.beans.PropertyVetoException;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPopupMenu;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;
import org.jrichclient.richdock.helper.DropHelper;
import org.jrichclient.richdock.helper.IndexedLocationDockingPortHelper;
import org.jrichclient.richdock.utils.XMLUtils;

@SuppressWarnings("serial")
public class DesktopPaneDockingPort extends JDesktopPane implements DockingPort<Integer> {

// Private fields **************************************************************
	
	private static final int X_OFFSET = 30;
	private static final int Y_OFFSET = 30;
	private final DesktopPaneHelper helper;
	private final DesktopPaneDropHelper dropHelper;
		
// Constructors ****************************************************************
	
	public DesktopPaneDockingPort() {
		this("", null, null, null);
	}
	
	public DesktopPaneDockingPort(String title) {
		this(title, null, null, null);
	}
	
	public DesktopPaneDockingPort(String title, String iconFile) {
		this(title, iconFile, null, null);
	}
	
	public DesktopPaneDockingPort(String title, String iconFile, String toolTipText) {
		this(title, iconFile, toolTipText, null);
	}
	
	public DesktopPaneDockingPort(String title, String iconFile, String toolTipText, JPopupMenu popupMenu) {
		helper = new DesktopPaneHelper(title, iconFile, toolTipText, popupMenu);
		setToolTipText(toolTipText);
		addMouseListener(helper.getPopupMouseListener());
		
		dropHelper = new DesktopPaneDropHelper(this);
		dropHelper.setDropable(true);
	}

// Clone ***********************************************************************
	
	@Override
	public DesktopPaneDockingPort clone() throws CloneNotSupportedException {
		return (DesktopPaneDockingPort)XMLUtils.duplicate(this, false);
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

	public void setIconFile(String fileName) {
		helper.setIconFile(fileName);
	}
	
// ToolTipText *****************************************************************
	
	@Override
	public void setToolTipText(String toolTipText) {
		super.setToolTipText(toolTipText);
		
		helper.setToolTipText(toolTipText);
	}
	
// PopupMenu *******************************************************************

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
	
	public JDesktopPane getComponent() {
		return this;
	}

// Dock/Undock *****************************************************************

	public void dock(Dockable dockable, Integer location) {
		helper.dock(dockable, location);
	}

	public void undock(Dockable dockable, boolean disposeOnEmpty) {
		helper.undock(dockable, disposeOnEmpty);
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

// DesktopPaneHelper ***********************************************************
	
	private class DesktopPaneHelper extends IndexedLocationDockingPortHelper {
		
		public DesktopPaneHelper(String title, String iconFile, 
				String toolTipText, JPopupMenu popupMenu) {
			super(DesktopPaneDockingPort.this, title, iconFile, toolTipText, popupMenu);
		}

		@Override
		protected void install(Dockable dockable, Integer location) {
			if (!(dockable.getComponent() instanceof JInternalFrame)) 
				throw new IllegalArgumentException(
					"Only JInternalFrames can be docked in a DesktopPaneDockingPort.");
		
			JInternalFrame frame = (JInternalFrame)dockable.getComponent();
			// save maximized and minimized state because
			// calling pack() could change the state
			boolean maximized = frame.isMaximum();
			boolean minimized = frame.isIcon();

			Dimension frameSize = frame.getSize();
			if (frameSize.width == 0 || frameSize.height == 0)
				frame.pack();
				
			Point frameLocation = frame.getLocation();
			if (frameLocation.x == 0 && frameLocation.y == 0)
				frame.setLocation(location * X_OFFSET, location * Y_OFFSET);
				
			add(frame);
			frame.setVisible(true);
				
			try {
				frame.setMaximum(maximized);
				frame.setIcon(minimized);
			} catch (PropertyVetoException ex) { } //NOPMD
				
			validate();
		}

		@Override
		protected void uninstall(Dockable dockable, Integer location) {
			JInternalFrame frame = (JInternalFrame)dockable.getComponent();
			frame.setVisible(false);
			remove(frame);
			validate();
		}

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			DesktopPaneDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
	
// DesktopPaneDropHelper *******************************************************
	
	private class DesktopPaneDropHelper extends DropHelper {

		public DesktopPaneDropHelper(Component component) {
			super(component);
		}

		@Override
		protected boolean dropDockable(Dockable dockable, DropTargetDropEvent event) {
			BorderLayoutDockingPort borderPort = new BorderLayoutDockingPort();
			borderPort.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			borderPort.setDisposeOnEmpty(true);
			borderPort.dock(dockable, BorderLayout.CENTER);
			
			InternalFrameDockingPort port = new InternalFrameDockingPort();
			port.dock(borderPort, InternalFrameDockingPort.LOCATIONNAME_CONTENT);
			port.setLocation(event.getLocation());
			Dimension size = ((Component)dockable).getSize();
			size.setSize(size.getWidth() + 10.0, size.getHeight() + 45);
			port.setSize(size);
			
			dock(port, getDockableCount());
			return true;
		}

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			DesktopPaneDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
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
		helper.dispose();
	}

}
