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

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetListener;
import java.lang.reflect.Method;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;

public abstract class DropHelper extends DropTargetAdapter 
		implements DropTargetListener {
	private final DropTarget dropTarget;
	
// Constructors ****************************************************************
	
	public DropHelper(Component component) {
		this(component, DnDConstants.ACTION_MOVE);
	}
	
	public DropHelper(Component component, int actions) {
		dropTarget = new DropTarget(component, actions, this, true);
		dropTarget.setActive(false);
	}
	
// Dropable ********************************************************************
	
	public boolean isDropable() {
		return dropTarget.isActive();
	}
	
	public void setDropable(boolean dropable) {
		boolean oldDropable = isDropable();
		dropTarget.setActive(dropable);
		firePropertyChange(DockingPort.PROPERTYNAME_DROPABLE, oldDropable, isDropable());
	}
	
// Drop ************************************************************************
		
	public void drop(DropTargetDropEvent event) {
		if (isDropable()) {
			DataFlavor targetFlavor = getTargetFlavor(event.getCurrentDataFlavors());
			Dockable dockable = getDockable(event.getTransferable(), targetFlavor);
			Dockable targetDockable = getTargetDockable(dockable, event.getDropAction());
			
			if (targetDockable != null) {
				event.dropComplete(dropDockable(targetDockable, event));
			} else event.rejectDrop();
		} else event.rejectDrop();
	}
			
	protected DataFlavor getTargetFlavor(DataFlavor[] flavors) {
		for (DataFlavor flavor : flavors) {
			Class<?> dataClass = flavor.getRepresentationClass();
			if (Dockable.class.isAssignableFrom(dataClass)) {
				return flavor;
			}
		}
			
		return null;
	}

	protected Dockable getDockable(Transferable transferable, DataFlavor targetFlavor) {
		if (targetFlavor == null)
			return null;
		
		try {
			return (Dockable)transferable.getTransferData(targetFlavor);
		} catch (Exception ex) {
			return null;
		}
	}
	
	protected Dockable getTargetDockable(Dockable dockable, int dropAction) {
		switch (dropAction) {
			case DnDConstants.ACTION_MOVE:
				return dockable;
			case DnDConstants.ACTION_COPY:
				try {
					Method cloneMethod = dockable.getClass().getDeclaredMethod("clone");
					cloneMethod.setAccessible(true);
					return (Dockable)cloneMethod.invoke(dockable);
				} catch (Exception ex) {
					return null;
				}
			default:
				return null;
		}
	}
	
	protected abstract boolean dropDockable(Dockable dockable, DropTargetDropEvent event);
	
	protected abstract void firePropertyChange(String propertyName, Object oldValue, Object newValue);
	
// Dispose *********************************************************************
	
	public void dispose() {
		dropTarget.removeDropTargetListener(this);
		dropTarget.setComponent(null);
	}
}
