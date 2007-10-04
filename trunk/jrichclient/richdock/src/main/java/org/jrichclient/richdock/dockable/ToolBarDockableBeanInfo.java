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

import java.beans.EventSetDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class ToolBarDockableBeanInfo extends SimpleBeanInfo {
// PropertyDescriptors *********************************************************
		
	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {
		List<PropertyDescriptor> descriptorList = new ArrayList<PropertyDescriptor>();
		addDockablePropertyDescriptors(descriptorList, ToolBarDockable.class);
		return createPropertyDescriptorArray(descriptorList);
	}
		
// EventSetDescriptors *********************************************************
		
	@Override
	public EventSetDescriptor[] getEventSetDescriptors() {
		return new EventSetDescriptor[] { } ;
	}
}
