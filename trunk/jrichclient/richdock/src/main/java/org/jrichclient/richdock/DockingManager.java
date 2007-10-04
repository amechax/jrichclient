package org.jrichclient.richdock;

import org.jrichclient.richdock.dockingport.DesktopDockingPort;

public class DockingManager {
	private static DockingPort<Integer> SINGLETON = new DesktopDockingPort();
	
	public static DockingPort<Integer> getDesktopDockingPort() {
		return SINGLETON;
	}
	
	public static void setDesktopDockingPort(DockingPort<Integer> dockingPort) {
		SINGLETON = dockingPort;
	}
}
