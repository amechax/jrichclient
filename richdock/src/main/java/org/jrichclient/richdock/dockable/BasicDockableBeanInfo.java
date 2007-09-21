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

import static org.jrichclient.richdock.utils.PropertyDescriptorFactory.*;

import java.beans.BeanDescriptor;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.EventSetDescriptor;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class BasicDockableBeanInfo extends SimpleBeanInfo {
// PropertyDescriptors *********************************************************
	
	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {
		List<PropertyDescriptor> descriptorList = new ArrayList<PropertyDescriptor>();
		addDockablePropertyDescriptors(descriptorList, BasicDockable.class);
		
		descriptorList.add(createPropertyDescriptor(BasicDockable.class,
			"content", "getContent", "setContent",
			BOUND, NOT_CONSTRAINED, TRANSIENT));
		
		return createPropertyDescriptorArray(descriptorList);
	}
	
// EventSetDescriptors *********************************************************
	
	@Override
	public EventSetDescriptor[] getEventSetDescriptors() {
		return new EventSetDescriptor[] { } ;
	}

// PersistenceDelegate *********************************************************
	
	private static PersistenceDelegate DELEGATE = new Delegate();
	
	private static class Delegate extends DefaultPersistenceDelegate {
		
		@Override
		protected Expression instantiate(Object oldInstance, Encoder out) {
			BasicDockable oldDockable = (BasicDockable)oldInstance;
			return new Expression(oldDockable, BasicDockable.class, "new",
				new Object[] { oldDockable.getContent() });
		}
	}
		
// BeanDescriptor **************************************************************
	
	@Override
	public BeanDescriptor getBeanDescriptor() {
		BeanDescriptor descriptor = new BeanDescriptor(BasicDockable.class);
		descriptor.setValue("persistenceDelegate", DELEGATE);
		return descriptor;
	}
}
