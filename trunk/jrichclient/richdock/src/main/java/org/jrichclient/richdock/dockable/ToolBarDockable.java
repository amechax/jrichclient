package org.jrichclient.richdock.dockable;

import java.awt.Component;

import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;
import org.jrichclient.richdock.helper.DockableHelper;
import org.jrichclient.richdock.utils.XMLUtils;

@SuppressWarnings("serial")
public class ToolBarDockable extends JToolBar implements Dockable {
	private final DockableHelper helper;
	
// Constructors ****************************************************************
	
	public ToolBarDockable() {
		this("");
	}
	
	public ToolBarDockable(String title) {
		super(title);
		helper = new ToolBarDockableHelper(this, title);
	}
	
// Clone ***********************************************************************
	
	@Override
	public ToolBarDockable clone() throws CloneNotSupportedException {
		return (ToolBarDockable)XMLUtils.duplicate(this, false);
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
	
// DockingPort *****************************************************************
	
	public DockingPort<?> getDockingPort() {
		return helper.getDockingPort();
	}

	public void setDockingPort(DockingPort<?> dockingPort) {
		helper.setDockingPort(dockingPort);
	}
	
// Component *******************************************************************
	
	public Component getComponent() {
		return this;
	}
	
// ToolBarDockableHelper *******************************************************
	
	private class ToolBarDockableHelper extends DockableHelper {
		public ToolBarDockableHelper(Dockable dockable, String title) {
			super(dockable, title);
		}

		@Override
		protected void firePropertyChange(String propertyName, 
				Object oldValue, Object newValue) {
			ToolBarDockableHelper.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}

// CanClose ********************************************************************
	
	public boolean canClose() {
		return canClose();
	}

// Dispose *********************************************************************
	
	public boolean isDisposed() {
		return helper.isDisposed();
	}

	public void dispose() {
		helper.dispose();
	}
	
	@Override
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		super.firePropertyChange(propertyName, oldValue, newValue);
	}
}
