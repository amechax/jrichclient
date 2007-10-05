/*
 * JRichClient -- Java libraries for rich client applications.
 * Copyright (C) 2007 CompuLink, Ltd. 409 Vandiver Drive #4-200,
 * Columbia, Missouri 65202-1562, All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jrichclient.richdock.utils;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.jrichclient.richdock.DockingManager;
import org.jrichclient.richdock.DockingPort;

public class XMLUtils {
// JavaBeans persistance *******************************************************
	
	public static byte[] encodeObject(Object object, final boolean noisy) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(outputStream);
		encoder.setExceptionListener(new ExceptionListener() {
			public void exceptionThrown(Exception ex) {
				if (noisy)
					ex.printStackTrace();
			}
		});
		
		encoder.writeObject(object);
		encoder.close();
		
		if (noisy)
			System.out.println(outputStream.toString());
		
		return outputStream.toByteArray();
	}
	
	public static Object decodeObject(byte[] byteArray, final boolean noisy) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
		
		XMLDecoder decoder = new XMLDecoder(inputStream);
		decoder.setExceptionListener(new ExceptionListener() {
			public void exceptionThrown(Exception ex) {
				if (noisy)
					ex.printStackTrace();
			}
		});
		
		Object copy = decoder.readObject();
		decoder.close();
		return copy;
	}
	
	public static Object duplicate(Object object, boolean noisy) {
		byte[] momento = encodeObject(object, noisy);
		return decodeObject(momento, noisy);
	}
	
	public static void saveDesktop(final boolean noisy) {
		String fileName = 
			System.getProperty("user.dir") + File.separator + "richdock.txt";
		File file = new File(fileName);
		
		try {
			XMLEncoder encoder = new XMLEncoder(new FileOutputStream(file));
			encoder.setExceptionListener(new ExceptionListener() {
				public void exceptionThrown(Exception ex) {
					if (noisy)
						ex.printStackTrace();
				}
			});
			
			DockingPort<Integer> desktop = DockingManager.getDesktopDockingPort();
			encoder.writeObject(desktop);
			encoder.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}

}
