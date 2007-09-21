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
package org.jrichclient.richdock.utils;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.icons.ImageResources;

public class ActionUtils {
	
	public static class CloseDockableActionListener implements ActionListener {
		private Dockable dockable;

		public CloseDockableActionListener() {
			dockable = null;
		}
		
		public CloseDockableActionListener(Dockable dockable) {
			this.dockable = dockable;
		}
		
		public Dockable getDockable() {
			return dockable;
		}
		
		public void setDockable(Dockable dockable) {
			this.dockable = dockable;
		}
		
		public void actionPerformed(ActionEvent event) {
			if (dockable != null && dockable.canClose()) {
				dockable.dispose();
				dockable = null;
			}
		}
	}
	
	public static JToolBar createEmptyToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setRollover(true);
		toolBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		return toolBar;
	}

	public static JButton createCloseToolBarButton(Dockable dockable) {
		JButton closeButton = new JButton();
		closeButton.addActionListener(new CloseDockableActionListener(dockable));

		closeButton.setFocusPainted(false);
		closeButton.setText("");
		closeButton.setMargin(new Insets(1, 1, 1, 1));
		
		closeButton.setIcon(ImageResources.createIcon(ImageResources.CLOSE_IMAGE));
		closeButton.setDisabledIcon(ImageResources.createIcon(ImageResources.CLOSE_DISABLED_IMAGE));
		closeButton.setPressedIcon(ImageResources.createIcon(ImageResources.CLOSE_PRESSED_IMAGE));
		closeButton.setRolloverIcon(ImageResources.createIcon(ImageResources.CLOSE_ROLLOVER_IMAGE));
		
		return closeButton;
	}
	
	public static JToolBar createCloseToolBar(Dockable dockable) {
		JToolBar toolBar = createEmptyToolBar();
		JButton closeButton = createCloseToolBarButton(dockable);
		toolBar.add(closeButton);
		
		return toolBar;
	}
	
// PopupMenu factory methods ***************************************************
	
	public static JMenuItem createCloseDockableMenuItem(Dockable dockable) {
		JMenuItem closeMenuItem = new JMenuItem("Close");
		closeMenuItem.addActionListener(new CloseDockableActionListener(dockable));
		
		return closeMenuItem;
	}
	
	public static JPopupMenu createClosePopupMenu(Dockable dockable) {
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.add(createCloseDockableMenuItem(dockable));
		return popupMenu;
	}

}
