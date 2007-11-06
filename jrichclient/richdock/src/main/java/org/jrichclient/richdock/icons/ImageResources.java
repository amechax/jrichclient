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
package org.jrichclient.richdock.icons;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ListResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageResources extends ListResourceBundle {
// Icon directory **************************************************************
	
	public static final String BUNDLE_NAME = ImageResources.class.getCanonicalName();
	public static final String IMAGE_DIR = "org/jrichclient/richdock/icons/";
	
// Icon file names *************************************************************
	
	public static final String GLOBE_IMAGE = IMAGE_DIR + "globe.gif";
	public static final String RESIZE_IMAGE = IMAGE_DIR + "resize.gif";
	public static final String LEFT_ARROW_IMAGE = IMAGE_DIR + "left_arrow.gif";
	public static final String RIGHT_ARROW_IMAGE = IMAGE_DIR + "right_arrow.gif";
	public static final String UP_ARROW_IMAGE = IMAGE_DIR + "up_arrow.gif";
	public static final String DOWN_ARROW_IMAGE = IMAGE_DIR + "down_arrow.gif";
    
    public static final String CLOSE_IMAGE = IMAGE_DIR + "close_default.png";
	public static final String CLOSE_DISABLED_IMAGE = IMAGE_DIR + "close_disabled.png";
	public static final String CLOSE_PRESSED_IMAGE = IMAGE_DIR + "close_pressed.png";
	public static final String CLOSE_ROLLOVER_IMAGE = IMAGE_DIR + "close_rollover.png";
	
// Persistence delegate ********************************************************
	
	static {
		ImageIconPersistenceDelegate.install();
	}
	
// Resource bundle methods *****************************************************
	
	protected Object[][] getContents() {
		return contents;
	}
	
	static final Object[][] contents = {
		{ GLOBE_IMAGE, getImage(GLOBE_IMAGE) },
		{ RESIZE_IMAGE, getImage(RESIZE_IMAGE) },
		{ LEFT_ARROW_IMAGE, getImage(LEFT_ARROW_IMAGE) },
		{ RIGHT_ARROW_IMAGE, getImage(RIGHT_ARROW_IMAGE) },
		{ UP_ARROW_IMAGE, getImage(UP_ARROW_IMAGE) },
		{ DOWN_ARROW_IMAGE, getImage(DOWN_ARROW_IMAGE) },
		
		{ CLOSE_IMAGE, getImage(CLOSE_IMAGE) },
		{ CLOSE_DISABLED_IMAGE, getImage(CLOSE_DISABLED_IMAGE) },
		{ CLOSE_PRESSED_IMAGE, getImage(CLOSE_PRESSED_IMAGE) },
		{ CLOSE_ROLLOVER_IMAGE, getImage(CLOSE_ROLLOVER_IMAGE) }
	};
	
    public static Image getImage(String fileName) {
    	if (fileName == null)
    		return null;
    	
    	try {
    		ClassLoader loader = Thread.currentThread().getContextClassLoader();
    		URL fileURL = loader.getResource(fileName);
    		return Toolkit.getDefaultToolkit().getImage(fileURL);
    	} catch (Exception ex) {
    		return null;	// could be invalid fileName
    	}
	}
    
    public static Icon createIcon(String fileName) {
    	if (fileName == null)
    		return null;
    	
    	try {
    		ClassLoader loader = Thread.currentThread().getContextClassLoader();
    		URL fileURL = loader.getResource(fileName);
    		return new ImageIcon(fileURL);
    	} catch (Exception ex) {
    		return null;	// could be invalid fileName
    	}
    }
}
