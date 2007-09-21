package org.jrichclient.richdock.demo;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class GridBagPanelBuilder {
	private JPanel panel;
	
	public GridBagPanelBuilder() {
		panel = new JPanel(new GridBagLayout());
	}
	
	public void addSeparator(Icon icon, String text, 
			int gridx, int gridy, int gridwidth, double weightx, int left) {
		JPanel separatorPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		JLabel label = new JLabel("<html><b>" + text + "</b></html>", 
			icon, SwingConstants.LEADING);
		label.setForeground(UIManager.getColor("InternalFrame.activeTitleBackground"));

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		separatorPanel.add(label, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 1.0;
		c.insets = new Insets(0, 7, 0, 0);
		separatorPanel.add(new JSeparator(), c);
		
		add(separatorPanel, gridx, gridy, gridwidth, weightx, left);
	}
	
	public void addSeparator(String text, int gridx, int gridy, int gridwidth, double weightx, int left) {
		addSeparator(null, text, gridx, gridy, gridwidth, weightx, left);
	}
	
	public void add(Component component,
			int gridx, int gridy, int gridwidth, double weightx, int indent) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(7, indent, 0, 7);
		c.weightx = weightx;
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		panel.add(component, c);
	}
	
	public void setBorder(Border border) {
		panel.setBorder(border);
	}

	public JComponent getPanel() {
		return panel;
	}
}
