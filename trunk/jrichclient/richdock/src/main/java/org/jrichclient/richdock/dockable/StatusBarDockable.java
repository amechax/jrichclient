package org.jrichclient.richdock.dockable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;
import org.jrichclient.richdock.helper.DockableHelper;
import org.jrichclient.richdock.utils.XMLUtils;

@SuppressWarnings("serial")
public class StatusBarDockable extends JPanel implements Dockable {
	public static final String PROPERTYNAME_CORNER_ICON = "cornerIcon";
	
	private final StatusDockableHelper helper;
	private final JLabel cornerIconLabel;
	private final Box contentPanel;
	
// Constructor *****************************************************************
	
	public StatusBarDockable() {
		super(new BorderLayout());
		
		helper = new StatusDockableHelper(this);
		
		setPreferredSize(new Dimension(getWidth(), 23));
		
		cornerIconLabel = new JLabel();
		cornerIconLabel.setOpaque(false);
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.setOpaque(false);
        rightPanel.add(cornerIconLabel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.EAST);
        
        contentPanel = Box.createHorizontalBox();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 2, 4));
        add(contentPanel, BorderLayout.CENTER);
        
        setBackground(new Color(236, 233, 216));
	}
	
// Clone ***********************************************************************
	
	@Override
	public StatusBarDockable clone() throws CloneNotSupportedException {
		return (StatusBarDockable)XMLUtils.duplicate(this, false);
	}
		
// Component *******************************************************************
	
	public Component getComponent() {
		return this;
	}
	
	public void addComponent(Component comp) {
		contentPanel.add(comp);
	}
	
	public void addSeparator() {
		addComponent(new JSeparator(SwingConstants.VERTICAL));
	}
	
// Corner Icon *****************************************************************
	
	public Icon getCornerIcon() {
		return cornerIconLabel.getIcon();
	}
	
	public void setCornerIcon(Icon cornerIcon) {
		Icon oldCornerIcon = getCornerIcon();
		cornerIconLabel.setIcon(cornerIcon);
		firePropertyChange(PROPERTYNAME_CORNER_ICON, oldCornerIcon, getCornerIcon());
	}
	
// PaintComponent **************************************************************
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Color oldColor = g.getColor();
		
		int y = 0;
		int width = getWidth();
		
		g.setColor(new Color(156, 154, 140));
		g.drawLine(0, y, width, y);
		y++;
		
		g.setColor(new Color(196, 194, 183));
		g.drawLine(0, y, width, y);
		y++;
		
		g.setColor(new Color(218, 215, 201));
		g.drawLine(0, y, width, y);
		y++;
		
		g.setColor(new Color(233, 231, 217));
		g.drawLine(0, y, width, y);
		
		y = getHeight() - 7;
		g.setColor(new Color(234, 232, 212));
		g.drawLine(0, y, width, y);
		y++;
		
		g.setColor(new Color(234, 230, 211));
		g.drawLine(0, y, width, y);
		y++;
		
		g.setColor(new Color(231, 228, 209));
		g.drawLine(0, y, width, y);
		y++;
		
		g.setColor(new Color(227, 225, 205));
		g.drawLine(0, y, width, y);
		y++;
		
		g.setColor(new Color(221, 219, 199));
		g.drawLine(0, y, width, y);
		y++;
		
		g.setColor(new Color(217, 214, 192));
		g.drawLine(0, y, width, y);
		y++;
		
		g.setColor(new Color(223, 220, 202));
		g.drawLine(0, y, width, y);
		
		g.setColor(oldColor);
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
	
// DockingPort *****************************************************************
	
	public DockingPort<?> getDockingPort() {
		return helper.getDockingPort();
	}

	public void setDockingPort(DockingPort<?> dockingPort) {
		helper.setDockingPort(dockingPort);
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
	
// StatusDockableHelper ********************************************************
	
	private class StatusDockableHelper extends DockableHelper {

		public StatusDockableHelper(Dockable dockable) {
			super(dockable);
		}

		@Override
		protected void firePropertyChange(String propertyName, 
				Object oldValue, Object newValue) {
			StatusBarDockable.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}

// CanClose ********************************************************************
	
	public boolean canClose() {
		return helper.canClose();
	}
	
// Dispose *********************************************************************

	public boolean isDisposed() {
		return helper.isDisposed();
	}

	public void dispose() {
		helper.dispose();
	}
}
