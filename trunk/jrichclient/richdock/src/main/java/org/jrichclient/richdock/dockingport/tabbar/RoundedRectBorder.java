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
import java.awt.geom.*;

import javax.swing.border.Border;

@SuppressWarnings("serial")
public final class RoundedRectBorder implements Border {
	
	private final Paint bgPaint;
	private final Paint fgPaint;
	private final int arcWidth;
	private final int arcHeight;
	private final int stroke;
	
// Constructor *****************************************************************
	
	public RoundedRectBorder(Paint bgPaint, Paint fgPaint, 
			int arcWidth, int arcHeight, int stroke) {
		this.bgPaint = bgPaint;
		this.fgPaint = fgPaint;
		this.arcWidth = arcWidth;
		this.arcHeight = arcHeight;
		this.stroke = stroke;
	}
	
// Painting ********************************************************************
	
	public void paintBorder(Component comp, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2d = (Graphics2D)g.create();
		
		Rectangle2D boundsRect = new Rectangle2D.Double(x, y, width, height);
		RoundRectangle2D roundRect = new RoundRectangle2D.
			Double(x + stroke / 2, y + stroke / 2, width - stroke, 
					height - stroke, arcWidth, arcHeight);
		
		paintBackground(g2d, boundsRect, roundRect);
		paintRoundRect(g2d, roundRect);
	}
	
	private void paintBackground(Graphics2D g2d, Shape boundsRect, Shape roundRect) {
		Area bgArea = new Area(boundsRect);
		bgArea.subtract(new Area(roundRect));
		g2d.setPaint(bgPaint);
		g2d.fill(bgArea);
	}
	
	private void paintRoundRect(Graphics2D g2d, Shape roundRect) {
		if (stroke > 0) {
			g2d.setPaint(fgPaint);
			g2d.setStroke(new BasicStroke(stroke));
			g2d.draw(roundRect);
		}
	}
	
// Insets **********************************************************************
	
	public Insets getBorderInsets(Component comp) {
		int vInset = Math.max(stroke, arcHeight / 2);
		int hInset = Math.max(stroke, arcWidth / 2);
		return new Insets(vInset, hInset, vInset, hInset);
	}
	
// Opacity *********************************************************************
	
	public boolean isBorderOpaque() {
		return false;
	}
}