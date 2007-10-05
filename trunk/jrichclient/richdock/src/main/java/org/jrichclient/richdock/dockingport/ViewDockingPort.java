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
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPort;
import org.jrichclient.richdock.helper.DragHelper;
import org.jrichclient.richdock.helper.NamedLocationDockingPortHelper;
import org.jrichclient.richdock.helper.NamedLocationDropHelper;
import org.jrichclient.richdock.icons.ImageResources;
import org.jrichclient.richdock.utils.XMLUtils;

@SuppressWarnings("serial")
public final class ViewDockingPort extends JPanel implements DockingPort<String> {
// Property names **************************************************************
	
	public static final String PROPERTYNAME_TOOL_BAR = "toolBar";
	public static final String PROPERTYNAME_SELECTED = "selected";
	
// Location names **************************************************************
	
	public static final String LOCATIONNAME_CONTENT = "content";
	
// Private fields **************************************************************
	
	private final ViewHelper helper;
	private final DragHelper dragHelper;
	private final ViewDropHelper dropHelper;
	private final JLabel titleLabel;
	private final JPanel gradientPanel;
	private final JPanel headerPanel;
	private final MouseListener focusMouseListener;
	private final PropertyChangeListener keyboardFocusListener;
	private JToolBar toolBar;
	private boolean selected;
		
// Constructor *****************************************************************
	
	public ViewDockingPort() {
		super(new BorderLayout());
			
		helper = new ViewHelper();
		helper.setDisposeOnEmpty(true);

		titleLabel = new JLabel();
		titleLabel.setHorizontalAlignment(SwingConstants.LEADING);
		titleLabel.setForeground(getTextForeground(false));
		titleLabel.setOpaque(false);
		
        gradientPanel = new GradientPanel(new BorderLayout(), getHeaderBackground());
        gradientPanel.add(titleLabel, BorderLayout.WEST);
        gradientPanel.setBorder(BorderFactory.createEmptyBorder(3, 4, 3, 1));
        gradientPanel.setOpaque(false);

        headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(gradientPanel, BorderLayout.CENTER);
        headerPanel.setBorder(new RaisedHeaderBorder());
        headerPanel.setOpaque(false);
        
        add(headerPanel, BorderLayout.NORTH);
        setBorder(new ShadowBorder());
		dragHelper = new DragHelper(this, headerPanel);
		dropHelper = new ViewDropHelper(this);
        
        focusMouseListener = new FocusMouseListener();
        headerPanel.addMouseListener(focusMouseListener);
        headerPanel.addMouseListener(helper.getPopupMouseListener());
        
        keyboardFocusListener = new KeyboardFocusListener();
        KeyboardFocusManager focusManager = 
        	KeyboardFocusManager.getCurrentKeyboardFocusManager();
        focusManager.addPropertyChangeListener(keyboardFocusListener);
        
		toolBar = null;
		selected = false;
	}
	
// Clone ***********************************************************************
	
	@Override
	public ViewDockingPort clone() throws CloneNotSupportedException {
		return (ViewDockingPort)XMLUtils.duplicate(this, false);
	}
		
// Title ***********************************************************************
	
	public String getTitle() {
		return titleLabel.getText();
	}

	public void setTitle(String title) {
		String oldTitle = getTitle();
		titleLabel.setText(title);
		firePropertyChange(PROPERTYNAME_TITLE, oldTitle, getTitle());
	}
	
// IconImage *******************************************************************
	
	public String getIconFile() {
		return helper.getIconFile();
	}
	
	public void setIconFile(String fileName) {
		titleLabel.setIcon(ImageResources.createIcon(fileName));
		helper.setIconFile(fileName);
	}
		
// ToolTipText *****************************************************************
	
	@Override
	public String getToolTipText() {
		return headerPanel.getToolTipText();
	}
	
