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
import java.util.Iterator;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;
import org.jrichclient.richdock.helper.NamedLocationDockingPortHelper;
import org.jrichclient.richdock.helper.NamedLocationDropHelper;
import org.jrichclient.richdock.icons.ImageResources;
import org.jrichclient.richdock.utils.XMLUtils;

@SuppressWarnings("serial")
public class InternalFrameDockingPort extends JInternalFrame implements DockingPort<String> {	
// Location names **************************************************************
	
	public static final String LOCATIONNAME_CONTENT = "content";
	
// Private fields **************************************************************
	
	private final InternalFrameHelper helper;
	private final InternalFrameDropHelper dropHelper;
	private final InternalFrameListener closeListener;
	
	
// Constructor *****************************************************************
	
	public InternalFrameDockingPort() {
		super("", true, true, true, true);

		helper = new InternalFrameHelper();
		helper.setDisposeOnEmpty(true);
		
		dropHelper = new InternalFrameDropHelper(this);
		
		closeListener = new CloseListener();
		addInternalFrameListener(closeListener);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}
	
// Clone ***********************************************************************

	@Override
	public InternalFrameDockingPort clone() throws CloneNotSupportedException {
		return (InternalFrameDockingPort)XMLUtils.duplicate(this, false);
	}
		
// IconImage *******************************************************************
	
	public String getIconFile() {
		return helper.getIconFile();
	}

	public void setIconFile(String fileName) {
		setFrameIcon(ImageResources.createIcon(fileName));
		helper.setIconFile(fileName);
	}
	
// ToolTipText *****************************************************************
	
	@Override
	public void setToolTipText(String toolTipText) {
		super.setToolTipText(toolTipText);
		
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
			throw new IllegalArgumentException("JInternalFrame is always dragable");
	}
	
// Floatable *******************************************************************

	public boolean isFloatable() {
		return helper.isFloatable();
	}

	public void setFloatable(boolean floatable) {
		helper.setFloatable(floatable);
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
	
// Component *******************************************************************
	
	public JInternalFrame getComponent() {
		return this;
	}
	
// Dock/Undock *****************************************************************

	public void dock(Dockable dockable, String location) {
		helper.dock(dockable, location);
	}

	public void undock(Dockable dockable, boolean disposeOnEmpty) {
		helper.undock(dockable, disposeOnEmpty);
	}
		
// Lookups *********************************************************************

	public int getDockableCount() {
		return helper.getDockableCount();
	}

	public Dockable getDockable(String location) {
		return helper.getDockable(location);
	}

	public String getLocation(Dockable dockable) {
		return helper.getLocation(dockable);
	}

	public Iterator<Dockable> iterator() {
		return helper.iterator();
	}
	
// InternalFrameHelper *********************************************************

	private class InternalFrameHelper extends NamedLocationDockingPortHelper {

		protected InternalFrameHelper() {
			super(InternalFrameDockingPort.this);
		}

		@Override
		protected void install(Dockable dockable, String location) {
			InternalFrameDockingPort.this.install(dockable, location);
		}

		@Override
		protected void uninstall(Dockable dockable, String location) {
			InternalFrameDockingPort.this.uninstall(dockable, location);
		}

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			InternalFrameDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
	
	private void install(Dockable dockable, String location) {
		if (!LOCATIONNAME_CONTENT.equals(location))
			throw new IllegalArgumentException("Can only dock into content location");
		
		if (dockable.getComponent() instanceof Container) {
			setContentPane((Container) dockable.getComponent());
		} else {
			JPanel content = new JPanel(new BorderLayout());
			content.add(dockable.getComponent(), BorderLayout.CENTER);
			setContentPane(content);
		}
		validate();
		repaint();
		
		setTitle(dockable.getTitle());
		setIconFile(dockable.getIconFile());
		setToolTipText(dockable.getToolTipText());
		setPopupMenu(dockable.getPopupMenu());
		dockable.addPropertyChangeListener(helper.getDockableListener());
	}
	
	private void uninstall(Dockable dockable, String location) {
		setContentPane(new Container());
		validate();
		repaint();
		
		dockable.removePropertyChangeListener(helper.getDockableListener());
		setTitle("");
		setIconFile(null);
		setToolTipText(null);
		setPopupMenu(null);
	}
	
// InternalFrameDropHelper *****************************************************
	
	private class InternalFrameDropHelper extends NamedLocationDropHelper {

		public InternalFrameDropHelper(Component component) {
			super(InternalFrameDockingPort.this, component);
		}

		@Override
		protected boolean dropDockable(Dockable dockable, DropTargetDropEvent event) {
			return dropDockable(dockable, LOCATIONNAME_CONTENT);
		}

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			InternalFrameDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
		
// CloseListener ***************************************************************
	
	private class CloseListener extends InternalFrameAdapter {

		@Override
		public void internalFrameClosing(InternalFrameEvent event) {
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
		removeInternalFrameListener(closeListener);
		helper.dispose();
		
		super.dispose();
	}

}
