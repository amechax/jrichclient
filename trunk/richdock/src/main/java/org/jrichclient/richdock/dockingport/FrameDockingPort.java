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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.WindowConstants;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;
import org.jrichclient.richdock.helper.NamedLocationDockingPortHelper;
import org.jrichclient.richdock.helper.NamedLocationDropHelper;
import org.jrichclient.richdock.icons.ImageResources;
import org.jrichclient.richdock.utils.XMLUtils;

@SuppressWarnings("serial")
public class FrameDockingPort extends JFrame implements DockingPort<String> {
// Location names **************************************************************
	
	public static final String LOCATIONNAME_CONTENT = "content";
	
// Private fields **************************************************************
	
	private final FrameHelper helper;
	private final FrameDropHelper dropHelper;
	private final WindowListener closeListener;
		
// Constructors ****************************************************************
	
	public FrameDockingPort() {
		helper = new FrameHelper();
		helper.setDisposeOnEmpty(true);
		
		dropHelper = new FrameDropHelper(this);
		
		closeListener = new CloseListener();
		addWindowListener(closeListener);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}
		
// Clone ***********************************************************************

	@Override
	public FrameDockingPort clone() throws CloneNotSupportedException {
		return (FrameDockingPort)XMLUtils.duplicate(this, false);
	}
	
// IconFile ********************************************************************
	
	public String getIconFile() {
		return helper.getIconFile();
	}
	
	public void setIconFile(String fileName) {
		setIconImage(ImageResources.getImage(fileName));
		helper.setIconFile(fileName);
	}
		
// ToolTipText *****************************************************************
	
	public String getToolTipText() {
		return helper.getToolTipText();
	}
	
	public void setToolTipText(String toolTipText) {
		helper.setToolTipText(toolTipText);
	}
	
// PopupMenu *******************************************************************
	
	public JPopupMenu getPopupMenu() {
		return helper.getPopupMenu();
	}

	public void setPopupMenu(JPopupMenu popupMenu) {
		helper.setPopupMenu(popupMenu);
	}

// Dragable ********************************************************************
	
	public boolean isDragable() {
		return true;
	}

	public void setDragable(boolean dragable) {
		if (!dragable)
			throw new IllegalArgumentException("JFrame is always dragable");
	}

// Floatable *******************************************************************
	
	public boolean isFloatable() {
		return true;
	}
	
	public void setFloatable(boolean floatable) {
		if (!floatable)
			throw new IllegalArgumentException("JFrame is always floatable");
	}
	
// Dropable ********************************************************************
	
	public boolean isDropable() {
		return dropHelper.isDropable();
	}
	
	public void setDropable(boolean dropable) {
		dropHelper.setDropable(dropable);
	}
	
// DockingPort *****************************************************************
	
	public DockingPort<?> getDockingPort() {
		return helper.getDockingPort();
	}

	public void setDockingPort(DockingPort<?> dockingPort) {
		helper.setDockingPort(dockingPort);
	}
		
// Dock/Undock *****************************************************************

	public void dock(Dockable dockable, String location) {
		if (!LOCATIONNAME_CONTENT.equals(location))
			throw new IllegalArgumentException("Illegal docking location: " + location);
		
		helper.dock(dockable, location);
	}

	public void undock(Dockable dockable, boolean disposeOnEmpty) {
		helper.undock(dockable, disposeOnEmpty);
	}
	
// Install/Uninstall ***********************************************************
	
	private void install(Dockable dockable, String location) {
		if (!LOCATIONNAME_CONTENT.equals(location))
			throw new IllegalArgumentException("Can only dock into content location");
		
		if (dockable instanceof Container) {
			setContentPane((Container) dockable);
		} else {
			JPanel content = new JPanel(new BorderLayout());
			content.add((Component)dockable, BorderLayout.CENTER);
			setContentPane(content);
		}
		validate();
		
		setTitle(dockable.getTitle());
		setIconFile(dockable.getIconFile());
		setToolTipText(dockable.getToolTipText());
		setPopupMenu(dockable.getPopupMenu());
		dockable.addPropertyChangeListener(helper.getDockableListener());
	}
	
	private void uninstall(Dockable dockable) {
		setContentPane(new Container());
		validate();
		
		dockable.removePropertyChangeListener(helper.getDockableListener());
		setTitle("");
		setIconFile(null);
		setToolTipText(null);
		setPopupMenu(null);
	}
	
// Lookups *********************************************************************

	public Dockable getDockable(String location) {
		return helper.getDockable(location);
	}

	public int getDockableCount() {
		return helper.getDockableCount();
	}

	public String getLocation(Dockable dockable) {
		return helper.getLocation(dockable);
	}

	public Iterator<Dockable> iterator() {
		return helper.iterator();
	}
	
// FrameHelper *****************************************************************

	private class FrameHelper extends NamedLocationDockingPortHelper {
		
		public FrameHelper() {
			super(FrameDockingPort.this);
		}

		@Override
		protected void install(Dockable dockable, String location) {
			FrameDockingPort.this.install(dockable, location);
		}

		@Override
		protected void uninstall(Dockable dockable, String location) {
			FrameDockingPort.this.uninstall(dockable);
		}

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			FrameDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
	
// FrameDropHelper *************************************************************
	
	private class FrameDropHelper extends NamedLocationDropHelper {

		public FrameDropHelper(Component component) {
			super(FrameDockingPort.this, component);
		}

		@Override
		protected boolean dropDockable(Dockable dockable, DropTargetDropEvent event) {
			return dropDockable(dockable, LOCATIONNAME_CONTENT);
		}

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			FrameDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
		
// CloseListener ***************************************************************
	
	private class CloseListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent event) {
			if (canClose())
				dispose();
		}
	}
	
// CanClose ********************************************************************
	
	public boolean canClose() {
		return helper.canClose();
	}

// DisposeOnEmpty **************************************************************
	
	public boolean getDisposeOnEmpty() {
		return helper.getDisposeOnEmpty();
	}
	
	public void setDisposeOnEmpty(boolean disposeOnEmpty) {
		helper.setDisposeOnEmpty(disposeOnEmpty);
	}
	
// Dispose *********************************************************************

	public boolean isDisposed() {
		return helper.isDisposed();
	}
	
	@Override
	public void dispose() {
		dropHelper.dispose();
		removeWindowListener(closeListener);
		helper.dispose();
		
		super.dispose();
	}
}
