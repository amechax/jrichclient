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
package org.jrichclient.richdock.helper;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceAdapter;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceListener;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingManager;
import org.jrichclient.richdock.dockingport.FrameDockingPort;
import org.jrichclient.richdock.dockingport.TabbedPaneDockingPort;

public class DragHelper extends DragSourceAdapter implements 
		DragGestureListener, DragSourceListener {
	private final Dockable dockable;
	private final DragGestureRecognizer recognizer;
	private int lastAction;
	private Dockable dragDockable;
	
// Constructors ****************************************************************
	
	public DragHelper(Dockable dockable, Component dragComponent) {
		this(dockable, dragComponent, DnDConstants.ACTION_COPY_OR_MOVE);
	}
	
	public DragHelper(Dockable dockable, Component dragComponent, int actions) {
		this.dockable = dockable;
		DragSource dragSource = DragSource.getDefaultDragSource();
		recognizer = dragSource.createDefaultDragGestureRecognizer(
			dragComponent, actions, this);
	}
	
// DragGestureListener *********************************************************
	
	public void dragGestureRecognized(DragGestureEvent event) {
		lastAction = event.getDragAction();
		dragDockable = findDockable(event.getDragOrigin());
		if (dragDockable != null && dragDockable.isDragable())
			event.startDrag(null, new LocalObjectTransferable(dragDockable), this);
	}
	
// DragSourceListener **********************************************************
	
	@Override
	public void dropActionChanged(DragSourceDragEvent event) {
		lastAction = event.getDropAction();
	}

	@Override
	public void dragDropEnd(final DragSourceDropEvent event) {
		if (event.getDropSuccess())
			return;
		
		final Dockable targetDockable = getTargetDockable(dragDockable, lastAction);
		if (targetDockable != null && targetDockable.isFloatable())
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					floatDockable(targetDockable, null, event.getLocation());
				}
			});
	}
			
	protected Dockable findDockable(Point pt) {
		return dockable;
	}

	protected Dockable getTargetDockable(Dockable dockable, int dropAction) {
		switch (dropAction) {
		case DnDConstants.ACTION_MOVE:
			return dockable;
		case DnDConstants.ACTION_COPY:
			try {
				Method cloneMethod = dockable.getClass().getDeclaredMethod("clone");
				cloneMethod.setAccessible(true);
				return (Dockable)cloneMethod.invoke(dockable);
			} catch (Exception ex) {
				return null;
			}
		default:
			return null;
		}
	}
	
	protected Dockable floatDockable(Dockable dockable, Dimension size, Point location) {
		Dimension frameSize = size;
		if (frameSize == null) {
			frameSize = ((Component)dockable).getSize();
			if (frameSize.height != 0 && frameSize.width != 0)
				frameSize.setSize(frameSize.width + 20, frameSize.height + 45);
			else frameSize = null;
		}
		
		TabbedPaneDockingPort tabbedDockingPort = new TabbedPaneDockingPort();
		tabbedDockingPort.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		tabbedDockingPort.setDisposeOnEmpty(true);
		tabbedDockingPort.setDropable(true);
		tabbedDockingPort.dock(dockable, 0);
		
		FrameDockingPort framePort = new FrameDockingPort();
		framePort.dock(tabbedDockingPort, FrameDockingPort.LOCATIONNAME_CONTENT);
		
		if (frameSize == null)
			framePort.pack();
		else framePort.setSize(frameSize);
		
		framePort.setLocation(location);
		DockingManager.getDesktopDockingPort().dock(framePort, 0);
		// framePort.setVisible(true);
		
		return framePort;
	}
	
// LocalObjectTranserable ******************************************************
	
	public static class LocalObjectTransferable implements Transferable {
		private final Object object;
		
		public LocalObjectTransferable(Object object) {
			this.object = object;
		}
		
		public Object getTransferData(DataFlavor flavor)
				throws UnsupportedFlavorException, IOException {
			if (!isDataFlavorSupported(flavor))
				throw new UnsupportedFlavorException(flavor);

			return object;
		}

		public DataFlavor[] getTransferDataFlavors() {
			DataFlavor[] flavors = new DataFlavor[1];
			String mimeType = DataFlavor.javaJVMLocalObjectMimeType + 
				";class=" + object.getClass().getName();
			try {
				flavors[0] = new DataFlavor(mimeType);
				return flavors;
			} catch (ClassNotFoundException ex) {
				return new DataFlavor[0];
			}
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return "application".equals(flavor.getPrimaryType()) && 
			"x-java-jvm-local-objectref".equals(flavor.getSubType()) && 
			flavor.getRepresentationClass().isAssignableFrom(object.getClass());
		}
	}
	
// Dispose *********************************************************************
	
	public void dispose() {
        recognizer.setComponent(null);
		recognizer.removeDragGestureListener(this);
	}

}
