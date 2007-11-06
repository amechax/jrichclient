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
package org.jrichclient.richdock;

import java.awt.Component;

import javax.swing.JPopupMenu;
/**
 * The <code>Dockable</code> interface represents a {@link java.awt.Component}
 * that has docking capabilities. It has bound properties for title, iconFile, 
 * toolTipText, popupMenu, dragable, floatable, dockingPort, and disposed.
 * 
 * To make a <code>Component</code> dockable, you can make it to content of a 
 * DockableAdapter, or implement this interface.  A typical implementation would 
 * extend the component so as to implement the interface.  In that case, the 
 * <code>getComponent()</code> method would simply return <code>this</code>.  
 * Note that the JavaBeans standard requires that all visual JavaBeans extend 
 * <code>Component</code>.  
 * 
 * If you choose to implement this interface, there is a {@link DockableHelper} 
 * class that can make implementing the methods defined in this interface much 
 * easer -- often a one line implementation.
 * 
 * RichDock uses JavaBeans persistence to save and restore a 
 * <code>Dockable</code>'s state, so your implementations should be compatible 
 * with it.
 * 
 * @see {@link DockingPort}
 * 
 * @author Bruce Alspaugh
 */
public interface Dockable extends PropertyChangeBroadcaster, Cloneable {
	/** Bound property name for title. */
	public static final String PROPERTYNAME_TITLE = "title";
	
	/** Bound property name for iconFile. */
	public static final String PROPERTYNAME_ICON_FILE = "iconFile";
	
	/** Bound property name for toolTipText. */
	public static final String PROPERTYNAME_TOOL_TIP_TEXT = "toolTipText";
	
	/** Bound property name for popupMenu. */
	public static final String PROPERTYNAME_POPUP_MENU = "popupMenu";
	
	/** Bound property name for dragable. */
	public static final String PROPERTYNAME_DRAGABLE = "dragable";
	
	/** Bound property name for floatable. */
	public static final String PROPERTYNAME_FLOATABLE = "floatable";
	
	/** Bound property name for dockingPort. */
	public static final String PROPERTYNAME_DOCKING_PORT = "dockingPort";
	
	/** Bound property name for disposed. */
	public static final String PROPERTYNAME_DISPOSED = "disposed";
			
	/**
	 * Returns the title of the <code>Dockable</code>.
	 * 
	 * @return title
	 */
	public String getTitle();
	
	/**
	 * Sets the title of the <code>Dockable</code> and fires a 
	 * <code>PropertyChangeEvent</code> if it is different from its previous 
	 * value.  
	 * <p>
	 * Titles should be set to an empty string instead of null because not all 
	 * <code>Components</code> (particularly {@link JFrame}) can
	 * handle a <code>null</code> title.
	 * 
	 * @param title
	 */
	public void setTitle(String title);
	
	/**
	 * Returns the file path of an icon associated with the <code>Dockable</code>.
	 * 
	 * @return icon file path
	 */
	public String getIconFile();
	
	/**
	 * Sets the file path of an icon associated with the <code>Dockable</code>
	 * and fires a <code>PropertyChangeEvent</code> if it is different from its
	 * previous value.
	 * 
	 * @param fileName
	 */
	public void setIconFile(String fileName);
	
	/**
	 * Returns the tooltip text.
	 * 
	 * @return toolTipText
	 */
	public String getToolTipText();
	
	/**
	 * Sets the tooltip text and fires a <code>PropertyChangeEvent</code> if it
	 * is different from its previous value.
	 * 
	 * @param toolTipText
	 */
	public void setToolTipText(String toolTipText);
	
	/**
	 * Returns the popup menu.
	 * 
	 * @return popupMenu
	 */
	public JPopupMenu getPopupMenu();
	
	/**
	 * Sets the popup menu and fires a <code>PropertyChangeEvent</code> if it 
	 * is different from its previous value.
	 * 
	 * @param popupMenu
	 */
	public void setPopupMenu(JPopupMenu popupMenu);
	
	/**
	 * Returns whether the <code>Dockable</code> can be dragged with the mouse.
	 * 
	 * @return dragable
	 */
	public boolean isDragable();
	
	/**
	 * Sets whether the <code>Dockable</code> can be dragged with the mouse and
	 * fires a <code>PropertyChangeEvent</code> if it is different from its
	 * previous value.
	 * 
	 * @param dragable
	 */
	public void setDragable(boolean dragable);
	
	/**
	 * Returns whether the <code>Dockable</code> should be placed in its own 
	 * window if no {@link DockingPort} accepts the drop.
	 * 
	 * @return floatable
	 */
	public boolean isFloatable();
	
	/**
	 * Sets whether the <code>Dockable</code> should be placed in its own window 
	 * if no <code>DockingPort</code> accepts the drop and fires a 
	 * <code>PropertyChangeEvent</code> if it is different from its previous 
	 * value.
	 * 
	 * @param floatable
	 */
	public void setFloatable(boolean floatable);
	
	/**
	 * Returns the parent {@link DockingPort} that contains this 
	 * <code>Dockable</code>, or <code>null</code> if it is not contained within 
	 * a <code>DockingPort</code>.
	 * 
	 * @return dockingPort
	 */
	public DockingPort<?> getDockingPort();
	
	/**
	 * Sets the parent {@link DockingPort} that contains the 
	 * <code>Dockable</code>, or <code>null</code> if it is not contained within 
	 * a <code>DockingPort</code>.  Fires a <code>PropertyChangeEvent</code>
	 * when it is different from it's previous value.
	 * <p>
	 * This method is intended for the use of {@link DockingPort}
	 * implementations, so client code should not normally need to call it.  
	 * Client code should call <code>DockingPort#dock</code> or  
	 * <code>DockingPort#undock</code> to change the parent 
	 * <code>DockingPort</code>.
	 * 
	 * @param dockingPort
	 */
	public void setDockingPort(DockingPort<?> dockingPort);
	
	/**
	 * Returns the outer {@link java.awt.Component} that is being 
	 * made dockable. The content of the <code>Dockable</code> is displayed 
	 * within the <code>Component</code>.
	 * <p>
	 * This method is required to return the same object throughout the 
	 * lifecycle of the <code>Dockable</code>.
	 * 
	 * @return component
	 */
	public Component getComponent();
	
	/**
	 * Returns whether the <code>Dockable</code> can be closed by the user.  
	 * <p>
	 * This method can ask the user whether they want to save, not save, or 
	 * cancel the close entirely if the user has unsaved changes.  If the user
	 * wants to cancel, this method should return <code>false</code>.  
	 * Otherwise, this method should return <code>true</code>.
	 * 
	 * @return whether the <code>Dockable</code> can be closed by the user
	 */
	public boolean canClose();
	
	/**
	 * Returns whether the <code>Dockable</code> has been disposed.  
	 * <p>
	 * It is an error to call any method other than this one if the 
	 * <code>Dockable</code> has been disposed.
	 * 
	 * @return disposed
	 */
	public boolean isDisposed();
	
	/**
	 * Releases the resources used by this <code>Dockable</code> so it can be 
	 * garbage collected without waiting for user interaction.  This includes 
	 * listeners, database connections, etc. 
	 * Fires a <code>PropertyChangeEvent</code> to let listeners know this
	 * <code>DockingPort</code> has been disposed.  The implementation is 
	 * responsible for undocking this <code>Dockable</code> from its parent
	 * <code>DockingPort</code> (if any) when <code>dispose()</code> is called.
	 * <p>
	 * It is an error to call any method other than <code>isDisposed()</code> on 
	 * a disposed <code>Dockable</code>.
	 */
	public void dispose();
}
