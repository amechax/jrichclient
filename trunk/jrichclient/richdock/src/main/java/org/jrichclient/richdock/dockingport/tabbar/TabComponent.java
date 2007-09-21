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
package org.jrichclient.richdock.dockingport.tabbar;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class TabComponent extends JComponent {
// Bound properties ************************************************************
	public static final String PROPERTYNAME_TEXT = "text";
	public static final String PROPERTYNAME_ICON = "icon";
	public static final String PROPERTYNAME_FONT = "font";
	public static final String PROPERTYNAME_SELECTED = "selected";

// UI defaults *****************************************************************
	
	public static final String KEY_DEFAULT_FONT = "TabComponent.defaultFont";
	public static final String KEY_VERTICAL_ALIGNMENT = "TabComponent.verticalAlignment";
	public static final String KEY_HORIZONTAL_ALIGNMENT = "TabComponent.horizontalAlignment";
	public static final String KEY_VERTICAL_TEXT_POSITION = "TabComponent.verticalTextPosition";
	public static final String KEY_HORIZONTAL_TEXT_POSITION = "TabComponent.horizontalTextPosition";
	public static final String KEY_TEXT_ICON_GAP = "TabComponent.textIconGap";
	public static final String KEY_CONTENT_MARGINS = "TabComponent.contentMargins";
	public static final String KEY_RENDERING_HINTS = "TabComponent.renderingHints";
	public static final String KEY_DEFAULT_FOREGROUND = "TabComponent.defaultForeground";
	public static final String KEY_DEFAULT_BACKGROUND = "TabComponent.defaultBackground";
	public static final String KEY_SELECTED_FOREGROUND = "TabComponent.selectedForeground";
	public static final String KEY_START_GRADIENT_COLOR = "TabComponent.startGradientColor";
	public static final String KEY_END_GRADIENT_COLOR = "TabComponent.endGradientColor";
	public static final String KEY_DEFAULT_BORDER = "TabComponent.defaultBorder";
	public static final String KEY_HOVER_BORDER = "TabComponent.hoverBorder";
			
// Private instance variables **************************************************
	
	private String text;
	private Icon icon;
	private boolean selected;
	private Rotation rotation;
	private Dimension preferredSize = new Dimension();
		
// Construction ****************************************************************
	
	public TabComponent() {
		this(null, null, SwingConstants.LEADING);
	}
	
	public TabComponent(String text) {
		this(text, null, SwingConstants.LEADING);
	}
	
	public TabComponent(String text, int horizontalAlignment) {
		this(text, null, horizontalAlignment);
	}
	
	public TabComponent(Icon icon) {
		this(null, icon, SwingConstants.CENTER);
	}
	
	public TabComponent(Icon icon, int horizontalAlignment) {
		this(null, icon, horizontalAlignment);
	}
	
	public TabComponent(String text, Icon icon) {
		this(text, icon, SwingConstants.LEADING);
	}
	
	public TabComponent(String text, Icon icon, int horizontalAlignment) {
		this(text, icon, horizontalAlignment, Rotation.ROTATION_0);
	}
	
	public TabComponent(String text, Icon icon, int horizontalAlignment, Rotation rotation) {
		this.text = text;
		this.icon = icon;
		this.selected = false;
		this.rotation = rotation;
		
		setOpaque(false);
		initClientProperties(horizontalAlignment);
		preferredSize = new Dimension();
		calcPreferredSize(preferredSize);
		
		addComponentListener(new ResizeHandler());
	}
	
	private void initClientProperties(int horizontalAlignment) {
		Font defaultFont = UIManager.getFont(KEY_DEFAULT_FONT);
		if (defaultFont == null)
			defaultFont = UIManager.getFont("Label.font");
		putClientProperty(KEY_DEFAULT_FONT, defaultFont);
		super.setFont(defaultFont);
		
		Integer verticalAlignment = getUIInt(KEY_VERTICAL_ALIGNMENT);
		if (verticalAlignment == null)
			verticalAlignment = SwingConstants.CENTER;
		putClientProperty(KEY_VERTICAL_ALIGNMENT, verticalAlignment);
		putClientProperty(KEY_HORIZONTAL_ALIGNMENT, horizontalAlignment);
		
		Integer verticalTextPosition = getUIInt(KEY_VERTICAL_TEXT_POSITION);
		if (verticalTextPosition == null)
			verticalTextPosition = SwingConstants.CENTER;
		putClientProperty(KEY_VERTICAL_TEXT_POSITION, verticalTextPosition);
		
		Integer horizontalTextPosition = getUIInt(KEY_HORIZONTAL_TEXT_POSITION);
		if (horizontalTextPosition == null)
			horizontalTextPosition = SwingConstants.TRAILING;
		putClientProperty(KEY_HORIZONTAL_TEXT_POSITION, horizontalTextPosition);
		
		Integer textIconGap = getUIInt(KEY_TEXT_ICON_GAP);
		if (textIconGap == null)
			textIconGap = 4;
		putClientProperty(KEY_TEXT_ICON_GAP, textIconGap);
				
		Insets contentMargins = UIManager.getInsets(KEY_CONTENT_MARGINS);
		if (contentMargins == null)
			contentMargins = new Insets(0, 0, 1, 1);
		putClientProperty(KEY_CONTENT_MARGINS, contentMargins);
		
		@SuppressWarnings("unchecked")
		Map<RenderingHints.Key, Object> renderingHints = 
			(Map<RenderingHints.Key, Object>)UIManager.get(KEY_RENDERING_HINTS);
		if (renderingHints == null) {
			renderingHints = new HashMap<RenderingHints.Key, Object>();
			//renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		putClientProperty(KEY_RENDERING_HINTS, renderingHints);
		
		Color defaultForeground = UIManager.getColor(KEY_DEFAULT_FOREGROUND);
		if (defaultForeground == null)
			defaultForeground = UIManager.getColor("Label.foreground");
		putClientProperty(KEY_DEFAULT_FOREGROUND, defaultForeground);
		super.setForeground(defaultForeground);
		
		Color defaultBackground = UIManager.getColor(KEY_DEFAULT_BACKGROUND);
		if (defaultBackground == null)
			defaultBackground = UIManager.getColor("Label.background");
		putClientProperty(KEY_DEFAULT_BACKGROUND, defaultBackground);
		super.setBackground(defaultBackground);
		
		Color focusedForeground = UIManager.getColor(KEY_SELECTED_FOREGROUND);
		if (focusedForeground == null)
			focusedForeground = UIManager.getColor("InternalFrame.activeTitleForeground");
		putClientProperty(KEY_SELECTED_FOREGROUND, focusedForeground);
		
		Color startGradientColor = UIManager.getColor(KEY_START_GRADIENT_COLOR);
		if (startGradientColor == null)
			startGradientColor = UIManager.getColor("InternalFrame.activeTitleBackground");
		putClientProperty(KEY_START_GRADIENT_COLOR, startGradientColor);
		
		Color endGradientColor = UIManager.getColor(KEY_END_GRADIENT_COLOR);
		if (endGradientColor == null)
			endGradientColor = startGradientColor.brighter();
		putClientProperty(KEY_END_GRADIENT_COLOR, endGradientColor);
		
		Border defaultBorder = UIManager.getBorder(KEY_DEFAULT_BORDER);
		if (defaultBorder == null)
			defaultBorder = new RoundedRectBorder(getBackground(), Color.GRAY, 4, 4, 1);
		putClientProperty(KEY_DEFAULT_BORDER, defaultBorder);
		super.setBorder(defaultBorder);
		
		Border hoverBorder = UIManager.getBorder(KEY_HOVER_BORDER);
		if (hoverBorder == null)
			hoverBorder = new RoundedRectBorder(getBackground(), Color.BLACK, 4, 4, 1);
		putClientProperty(KEY_HOVER_BORDER, hoverBorder);
	}

// UIManager *******************************************************************
    
	private Integer getUIInt(String key) {
		return (Integer)UIManager.get(key);
	}
	
// Rendering hints *************************************************************
	
	@SuppressWarnings("unchecked")
	private Map<RenderingHints.Key, Object> getRenderingHints() {
		return (Map<RenderingHints.Key, Object>)getClientProperty(KEY_RENDERING_HINTS);
	}
	
// Text ************************************************************************
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		String oldText = getText();
		this.text = text;
		calcPreferredSize(preferredSize);
		invalidate();
		firePropertyChange(PROPERTYNAME_TEXT, oldText, text);
	}
	
// Icon ************************************************************************
	
	public Icon getIcon() {
		return icon;
	}
	
	public void setIcon(Icon icon) {
		Icon oldIcon = getIcon();
		this.icon = icon;
		calcPreferredSize(preferredSize);
		invalidate();
		firePropertyChange(PROPERTYNAME_ICON, oldIcon, icon);
	}
	
// Font ************************************************************************
	
	@Override
	public void setFont(Font font) {
		super.setFont(font);			
		calcPreferredSize(preferredSize);
		invalidate();
	}
	
// Selected ********************************************************************
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		boolean oldSelected = isSelected();
		this.selected = selected;
		repaint();
		firePropertyChange(PROPERTYNAME_SELECTED, oldSelected, selected);
	}
		