	@Override
	public void setToolTipText(String toolTipText) {
		String oldToolTipText = headerPanel.getToolTipText();
		headerPanel.setToolTipText(toolTipText);
		firePropertyChange(PROPERTYNAME_TOOL_TIP_TEXT, oldToolTipText, toolTipText);
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
	
// Component *******************************************************************
	
	public JPanel getComponent() {
		return this;
	}
	
// ToolBar *********************************************************************
	
    public JToolBar getToolBar() {
        return toolBar;
    }

    public void setToolBar(JToolBar toolBar) {
        JToolBar oldToolBar = getToolBar();
        if (oldToolBar == toolBar)
        	return;
        
        if (oldToolBar != null)
        	headerPanel.remove(oldToolBar);
        	
        if (toolBar != null) {
        	toolBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            headerPanel.add(toolBar, BorderLayout.EAST);
        }
        	
        this.toolBar = toolBar;
        updateHeader();
        headerPanel.validate();
        
        firePropertyChange(PROPERTYNAME_TOOL_BAR, oldToolBar, getToolBar());
    }
    
// ToolBar *********************************************************************
    
    /**
     * Answers if the panel is currently selected (or in other words active)
     * or not. In the selected state, the header background will be
     * rendered differently.
     * 
     * @return boolean  a boolean, where true means the frame is selected 
     *                  (currently active) and false means it is not  
     */
    public boolean isSelected() {
        return selected;
    }
    
    
    /**
     * This panel draws its title bar differently if it is selected,
     * which may be used to indicate to the user that this panel
     * has the focus, or should get more attention than other
     * simple internal frames.
     *
     * @param newValue  a boolean, where true means the frame is selected 
     *                  (currently active) and false means it is not
     */
    public void setSelected(boolean newValue) {
        boolean oldValue = isSelected();
        selected = newValue;
        updateHeader();
        firePropertyChange(PROPERTYNAME_SELECTED, oldValue, isSelected());
    }
	
// Dock ************************************************************************

	public void dock(Dockable dockable, String location) {
		if (!LOCATIONNAME_CONTENT.equals(location))
			throw new IllegalArgumentException("Illegal docking location: " + location);
		
		helper.dock(dockable, location);
	}

	public void undock(Dockable dockable, boolean disposeOnEmpty) {
		helper.undock(dockable, disposeOnEmpty);
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

    // Building *************************************************************

    /**
     * Updates the header.
     */
    private void updateHeader() {
        gradientPanel.setBackground(getHeaderBackground());
        gradientPanel.setOpaque(isSelected());
        titleLabel.setForeground(getTextForeground(isSelected()));
        headerPanel.repaint();
    }

    /**
     * Updates the UI. In addition to the superclass behavior, we need
     * to update the header component.
     */
    @Override
    public void updateUI() {
        super.updateUI();
        
        if (gradientPanel != null)
        	updateHeader();
    }

    /**
     * Determines and answers the header's text foreground color.
     * Tries to lookup a special color from the L&amp;F.
     * In case it is absent, it uses the standard internal frame forground.
     * 
     * @param isSelected   true to lookup the active color, false for the inactive
     * @return the color of the foreground text
     */
    private Color getTextForeground(boolean isSelected) {
        return UIManager.getColor(isSelected ? "InternalFrame.activeTitleForeground" 
                : "Label.foreground");
    }

    /**
     * Determines and answers the header's background color.
     * Tries to lookup a special color from the L&amp;F.
     * In case it is absent, it uses the standard internal frame background.
     * 
     * @return the color of the header's background
     */
    private Color getHeaderBackground() {
        return UIManager.getColor("InternalFrame.activeTitleBackground");
    }

    // A custom border for the raised header pseudo 3D effect.
    private static class RaisedHeaderBorder extends AbstractBorder {
    	private static final long serialVersionUID = 1L;
		private static final Insets INSETS = new Insets(1, 1, 1, 0);
        
        @Override
        public Insets getBorderInsets(Component c) { return INSETS; }
        
        @Override
        public void paintBorder(Component c, Graphics g,
            int x, int y, int w, int h) {
                
            g.translate(x, y);
            g.setColor(UIManager.getColor("controlLtHighlight"));
            g.fillRect(0, 0,   w, 1);
            g.fillRect(0, 1,   1, h-1);
            g.setColor(UIManager.getColor("controlShadow"));
            g.fillRect(0, h-1, w, 1);
            g.translate(-x, -y);
        }
    }

    // A custom border that has a shadow on the right and lower sides.
    private static class ShadowBorder extends AbstractBorder {
    	private static final Insets INSETS = new Insets(1, 1, 3, 3);
        
        @Override
        public Insets getBorderInsets(Component c) { return INSETS; }
        
        @Override
        public void paintBorder(Component c, Graphics g,
            int x, int y, int w, int h) {
                
            Color shadow        = UIManager.getColor("controlShadow");
            if (shadow == null) {
                shadow = Color.GRAY;
            }
            Color lightShadow   = new Color(shadow.getRed(), 
                                            shadow.getGreen(), 
                                            shadow.getBlue(), 
                                            170);
            Color lighterShadow = new Color(shadow.getRed(),
                                            shadow.getGreen(),
                                            shadow.getBlue(),
                                            70);
            g.translate(x, y);
            
            g.setColor(shadow);
            g.fillRect(0, 0, w - 3, 1);
            g.fillRect(0, 0, 1, h - 3);
            g.fillRect(w - 3, 1, 1, h - 3);
            g.fillRect(1, h - 3, w - 3, 1);
            // Shadow line 1
            g.setColor(lightShadow);
            g.fillRect(w - 3, 0, 1, 1);
            g.fillRect(0, h - 3, 1, 1);
            g.fillRect(w - 2, 1, 1, h - 3);
            g.fillRect(1, h - 2, w - 3, 1);
            // Shadow line2
            g.setColor(lighterShadow);
            g.fillRect(w - 2, 0, 1, 1);
            g.fillRect(0, h - 2, 1, 1);
            g.fillRect(w-2, h-2, 1, 1);
            g.fillRect(w - 1, 1, 1, h - 2);
            g.fillRect(1, h - 1, w - 2, 1);
            g.translate(-x, -y);
        }
    }
    
    /**
     * A panel with a horizontal gradient background.
     */
    private static final class GradientPanel extends JPanel {

		private GradientPanel(LayoutManager lm, Color background) {
            super(lm);
            setBackground(background);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (!isOpaque()) {
                return;
            }
            Color control = UIManager.getColor("control");
            int width  = getWidth();
            int height = getHeight();

            Graphics2D g2 = (Graphics2D) g;
            Paint storedPaint = g2.getPaint();
            g2.setPaint(
                new GradientPaint(0, 0, getBackground(), width, 0, control));
            g2.fillRect(0, 0, width, height);
            g2.setPaint(storedPaint);
        }
    }
    
// ViewHelper ******************************************************************
    
    private class ViewHelper extends NamedLocationDockingPortHelper {
    	public ViewHelper() {
			super(ViewDockingPort.this);
		}
    	
		@Override
		protected void install(Dockable dockable, String location) {
			ViewDockingPort.this.install(dockable, location);
		}

		@Override
		protected void uninstall(Dockable dockable, String location) {
			ViewDockingPort.this.uninstall(dockable);
		}
    	
		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			ViewDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
    }
    
// Install/Uninstall ***********************************************************
	
	private void install(Dockable dockable, String location) {
		if (!LOCATIONNAME_CONTENT.equals(location))
			throw new IllegalArgumentException("Invalid location: " + location);

		BorderLayout layout = (BorderLayout)getLayout();
		Component oldComponent = layout.getLayoutComponent(BorderLayout.CENTER);
		if (oldComponent != null)
			remove(oldComponent);
			
		add(dockable.getComponent(), BorderLayout.CENTER);
		validate();
		
		setTitle(dockable.getTitle());
		setIconFile(dockable.getIconFile());
		setToolTipText(dockable.getToolTipText());
		setPopupMenu(dockable.getPopupMenu());
		dockable.addPropertyChangeListener(helper.getDockableListener());
	}
	
	private void uninstall(Dockable dockable) {
		remove(dockable.getComponent());
		validate();
		
		dockable.removePropertyChangeListener(helper.getDockableListener());
		setTitle("");
		setIconFile(null);
		setToolTipText(null);
		setPopupMenu(null);
	}
    	
// Focus Mouse Listener ********************************************************
	
	private class FocusMouseListener extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent event) {
			headerPanel.requestFocusInWindow();
		}
	}
    
// Keyboard Focus Listener *****************************************************

