/*
 * JRichClient -- Libraries for Java Rich Client Applications
 * 
 * Copyright 2007 CompuLink, Ltd. 409 Vandiver Drive #4-200,
 * Columbia, Missouri 65202-1562, All Rights Reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.jrichclient.richdock.demo;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.jrichclient.richdock.dockable.BasicDockable;
import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.dockingport.ViewDockingPort;
import org.jrichclient.richdock.utils.ActionUtils;

public class SystemProperties {
	private static final String PROPERTIES_IMAGE = "org/jrichclient/richdock/icons/prop_ps.gif";
	
	public static Dockable createSystemPropertiesDockable() {
		JScrollPane scrollPane = new JScrollPane(new JTable(new PropertyTableModel()));
		Dockable dockable = new BasicDockable(scrollPane, 
			"System Properties", PROPERTIES_IMAGE);
		
		ViewDockingPort port = new ViewDockingPort();
		port.dock(dockable, ViewDockingPort.LOCATIONNAME_CONTENT);
		port.setToolBar(ActionUtils.createCloseToolBar(dockable));
		return port;
	}
	
	@SuppressWarnings("serial")
	public static class PropertyTableModel extends AbstractTableModel {
		private String[] columnNames = new String[] {
			"Property Name", "Property Value"
		};
		
		private String[][] propertyArray = new String[][] {
			{ "java.version", System.getProperty("java.version") },
			{ "java.vendor", System.getProperty("java.vendor") },
			{ "java.vendor.url", System.getProperty("java.vendor.url") },
			{ "java.class.version", System.getProperty("java.class.version") },
			{ "os.name", System.getProperty("os.name") },
			{ "os.version", System.getProperty("os.version") },
			{ "os.arch", System.getProperty("os.arch") },
			{ "file.separator", System.getProperty("file.separator") },
			{ "path.separator", System.getProperty("path.separator") },
			{ "line.separator", System.getProperty("line.separator") },
			{ "java.specification.version", System.getProperty("java.specification.version") },
			{ "java.vm.specification.version", System.getProperty("java.vm.specification.version") },
			{ "java.vm.specification.vendor", System.getProperty("java.vm.specification.vendor") },
			{ "java.vm.specification.name", System.getProperty("java.vm.specification.name") },
			{ "java.vm.version", System.getProperty("java.vm.version") },
			{ "java.vm.vendor", System.getProperty("java.vm.vendor") },
			{ "java.vm.name", System.getProperty("java.vm.name") }
		};

		public int getColumnCount() {
			return propertyArray[0].length;
		}
		
		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		public int getRowCount() {
			return propertyArray.length;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			return propertyArray[rowIndex][columnIndex];
		}
	}
}
