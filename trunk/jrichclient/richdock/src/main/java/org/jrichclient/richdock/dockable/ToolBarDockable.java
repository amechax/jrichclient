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
package org.jrichclient.richdock.dockable;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.event.SwingPropertyChangeSupport;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;
import org.jrichclient.richdock.helper.DockableHelper;
import org.jrichclient.richdock.utils.XMLUtils;

public class ToolBarDockable implements Dockable {
	private final JToolBar toolBar;
	private final PropertyChangeSupport pcs;
	private final DockableHelper helper;
	
// Constructors ****************************************************************
		
	public ToolBarDockable(JToolBar toolBar) {
		this.toolBar = toolBar;
		this.helper = new ToolBarDockableHelper(this, toolBar.getName());
		pcs = new SwingPropertyChangeSupport(this);
	}
	
// Clone ***********************************************************************
	
	@Override
	public ToolBarDockable clone() throws CloneNotSupportedException {
		return (ToolBarDockable)XMLUtils.duplicate(this, false);
	}
	
// PropertyChangeBroadcaster ***************************************************
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
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
	
// Title ***********************************************************************

	public String getTitle() {
		return helper.getTitle();
	}

	public void setTitle(String title) {
		toolBar.setName(title);
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
		toolBar.setToolTipText(toolTipText);
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
		toolBar.setFloatable(floatable);
		helper.setFloatable(floatable);
	}

	
// DockingPort *****************************************************************
	
	public DockingPort<?> getDockingPort() {
		return helper.getDockingPort();
	}

	public void setDockingPort(DockingPort<?> dockingPort) {
		helper.setDockingPort(dockingPort);
	}
	
// Component *******************************************************************
	
	public JToolBar getComponent() {
		return toolBar;
	}
	
// ToolBarDockableHelper *******************************************************
	
	private class ToolBarDockableHelper extends DockableHelper {
		public ToolBarDockableHelper(Dockable dockable, String title) {
			super(dockable, title);
		}

		@Override
		protected void firePropertyChange(String propertyName, 
				Object oldValue, Object newValue) {
			pcs.firePropertyChange(propertyName, oldValue, newValue);
		}
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
		helper.dispose();
	}
}
