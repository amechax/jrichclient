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

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;

public abstract class NamedLocationDropHelper extends DropHelper {
	private DockingPort<String> dockingPort;

	public NamedLocationDropHelper(DockingPort<String> dockingPort, Component component) {
		super(component);
		
		this.dockingPort = dockingPort;
	}

	public NamedLocationDropHelper(DockingPort<String> dockingPort, Component component, int actions) {
		super(component, actions);
		
		this.dockingPort = dockingPort;
	}

	protected boolean dropDockable(Dockable dockable, String location) {
		Dockable oldDockable = dockingPort.getDockable(location);
		
		if (oldDockable == null) {
			dockingPort.dock(dockable, location);
		} else {
			if (oldDockable.canClose()) {
				dockingPort.undock(oldDockable, false);
				dockingPort.dock(dockable, location);
				oldDockable.dispose();
			} else {
				return false;
			}
		}
		
		return true;
	}
}
