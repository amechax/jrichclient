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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JViewport;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;
import org.jrichclient.richdock.dockingport.tabbar.RoundedRectBorder;
import org.jrichclient.richdock.helper.DropHelper;
import org.jrichclient.richdock.helper.NamedLocationDockingPortHelper;
import org.jrichclient.richdock.icons.ImageResources;
import org.jrichclient.richdock.utils.XMLUtils;

@SuppressWarnings("serial")
public class ScrollArrowDockingPort extends JPanel implements DockingPort<String> {
// Location name ***************************************************************
	
	public static final String LOCATIONNAME_CONTENT = "content";
	
// Property names **************************************************************
	
	public static final String PROPERTYNAME_HORIZONTAL_ARROW_POLICY = "horizontalArrowPolicy";
	public static final int HORIZONTAL_ARROW_AS_NEEDED = 0;
	public static final int HORIZONTAL_ARROW_NEVER = 1;
	public static final int HORIZONTAL_ARROW_ALWAYS = 2;
	
	public static final String PROPERTYNAME_VERTICAL_ARROW_POLICY = "verticalArrowPolicy";
	public static final int VERTICAL_ARROW_AS_NEEDED = 0;
	public static final int VERTICAL_ARROW_NEVER = 1;
	public static final int VERTICAL_ARROW_ALWAYS = 2;
	
// Private instance variables **************************************************
	
	private final Border defaultBorder;
	private final Border hoverBorder;
	private final MouseListener arrowListener;
	
	private final JLabel upArrow;
	private final JLabel downArrow;
	private final JLabel leftArrow;
	private final JLabel rightArrow;
	
	private final JViewport viewport;
	private final ChangeListener viewportListener;
	private final ScrollArrowHelper helper;
	private final DropHelper dropHelper;
	
	private int hap;
	private int vap;
	
// Constructors ****************************************************************
	
	public ScrollArrowDockingPort() {
		this(HORIZONTAL_ARROW_AS_NEEDED, VERTICAL_ARROW_AS_NEEDED);
	}
	
	public ScrollArrowDockingPort(int hap, int vap) {
		super(new BorderLayout(5, 5));
		
		this.hap = hap;
		this.vap = vap;
		
		defaultBorder = new RoundedRectBorder(getBackground(), Color.GRAY, 4, 4, 1);
		hoverBorder = new RoundedRectBorder(getBackground(), Color.BLACK, 4, 4, 1);
		arrowListener = new ArrowListener();
		
		upArrow = new JLabel(ImageResources.createIcon(ImageResources.UP_ARROW_IMAGE));
		upArrow.setBorder(defaultBorder);
		upArrow.addMouseListener(arrowListener);
		add(upArrow, BorderLayout.NORTH);
		
		downArrow = new JLabel(ImageResources.createIcon(ImageResources.DOWN_ARROW_IMAGE));
		downArrow.setBorder(defaultBorder);
		downArrow.addMouseListener(arrowListener);
		add(downArrow, BorderLayout.SOUTH);
		
		leftArrow = new JLabel(ImageResources.createIcon(ImageResources.LEFT_ARROW_IMAGE));
		leftArrow.setBorder(defaultBorder);
		leftArrow.addMouseListener(arrowListener);
		add(leftArrow, BorderLayout.WEST);
		
		rightArrow = new JLabel(ImageResources.createIcon(ImageResources.RIGHT_ARROW_IMAGE));
		rightArrow.setBorder(defaultBorder);
		rightArrow.addMouseListener(arrowListener);
		add(rightArrow, BorderLayout.EAST);
		
		viewport = new JViewport();
		add(viewport, BorderLayout.CENTER);
		viewportListener = new ViewPortListener();
		viewport.addChangeListener(viewportListener);
		
		helper = new ScrollArrowHelper();
		dropHelper = new ScrollArrowDropHelper(this);
	}
	
// Clone ***********************************************************************
	
