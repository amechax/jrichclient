/*
 * JRichClient -- Libraries for Java Rich Client Applications
 * 
 * Copyright 2007 CompuLink, Ltd. 409 Vandiver Drive #4-200,
 * Columbia, Missouri 65202-1562, All Rights Reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.jrichclient.richdock.demo;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jrichclient.richdock.dockable.BasicDockable;
import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.dockingport.ScrollPaneDockingPort;
import org.jrichclient.richdock.dockingport.ViewDockingPort;
import org.jrichclient.richdock.utils.ActionUtils;

@SuppressWarnings("serial")
public final class ProjectDockable {
	public static final String VIEW_TITLE = "Project";
	public static final String VIEW_IMAGE = "org/jrichclient/richdock/icons/project.gif";
	
// display name strings ********************************************************
	
	public static final String DISPLAYNAME_NAME = "Name";
	public static final String DISPLAYNAME_DESCRIPTION = "Description";
	public static final String DISPLAYNAME_START_DATE = "Start Date";
	public static final String DISPLAYNAME_REVIEW_DATE = "Review Date";
	public static final String DISPLAYNAME_AMEND_DATE = "Amend Date";
	public static final String DISPLAYNAME_END_DATE = "End Date";
	public static final String DISPLAYNAME_LABOR_BUDGET = "Labor Budget";
	public static final String DISPLAYNAME_MATERIAL_BUDGET = "Material Budget";
	public static final String DISPLAYNAME_EQUIPMENT_BUDGET = "Equipment Budget";
	public static final String DISPLAYNAME_CONTRACTOR_BUDGET = "Contractor Budget";
	public static final String DISPLAYNAME_MEMO = "Memo";

// data entry fields ***********************************************************
	
	private final JTextField nameField;
	private final JLabel nameLabel;
	
	private final JTextField descriptionField;
	private final JLabel descriptionLabel;
	
	private final JTextField startDateField;
	private final JLabel startDateLabel;
	
	private final JTextField reviewDateField;
	private final JLabel reviewDateLabel;
	
	private final JTextField amendDateField;
	private final JLabel amendDateLabel;
	
	private final JTextField endDateField;
	private final JLabel endDateLabel;
	
	private final JFormattedTextField laborBudgetField;
	private final JLabel laborBudgetLabel;
	
	private final JFormattedTextField materialBudgetField;
	private final JLabel materialBudgetLabel;
	
	private final JFormattedTextField equipmentBudgetField;
	private final JLabel equipmentBudgetLabel;
	
	private final JFormattedTextField contractorBudgetField;
	private final JLabel contractorBudgetLabel;
	
	private final JTextArea memoField;
	private final JLabel memoLabel;
	
	private ProjectDockable() {
		nameField = new JTextField(20);
		nameLabel = new JLabel(DISPLAYNAME_NAME);
		nameLabel.setLabelFor(nameField);
		nameLabel.setDisplayedMnemonic('N');
		
		descriptionField = new JTextField(30);
		descriptionLabel = new JLabel(DISPLAYNAME_DESCRIPTION);
		descriptionLabel.setLabelFor(descriptionField);
		descriptionLabel.setDisplayedMnemonic('D');
		
		startDateField = new JTextField(10);
		startDateLabel = new JLabel(DISPLAYNAME_START_DATE);
		startDateLabel.setLabelFor(startDateField);
		startDateLabel.setDisplayedMnemonic('S');
		
		reviewDateField = new JTextField(10);
		reviewDateLabel = new JLabel(DISPLAYNAME_REVIEW_DATE);
		reviewDateLabel.setLabelFor(reviewDateField);
		reviewDateLabel.setDisplayedMnemonic('R');
		
		amendDateField = new JTextField(10);
		amendDateLabel = new JLabel(DISPLAYNAME_AMEND_DATE);
		amendDateLabel.setLabelFor(amendDateField);
		amendDateLabel.setDisplayedMnemonic('A');
		
		endDateField = new JTextField(10);
		endDateLabel = new JLabel(DISPLAYNAME_END_DATE);
		endDateLabel.setLabelFor(endDateField);
		endDateLabel.setDisplayedMnemonic('E');
		
		laborBudgetField = new JFormattedTextField(10);
		laborBudgetLabel = new JLabel(DISPLAYNAME_LABOR_BUDGET);
		laborBudgetLabel.setLabelFor(laborBudgetField);
		laborBudgetLabel.setDisplayedMnemonic('L');
		
		materialBudgetField = new JFormattedTextField(10);
		materialBudgetLabel = new JLabel(DISPLAYNAME_MATERIAL_BUDGET);
		materialBudgetLabel.setLabelFor(materialBudgetField);
		materialBudgetLabel.setDisplayedMnemonic('M');
		
		equipmentBudgetField = new JFormattedTextField(10);
		equipmentBudgetLabel = new JLabel(DISPLAYNAME_EQUIPMENT_BUDGET);
		equipmentBudgetLabel.setLabelFor(equipmentBudgetField);
		equipmentBudgetLabel.setDisplayedMnemonic('q');
		
		contractorBudgetField = new JFormattedTextField(10);
		contractorBudgetLabel = new JLabel(DISPLAYNAME_CONTRACTOR_BUDGET);
		contractorBudgetLabel.setLabelFor(contractorBudgetField);
		contractorBudgetLabel.setDisplayedMnemonic('C');
		
		memoField = new JTextArea(4, 40);
		memoLabel = new JLabel(DISPLAYNAME_MEMO);
		memoLabel.setLabelFor(memoField);
	}
	
	public JComponent createPanel() {
		GridBagPanelBuilder builder = new GridBagPanelBuilder();
		
		builder.addSeparator("Project", 0, 0, 5, 1.0, 0);
		builder.add(nameLabel, 1, 1, 1, 0.0, 14);
		builder.add(nameField, 2, 1, 3, 1.0, 0);
		builder.add(descriptionLabel, 1, 2, 1, 0.0, 14);
		builder.add(descriptionField, 2, 2, 3, 1.0, 0);
		
		builder.addSeparator("Schedule", 0, 3, 5, 1.0, 0);
		builder.add(startDateLabel, 1, 4, 1, 0.0, 14);
		builder.add(startDateField, 2, 4, 1, 0.5, 0);
		builder.add(reviewDateLabel, 3, 4, 1, 0.0, 0);
		builder.add(reviewDateField, 4, 4, 1, 0.5, 0);
		
		builder.add(amendDateLabel, 1, 5, 1, 0.0, 14);
		builder.add(amendDateField, 2, 5, 1, 0.5, 0);
		builder.add(endDateLabel, 3, 5, 1, 0.0, 0);
		builder.add(endDateField, 4, 5, 1, 0.5, 0);
		
		builder.addSeparator("Budget", 0, 6, 5, 1.0, 0);
		builder.add(laborBudgetLabel, 1, 7, 1, 0.0, 14);
		builder.add(laborBudgetField, 2, 7, 1, 0.5, 0);
		builder.add(materialBudgetLabel, 3, 7, 1, 0.0, 0);
		builder.add(materialBudgetField, 4, 7, 1, 0.0, 0);
		
		builder.add(equipmentBudgetLabel, 1, 8, 1, 0.0, 14);
		builder.add(equipmentBudgetField, 2, 8, 1, 0.5, 0);
		builder.add(contractorBudgetLabel, 3, 8, 1, 0.0, 0);
		builder.add(contractorBudgetField, 4, 8, 1, 0.5, 0);
		
		builder.addSeparator(DISPLAYNAME_MEMO, 0, 9, 5, 1.0, 0);
		builder.add(new JScrollPane(memoField), 1, 10, 4, 1.0, 14);
		
		builder.setBorder(BorderFactory.createEmptyBorder(7, 14, 14, 7));
		return builder.getPanel();
	}
	
	public static Dockable createProjectDockableView() {
		BasicDockable project = new BasicDockable(
			new ProjectDockable().createPanel(), VIEW_TITLE, VIEW_IMAGE);
		
		ScrollPaneDockingPort scrollPane = new ScrollPaneDockingPort();
		scrollPane.dock(project, ScrollPaneDockingPort.LOCATIONNAME_CONTENT);
		
		ViewDockingPort viewPort = new ViewDockingPort();
		viewPort.dock(scrollPane, ViewDockingPort.LOCATIONNAME_CONTENT);
		viewPort.setToolBar(ActionUtils.createCloseToolBar(scrollPane));
		
		return viewPort;
	}
	
}