// Rotation ********************************************************************
	
	public Rotation getRotation() {
		return rotation;
	}
		
// Painting ********************************************************************
		
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g.create();
		Rectangle paintRect = new Rectangle(0, 0, 0, 0);
		Rectangle iconRect = new Rectangle(0, 0, 0, 0);
		Rectangle textRect = new Rectangle(0, 0, 0, 0);
		String clippedText = calcLayout(paintRect, iconRect, textRect);
		
		g2d.addRenderingHints(getRenderingHints());
		paintBackground(g2d, paintRect);
				
		switch (getRotation()) {
			case ROTATION_90:
				g2d.rotate(Math.toRadians(90));
				g2d.translate(0, -getWidth());
				break;
			case ROTATION_180:
				g2d.rotate(Math.toRadians(180));
				g2d.translate(-getWidth(), -getHeight());
				break;
			case ROTATION_270:
				g2d.rotate(Math.toRadians(270));
				g2d.translate(-getHeight(), 0);
				break;
		}
		
		paintText(g2d, textRect, clippedText);
		paintIcon(g2d, iconRect, getIcon());
	}
	
	private String calcLayout(Rectangle innerRect, Rectangle iconRect, Rectangle textRect) {
		SwingUtilities.calculateInnerArea(this, innerRect);
		
		Rectangle contentRect;
		switch (getRotation()) {
			case ROTATION_90:
			case ROTATION_270:
				contentRect = new Rectangle(innerRect.x, innerRect.y, innerRect.height, innerRect.width);
				break;
			default:
				contentRect = new Rectangle(innerRect);
				break;
		}
		
		Insets contentMargins = (Insets)getClientProperty(KEY_CONTENT_MARGINS);
		contentRect.x += contentMargins.left;
		contentRect.y += contentMargins.top;
		contentRect.width -= contentMargins.left + contentMargins.right;
		contentRect.height -= contentMargins.top + contentMargins.bottom;

		FontMetrics fm = getFontMetrics(getFont());
		Integer verticalAlignment = (Integer)getClientProperty(KEY_VERTICAL_ALIGNMENT);
		Integer horizontalAlignment = (Integer)getClientProperty(KEY_HORIZONTAL_ALIGNMENT);
		Integer verticalTextPosition = (Integer)getClientProperty(KEY_VERTICAL_TEXT_POSITION);
		Integer horizontalTextPosition = (Integer)getClientProperty(KEY_HORIZONTAL_TEXT_POSITION);
		Integer textIconGap = (Integer)getClientProperty(KEY_TEXT_ICON_GAP);
		
		return SwingUtilities.layoutCompoundLabel(this, 
			fm, getText(), getIcon(), 
			verticalAlignment, horizontalAlignment, 
			verticalTextPosition, horizontalTextPosition, 
			contentRect, iconRect, textRect, textIconGap);
	}
		
	protected void paintBackground(Graphics2D g2d, Rectangle paintRect) {
		if (isSelected()) {
			Color startColor = (Color)getClientProperty(KEY_START_GRADIENT_COLOR);
			Color endColor = (Color)getClientProperty(KEY_END_GRADIENT_COLOR);
	        
	        Paint savePaint = g2d.getPaint();
	        switch (getRotation()) {
	        	case ROTATION_0:
	        		g2d.setPaint(new GradientPaint(0, 0, startColor, 0, paintRect.height, endColor));
	        		break;
	        	case ROTATION_90:
	        		g2d.setPaint(new GradientPaint(paintRect.width, 0, startColor, 0, 0, endColor));
	        		break;
	        	case ROTATION_180:
	        		g2d.setPaint(new GradientPaint(0, paintRect.height, startColor, 0, 0, endColor));
	        		break;
	        	case ROTATION_270:
	        		g2d.setPaint(new GradientPaint(0, 0, startColor, paintRect.width, 0, endColor));
	        		break;
	        }
	        
	        g2d.fillRect(paintRect.x, paintRect.y, paintRect.width, paintRect.height);
	        g2d.setPaint(savePaint);
		}
	}
	
	protected void paintText(Graphics2D g2d, Rectangle textRect, String text) {
		if (text != null) {
			FontMetrics fm = getFontMetrics(getFont());
			Color foreground = isSelected() ? 
				(Color)getClientProperty(KEY_SELECTED_FOREGROUND) : 
				(Color)getClientProperty(KEY_DEFAULT_FOREGROUND);
			g2d.setColor(foreground);
			g2d.drawString(text, textRect.x, textRect.y + fm.getAscent());
		}
	}
	
	protected void paintIcon(Graphics2D g2d, Rectangle iconRect, Icon icon) {
		if (icon != null)
			icon.paintIcon(this, g2d, iconRect.x, iconRect.y);
	}
		
