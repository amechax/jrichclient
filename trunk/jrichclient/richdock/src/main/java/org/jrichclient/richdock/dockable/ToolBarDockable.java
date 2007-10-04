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

import java.awt.Component;

import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;
import org.jrichclient.richdock.helper.DockableHelper;
import org.jrichclient.richdock.utils.XMLUtils;

@SuppressWarnings("serial")
public class ToolBarDockable extends JToolBar implements Dockable {
	private final DockableHelper helper;
	
// Constructors ****************************************************************
	
	public ToolBarDockable() {
		this("");
	}
	
	public ToolBarDockable(String title) {
		super(title);
		helper = new ToolBarDockableHelper(this, title);
	}
	
// Clone ***********************************************************************
	
	@Override
	public ToolBarDockable clone() throws CloneNotSupportedException {
		return (ToolBarDockable)XMLUtils.duplicate(this, false);
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
	
// DockingPort *****************************************************************
	
	public DockingPort<?> getDockingPort() {
		return helper.getDockingPort();
	}

	public void setDockingPort(DockingPort<?> dockingPort) {
		helper.setDockingPort(dockingPort);
	}
	
// Component *******************************************************************
	
	public Component getComponent() {
		return this;
	}
	
// ToolBarDockableHelper *******************************************************
	
	private class ToolBarDockableHelper extends DockableHelper {
		public ToolBarDockableHelper(Dockable dockable, String title) {
			super(dockable, title);
		}

		@Override
		protected void firePropertyChange(String propertyName, 
				Object oldValue, Object newValue) {
			ToolBarDockable.this.firePropertyChange(propertyName, oldValue, newValue);
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