	@Override
	public ScrollArrowDockingPort clone() throws CloneNotSupportedException {
		return (ScrollArrowDockingPort)XMLUtils.duplicate(this, false);
	}
	
// Horizontal ******************************************************************
	
	public int getHorizontalArrowPolicy() {
		return hap;
	}
	
	public void setHorizontalArrowPolicy(int hap) {
		int oldHap = getHorizontalArrowPolicy();
		updateArrowVisibility(hap, getVerticalArrowPolicy());
		this.hap = hap;
		firePropertyChange(PROPERTYNAME_HORIZONTAL_ARROW_POLICY, oldHap, getHorizontalArrowPolicy());
	}
	
// Vertical ********************************************************************
	
	public int getVerticalArrowPolicy() {
		return vap;
	}
	
	public void setVerticalArrowPolicy(int vap) {
		int oldVap = getVerticalArrowPolicy();
		updateArrowVisibility(getHorizontalArrowPolicy(), vap);
		this.vap = vap;
		firePropertyChange(PROPERTYNAME_VERTICAL_ARROW_POLICY, oldVap, getVerticalArrowPolicy());
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
		if (!LOCATIONNAME_CONTENT.equals(location))
			throw new IllegalArgumentException("Invalid location: " + location);
		
		viewport.setView((Component)dockable);
		
		setTitle(dockable.getTitle());
		setIconFile(dockable.getIconFile());
		setToolTipText(dockable.getToolTipText());
		setPopupMenu(dockable.getPopupMenu());
		dockable.addPropertyChangeListener(helper.getDockableListener());
	}
	
