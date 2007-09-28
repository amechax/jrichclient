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

/**
 * <code>DockingPort</code> is a generic interface that extends the 
 * {@link Dockable} interface to describe <code>java.awt.Components</code> that 
 * can act as a container for other <code>Dockable</code>s. <code>DockingPort</code>s 
 * can contain other <code>Dockable</code>s and can themselves be contained by 
 * other <code>DockingPort</code>s.  
 * <p>
 * A <code>Dockable</code> can only be contained within at most one
 * <code>DockingPort</code> at any given time. It is also important to maintain 
 * the invariant that every child <code>Dockable</code> within a given 
 * <code>DockingPort</code> has that given <code>DockingPort</code> as its 
 * parent. 
 * <p>
 * The interface is generic so as to describe the locations where 
 * <code>Dockable</code>s can be docked within them.  For example, indexed 
 * <code>DockingPort</code>s use an <code>Integer</code> to specify the 
 * locations, whereas named <code>DockingPort</code>s use a <code>String</code> 
 * to describe the locations.  Some <code>DockingPort</code>s can only contain 
 * one <code>Dockable</code> in a named "content" location, in which case they 
 * are referred to as content <code>DockingPort</code>s.
 * <p>
 * <code>DockingPort</code> also extends {@link Iterable} interface so clients 
 * can iterate over the <code>Dockable</code>s contained within them.
 * 
 * 
 * 
 * @author Bruce Alspaugh
 *
 * @param <T> - The generic type of the locations where <code>Dockables</code>
 *              can be docked within the <code>DockingPort</code>
 */
public interface DockingPort<T> extends Dockable, Iterable<Dockable> {
	/**
	 * Bound property name for the dockable count.
	 */
	public static final String PROPERTYNAME_DOCKABLE_COUNT = "dockableCount";
	
	/**
	 * Bound property name for the dropable property.
	 */
	public static final String PROPERTYNAME_DROPABLE = "dropable";
	
	/**
	 * Bound property name for the disposeOnEmpty property.
	 * @see setDisposeOnEmpty().
	 */
	public static final String PROPERTYNAME_DISPOSE_ON_EMPTY = "disposeOnEmpty";
	
	/**
	 * Adds the <code>dockable</code> to the <code>DockingPort</code> in the 
	 * specified <code>location</code>.  The <code>dockable</code> will be 
	 * undocked from any <code>DockingPort</code> it was in previously.  The
	 * dockable count will be incremented and a <code>PropertChangeEvent</code>
	 * will be fired.
	 * 
	 * @param dockable - The <code>dockable</code> to add.
	 * @param location - The <code>location</code> to place the <code>dockable</code>.
	 */
	public void dock(Dockable dockable, T location);
	
	/**
	 * Removes the <code>dockable</code> from the <code>DockingPort</code> if it
	 * is contained within it.  The dockable count will be decremented and a 
	 * <code>PropertyChangeEvent</code> will be fired.
	 * <p>
	 * If the DockingPort becomes empty and <code>disposeOnEmpty</code> is 
	 * <code>true</code>, then the <code>DockingPort</code> will be 
	 * automatically disposed.  
	 * 
	 * @param dockable
	 * @param disposeOnEmpty
	 * @see getDisposeOnEmpty()
	 */
	public void undock(Dockable dockable, boolean disposeOnEmpty);
	
	/**
	 * Returns the location of the <code>dockable</code> or <code>null</code> if 
	 * the <code>dockable</code> is not contained with this <code>DockingPort</code>.
	 * 
	 * @param dockable - The <code>dockable</code> to find the location of.
	 * @return The location of the <code>dockable</code>.
	 */
	public T getLocation(Dockable dockable);
	
	/**
	 * Returns the <code>Dockable</code> located at the specified 
	 * <code>location</code>, or <code>null</code> if there is no dockable there.
	 * 
	 * @param location - The <code>location</code> to check for a dockable.
	 * @return The <code>Dockable</code> at the <code>location</code> or 
	 *         <code>null</code> if there is none.
	 */
	public Dockable getDockable(T location);
	
	/**
	 * Returns the number of <code>Dockable</code>s contained within this 
	 * <code>DockingPort</code>.  A <code>PropertyChangeEvent<code> is fired
	 * whenever the count changes.
	 * 
	 * @return - The number of <code>Dockable</code>s contained within this 
	 *           <code>DockingPort</code>.
	 */
	public int getDockableCount();
	
	/**
	 * Returns a read-only {@link Iterator} over the <code>Dockable</code>s 
	 * contained with the <code>DockingPort</code>.
	 */
	public Iterator<Dockable> iterator();
	
	/**
	 * Specifies whether the user can drop another <code>Dockable</code> into 
	 * this <code>DockingPort</code> using drag and drop.
	 * 
	 * @return dropable
	 */
	public boolean isDropable();
	
	/**
	 * Sets whether the user can drop another <code>Dockable</code> into this
	 * <code>DockingPort</code> using drag and drop and fires a 
	 * <code>PropertyChangeEvent</code>.
	 * 
	 * @param dropable
	 */
	public void setDropable(boolean dropable);
	
	/**
	 * Returns this <code>DockingPort</code>'s preference whether it should be 
	 * disposed when it becomes empty.  Keeping track of this preference is 
	 * useful when the user closes all the child <code>Dockables</code> within 
	 * this <code>DockingPort</code>. Rather than leave this 
	 * <code>DockingPort</code> empty, it could be disposed which would also
	 * undock it from its parent. Client code should honor this preference when 
	 * calling <code>undock</code>.
	 * 
	 * @return - Whether the <code>DockingPort</code> should be disposed when 
	 *           it becomes empty.
	 */
	public boolean getDisposeOnEmpty();
	
	/**
	 * Set this <code>DockingPort</code>'s preference whether it should be 
	 * disposed when it becomes empty and fires a <code>PropertyChangeEvent</code>
	 * if different from its previous value.  Keeping track of this preference 
	 * is useful when the user closes all the child <code>Dockables</code> 
	 * within this <code>DockingPort</code>. Rather than leave this 
	 * <code>DockingPort</code> empty, it could be disposed which would also 
	 * undock it from its parent.  Client code should honor this preference when 
	 * calling <code>undock</code>.
	 * 
	 * @param disposeOnEmpty - Whether the <code>DockingPort</code> should be 
	 *                         disposed when it becomes empty.
	 */
	public void setDisposeOnEmpty(boolean disposeOnEmpty);
}
