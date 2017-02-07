/*
 * MegaMekLab - Copyright (C) 2017 The MegaMek Team
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package megameklab.com.ui.handheld;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import megamek.common.Entity;
import megamek.common.HandheldWeapon;
import megamek.common.TechConstants;
import megameklab.com.ui.MegaMekLabMainUI;
import megameklab.com.ui.handheld.tabs.PreviewTab;
import megameklab.com.ui.handheld.tabs.StructureTab;
import megameklab.com.util.MenuBarCreator;

/**
 * UI for constructing handheld weapons
 * 
 * @author Neoancient
 *
 */
public class MainUI extends MegaMekLabMainUI {
	
	private static final long serialVersionUID = 874923468536662902L;
	
	StructureTab structureTab;
    PreviewTab previewTab;
    StatusBar statusbar;
    JTabbedPane ConfigPane = new JTabbedPane(SwingConstants.TOP);
    JPanel masterPanel = new JPanel();
    JScrollPane scroll = new JScrollPane();
    private MenuBarCreator menubarcreator;
	
	public MainUI() {
        super();
        createNewUnit(Entity.ETYPE_HANDHELD_WEAPON, false);
        setTitle(getEntity().getChassis() + " " + getEntity().getModel() + ".mtf");
        menubarcreator = new MenuBarCreator(this);
        setJMenuBar(menubarcreator);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        scroll.setViewportView(masterPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        this.add(scroll);

        reloadTabs();
        setVisible(true);
        repaint();
        refreshAll();
	}
	
	/* (non-Javadoc)
	 * @see megameklab.com.ui.MegaMekLabMainUI#reloadTabs()
	 */
	@Override
	public void reloadTabs() {
        masterPanel.removeAll();
        ConfigPane.removeAll();

        masterPanel.setLayout(new BorderLayout());

        statusbar = new StatusBar(this);
        structureTab = new StructureTab(this);
        previewTab = new PreviewTab(this);

        structureTab.addRefreshedListener(this);

        ConfigPane.addTab("Build", structureTab);
        ConfigPane.addTab("Preview", previewTab);

        masterPanel.add(ConfigPane, BorderLayout.CENTER);
        masterPanel.add(statusbar, BorderLayout.SOUTH);

        refreshHeader();
        this.repaint();
	}

	/* (non-Javadoc)
	 * @see megameklab.com.ui.MegaMekLabMainUI#createNewUnit(long, boolean)
	 */
	@Override
	public void createNewUnit(long entitytype, boolean isSuperHeavy) {
        setEntity(new HandheldWeapon());
        getEntity().setYear(3145);
        getEntity().setTechLevel(TechConstants.T_IS_TW_NON_BOX);
        getEntity().setArmorTechLevel(TechConstants.T_IS_TW_NON_BOX);
        getEntity().autoSetInternal();
        getEntity().setChassis("New");
        getEntity().setModel("Handheld Weapon");
	}
	
	/* (non-Javadoc)
	 * @see megameklab.com.ui.MegaMekLabMainUI#refreshAll()
	 */
	@Override
	public void refreshAll() {
        statusbar.refresh();
        structureTab.refresh();
        previewTab.refresh();
	}

	/* (non-Javadoc)
	 * @see megameklab.com.ui.MegaMekLabMainUI#refreshArmor()
	 */
	@Override
	public void refreshArmor() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see megameklab.com.ui.MegaMekLabMainUI#refreshBuild()
	 */
	@Override
	public void refreshBuild() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see megameklab.com.ui.MegaMekLabMainUI#refreshEquipment()
	 */
	@Override
	public void refreshEquipment() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see megameklab.com.ui.MegaMekLabMainUI#refreshHeader()
	 */
	@Override
	public void refreshHeader() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see megameklab.com.ui.MegaMekLabMainUI#refreshStatus()
	 */
	@Override
	public void refreshStatus() {
        statusbar.refresh();
	}

	/* (non-Javadoc)
	 * @see megameklab.com.ui.MegaMekLabMainUI#refreshStructure()
	 */
	@Override
	public void refreshStructure() {
        structureTab.refresh();
	}

	/* (non-Javadoc)
	 * @see megameklab.com.ui.MegaMekLabMainUI#refreshWeapons()
	 */
	@Override
	public void refreshWeapons() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see megameklab.com.ui.MegaMekLabMainUI#refreshPreview()
	 */
	@Override
	public void refreshPreview() {
        previewTab.refresh();
	}

}
