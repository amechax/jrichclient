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
package org.jrichclient.richdock.helper;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;

public abstract class DockableHelper {
	private final Dockable dockable;
	private final MouseListener popupMouseListener;
	
	private String title;
	private String iconFile;
	private String toolTipText;
	private JPopupMenu popupMenu;
	private boolean dragable;
	private boolean floatable;
	private DockingPort<?> dockingPort;
	private boolean disposed;
	
// Constructors ****************************************************************
	
	public DockableHelper(Dockable dockable) {
		this(dockable, "", null, null, null);
	}
	
	public DockableHelper(Dockable dockable, String title) {
		this(dockable, title, null, null, null);
	}
	
	public DockableHelper(Dockable dockable, String title, String iconFile) {
		this(dockable, title, iconFile, null, null);
	}
	
	public DockableHelper(Dockable dockable, String title, String iconFile, String toolTipText) {
		this(dockable, title, iconFile, toolTipText, null);
	}
	
	public DockableHelper(Dockable dockable, String title, String iconFile, String toolTipText, JPopupMenu popupMenu) {
		this.dockable = dockable;
		this.popupMouseListener = new PopupMouseListener();
		
		this.title = title;
		this.iconFile = iconFile;
		this.toolTipText = toolTipText;
		this.popupMenu = popupMenu;
		this.dragable = true;
		this.floatable = true;
		this.dockingPort = null;
		this.disposed = false;
	}
	
// Title ***********************************************************************
	
	public String getTitle() {
		checkInvariants();
		return title;
	}
	
	public void setTitle(String title) {
		checkInvariants();
		String oldTitle = getTitle();
		this.title = title;
		firePropertyChange(Dockable.PROPERTYNAME_TITLE, oldTitle, getTitle());
	}
	
// Icon ************************************************************************

	public String getIconFile() {
		checkInvariants();
		return iconFile;
	}

	public void setIconFile(String fileName) {
		checkInvariants();
		String oldIconFile = getIconFile();
		this.iconFile = fileName;
		firePropertyChange(Dockable.PROPERTYNAME_ICON_FILE, oldIconFile, getIconFile());
	}
	
// ToolTipText *****************************************************************
	
	public String getToolTipText() {
		checkInvariants();
		return toolTipText;
	}

	public void setToolTipText(String toolTipText) {
		checkInvariants();
		String oldToolTipText = getToolTipText();
		this.toolTipText = toolTipText;
		firePropertyChange(Dockable.PROPERTYNAME_TOOL_TIP_TEXT, oldToolTipText, getToolTipText());
	}
	
// PopupMenu *******************************************************************
	
	public JPopupMenu getPopupMenu() {
		checkInvariants();
		return popupMenu;
	}

	public void setPopupMenu(JPopupMenu popupMenu) {
		checkInvariants();
		JPopupMenu oldPopupMenu = getPopupMenu();
		this.popupMenu = popupMenu;
		firePropertyChange(Dockable.PROPERTYNAME_POPUP_MENU, oldPopupMenu, getPopupMenu());
	}
	
// PopupMouseListener **********************************************************
	
	private class PopupMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent event) {
			maybeShowPopup(event);
		}
		
		@Override
		public void mouseReleased(MouseEvent event) {
			maybeShowPopup(event);
		}
		
		private void maybeShowPopup(MouseEvent event) {
			if (event.isPopupTrigger()) {
				Dockable dockable = findDockable(event.getPoint());
				if (dockable != null) {
					JPopupMenu popupMenu = dockable.getPopupMenu();
					if (popupMenu != null)
						popupMenu.show(event.getComponent(), event.getX(), event.getY());
				}
			}
		}
	}
	
	protected Dockable findDockable(Point pt) {
		return dockable;
	}
	
	public MouseListener getPopupMouseListener() {
		checkInvariants();
		return popupMouseListener;
	}
		
// Dragable ********************************************************************

	public boolean isDragable() {
		checkInvariants();
		return dragable;
	}

	public void setDragable(boolean dragable) {
		checkInvariants();
		boolean oldDragable = isDragable();
		this.dragable = dragable;
		firePropertyChange(Dockable.PROPERTYNAME_DRAGABLE, oldDragable, isDragable());
	}
	
// Floatable *******************************************************************

	public boolean isFloatable() {
		checkInvariants();
		return floatable;
	}

	public void setFloatable(boolean floatable) {
		checkInvariants();
		boolean oldFloatable = isFloatable();
		this.floatable = floatable;
		firePropertyChange(Dockable.PROPERTYNAME_FLOATABLE, oldFloatable, isFloatable());
	}
	
// DockingPort *****************************************************************
	
	public DockingPort<?> getDockingPort() {
		checkInvariants();
		return dockingPort;
	}

	public void setDockingPort(DockingPort<?> dockingPort) {
		checkInvariants();
		DockingPort<?> oldDockingPort = getDockingPort();
		this.dockingPort = dockingPort;
		firePropertyChange(Dockable.PROPERTYNAME_DOCKING_PORT, oldDockingPort, getDockingPort());
	}
	
// FirePropertyChange **********************************************************
	
	protected abstract void firePropertyChange(String propertyName, Object oldValue, Object newValue);
	
// CanClose ********************************************************************

	public boolean canClose() {
		checkInvariants();
		return true;
	}
	
// Disposed ********************************************************************
	
	public boolean isDisposed() {
		return disposed;
	}

	public void dispose() {
		if (!isDisposed()) {
			DockingPort<?> parent = dockable.getDockingPort();
			if (parent != null)
				parent.undock(dockable, parent.getDisposeOnEmpty());
			
			this.disposed = true;
			firePropertyChange(Dockable.PROPERTYNAME_DISPOSED, false, isDisposed());
		}
	}
	
// Invariants ******************************************************************
	
	protected void checkInvariants() {
		assert(!isDisposed());
	}

}
