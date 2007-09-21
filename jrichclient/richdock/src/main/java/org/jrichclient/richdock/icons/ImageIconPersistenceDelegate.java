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

import java.beans.BeanInfo;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;

public class ImageIconPersistenceDelegate extends DefaultPersistenceDelegate {
	@Override
	protected Expression instantiate(Object oldInstance, Encoder out) {
		ImageIcon oldImageIcon = (ImageIcon)oldInstance;
		return new Expression(oldInstance, ImageIconPersistenceDelegate.class, 
			"createIcon", new Object[] { oldImageIcon.toString() });
	}
	
	public static ImageIcon createIcon(String uriString) 
			throws URISyntaxException, MalformedURLException {
		URI uri = new URI(uriString);
		return new ImageIcon(uri.toURL());
	}
	
	public static void install() {
		try {
			BeanInfo info = Introspector.getBeanInfo(ImageIcon.class);
			info.getBeanDescriptor().setValue("persistenceDelegate", 
				new ImageIconPersistenceDelegate());
		} catch (IntrospectionException ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}
}