	private void uninstall(Dockable dockable) {
		viewport.setView(null);
		
		dockable.removePropertyChangeListener(helper.getDockableListener());
		setTitle("");
		setIconFile(null);
		setToolTipText(null);
		setPopupMenu(null);
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
	
// ScrollArrowHelper ***********************************************************

	private class ScrollArrowHelper extends NamedLocationDockingPortHelper {

		protected ScrollArrowHelper() {
			super(ScrollArrowDockingPort.this);
		}

		@Override
		protected void install(Dockable dockable, String location) {
			ScrollArrowDockingPort.this.install(dockable, location);
		}

		@Override
		protected void uninstall(Dockable dockable, String location) {
			ScrollArrowDockingPort.this.uninstall(dockable);
		}

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			ScrollArrowDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
	
// ScrollArrowDropHelper *******************************************************
	
	private class ScrollArrowDropHelper extends DropHelper {

		public ScrollArrowDropHelper(Component component) {
			super(component);
		}

		@Override
		protected boolean dropDockable(Dockable dockable, DropTargetDropEvent event) {
			dock(dockable, LOCATIONNAME_CONTENT);
			return true;
		}

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			ScrollArrowDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
	
// ArrowListener ***************************************************************
	
	private enum Direction { UP, DOWN, LEFT, RIGHT };
	
	private class ArrowListener extends MouseAdapter implements ActionListener {
		private final Timer scrollTimer;
		private Direction direction;
		
		public ArrowListener() {
			scrollTimer = new Timer(60, this);
		}
		
		@Override
		public void mousePressed(MouseEvent event) {
			if (event.getSource() == upArrow)
				direction = Direction.UP;
			else if (event.getSource() == downArrow)
				direction = Direction.DOWN;
			else if (event.getSource() == leftArrow)
				direction = Direction.LEFT;
			else if (event.getSource() == rightArrow)
				direction = Direction.RIGHT;
			
			scroll(direction, 10);
			scrollTimer.stop();
			scrollTimer.start();
		}
		
		@Override
		public void mouseReleased(MouseEvent event) {
			scrollTimer.stop();
		}
		
		public void actionPerformed(ActionEvent event) {
			scroll(direction, 15);
		}
		
		@Override
		public void mouseEntered(MouseEvent event) {
			JComponent comp = (JComponent)event.getSource();
			comp.setBorder(hoverBorder);
		}
		
		@Override
		public void mouseExited(MouseEvent event) {
			JComponent comp = (JComponent)event.getSource();
			comp.setBorder(defaultBorder);
		}
		
		private void scroll(Direction direction, int delta) {
			switch (direction) {
				case UP:
					scrollUp(delta);
					break;
				case DOWN:
					scrollDown(delta);
					break;
				case LEFT:
					scrollLeft(delta);
					break;
				case RIGHT:
					scrollRight(delta);
					break;
			}
		}
		
		private void scrollUp(int delta) {
			Rectangle viewRect = viewport.getViewRect();
			viewRect.y = Math.max(0, viewRect.y - delta);
			viewport.setViewPosition(new Point(viewRect.x, viewRect.y));
		}
		
		private void scrollDown(int delta) {
			Rectangle viewRect = viewport.getViewRect();
			Dimension viewSize = viewport.getViewSize();
			Dimension extentSize = viewport.getExtentSize();
			viewRect.y = Math.min(viewSize.height - extentSize.height, viewRect.y + delta);
			viewport.setViewPosition(new Point(viewRect.x, viewRect.y));
		}
		
		private void scrollLeft(int delta) {
			Rectangle viewRect = viewport.getViewRect();
			viewRect.x = Math.max(0, viewRect.x - delta);
			viewport.setViewPosition(new Point(viewRect.x, viewRect.y));
		}
		
		private void scrollRight(int delta) {
			Rectangle viewRect = viewport.getViewRect();
			Dimension viewSize = viewport.getViewSize();
			Dimension extentSize = viewport.getExtentSize();
			viewRect.x = Math.min(viewSize.width - extentSize.width, viewRect.x + delta);
			viewport.setViewPosition(new Point(viewRect.x, viewRect.y));
		}
	}
	
// ArrowVisibility *************************************************************
	
	private void updateArrowVisibility(int hap, int vap) {
		Dimension viewSize = viewport.getViewSize();
		Dimension extentSize = viewport.getExtentSize();
		
		switch (hap) {
			case HORIZONTAL_ARROW_AS_NEEDED:
				leftArrow.setVisible(viewSize.width > extentSize.width);
				rightArrow.setVisible(viewSize.width > extentSize.width);
				break;
			case HORIZONTAL_ARROW_NEVER:
				leftArrow.setVisible(false);
				rightArrow.setVisible(false);
				break;
			case HORIZONTAL_ARROW_ALWAYS:
				leftArrow.setVisible(true);
				rightArrow.setVisible(true);
				break;
			default:
				throw new IllegalArgumentException("Invalid horizontal arrow policy");
		}
		
		switch (vap) {
			case VERTICAL_ARROW_AS_NEEDED:
				upArrow.setVisible(viewSize.height > extentSize.height);
				downArrow.setVisible(viewSize.height > extentSize.height);
				break;
			case HORIZONTAL_ARROW_NEVER:
				upArrow.setVisible(false);
				downArrow.setVisible(false);
				break;
			case HORIZONTAL_ARROW_ALWAYS:
				upArrow.setVisible(true);
				downArrow.setVisible(true);
				break;
			default:
				throw new IllegalArgumentException("Invalid vertical arrow policy");
		}
	}
	
// ViewPortListener ************************************************************
		
	private class ViewPortListener implements ChangeListener {
		public void stateChanged(ChangeEvent event) {
			updateArrowVisibility(getHorizontalArrowPolicy(), getVerticalArrowPolicy());
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

	public void dispose() {
		upArrow.removeMouseListener(arrowListener);
		downArrow.removeMouseListener(arrowListener);
		leftArrow.removeMouseListener(arrowListener);
		rightArrow.removeMouseListener(arrowListener);
		viewport.removeChangeListener(viewportListener);
		helper.dispose();
	}
}
