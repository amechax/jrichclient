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
package org.jrichclient.richdock.utils;

import java.beans.BeanInfo;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.IntrospectionException;
import java.beans.Introspector;

/**
 * This class is a workaround for a bug: 5015403
 * 
 * XMLEncoder does not encode enumerations correctly 
 * 
 * @see <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5015403">Bug 5015403</a>
 *
 * @author Bruce Alspaugh
 */
public class EnumPersistenceDelegate extends DefaultPersistenceDelegate {
	private static EnumPersistenceDelegate INSTANCE = new EnumPersistenceDelegate();

	public static void installFor(Enum<?>[] values) {
		Class<? extends Enum<?>> declaringClass = values[0].getDeclaringClass();
		installFor(declaringClass);

		for (Enum<?> e : values)
			if (e.getClass() != declaringClass)
				installFor(e.getClass());
	}

	static void installFor(Class<? extends Object> enumClass) {
		try {
			BeanInfo info = Introspector.getBeanInfo(enumClass);
			info.getBeanDescriptor().setValue("persistenceDelegate", INSTANCE );
		} catch (IntrospectionException exception) {
			throw new RuntimeException("Unable to persist enumerated type " + enumClass, exception);
		}
	}

	@Override
	protected Expression instantiate(Object oldInstance, Encoder out) {
		Enum<?> e = (Enum<?>)oldInstance;
		return new Expression(Enum.class, "valueOf",
				new Object[] { e.getDeclaringClass(), e.name() });
	}

	@Override
	protected boolean mutatesTo(Object oldInstance, Object newInstance) {
		return oldInstance == newInstance;
	}
}