// Sizes ***********************************************************************
	
	private void calcPreferredSize(Dimension preferredSize) {
		Insets contentMargins = (Insets)getClientProperty(KEY_CONTENT_MARGINS);
		int dx = contentMargins.left + contentMargins.right;
		int dy = contentMargins.top + contentMargins.bottom;
		
		Icon icon = this.icon;
		String text = this.text;
		Font font = getFont();
		
		if ((icon == null) && ((text == null) || ((text != null) && (font == null)))) {
			preferredSize.setSize(dx, dy);
		} else if ((text == null) || ((icon != null) && (font == null))) {
			preferredSize.setSize(icon.getIconWidth() + dx, icon.getIconHeight() + dy);
		} else {
			Rectangle iconRect = new Rectangle(0, 0, 0, 0);
			Rectangle textRect = new Rectangle(0, 0, 0, 0);
			Rectangle contentRect = new Rectangle(dx, dy, Short.MAX_VALUE, Short.MAX_VALUE);
			
			FontMetrics fm = getFontMetrics(font);
			Integer verticalAlignment = (Integer)getClientProperty(KEY_VERTICAL_ALIGNMENT);
			Integer horizontalAlignment = (Integer)getClientProperty(KEY_HORIZONTAL_ALIGNMENT);
			Integer verticalTextPosition = (Integer)getClientProperty(KEY_VERTICAL_TEXT_POSITION);
			Integer horizontalTextPosition = (Integer)getClientProperty(KEY_HORIZONTAL_TEXT_POSITION);
			Integer textIconGap = (Integer)getClientProperty(KEY_TEXT_ICON_GAP);

            SwingUtilities.layoutCompoundLabel(this, fm, text, icon, 
                verticalAlignment, horizontalAlignment,
                verticalTextPosition, horizontalTextPosition,
                contentRect, iconRect, textRect, textIconGap);
            
            int x1 = Math.min(iconRect.x, textRect.x);
            int x2 = Math.max(iconRect.x + iconRect.width, textRect.x + textRect.width);
            int y1 = Math.min(iconRect.y, textRect.y);
            int y2 = Math.max(iconRect.y + iconRect.height, textRect.y + textRect.height);
            preferredSize.setSize(x2 - x1 + dx, y2 - y1 + dy);
		}
		
		Rotation rot = rotation;
		if (rot == Rotation.ROTATION_90 || rot == Rotation.ROTATION_270)
			preferredSize.setSize(preferredSize.height, preferredSize.width);
		
		Border border = getBorder();
		if (border != null) {
			Insets insets = border.getBorderInsets(this);
			preferredSize.height += insets.top + insets.bottom;
			preferredSize.width += insets.left + insets.right;
		}
	}

    @Override
    public Dimension getPreferredSize() {
    	return preferredSize;
    }
    
    @Override
    public boolean isPreferredSizeSet() {
    	return true;
    }
    
    @Override
    public Dimension getMinimumSize() {
    	return getPreferredSize();
    }
    
    @Override
    public boolean isMinimumSizeSet() {
    	return true;
    }
    
    @Override
    public Dimension getMaximumSize() {
    	return getPreferredSize();
    }
    
    @Override
    public boolean isMaximumSizeSet() {
    	return true;
    }
    
// Resize handler **************************************************************
    
    /**
     * Force a repaint whenever the component is resized to avoid screen
     * artifacts.  Could this be a bug in Swing?
     * 
     * {@link http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4665129}
     */
    private class ResizeHandler extends ComponentAdapter {
    	@Override
    	public void componentResized(ComponentEvent event) {
    		repaint();
    	}
    }
}
