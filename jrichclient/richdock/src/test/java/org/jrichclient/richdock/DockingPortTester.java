package org.jrichclient.richdock;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import static org.jrichclient.richdock.UnitTestUtils.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.jrichclient.richdock.dockable.BasicDockable;
import org.junit.Test;

public class DockingPortTester<T> extends DockableTester {
	private final DockingPort<T> dockingPort;

// Constructor *****************************************************************
	
	public DockingPortTester(DockingPort<T> dockingPort) {
		super(dockingPort);
		
		this.dockingPort = dockingPort;
	}
	
// Dropable ********************************************************************

	@Test
	public void testDropable() {
		boolean oldDropable = dockingPort.isDropable();
		boolean newDropable = !oldDropable;
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(DockingPort.PROPERTYNAME_DROPABLE, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			DockingPort.PROPERTYNAME_DROPABLE, oldDropable, newDropable));
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			DockingPort.PROPERTYNAME_DROPABLE, newDropable, oldDropable));
		
		replay(listener);
		dockingPort.setDropable(newDropable);
		assertSame(newDropable, dockingPort.isDropable());
		
		dockingPort.setDropable(oldDropable);
		assertSame(oldDropable, dockingPort.isDropable());
		verify(listener);
		dockingPort.removePropertyChangeListener(DockingPort.PROPERTYNAME_DROPABLE, listener);
	}
	
// Dock/Undock *****************************************************************
	
	public void testDockUndock(T location, Dockable testDockable) {
		int count = dockingPort.getDockableCount();
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(
			DockingPort.PROPERTYNAME_DOCKABLE_COUNT, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			DockingPort.PROPERTYNAME_DOCKABLE_COUNT, count, count + 1));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			DockingPort.PROPERTYNAME_DOCKABLE_COUNT, count + 1, count));
		
		replay(listener);
		dockingPort.dock(testDockable, location);
		assertEquals(count + 1, dockingPort.getDockableCount());
		
		assertEquals(dockingPort, testDockable.getDockingPort());
		assertEquals(testDockable, dockingPort.getDockable(location));
		assertEquals(location, dockingPort.getLocation(testDockable));
		
		boolean found = false;
		for (Dockable d : dockingPort) {
			if (d == testDockable) {
				found = true;
				break;
			}
		}
		if (!found)
			fail("Dockable was not returned by Iterator");
		
		dockingPort.undock(testDockable, false);
		assertEquals(count, dockingPort.getDockableCount());
		assertNull(dockingPort.getLocation(testDockable));
		if (location instanceof String)
			assertNull(dockingPort.getDockable(location));
		
		found = false;
		for (Dockable d : dockingPort) {
			if (d == testDockable) {
				found = true;
				break;
			}
		}
		if (found)
			fail("Dockable should not be returned by Iterator");
		
		verify(listener);
		dockingPort.removePropertyChangeListener(
			DockingPort.PROPERTYNAME_DOCKABLE_COUNT, listener);
	}
	
	public void testDockUndock(T location) {
		Dockable testDockable = new BasicDockable();
		testDockUndock(location, testDockable);
	}
	
// IllegalUndock ***************************************************************
	
	public void testIllegalUndock() {
		Dockable dockable = new BasicDockable();
		int count = dockingPort.getDockableCount();
		dockingPort.undock(dockable, false);
		assertEquals(count, dockingPort.getDockableCount());
	}
	
// Iterator ********************************************************************
	
	@Test
	public final void testIterator() {
		int count = 0;
		for (Dockable dockable : dockingPort) {
			T location = dockingPort.getLocation(dockable);
			assertSame(dockable, dockingPort.getDockable(location));
			assertSame(dockingPort, dockable.getDockingPort());
			count++;
		}
		
		assertEquals(dockingPort.getDockableCount(), count);
	}
	
// CanClose ********************************************************************
	
	@Test
	public void testCanClose() {
		boolean canClose = true;
		for (Dockable dockable : dockingPort) {
			if (!dockable.canClose()) {
				canClose = false;
				break;
			}
		}
				
		assertEquals(canClose, dockingPort.canClose());
	}
	
// DisposeWhenEmpty ************************************************************
	
	@Test
	public final void testDisposeWhenEmpty() {
		boolean oldDisposeWhenEmpty = dockingPort.getDisposeOnEmpty();
		boolean newDisposeWhenEmpty = !oldDisposeWhenEmpty;
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(DockingPort.PROPERTYNAME_DISPOSE_ON_EMPTY, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			DockingPort.PROPERTYNAME_DISPOSE_ON_EMPTY, oldDisposeWhenEmpty, newDisposeWhenEmpty));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			DockingPort.PROPERTYNAME_DISPOSE_ON_EMPTY, newDisposeWhenEmpty, oldDisposeWhenEmpty));
		
		replay(listener);
		
		dockingPort.setDisposeOnEmpty(newDisposeWhenEmpty);
		assertSame(newDisposeWhenEmpty, dockingPort.getDisposeOnEmpty());
		
		dockingPort.setDisposeOnEmpty(oldDisposeWhenEmpty);
		assertSame(oldDisposeWhenEmpty, dockingPort.getDisposeOnEmpty());	
		
		verify(listener);
		dockingPort.removePropertyChangeListener(DockingPort.PROPERTYNAME_DISPOSE_ON_EMPTY, listener);
	}
	
// Dispose *********************************************************************
	
	public void testDispose(DockingPort<?> dockingPort) {
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(Dockable.PROPERTYNAME_DISPOSED, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			Dockable.PROPERTYNAME_DISPOSED, false, true));
		
		replay(listener);
		assertFalse(dockingPort.isDisposed());
		
		List<Dockable> dockableList = new ArrayList<Dockable>();
		for (Dockable dockable : dockingPort) {
			dockableList.add(dockable);
			assertFalse(dockable.isDisposed());
		}
		
		super.testDispose(dockingPort);
		
		for (Dockable dockable : dockableList)
			assertTrue(dockable.isDisposed());
		assertTrue(dockingPort.isDisposed());
		
		verify(listener);
	}
	
	@Override
	public void tearDown() {
		testDispose(dockingPort);
	}
}
