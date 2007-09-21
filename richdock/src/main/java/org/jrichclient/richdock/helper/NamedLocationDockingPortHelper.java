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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JPopupMenu;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;

public abstract class NamedLocationDockingPortHelper extends DockingPortHelper 
		implements Iterable<Dockable>{
	private final DockingPort<String> dockingPort;
	private final Map<String, Dockable> dockableMap;

// Constructors ****************************************************************
	
	protected NamedLocationDockingPortHelper(DockingPort<String> dockingPort) {
		this(dockingPort, "", null, null, null);
	}
	
	protected NamedLocationDockingPortHelper(DockingPort<String> dockingPort, String title) {
		this(dockingPort, title, null, null, null);
	}
	
	protected NamedLocationDockingPortHelper(DockingPort<String> dockingPort, String title, String iconFile) {
		this(dockingPort, title, iconFile, null, null);
	}
	
	protected NamedLocationDockingPortHelper(DockingPort<String> dockingPort, String title, String iconFile, String toolTipText) {
		this(dockingPort, title, iconFile, toolTipText, null);
	}
	
	protected NamedLocationDockingPortHelper(DockingPort<String> dockingPort, String title, String iconFile, String toolTipText, JPopupMenu popupMenu) {
		super(dockingPort, title, iconFile, toolTipText, popupMenu);
		
		this.dockingPort = dockingPort;
		this.dockableMap = new HashMap<String, Dockable>();
	}
	
// Dock ************************************************************************
	
	public void dock(Dockable dockable, String location) {
		checkInvariants();
		
		DockingPort<?> parent = dockable.getDockingPort();
		if (parent == dockingPort) {
			parent.undock(dockable, false);
		} else if (parent != null) {
			parent.undock(dockable, parent.getDisposeOnEmpty());
		}
		
		if (getDockable(location) != null)
			throw new IllegalArgumentException("Location already occupied: " + location);

		install(dockable, location);
		
		dockableMap.put(location, dockable);
		dockable.setDockingPort(dockingPort);
		int dockableCount = getDockableCount();
		firePropertyChange(DockingPort.PROPERTYNAME_DOCKABLE_COUNT, dockableCount-1, dockableCount);
	}
	
// Undock **********************************************************************
	
	public void undock(Dockable dockable, boolean disposeOnEmpty) {
		checkInvariants();
		if (dockingPort != dockable.getDockingPort())
			return;
		
		uninstall(dockable, getLocation(dockable));

		dockableMap.remove(getLocation(dockable));
		dockable.setDockingPort(null);
		int dockableCount = getDockableCount();
		firePropertyChange(DockingPort.PROPERTYNAME_DOCKABLE_COUNT, dockableCount+1, dockableCount);
		
		if (dockableCount == 0 && disposeOnEmpty)
			dockingPort.dispose();
	}
	
// Install/uninstall ***********************************************************
	
	protected abstract void install(Dockable dockable, String location);
	protected abstract void uninstall(Dockable dockable, String location);
			
// Lookups *********************************************************************
	
	public int getDockableCount() {
		checkInvariants();
		return dockableMap.size();
	}

	public Dockable getDockable(String location) {
		checkInvariants();
		return dockableMap.get(location);
	}

	public String getLocation(Dockable dockable) {
		checkInvariants();
		for (String key : dockableMap.keySet()) {
			if (dockable == dockableMap.get(key))
				return key;
		}
				
		return null;
	}

	public Iterator<Dockable> iterator() {
		checkInvariants();
		return dockableMap.values().iterator();
	}
}
