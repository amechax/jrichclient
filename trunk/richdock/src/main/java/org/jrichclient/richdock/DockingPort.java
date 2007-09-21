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

import java.util.Iterator;

public interface DockingPort<T> extends Dockable, Iterable<Dockable> {
	public static final String PROPERTYNAME_DOCKABLE_COUNT = "dockableCount";
	public static final String PROPERTYNAME_DROPABLE = "dropable";
	public static final String PROPERTYNAME_DISPOSE_ON_EMPTY = "disposeOnEmpty";
	
	public void dock(Dockable dockable, T location);
	public void undock(Dockable dockable, boolean disposeOnEmpty);
	
	public T getLocation(Dockable dockable);
	public Dockable getDockable(T location);
	public int getDockableCount();
	
	public Iterator<Dockable> iterator();
	
	public boolean isDropable();
	public void setDropable(boolean dropable);
	
	public boolean getDisposeOnEmpty();
	public void setDisposeOnEmpty(boolean disposeOnEmpty);
}
