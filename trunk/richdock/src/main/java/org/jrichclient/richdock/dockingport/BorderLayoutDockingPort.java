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
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;
import org.jrichclient.richdock.helper.NamedLocationDockingPortHelper;
import org.jrichclient.richdock.helper.NamedLocationDropHelper;
import org.jrichclient.richdock.utils.XMLUtils;

@SuppressWarnings("serial")
public class BorderLayoutDockingPort extends JPanel implements DockingPort<String> {
// Property names **************************************************************
	
	public static final String PROPERTYNAME_HGAP = "hgap";
	public static final String PROPERTYNAME_VGAP = "vgap";
	
// Private fields **************************************************************
	
	private final BorderLayout layout;
	private final BorderLayoutHelper helper;
	private final BorderLayoutDropHelper dropHelper;
		
// Constructors ****************************************************************
	
	public BorderLayoutDockingPort() {
		layout = new BorderLayout();
		setLayout(layout);
		
		helper = new BorderLayoutHelper();
		addMouseListener(helper.getPopupMouseListener());
		
		dropHelper = new BorderLayoutDropHelper(this);
	}
		
// Clone ***********************************************************************
	
	@Override
	public BorderLayoutDockingPort clone() throws CloneNotSupportedException {
		return (BorderLayoutDockingPort)XMLUtils.duplicate(this, false);
	}
	
// Hgap ************************************************************************
	
	public int getHgap() {
		return layout.getHgap();
	}
	
	public void setHgap(int hgap) {
		int oldHgap = getHgap();
		layout.setHgap(hgap);
		firePropertyChange(PROPERTYNAME_HGAP, oldHgap, getHgap());
	}
	
// VGap ************************************************************************
	
	public int getVgap() {
		return layout.getVgap();
	}
	
	public void setVgap(int vgap) {
		int oldVgap = getVgap();
		layout.setVgap(vgap);
		firePropertyChange(PROPERTYNAME_VGAP, oldVgap, getVgap());
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

// Dock/Undock *****************************************************************

	public void dock(Dockable dockable, String location) {
		helper.dock(dockable, location);
	}

	public void undock(Dockable dockable, boolean disposeOnEmpty) {
		helper.undock(dockable, disposeOnEmpty);
	}
	
// Install/Uninstall ***********************************************************
	
	private void install(Dockable dockable, String location) {
		Component oldComponent = layout.getLayoutComponent(location);
		if (oldComponent != null)
			remove(oldComponent);
		
		add((Component)dockable, location);
		validate();
		
		if (BorderLayout.CENTER.equals(location)) {
			setTitle(dockable.getTitle());
			setIconFile(dockable.getIconFile());
			setToolTipText(dockable.getToolTipText());
			setPopupMenu(dockable.getPopupMenu());
			dockable.addPropertyChangeListener(helper.getDockableListener());
		}
	}
	
	private void uninstall(Dockable dockable, String location) {
		remove((Component)dockable);
		validate();
		
		if (BorderLayout.CENTER.equals(location)) {
			dockable.removePropertyChangeListener(helper.getDockableListener());
			setTitle("");
			setIconFile(null);
			setToolTipText(null);
			setPopupMenu(null);
		}
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

// BorderLayoutHelper **********************************************************
	
	private class BorderLayoutHelper extends NamedLocationDockingPortHelper {
		
		public BorderLayoutHelper() {
			super(BorderLayoutDockingPort.this);
		}

		@Override
		protected void install(Dockable dockable, String location) {
			BorderLayoutDockingPort.this.install(dockable, location);
		}

		@Override
		protected void uninstall(Dockable dockable, String location) {
			BorderLayoutDockingPort.this.uninstall(dockable, location);
		}

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			BorderLayoutDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
	
// BorderLayoutDropHelper ******************************************************
	
	private class BorderLayoutDropHelper extends NamedLocationDropHelper {

		public BorderLayoutDropHelper(Component component) {
			super(BorderLayoutDockingPort.this, component);
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
			if (comp == null)
				return BorderLayout.CENTER;
			
			return (String)layout.getConstraints(comp);
		}	

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			BorderLayoutDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
	
// DisposeOnEmpty **************************************************************
	
	public boolean getDisposeOnEmpty() {
		return helper.getDisposeOnEmpty();
	}

	public void setDisposeOnEmpty(boolean disposeOnEmpty) {
		helper.setDisposeOnEmpty(disposeOnEmpty);
	}

// CanClose ********************************************************************
	
	public boolean canClose() {
		return helper.canClose();
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