	private class KeyboardFocusListener implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent event) {
			String propertyName = event.getPropertyName();
			if ("focusOwner".equals(propertyName)) {
				Component comp = (Component)event.getNewValue();
				
				boolean selected = false;
				while (comp != null && !selected) {
					if (comp == ViewDockingPort.this)
						selected = true;
					else comp = comp.getParent();
				}
				
				setSelected(selected);
			}
		}
	}
	
// DropHelper ******************************************************************
	
	private class ViewDropHelper extends NamedLocationDropHelper {

		public ViewDropHelper(Component component) {
			super(ViewDockingPort.this, component);
		}

		@Override
		protected boolean dropDockable(Dockable dockable, DropTargetDropEvent event) {
			return dropDockable(dockable, LOCATIONNAME_CONTENT);
		}

		@Override
		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			ViewDockingPort.this.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
	
// Close ***********************************************************************
	
	public boolean canClose() {
		return helper.canClose();
	}
		
// DisposeWhenEmpty ************************************************************
    
	public boolean getDisposeOnEmpty() {
		return helper.getDisposeOnEmpty();
	}
	
	public void setDisposeOnEmpty(boolean disposeWhenEmpty) {
		helper.setDisposeOnEmpty(disposeWhenEmpty);
	}
    
// Dispose *********************************************************************
    
	public boolean isDisposed() {
		return helper.isDisposed();
	}

	public void dispose() {
		headerPanel.removeMouseListener(helper.getPopupMouseListener());
		headerPanel.removeMouseListener(focusMouseListener);
		
		KeyboardFocusManager focusManager = 
        	KeyboardFocusManager.getCurrentKeyboardFocusManager();
        focusManager.removePropertyChangeListener(keyboardFocusListener);
        
        dragHelper.dispose();
        helper.dispose();
	}
}
