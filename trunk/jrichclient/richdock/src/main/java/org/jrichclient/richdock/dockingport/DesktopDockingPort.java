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
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;

import javax.swing.JPopupMenu;
import javax.swing.event.SwingPropertyChangeSupport;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;
import org.jrichclient.richdock.helper.IndexedLocationDockingPortHelper;
import org.jrichclient.richdock.utils.XMLUtils;

public class DesktopDockingPort implements DockingPort<Integer> {
	public static final String PROPERTYNAME_EXIT_ON_DISPOSE = "exitOnDispose";
	
	private final PropertyChangeSupport pcs;
	private final DesktopDockingPortHelper helper;
	private boolean exitOnDispose;
	
// Constructor *****************************************************************
	
	public DesktopDockingPort() {
		pcs = new SwingPropertyChangeSupport(this);
		helper = new DesktopDockingPortHelper(this);
		helper.setDragable(false);
		helper.setFloatable(false);
		helper.setDisposeOnEmpty(true);
		exitOnDispose = true;
	}
	
// Clone ***********************************************************************

	@Override
	public DesktopDockingPort clone() throws CloneNotSupportedException {
		return (DesktopDockingPort)XMLUtils.duplicate(this, false);
	}
	
// PropertyChangeBroadcaster ***************************************************
	
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	public PropertyChangeListener[] getPropertyChangeListeners() {
		return pcs.getPropertyChangeListeners();
	}

	public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
		return pcs.getPropertyChangeListeners();
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(propertyName, listener);
	}
	
// Size ************************************************************************
	
	public Dimension getSize() {
		return null;
	}

	public void setSize(Dimension size) {
		throw new UnsupportedOperationException("Cannot change the size of the desktop");

	}
	
// Location ********************************************************************

	public Point getLocation() {
		return null;
	}

	public void setLocation(Point location) {
		throw new UnsupportedOperationException("Cannot change the size of the desktop");
	}

	public Integer getLocation(Dockable dockable) {
		return helper.getLocation(dockable);
	}
	
// Title ***********************************************************************
	
	public String getTitle() {
		return helper.getTitle();
	}

	public void setTitle(String title) {
		helper.setTitle(title);
	}

// IconFile ********************************************************************
	
	public String getIconFile() {
		return helper.getIconFile();
	}

	public void setIconFile(String fileName) {
		helper.setIconFile(fileName);
	}
	
// ToolTipText *****************************************************************
	
	public String getToolTipText() {
		return helper.getToolTipText();
	}

	public void setToolTipText(String toolTipText) {
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
		return false;
	}

	public void setDragable(boolean dragable) {
		if (dragable)
			throw new IllegalArgumentException("Desktop cannot be made dragable.");
	}
	
// Floatable *******************************************************************

	public boolean isFloatable() {
		return false;
	}

	public void setFloatable(boolean floatable) {
		if (floatable)
			throw new IllegalArgumentException("Desktop cannot be made floatable.");
	}

// Dropable ********************************************************************

	public boolean isDropable() {
		return false;
	}

	public void setDropable(boolean dropable) {
		if (dropable)
			throw new IllegalArgumentException("Desktop cannot be make dropable.");
	}

// DockingPort *****************************************************************
	
	public DockingPort<?> getDockingPort() {
		return helper.getDockingPort();
	}

	public void setDockingPort(DockingPort<?> dockingPort) {
		helper.setDockingPort(dockingPort);
	}
	
// Component *******************************************************************
	
	public Component getComponent() {
		return null;
	}

// Dock/Undock *****************************************************************

	public void dock(Dockable dockable, Integer location) {
		helper.dock(dockable, location);
	}

	public void undock(Dockable dockable, boolean disposeOnEmpty) {
		helper.undock(dockable, disposeOnEmpty);
	}

	public Dockable getDockable(Integer location) {
		return helper.getDockable(location);
	}

	public int getDockableCount() {
		return helper.getDockableCount();
	}

	public Iterator<Dockable> iterator() {
		return helper.iterator();
	}

// DesktopDockingPortHelper ****************************************************
	
	private class DesktopDockingPortHelper extends IndexedLocationDockingPortHelper {

		protected DesktopDockingPortHelper(DockingPort<Integer> dockingPort) {
			super(dockingPort);
		}

		@Override
		protected void install(Dockable dockable, Integer location) {
			if (!(dockable instanceof Frame))
				throw new IllegalArgumentException("Only frames can be docked in the desktop");
			
			Frame frame = (Frame)dockable.getComponent();
			Dimension size = dockable.getSize();
			if (size == null || size.width == 0 || size.height == 0)
				frame.pack();
			
			Point position = dockable.getLocation();
			if (position == null)
				frame.setLocationByPlatform(true);
			
			frame.setVisible(true);
		}

		@Override
		protected void uninstall(Dockable dockable, Integer location) {
			Frame frame = (Frame)dockable.getComponent();
			frame.setVisible(false);
		}

		@Override
		protected void firePropertyChange(String propertyName, 
				Object oldValue, Object newValue) {
			pcs.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
	
// Close ***********************************************************************
	
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
	
// ExitOnDispose ***************************************************************
	
	public boolean getExitOnDispose() {
		return exitOnDispose;
	}
	
	public void setExitOnDispose(boolean exitOnDispose) {
		boolean oldExitOnDispose = getExitOnDispose();
		this.exitOnDispose = exitOnDispose;
		pcs.firePropertyChange(PROPERTYNAME_EXIT_ON_DISPOSE, oldExitOnDispose, getExitOnDispose());
	}
	
// Dispose *********************************************************************
	
	public boolean isDisposed() {
		return helper.isDisposed();
	}

	public void dispose() {
		helper.dispose();
		if (getExitOnDispose())
			System.exit(0);
	}

}
