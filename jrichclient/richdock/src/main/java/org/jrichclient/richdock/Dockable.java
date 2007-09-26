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
package org.jrichclient.richdock;

import java.awt.Component;

import javax.swing.JPopupMenu;

public interface Dockable extends PropertyChangeBroadcaster, Cloneable {
	public static final String PROPERTYNAME_TITLE = "title";
	public static final String PROPERTYNAME_ICON_FILE = "iconFile";
	public static final String PROPERTYNAME_TOOL_TIP_TEXT = "toolTipText";
	public static final String PROPERTYNAME_POPUP_MENU = "popupMenu";
	public static final String PROPERTYNAME_DRAGABLE = "dragable";
	public static final String PROPERTYNAME_FLOATABLE = "floatable";
	public static final String PROPERTYNAME_DOCKING_PORT = "dockingPort";
	public static final String PROPERTYNAME_DISPOSED = "disposed";
	
	public Dockable clone() throws CloneNotSupportedException;
	
	public String getTitle();
	public void setTitle(String title);
	
	public String getIconFile();
	public void setIconFile(String fileName);
	
	public String getToolTipText();
	public void setToolTipText(String toolTipText);
	
	public JPopupMenu getPopupMenu();
	public void setPopupMenu(JPopupMenu popupMenu);
	
	public boolean isDragable();
	public void setDragable(boolean dragable);
	
	public boolean isFloatable();
	public void setFloatable(boolean floatable);
	
	public DockingPort<?> getDockingPort();
	public void setDockingPort(DockingPort<?> dockingPort);
	
	public Component getComponent();
	
	public boolean canClose();
	
	public boolean isDisposed();
	public void dispose();
}
