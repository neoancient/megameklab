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
package megameklab.com.ui.handheld.tabs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import megamek.common.TechConstants;
import megameklab.com.ui.EntitySource;
import megameklab.com.util.ITab;
import megameklab.com.util.RefreshListener;
import megameklab.com.util.UnitUtil;

/**
 * For building handheld weapons.
 * 
 * @author Neoancient
 *
 */
public class StructureTab extends ITab implements ActionListener {

	private static final long serialVersionUID = -1262535227804234003L;

	private RefreshListener refresh;
    
    private String[] techTypes = { "Inner Sphere", "Clan", "Mixed Inner Sphere", "Mixed Clan" };
    private JComboBox<String> techType = new JComboBox<String>(techTypes);
    private String[] isTechLevels = { "Introductory", "Standard", "Advanced", "Experimental", "Unoffical" };
    private String[] clanTechLevels = { "Standard", "Advanced", "Experimental", "Unoffical" };
    private JComboBox<String> techLevel = new JComboBox<String>(isTechLevels);

    private JTextField era = new JTextField(3);
    private JTextField source = new JTextField(3);

    private JTextField chassis = new JTextField(5);
    private JTextField model = new JTextField(5);
    
    private boolean handlersActive;

    public StructureTab(EntitySource eSource) {
        super(eSource);
        setUpPanels();
        refresh();
        handlersActive = true;
    }

    public void setUpPanels() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JPanel basicPanel = new JPanel(new GridBagLayout());
        JPanel equipmentPanel = new JPanel(new GridBagLayout());
        JPanel ammoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Dimension comboSize = new Dimension(200, 25);
        Dimension labelSize = new Dimension(110, 25);

