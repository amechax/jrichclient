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

import java.awt.Component;
import java.awt.Point;
import java.awt.dnd.DropTargetDropEvent;
import java.util.Iterator;

import javax.swing.JPopupMenu;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;
import org.jrichclient.richdock.helper.NamedLocationDockingPortHelper;
import org.jrichclient.richdock.helper.NamedLocationDropHelper;
import org.jrichclient.richdock.multisplitpane.DefaultSplitPaneModel;
import org.jrichclient.richdock.multisplitpane.JXMultiSplitPane;
import org.jrichclient.richdock.multisplitpane.MultiSplitLayout;
import org.jrichclient.richdock.multisplitpane.MultiSplitLayout.Leaf;
import org.jrichclient.richdock.multisplitpane.MultiSplitLayout.Node;
import org.jrichclient.richdock.multisplitpane.MultiSplitLayout.Split;
import org.jrichclient.richdock.utils.XMLUtils;

@SuppressWarnings("serial")
public class MultiSplitDockingPort extends JXMultiSplitPane implements DockingPort<String> {
	private final MultiSplitHelper helper;
	private final MultiSplitDropHelper dropHelper;
		
// Constructors ****************************************************************
	
	public MultiSplitDockingPort() {
		this(new DefaultSplitPaneModel(), "", null, null, null);
	}
	
	public MultiSplitDockingPort(MultiSplitLayout.Node model) {
		this(model, "", null, null, null);
	}
	
	public MultiSplitDockingPort(MultiSplitLayout.Node model, String title) {
		this(model, title, null, null, null);
	}
		
	public MultiSplitDockingPort(MultiSplitLayout.Node model, String title, String iconFile) {
		this(model, title, iconFile, null, null);
	}
	
	public MultiSplitDockingPort(MultiSplitLayout.Node model, String title, String iconFile, String toolTipText) {
		this(model, title, iconFile, toolTipText, null);
	}
	
	public MultiSplitDockingPort(MultiSplitLayout.Node model, String title, String iconFile, String toolTipText, JPopupMenu popupMenu) {
		if (model != null)
			setModel(model);
		
		helper = new MultiSplitHelper(title, iconFile, toolTipText, popupMenu);
		setToolTipText(toolTipText);
		addMouseListener(helper.getPopupMouseListener());
		
		dropHelper = new MultiSplitDropHelper(this);
	}
	
// Clone ***********************************************************************
	
	@Override
	public MultiSplitDockingPort clone() throws CloneNotSupportedException {
		return (MultiSplitDockingPort)XMLUtils.duplicate(this, false);
	}
	
// Model ***********************************************************************
	
	public MultiSplitLayout.Node getModel() {
		return getMultiSplitLayout().getModel();
	}
		
// Title ***********************************************************************

	public String getTitle() {
		return helper.getTitle();
	}

	public void setTitle(String title) {
		helper.setTitle(title);
	}
	
// IconFile ********************************************************************
	
	public String getIconFile() {
		return helper.getIconFile();
	}

	public void setIconFile(String fileName) {
		helper.setIconFile(fileName);
	}
	
// ToolTipText *****************************************************************
	
	public void setToolTipText(String toolTipText) {
		super.setToolTipText(toolTipText);
		
		helper.setToolTipText(toolTipText);
	}
		
// Popup menu ******************************************************************
	
	public JPopupMenu getPopupMenu() {
		return helper.getPopupMenu();
	}

	public void setPopupMenu(JPopupMenu popupMenu) {
		helper.setPopupMenu(popupMenu);
	}
	
// Dragable ********************************************************************
	
	public boolean isDragable() {
		return helper.isDragable();
	}
	
	public void setDragable(boolean dragable) {
		helper.setDragable(dragable);
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

// Dock/Undock *****************************************************************	

	public void dock(Dockable dockable, String location) {
		helper.dock(dockable, location);
	}

	public void undock(Dockable dockable, boolean disposeOnEmpty) {
		helper.undock(dockable, disposeOnEmpty);
	}

// Install/Uninstall ***********************************************************
	
	private void install(Dockable dockable, String location) {
		if (findLeaf(getModel(), location) == null)
			throw new IllegalArgumentException("Location is not in model");
		
		add((Component)dockable, location);
		revalidate();		
	}
	
	private void uninstall(Dockable dockable) {
		remove((Component)dockable);
		revalidate();
		repaint();
	}
	
	protected static Leaf findLeaf(Node node, String location) {
		if (node instanceof Leaf) {
			Leaf leaf = (Leaf)node;
			if (location.equals(leaf.getName()))
				return leaf;
		}
		
		if (node instanceof Split) {
			Split split = (Split)node;
			for (Node n : split.getChildren()) {
				Leaf leaf = MultiSplitDockingPort.findLeaf(n, location);
				if (leaf != null)
					return leaf;
			}
		}
		
		return null;
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
	
// MultiSplitHelper ************************************************************

	private class MultiSplitHelper extends NamedLocationDockingPortHelper {
		
		public MultiSplitHelper(String title, String iconFile, String toolTipText, JPopupMenu popupMenu) {
			super(MultiSplitDockingPort.this, title, iconFile, toolTipText, popupMenu);
		}

		@Override
		protected void install(Dockable dockable, String location) {
			MultiSplitDockingPort.this.install(dockable, location);
		}

		@Override
		protected void uninstall(Dockable dockable, String location) {
			MultiSplitDockingPort.this.uninstall(dockable);
		}

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			MultiSplitDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
	
// MultiSplitDropHelper ********************************************************
	
	private class MultiSplitDropHelper extends NamedLocationDropHelper {

		public MultiSplitDropHelper(Component component) {
			super(MultiSplitDockingPort.this, component);
		}

		@Override
		protected boolean dropDockable(Dockable dockable, DropTargetDropEvent event) {
			String dropLocation = getDropLocation(event.getLocation());
			return dropDockable(dockable, dropLocation);
		}
		
		private String getDropLocation(Point point) {
			MultiSplitLayout layout = getMultiSplitLayout();
			Leaf leaf = findLeaf(layout.getModel(), point);
			return leaf == null ? null : leaf.getName();
		}

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			MultiSplitDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
	
// FindLeaf ********************************************************************
	
	protected static Leaf findLeaf(Node node, Point point) {
		if (node.getBounds().contains(point)) {
			if (node instanceof Leaf)
				return (Leaf)node;
			
			if (node instanceof Split) {
				Split split = (Split)node;
				for (Node n : split.getChildren()) {
					Leaf leaf = MultiSplitDockingPort.findLeaf(n, point);
					if (leaf != null)
						return leaf;
				}
			}
		}
		
		return null;
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

	public void dispose() {
		dropHelper.dispose();
		removeMouseListener(helper.getPopupMouseListener());
		helper.dispose();
	}
}
