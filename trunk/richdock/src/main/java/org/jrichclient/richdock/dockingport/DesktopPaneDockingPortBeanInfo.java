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
package org.jrichclient.richdock.dockingport;

import static org.jrichclient.richdock.utils.PropertyDescriptorFactory.*;

import java.beans.BeanDescriptor;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.EventSetDescriptor;
import java.beans.PersistenceDelegate;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.beans.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;

public class DesktopPaneDockingPortBeanInfo extends SimpleBeanInfo {
// PropertyDescriptors *********************************************************
	
	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {
		List<PropertyDescriptor> descriptorList = new ArrayList<PropertyDescriptor>();
		addDockingPortPropertyDescriptors(descriptorList, DesktopPaneDockingPort.class);
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
		protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {
			super.initialize(type, oldInstance, newInstance, out);

			DockingPort<?> port = (DockingPort<?>)oldInstance;
			for (Dockable dockable : port)
				out.writeStatement(new Statement(port, "dock", 
					new Object[] { dockable, port.getLocation(dockable) }));
		}
	}
		
// BeanDescriptor **************************************************************
	
	@Override
	public BeanDescriptor getBeanDescriptor() {
		BeanDescriptor descriptor = new BeanDescriptor(DesktopPaneDockingPort.class);
		descriptor.setValue("persistenceDelegate", DELEGATE);
		return descriptor;
	}
}