        chassis.setText(getHandheld().getChassis());
        model.setText(getHandheld().getModel());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 1, 2);
        basicPanel.add(createLabel("Chassis:", labelSize), gbc);
        gbc.gridx = 1;
        basicPanel.add(chassis, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        basicPanel.add(createLabel("Model:", labelSize), gbc);
        gbc.gridx = 1;
        basicPanel.add(model, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        basicPanel.add(createLabel("Year:", labelSize), gbc);
        gbc.gridx = 1;
        basicPanel.add(era, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        basicPanel.add(createLabel("Source/Era:", labelSize), gbc);
        gbc.gridx = 1;
        basicPanel.add(source, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        basicPanel.add(createLabel("Tech:", labelSize), gbc);
        gbc.gridx = 1;
        basicPanel.add(techType, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        basicPanel.add(createLabel("Tech Level:", labelSize), gbc);
        gbc.gridx = 1;
        basicPanel.add(techLevel, gbc);

        setFieldSize(era, comboSize);
        setFieldSize(source, comboSize);
        setFieldSize(chassis, comboSize);
        setFieldSize(model, comboSize);
        setFieldSize(techType, comboSize);
        setFieldSize(techLevel, comboSize);

        basicPanel.setBorder(BorderFactory.createTitledBorder("Basic Information"));
        
        leftPanel.add(basicPanel);
        leftPanel.add(Box.createVerticalGlue());
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(new JScrollPane(leftPanel), gbc);
        gbc.gridx = 1;
        gbc.fill = java.awt.GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
//        add(equipmentPane, gbc);
    }

    public JLabel createLabel(String text, Dimension maxSize) {

        JLabel label = new JLabel(text, SwingConstants.RIGHT);

        setFieldSize(label, maxSize);
        return label;
    }

    public void setFieldSize(JComponent box, Dimension maxSize) {
        box.setPreferredSize(maxSize);
        box.setMaximumSize(maxSize);
        box.setMinimumSize(maxSize);
    }

    public void refresh() {
        handlersActive = false;
        era.setText(Integer.toString(getHandheld().getYear()));
        source.setText(getHandheld().getSource());

        if (getHandheld().isClan()) {
            techLevel.removeAllItems();
            for (String item : clanTechLevels) {
                techLevel.addItem(item);
            }
        } else {
            techLevel.removeAllItems();
            for (String item : isTechLevels) {
                techLevel.addItem(item);
            }
        }

        if (getHandheld().isMixedTech()) {
            if (getHandheld().isClan()) {

                techType.setSelectedIndex(3);
                if (getHandheld().getTechLevel() >= TechConstants.T_CLAN_UNOFFICIAL) {
                    techLevel.setSelectedIndex(3);
                } else if (getHandheld().getTechLevel() >= TechConstants.T_CLAN_EXPERIMENTAL) {
                    techLevel.setSelectedIndex(2);
                } else {
                    techLevel.setSelectedIndex(1);
                }
            } else {
                techType.setSelectedIndex(2);

                if (getHandheld().getTechLevel() >= TechConstants.T_IS_UNOFFICIAL) {
                    techLevel.setSelectedIndex(4);
                } else if (getHandheld().getTechLevel() >= TechConstants.T_IS_EXPERIMENTAL) {
                    techLevel.setSelectedIndex(3);
                } else {
                    techLevel.setSelectedIndex(2);
                }
            }
        } else if (getHandheld().isClan()) {

            techType.setSelectedIndex(1);
            if (getHandheld().getTechLevel() >= TechConstants.T_CLAN_UNOFFICIAL) {
                techLevel.setSelectedIndex(3);
            } else if (getHandheld().getTechLevel() >= TechConstants.T_CLAN_EXPERIMENTAL) {
                techLevel.setSelectedIndex(2);
            } else if (getHandheld().getTechLevel() >= TechConstants.T_CLAN_ADVANCED) {
                techLevel.setSelectedIndex(1);
            } else {
                techLevel.setSelectedIndex(0);
            }
        } else {
            techType.setSelectedIndex(0);

            if (getHandheld().getTechLevel() >= TechConstants.T_IS_UNOFFICIAL) {
                techLevel.setSelectedIndex(4);
            } else if (getHandheld().getTechLevel() >= TechConstants.T_IS_EXPERIMENTAL) {
                techLevel.setSelectedIndex(3);
            } else if (getHandheld().getTechLevel() >= TechConstants.T_IS_ADVANCED) {
                techLevel.setSelectedIndex(2);
            } else if (getHandheld().getTechLevel() >= TechConstants.T_IS_TW_NON_BOX) {
                techLevel.setSelectedIndex(1);
            } else {
                techLevel.setSelectedIndex(0);
            }
        }
        handlersActive = true;
    }

    public void addRefreshedListener(RefreshListener l) {
        refresh = l;
//        equipmentView.addRefreshedListener(refresh);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if (!handlersActive) {
    		return;
    	}
        if (e.getSource() instanceof JComboBox) {
            @SuppressWarnings("unchecked")
            JComboBox<String> combo = (JComboBox<String>) e.getSource();
            if (combo.equals(techLevel)) {
                int unitTechLevel = techLevel.getSelectedIndex();
                if (getHandheld().isClan()) {
                    switch (unitTechLevel) {
                    case 0:
                        getHandheld().setTechLevel(TechConstants.T_CLAN_TW);
                        getHandheld().setArmorTechLevel(TechConstants.T_CLAN_TW);
                        techType.setSelectedIndex(1);
                        break;
                    case 1:
                        getHandheld().setTechLevel(TechConstants.T_CLAN_ADVANCED);
                        getHandheld().setArmorTechLevel(TechConstants.T_CLAN_ADVANCED);
                        break;
                    case 2:
                        getHandheld().setTechLevel(TechConstants.T_CLAN_EXPERIMENTAL);
                        getHandheld().setArmorTechLevel(TechConstants.T_CLAN_EXPERIMENTAL);
                        break;
                    case 3:
                        getHandheld().setTechLevel(TechConstants.T_CLAN_UNOFFICIAL);
                        getHandheld().setArmorTechLevel(TechConstants.T_CLAN_UNOFFICIAL);
                        break;
                    default:
                        getHandheld().setTechLevel(TechConstants.T_CLAN_TW);
                        getHandheld().setArmorTechLevel(TechConstants.T_CLAN_TW);
                        break;
                    }

                } else {
                    switch (unitTechLevel) {
                    case 0:
                        getHandheld().setTechLevel(TechConstants.T_INTRO_BOXSET);
                        getHandheld().setArmorTechLevel(TechConstants.T_INTRO_BOXSET);
                        techType.setSelectedIndex(0);
                        break;
                    case 1:
                        getHandheld().setTechLevel(TechConstants.T_IS_TW_NON_BOX);
                        getHandheld().setArmorTechLevel(TechConstants.T_IS_TW_NON_BOX);
                        techType.setSelectedIndex(0);
                        break;
                    case 2:
                        getHandheld().setTechLevel(TechConstants.T_IS_ADVANCED);
                        getHandheld().setArmorTechLevel(TechConstants.T_IS_ADVANCED);
                        break;
                    case 3:
                        getHandheld().setTechLevel(TechConstants.T_IS_EXPERIMENTAL);
                        getHandheld().setArmorTechLevel(TechConstants.T_IS_EXPERIMENTAL);
                        break;
                    default:
                        getHandheld().setTechLevel(TechConstants.T_IS_UNOFFICIAL);
                        getHandheld().setArmorTechLevel(TechConstants.T_IS_UNOFFICIAL);
                        break;
                    }

                }
                UnitUtil.checkEquipmentByTechLevel(getHandheld());
            } else if (combo.equals(techType)) {
                if ((techType.getSelectedIndex() == 1) && (!getHandheld().isClan() || getHandheld().isMixedTech())) {
                    techLevel.removeAllItems();
                    for (String item : clanTechLevels) {
                        techLevel.addItem(item);
                    }
                    if (!getHandheld().isClan()) {
                        int level = TechConstants.getOppositeTechLevel(getHandheld().getTechLevel());
                        getHandheld().setTechLevel(level);
                        getHandheld().setArmorTechLevel(level);
                    }
                    getHandheld().setMixedTech(false);
                } else if ((techType.getSelectedIndex() == 0)
                        && (getHandheld().isClan() || getHandheld().isMixedTech())) {
                    techLevel.removeAllItems();

                    for (String item : isTechLevels) {
                        techLevel.addItem(item);
                    }

                    if (getHandheld().isClan()) {
                        int level = TechConstants.getOppositeTechLevel(getHandheld().getTechLevel());
                        getHandheld().setTechLevel(level);
                        getHandheld().setArmorTechLevel(level);
                    }
                    getHandheld().setMixedTech(false);
                } else if ((techType.getSelectedIndex() == 2)
                        && (!getHandheld().isMixedTech() || getHandheld().isClan())) {
                    techLevel.removeAllItems();
                    for (String item : isTechLevels) {
                        techLevel.addItem(item);
                    }
                    if (getHandheld().getYear() < 3090) {
                        // before 3090, mixed tech is experimental
                        if ((getHandheld().getTechLevel() != TechConstants.T_IS_UNOFFICIAL)) {
                            getHandheld().setTechLevel(TechConstants.T_IS_EXPERIMENTAL);
                            getHandheld().setArmorTechLevel(TechConstants.T_IS_EXPERIMENTAL);
                        }
                    } else if (getHandheld().getYear() < 3145) {
                        // between 3090 and 3145, mixed tech is advanced
                        if ((getHandheld().getTechLevel() != TechConstants.T_IS_UNOFFICIAL)
                                && (getHandheld().getTechLevel() != TechConstants.T_IS_EXPERIMENTAL)) {
                            getHandheld().setTechLevel(TechConstants.T_IS_ADVANCED);
                            getHandheld().setArmorTechLevel(TechConstants.T_IS_ADVANCED);
                        }
                    } else {
                        // from 3145 on, mixed tech is tourney legal
                        if ((getHandheld().getTechLevel() != TechConstants.T_IS_UNOFFICIAL)
                                && (getHandheld().getTechLevel() != TechConstants.T_IS_EXPERIMENTAL)
                                && (getHandheld().getTechLevel() != TechConstants.T_IS_TW_NON_BOX)) {
                            getHandheld().setTechLevel(TechConstants.T_IS_TW_NON_BOX);
                            getHandheld().setArmorTechLevel(TechConstants.T_IS_TW_NON_BOX);
                        }
                    }
                    getHandheld().setMixedTech(true);
                } else if ((techType.getSelectedIndex() == 3)
                        && (!getHandheld().isMixedTech() || !getHandheld().isClan())) {
                    techLevel.removeAllItems();
                    for (String item : clanTechLevels) {
                        techLevel.addItem(item);
                    }
                    if (getHandheld().getYear() < 3090) {
                        // before 3090, mixed tech is experimental
                        if ((getHandheld().getTechLevel() != TechConstants.T_CLAN_UNOFFICIAL)) {
                            getHandheld().setTechLevel(TechConstants.T_CLAN_EXPERIMENTAL);
                            getHandheld().setArmorTechLevel(TechConstants.T_CLAN_EXPERIMENTAL);
                        }
                    } else if (getHandheld().getYear() < 3145) {
                        // between 3090 and 3145, mixed tech is advanced
                        if ((getHandheld().getTechLevel() != TechConstants.T_CLAN_UNOFFICIAL)
                                && (getHandheld().getTechLevel() != TechConstants.T_CLAN_EXPERIMENTAL)) {
                            getHandheld().setTechLevel(TechConstants.T_CLAN_ADVANCED);
                            getHandheld().setArmorTechLevel(TechConstants.T_CLAN_ADVANCED);
                        }
                    } else {
                        // from 3145 on, mixed tech is tourney legal
                        if ((getHandheld().getTechLevel() != TechConstants.T_CLAN_UNOFFICIAL)
                                && (getHandheld().getTechLevel() != TechConstants.T_CLAN_EXPERIMENTAL)
                                && (getHandheld().getTechLevel() != TechConstants.T_CLAN_TW)) {
                            getHandheld().setTechLevel(TechConstants.T_CLAN_TW);
                            getHandheld().setArmorTechLevel(TechConstants.T_CLAN_TW);
                        }
                    }
                    getHandheld().setMixedTech(true);
                } else {
                    return;
                }
                UnitUtil.checkEquipmentByTechLevel(getHandheld());
            }
        }
        refresh.refreshAll();
    }

}
