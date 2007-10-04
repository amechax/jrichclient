package org.jrichclient.richdock.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingManager;
import org.jrichclient.richdock.dockable.BasicDockable;
import org.jrichclient.richdock.dockable.ToolBarDockable;
import org.jrichclient.richdock.dockingport.*;
import org.jrichclient.richdock.dockingport.tabbar.Rotation;
import org.jrichclient.richdock.dockingport.tabbar.TabBarDockingPort;
import org.jrichclient.richdock.icons.ImageResources;
import org.jrichclient.richdock.multisplitpane.DefaultSplitPaneModel;
import org.jrichclient.richdock.utils.ActionUtils;

public final class BasicDemo {
	
	public static void buildDemo() {
		LookAndFeelUtils.setSystemLookAndFeel();
		
		DesktopPaneDockingPort desktopPort = new DesktopPaneDockingPort();
		desktopPort.dock(createInternalDockable("Test Dockable 1"), 0);
		desktopPort.dock(createInternalDockable("Test Dockable 2"), 1);
		desktopPort.setPreferredSize(new Dimension(400, 250));
		
		MultiSplitDockingPort multiSplitPort = 
			new MultiSplitDockingPort(new DefaultSplitPaneModel(), 
				"RichDock Demo Application", ImageResources.GLOBE_IMAGE);
		
		multiSplitPort.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		multiSplitPort.dock(createAreaDockable("Left Dockable"), DefaultSplitPaneModel.LEFT);
		multiSplitPort.dock(desktopPort, DefaultSplitPaneModel.TOP);
		multiSplitPort.dock(createBottomDockable(), DefaultSplitPaneModel.BOTTOM);
		
		TabBarDockingPort tabPort = new TabBarDockingPort(Rotation.ROTATION_0);
		tabPort.dock(createTestDockable("Tab Dockable 1"), 0);
		tabPort.dock(createTestDockable("Tab Dockable 2"), 1);
		tabPort.dock(createTestDockable("Tab Dockable 3"), 2);
		tabPort.dock(createTestDockable("Tab Dockable 4"), 3);
		ScrollArrowDockingPort scrollPort = new ScrollArrowDockingPort();
		scrollPort.dock(tabPort, ScrollArrowDockingPort.LOCATIONNAME_CONTENT);
		
		BorderLayoutDockingPort content = new BorderLayoutDockingPort();
		content.dock(multiSplitPort, BorderLayout.CENTER);
		content.dock(scrollPort, BorderLayout.SOUTH);
		
		BorderLayoutDockingPort borderPort = new BorderLayoutDockingPort();
		borderPort.dock(createToolBar(), BorderLayout.NORTH);
		borderPort.dock(content, BorderLayout.CENTER);
		
		FrameDockingPort frameDockingPort = new FrameDockingPort();
		frameDockingPort.dock(borderPort, FrameDockingPort.LOCATIONNAME_CONTENT);
		frameDockingPort.setSize(640, 480);
		frameDockingPort.setLocationByPlatform(true);
		
		DockingManager.getDesktopDockingPort().dock(frameDockingPort, 0);
	}

	private static Dockable createTestDockable(String title) {
		BasicDockable basicDockable = new BasicDockable(new JButton(title), 
			title, ImageResources.GLOBE_IMAGE);
		basicDockable.setPopupMenu(ActionUtils.createClosePopupMenu(basicDockable));
			
		ViewDockingPort viewDockingPort = new ViewDockingPort();
		viewDockingPort.setToolBar(ActionUtils.createCloseToolBar(basicDockable));
		viewDockingPort.dock(basicDockable, ViewDockingPort.LOCATIONNAME_CONTENT);
		return viewDockingPort;
	}
	
	private static Dockable createInternalDockable(String title) {
		TabbedPaneDockingPort tabbedPort = new TabbedPaneDockingPort();
		tabbedPort.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		tabbedPort.setDisposeOnEmpty(true);
		tabbedPort.setDropable(true);
		tabbedPort.dock(createTestDockable(title), 0);
		
		InternalFrameDockingPort port = new InternalFrameDockingPort();
		port.dock(tabbedPort, InternalFrameDockingPort.LOCATIONNAME_CONTENT);
		
		return port;
	}
	
	private static Dockable createAreaDockable(String title) {
		TabbedPaneDockingPort tabbedPort = new TabbedPaneDockingPort();
		tabbedPort.setDropable(true);
		tabbedPort.dock(createTestDockable(title), 0);
		
		return tabbedPort;
	}
	
	private static Dockable createBottomDockable() {
		TabbedPaneDockingPort tabbedPort = new TabbedPaneDockingPort();
		tabbedPort.setDropable(true);
		
		tabbedPort.dock(SystemProperties.createSystemPropertiesDockable(), 0);
		tabbedPort.dock(ConsoleFactory.createErrorConsole(), 0);
		tabbedPort.dock(ConsoleFactory.createOutputConsole(), 0);
		tabbedPort.dock(ProjectDockable.createProjectDockableView(), 0);
		
		return tabbedPort;
	}
	
	private static Dockable createToolBar() {
		ToolBarDockable dockable = new ToolBarDockable("Toolbar");
		dockable.add(createToolBarButton("save.gif"));
		dockable.add(createToolBarButton("refresh.gif"));
		dockable.addSeparator();
		dockable.add(createToolBarButton("first.gif"));
		dockable.add(createToolBarButton("previous.gif"));
		dockable.add(createToolBarButton("next.gif"));
		dockable.add(createToolBarButton("last.gif"));
		dockable.addSeparator();
		dockable.add(createToolBarButton("add.gif"));
		dockable.add(createToolBarButton("remove.gif"));
		dockable.setRollover(true);
		return dockable;
	}
	
	private static JButton createToolBarButton(String fileName) {
		return new JButton(ImageResources.createIcon(ImageResources.IMAGE_DIR + fileName));
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				buildDemo();
			}
		});
	}
}
