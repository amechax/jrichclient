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
import java.awt.dnd.DropTargetDropEvent;
import java.util.Iterator;

import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;
import org.jrichclient.richdock.helper.NamedLocationDockingPortHelper;
import org.jrichclient.richdock.helper.NamedLocationDropHelper;
import org.jrichclient.richdock.utils.XMLUtils;

@SuppressWarnings("serial")
public class ScrollPaneDockingPort extends JScrollPane implements DockingPort<String> {
// Location names **************************************************************
	
	public static final String LOCATIONNAME_CONTENT = "content";
	
// Private fields **************************************************************

	private final ScrollPaneHelper helper;
	private final ScrollPaneDropHelper dropHelper;
		
// Constructor *****************************************************************
	
	public ScrollPaneDockingPort() {
		helper = new ScrollPaneHelper();
		dropHelper = new ScrollPaneDropHelper(this);
	}
		
// Clone ***********************************************************************

	@Override
	public ScrollPaneDockingPort clone() throws CloneNotSupportedException {
		return (ScrollPaneDockingPort)XMLUtils.duplicate(this, false);
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
	
	public JScrollPane getComponent() {
		return this;
	}

// Dock/Undock *****************************************************************

	public void dock(Dockable dockable, String location) {
		helper.dock(dockable, location);
	}

	public void undock(Dockable dockable, boolean disposeOnEmpty) {
		helper.undock(dockable, disposeOnEmpty);
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

// ScrollPaneHelper ************************************************************
	
	private class ScrollPaneHelper extends NamedLocationDockingPortHelper {

		protected ScrollPaneHelper() {
			super(ScrollPaneDockingPort.this);
		}

		@Override
		protected void install(Dockable dockable, String location) {
			ScrollPaneDockingPort.this.install(dockable, location);
		}

		@Override
		protected void uninstall(Dockable dockable, String location) {
			ScrollPaneDockingPort.this.uninstall(dockable);
		}

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			ScrollPaneDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
	
// Install/Uninstall ***********************************************************
	
	private void install(Dockable dockable, String location) {
		if (!LOCATIONNAME_CONTENT.equals(location))
			throw new IllegalArgumentException("Illegal docking location: " + location);
		
		setViewportView(dockable.getComponent());
		
		setTitle(dockable.getTitle());
		setIconFile(dockable.getIconFile());
		setToolTipText(dockable.getToolTipText());
		setPopupMenu(dockable.getPopupMenu());
		dockable.addPropertyChangeListener(helper.getDockableListener());
	}
	
	private void uninstall(Dockable dockable) {
		setViewport(null);
		
		dockable.removePropertyChangeListener(helper.getDockableListener());
		setTitle("");
		setIconFile(null);
		setToolTipText(null);
		setPopupMenu(null);
	}
	
// ScrollPaneDropHelper ********************************************************
	
	private class ScrollPaneDropHelper extends NamedLocationDropHelper {

		public ScrollPaneDropHelper(Component component) {
			super(ScrollPaneDockingPort.this, component);
		}

		@Override
		protected boolean dropDockable(Dockable dockable, DropTargetDropEvent event) {
			return dropDockable(dockable, LOCATIONNAME_CONTENT);
		}

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			ScrollPaneDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
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
		helper.dispose();
	}

}
