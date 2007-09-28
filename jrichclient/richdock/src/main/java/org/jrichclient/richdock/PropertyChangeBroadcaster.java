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

import java.beans.PropertyChangeListener;

/**
 * PropertyChangeBroadcaster defines an interface that implements the Subject 
 * in the Observer design pattern.  Implementations of this interface fire bound 
 * property change notifications according to JavaBeans conventions.
 * 
 * @author Bruce Alspaugh
 */
public interface PropertyChangeBroadcaster {
	
	/**
	 * Add a {@link PropertyChangeListener} to the listener list. The listener is 
	 * registered for all properties. The same listener object may be added more 
	 * than once, and will be called as many times as it is added. If 
	 * <code>listener</code> is <code>null</code>, no exception is thrown and no 
	 * action is taken.
	 * 
	 * @param listener - The <code>PropertyChangeListener</code> to be added.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener);
	
	/**
	 * Add a {@link PropertyChangeListener} for a specific property. The 
	 * listener will be invoked only when a call on 
	 * <code>firePropertyChange</code> names that specific property. The same 
	 * listener object may be added more than once. For each property, the 
	 * listener will be invoked the number of times it was added for that 
	 * property. If <code>propertyName</code> or </code>listener</code> is 
	 * <code>null</code>, no exception is thrown and no action is taken.
	 * 
	 * @param propertyName - The name of the property to listen on.
	 * @param listener - The <code>PropertyChangeListener</code> to be added.
	 */
	public void addPropertyChangeListener(String propertyName, 
		PropertyChangeListener listener);
	
	/**
	 * Returns an array of all the listeners that were added to the 
	 * <code>PropertyChangeSupport</code> object with 
	 * <code>addPropertyChangeListener()</code>. 
	 * <p>
	 * If some listeners have been added with a named property, then the 
	 * returned array will be a mixture of <code>PropertyChangeListener</code>s 
	 * and <code>PropertyChangeListenerProxy</code>s. If the calling method is 
	 * interested in distinguishing the listeners then it must test each element 
	 * to see if it's a <code>PropertyChangeListenerProxy</code>, perform the 
	 * cast, and examine the parameter. 
	 * 
	 * @return all of the <ocde>PropertyChangeListener</code>s added or an empty 
	 *         array if no listeners have been added.
	 */
	public PropertyChangeListener[] getPropertyChangeListeners();
	
	/**
	 * Returns an array of all the listeners which have been associated with the 
	 * named property.
	 * 
	 * @param propertyName - The name of the property being listened to.
	 * @return all of the <code>PropertyChangeListener</code>s associated with 
	 *         the named property. If no such listeners have been added, or if 
	 *         <code>propertyName/code> is <code>null</code>, an empty array is 
	 *         returned.
	 */
	public PropertyChangeListener[] getPropertyChangeListeners(String propertyName);
	
	/**
	 * Remove a {@link PropertyChangeListener} from the listener list. This 
	 * removes a PropertyChangeListener that was registered for all properties. 
	 * If <code>listener</code> was added more than once to the same event 
	 * source, it will be notified one less time after being removed. If 
	 * <code>listener</code> is null, or was never added, no exception is thrown 
	 * and no action is taken.
	 * 
	 * @param listener - The <code>PropertyChangeListener</code> to be removed.
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener);
	
	/**
	 * Remove a PropertyChangeListener for a specific property. If 
	 * <code>listener</code> was added more than once to the same event source 
	 * for the specified property, it will be notified one less time after being 
	 * removed. If <code>propertyName</code> is null, no exception is thrown and 
	 * no action is taken. If <code>listener</code> is null, or was never added 
	 * for the specified property, no exception is thrown and no action is taken.
	 * 
	 * @param propertyName - The name of the property that was listened on.
	 * @param listener - The <code>PropertyChangeListener</code> to be removed.
	 */
	public void removePropertyChangeListener(String propertyName, 
		PropertyChangeListener listener);
}
