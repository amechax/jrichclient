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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPopupMenu;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;

public abstract class IndexedLocationDockingPortHelper extends DockingPortHelper 
		implements Iterable<Dockable> {
	private final DockingPort<Integer> dockingPort;
	private final List<Dockable> dockableList;
	
// Constructor *****************************************************************
	
	protected IndexedLocationDockingPortHelper(DockingPort<Integer> dockingPort) {
		this(dockingPort, "", null, null, null);
	}
	
	protected IndexedLocationDockingPortHelper(DockingPort<Integer> dockingPort, 
			String title) {
		this(dockingPort, title, null, null, null);
	}
	
	protected IndexedLocationDockingPortHelper(DockingPort<Integer> dockingPort,
			String title, String iconFile) {
		this(dockingPort, title, iconFile, null, null);
	}
	
	protected IndexedLocationDockingPortHelper(DockingPort<Integer> dockingPort,
			String title, String iconFile, String toolTipText) {
		this(dockingPort, title, iconFile, toolTipText, null);
	}

	protected IndexedLocationDockingPortHelper(DockingPort<Integer> dockingPort, 
			String title, String iconFile, String toolTipText, JPopupMenu popupMenu) {
		super(dockingPort, title, iconFile, toolTipText, popupMenu);
		
		this.dockingPort = dockingPort;
		this.dockableList = new ArrayList<Dockable>();
	}
	
// Dock/Undock *****************************************************************
	
	public void dock(Dockable dockable, Integer location) {
		checkInvariants();
		if (location == getLocation(dockable))
			return;
		
		DockingPort<?> parent = dockable.getDockingPort();
		if (parent == dockingPort) {
			parent.undock(dockable, false);
		} else if (parent != null) {
			parent.undock(dockable, parent.getDisposeOnEmpty());
		}
		
		dockableList.add(location.intValue(), dockable);
		dockable.setDockingPort(dockingPort);
		int dockableCount = getDockableCount();
		firePropertyChange(DockingPort.PROPERTYNAME_DOCKABLE_COUNT, dockableCount-1, dockableCount);
		
		install(dockable, location);
	}
	
	public void undock(Dockable dockable, boolean disposeOnEmpty) {
		checkInvariants();
		Integer location = getLocation(dockable);
		if (location == null)
			return;
		
		dockableList.remove(location.intValue());
		dockable.setDockingPort(dockingPort);
		int dockableCount = getDockableCount();
		firePropertyChange(DockingPort.PROPERTYNAME_DOCKABLE_COUNT, dockableCount+1, dockableCount);
		
		uninstall(dockable, location);
		
		if (dockableCount == 0 && disposeOnEmpty)
			dockingPort.dispose();
	}
	
// Install/Uninstall ***********************************************************

	protected abstract void install(Dockable dockable, Integer location);
	protected abstract void uninstall(Dockable dockable, Integer location);
	
// Lookups *********************************************************************
	
	public int getDockableCount() {
		checkInvariants();
		return dockableList.size();
	}
	
	public Dockable getDockable(Integer location) {
		checkInvariants();
		int index = location.intValue();
		if (0 <= index && index < getDockableCount())
			return dockableList.get(index);
		
		return null;
	}
	
	public Integer getLocation(Dockable dockable) {
		checkInvariants();
		int index = dockableList.indexOf(dockable);
		return index == -1 ? null : index;
	}

	public Iterator<Dockable> iterator() {
		checkInvariants();
		return dockableList.iterator();
	}
}
