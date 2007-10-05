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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JPopupMenu;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;
import org.jrichclient.richdock.helper.DockableHelper;
import org.jrichclient.richdock.utils.XMLUtils;

@SuppressWarnings("serial")
public class BasicDockable extends Container implements Dockable {
	public static final String PROPERTYNAME_CONTENT = "content";
	
	private final DockableHelper helper;
	private Component content;
	
// Constructors ****************************************************************
	
	public BasicDockable() {
		this(null, "", null, null, null);
	}
	
	public BasicDockable(Component content) {
		this(content, "", null, null, null);
	}
	
	public BasicDockable(Component content, String title) {
		this(content, title, null, null, null);
	}
	
	public BasicDockable(Component content, String title, String iconFile) {
		this(content, title, iconFile, null, null);
	}
	
	public BasicDockable(Component content, String title, String iconFile, String toolTipText) {
		this(content, title, iconFile, toolTipText, null);
	}
	
	public BasicDockable(Component content, String title, String iconFile, String toolTipText, JPopupMenu popupMenu) {
		setLayout(new BorderLayout());
		
		this.helper = new BasicDockableHelper(title, iconFile, toolTipText, popupMenu);
		
		if (content != null) {
			add(content, BorderLayout.CENTER);
			validate();
		}
		this.content = content;
	}
		
// Clone ***********************************************************************
	
	@Override
	public BasicDockable clone() throws CloneNotSupportedException {
		return (BasicDockable)XMLUtils.duplicate(this, false);
	}
	
// Content *********************************************************************
	
	public Component getContent() {
		return content;
	}
	
	public void setContent(Component content) {
		Component oldContent = getContent();
		if (oldContent != null)
			remove(oldContent);
		
		this.content = content;
		if (getContent() != null)
			add(getContent(), BorderLayout.CENTER);
		
		validate();
		
		firePropertyChange(PROPERTYNAME_CONTENT, oldContent, getContent());
	}
		
// Title ***********************************************************************

	public String getTitle() {
		return helper.getTitle();
	}

	public void setTitle(String title) {
		helper.setTitle(title);
	}
	
// Icon ************************************************************************
	
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

// DockingPort *****************************************************************
	
	public DockingPort<?> getDockingPort() {
		return helper.getDockingPort();
	}

	public void setDockingPort(DockingPort<?> dockingPort) {
		helper.setDockingPort(dockingPort);
	}
	
// Component *******************************************************************
	
	public Container getComponent() {
		return this;
	}
	
// BasicDockableHelper *********************************************************
	
	private class BasicDockableHelper extends DockableHelper {
		
		public BasicDockableHelper(String title, String iconFile, 
				String toolTipText, JPopupMenu popupMenu) {
			super(BasicDockable.this, title, iconFile, toolTipText, popupMenu);
		}
		
		@Override
		public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			BasicDockable.this.firePropertyChange(propertyName, oldValue, newValue);
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
