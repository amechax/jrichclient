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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

import javax.swing.JPopupMenu;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;

public abstract class DockingPortHelper extends DockableHelper implements Iterable<Dockable> {
// Private fields **************************************************************
	
	private final DockingPort<?> dockingPort;
	private final DockableListener dockableListener;
	private boolean dropable;
	private boolean disposeOnEmpty;
	
// Constructors ****************************************************************
		
	protected DockingPortHelper(DockingPort<?> dockingPort, String title, 
			String iconFile, String toolTipText, JPopupMenu popupMenu) {
		super(dockingPort, title, iconFile, toolTipText, popupMenu);
		
		this.dockingPort = dockingPort;
		this.dockableListener = new DockableListener();
		this.dropable = true;
		this.disposeOnEmpty = false;
	}

// Lookups *********************************************************************

	public abstract int getDockableCount();
	public abstract Iterator<Dockable> iterator();
	
// Dropable ********************************************************************
	
	public boolean isDropable() {
		checkInvariants();
		return dropable;
	}
	
	public void setDropable(boolean dropable) {
		checkInvariants();
		boolean oldDropable = isDropable();
		this.dropable = dropable;
		firePropertyChange(DockingPort.PROPERTYNAME_DROPABLE, oldDropable, isDropable());
	}
	
// DockableListener ************************************************************
	
	private class DockableListener implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent event) {
			String propertyName = event.getPropertyName();
			Object newValue = event.getNewValue();
			
			if (Dockable.PROPERTYNAME_TITLE.equals(propertyName)) {
				dockingPort.setTitle((String)newValue);
			} else if (Dockable.PROPERTYNAME_ICON_FILE.equals(propertyName)) {
				dockingPort.setIconFile((String)newValue);
			} else if (Dockable.PROPERTYNAME_TOOL_TIP_TEXT.equals(propertyName)) {
				dockingPort.setToolTipText((String)newValue);
			} else if (Dockable.PROPERTYNAME_POPUP_MENU.equals(propertyName)) {
				dockingPort.setPopupMenu((JPopupMenu)newValue);
			}
		}
	}
	
	public PropertyChangeListener getDockableListener() {
		return dockableListener;
	}
	
// DisposeOnEmpty **************************************************************
	
	public boolean getDisposeOnEmpty() {
		checkInvariants();
		return disposeOnEmpty;
	}
	
	public void setDisposeOnEmpty(boolean disposeOnEmpty) {
		checkInvariants();
		boolean oldDisposeOnEmpty = getDisposeOnEmpty();
		this.disposeOnEmpty = disposeOnEmpty;
		firePropertyChange(DockingPort.PROPERTYNAME_DISPOSE_ON_EMPTY, 
			oldDisposeOnEmpty, getDisposeOnEmpty());
	}
	
// CanClose ********************************************************************
	
	@Override
	public boolean canClose() {
		checkInvariants();
		for (Dockable dockable : this)
			if (!dockable.canClose())
				return false;
		
		return super.canClose();
	}
	
// Dispose *********************************************************************
	
	private Dockable[] getDockables() {
		Dockable[] dockableArray = new Dockable[getDockableCount()];
		int index = 0;
		for (Dockable dockable : this)
			dockableArray[index++] = dockable;
		
		return dockableArray;
	}
	
	@Override
	public void dispose() {
		this.disposeOnEmpty = false;
		
		Dockable[] dockableArray = getDockables();
		for (Dockable d : dockableArray) 
			d.dispose();
		
		super.dispose();
	}
}
