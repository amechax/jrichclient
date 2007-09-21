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
package org.jrichclient.richdock.dockingport.tabbar;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.helper.DragHelper;
import org.jrichclient.richdock.icons.ImageResources;

@SuppressWarnings("serial")
public class TabBarComponent extends TabComponent {
	private final TabBarDockingPort dockingPort;
	private final Dockable dockable;
	private final DragHelper dragHelper;
	private final PropertyChangeListener dockableListener;
	private final MouseListener tabMouseListener;

// TabBarComponent *************************************************************
	
	public TabBarComponent(TabBarDockingPort dockingPort, Dockable dockable, Rotation rotation) {
		super(dockable.getTitle(), ImageResources.createIcon(dockable.getIconFile()),
				SwingConstants.LEADING, rotation);
		this.dockingPort = dockingPort;
		this.dockable = dockable;
		dragHelper = new DragHelper(dockable, this);
		
		dockableListener = new DockableListener();
		dockable.addPropertyChangeListener(dockableListener);
		
		tabMouseListener = new TabMouseListener();
		addMouseListener(tabMouseListener);
	}
	
	public Dockable getDockable() {
		return dockable;
	}
	
	private class DockableListener implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent event) {
			String propertyName = event.getPropertyName();
			Object newValue = event.getNewValue();
			if (Dockable.PROPERTYNAME_TITLE.equals(propertyName))
				setText((String)newValue);
			else if (Dockable.PROPERTYNAME_ICON_FILE.equals(propertyName))
				setIcon(ImageResources.createIcon((String)newValue));
			else if (Dockable.PROPERTYNAME_TOOL_TIP_TEXT.equals(propertyName))
				setToolTipText((String)newValue);
		}
	}
	
	private class TabMouseListener extends MouseAdapter {
		private Border defaultBorder;
		private Border hoverBorder;
		
		public TabMouseListener() {
			defaultBorder = new RoundedRectBorder(getBackground(), Color.GRAY, 4, 4, 1);
			hoverBorder = new RoundedRectBorder(getBackground(), Color.BLACK, 4, 4, 1);
		}
		
		@Override
		public void mouseEntered(MouseEvent event) {
			setBorder(hoverBorder);
		}
		
		@Override
		public void mouseExited(MouseEvent event) {
			setBorder(defaultBorder);
		}
		
		@Override
		public void mouseClicked(MouseEvent event) {
			dockingPort.setSelectedDockable(getDockable());
		}
		
		@Override
		public void mousePressed(MouseEvent event) {
			maybeShowPopup(event);
		}
		
		@Override
		public void mouseReleased(MouseEvent event) {
			maybeShowPopup(event);
		}
		
		private void maybeShowPopup(MouseEvent event) {
			if (event.isPopupTrigger()) {
				JPopupMenu popupMenu = dockable.getPopupMenu();
				if (popupMenu != null)
					popupMenu.show(event.getComponent(), event.getX(), event.getY());
			}
		}
	}
	
// Dispose *********************************************************************
	
	public void dispose() {
		dragHelper.dispose();
		dockable.removePropertyChangeListener(dockableListener);
		removeMouseListener(tabMouseListener);
	}
}
