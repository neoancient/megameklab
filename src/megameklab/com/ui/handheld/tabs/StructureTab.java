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
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import megamek.common.AmmoType;
import megamek.common.Entity;
import megamek.common.EquipmentType;
import megamek.common.HandheldWeapon;
import megamek.common.LocationFullException;
import megamek.common.Mounted;
import megamek.common.TechConstants;
import megamek.common.WeaponType;
import megamek.common.weapons.ArtilleryWeapon;
import megameklab.com.ui.EntitySource;
import megameklab.com.util.CriticalTableModel;
import megameklab.com.util.EquipmentTableModel;
import megameklab.com.util.ITab;
import megameklab.com.util.RefreshListener;
import megameklab.com.util.UnitUtil;
import megameklab.com.util.XTableColumnModel;

/**
 * For building handheld weapons.
 * 
 * @author Neoancient
 *
 */
public class StructureTab extends ITab implements ActionListener, ChangeListener, KeyListener {

	private static final long serialVersionUID = -1262535227804234003L;

    private static final int T_ENERGY    =  0;
    private static final int T_BALLISTIC =  1;
    private static final int T_MISSILE   =  2;
    private static final int T_ARTILLERY =  3;
    private static final int T_WEAPON    =  4;
    private static final int T_AMMO      =  5;
    private static final int T_OTHER     =  6;
    private static final int T_NUM       =  7;

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
    
    private JButton addButton = new JButton("Add");
    private JButton removeButton = new JButton("Remove");
    private JButton removeAllButton = new JButton("Remove All");
    private JComboBox<String> choiceType = new JComboBox<String>();
    private JTextField txtFilter = new JTextField();

    private JRadioButton rbtnStats = new JRadioButton("Stats");
    private JRadioButton rbtnFluff = new JRadioButton("Fluff");

    private TableRowSorter<EquipmentTableModel> equipmentSorter;

    private CriticalTableModel equipmentList;
    private EquipmentTableModel masterEquipmentList;
    private JTable masterEquipmentTable = new JTable();
    private JScrollPane masterEquipmentScroll = new JScrollPane();
    private JTable equipmentTable = new JTable();
    private JScrollPane equipmentScroll = new JScrollPane();

    private String ADD_COMMAND = "ADD";
    private String REMOVE_COMMAND = "REMOVE";
    private String REMOVEALL_COMMAND = "REMOVEALL";
    
    private JSpinner spnArmorTonnage;
    private JLabel lblArmorPoints = new JLabel();
    private JTextField txtHeatSinks = new JTextField();

    public static String getTypeName(int type) {
        switch(type) {
        case T_WEAPON:
            return "All Weapons";
        case T_ENERGY:
            return "Energy Weapons";
        case T_BALLISTIC:
            return "Ballistic Weapons";
        case T_MISSILE:
            return "Missile Weapons";
        case T_ARTILLERY:
            return "Artillery Weapons";
        case T_AMMO:
            return "Ammunition";
        case T_OTHER:
            return "Other Equipment";
        default:
            return "?";
        }
    }

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
        JPanel databasePanel = new JPanel(new GridBagLayout());

        JPanel basicPanel = new JPanel(new GridBagLayout());
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
        chassis.addKeyListener(this);

        gbc.gridx = 0;
        gbc.gridy = 1;
        basicPanel.add(createLabel("Model:", labelSize), gbc);
        gbc.gridx = 1;
        basicPanel.add(model, gbc);
        model.addKeyListener(this);

        gbc.gridx = 0;
        gbc.gridy = 2;
        basicPanel.add(createLabel("Year:", labelSize), gbc);
        gbc.gridx = 1;
        basicPanel.add(era, gbc);
        era.addKeyListener(this);
        era.addKeyListener(this);

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
        techType.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 5;
        basicPanel.add(createLabel("Tech Level:", labelSize), gbc);
        gbc.gridx = 1;
        basicPanel.add(techLevel, gbc);
        techLevel.addActionListener(this);

        setFieldSize(era, comboSize);
        setFieldSize(source, comboSize);
        setFieldSize(chassis, comboSize);
        setFieldSize(model, comboSize);
        setFieldSize(techType, comboSize);
        setFieldSize(techLevel, comboSize);
        setFieldSize(txtHeatSinks, comboSize);
        txtHeatSinks.setEditable(false);

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
        add(leftPanel, gbc);
        gbc.gridx = 1;
        gbc.fill = java.awt.GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(databasePanel, gbc);

        equipmentList = new CriticalTableModel(eSource.getEntity(), CriticalTableModel.WEAPONTABLE);
        equipmentTable.setModel(equipmentList);
        equipmentTable.setIntercellSpacing(new Dimension(0, 0));
        equipmentTable.setShowGrid(false);
        equipmentTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        equipmentTable.setDoubleBuffered(true);
        TableColumn column = null;
        for (int i = 0; i < equipmentList.getColumnCount(); i++) {
            column = equipmentTable.getColumnModel().getColumn(i);
            if(i == 0) {
                column.setPreferredWidth(200);
            }
            column.setCellRenderer(equipmentList.getRenderer());

        }
        equipmentScroll.setViewportView(equipmentTable);
        equipmentScroll.setMinimumSize(new java.awt.Dimension(300, 200));
        equipmentScroll.setPreferredSize(new java.awt.Dimension(300, 200));

        masterEquipmentList = new EquipmentTableModel(eSource.getEntity());
        masterEquipmentTable.setModel(masterEquipmentList);
        masterEquipmentTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        equipmentSorter = new TableRowSorter<EquipmentTableModel>(masterEquipmentList);
        equipmentSorter.setComparator(EquipmentTableModel.COL_HEAT, new WeaponIntegerSorter());
        equipmentSorter.setComparator(EquipmentTableModel.COL_MRANGE, new WeaponIntegerSorter());
        equipmentSorter.setComparator(EquipmentTableModel.COL_DAMAGE, new WeaponDamageSorter());
        equipmentSorter.setComparator(EquipmentTableModel.COL_RANGE, new WeaponRangeSorter());
        equipmentSorter.setComparator(EquipmentTableModel.COL_COST, new FormattedNumberSorter());
        masterEquipmentTable.setRowSorter(equipmentSorter);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        sortKeys.add(new RowSorter.SortKey(EquipmentTableModel.COL_NAME, SortOrder.ASCENDING));
        equipmentSorter.setSortKeys(sortKeys);
        XTableColumnModel equipColumnModel = new XTableColumnModel();
        masterEquipmentTable.setColumnModel(equipColumnModel);
        masterEquipmentTable.createDefaultColumnsFromModel();
        column = null;
        for (int i = 0; i < EquipmentTableModel.N_COL; i++) {
            column = masterEquipmentTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(masterEquipmentList.getColumnWidth(i));
            column.setCellRenderer(masterEquipmentList.getRenderer());
        }
        masterEquipmentTable.setIntercellSpacing(new Dimension(0, 0));
        masterEquipmentTable.setShowGrid(false);
        masterEquipmentTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        masterEquipmentTable.setDoubleBuffered(true);
        masterEquipmentScroll.setViewportView(masterEquipmentTable);

        masterEquipmentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable)e.getSource();
                    int view = target.getSelectedRow();
                    int selected = masterEquipmentTable.convertRowIndexToModel(view);
                    EquipmentType equip = masterEquipmentList.getType(selected);
                    addEquipment(equip);
                    fireTableRefresh();
                }
            }
        });

        masterEquipmentTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "add");
        masterEquipmentTable.getActionMap().put("add", new EnterAction());

        Enumeration<EquipmentType> miscTypes = EquipmentType.getAllTypes();
        ArrayList<EquipmentType> allTypes = new ArrayList<EquipmentType>();
        while (miscTypes.hasMoreElements()) {
            EquipmentType eq = miscTypes.nextElement();
            allTypes.add(eq);
        }

        masterEquipmentList.setData(allTypes);

        loadEquipmentTable();

        DefaultComboBoxModel<String> typeModel = new DefaultComboBoxModel<String>();
        for (int i = 0; i < T_NUM; i++) {
            typeModel.addElement(getTypeName(i));
        }
        choiceType.setModel(typeModel);
        choiceType.setSelectedIndex(0);
        choiceType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterEquipment();
            }
        });

        txtFilter.setText("");
        txtFilter.setMinimumSize(new java.awt.Dimension(200, 28));
        txtFilter.setPreferredSize(new java.awt.Dimension(200, 28));
        txtFilter.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                filterEquipment();
            }
            public void insertUpdate(DocumentEvent e) {
                filterEquipment();
            }
            public void removeUpdate(DocumentEvent e) {
                filterEquipment();
            }
        });

        filterEquipment();
        addButton.setMnemonic('A');
        removeButton.setMnemonic('R');
        removeAllButton.setMnemonic('l');

        ButtonGroup bgroupView = new ButtonGroup();
        bgroupView.add(rbtnStats);
        bgroupView.add(rbtnFluff);

        rbtnStats.setSelected(true);
        rbtnStats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setEquipmentView();
            }
        });
        rbtnFluff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setEquipmentView();
            }
        });
        JPanel viewPanel = new JPanel(new GridLayout(0,2));
        viewPanel.add(rbtnStats);
        viewPanel.add(rbtnFluff);
        setEquipmentView();

        //layout
        gbc = new GridBagConstraints();

        JPanel loadoutPanel = new JPanel(new GridBagLayout());

        loadoutPanel.setBorder(BorderFactory.createTitledBorder("Current Loadout"));
        databasePanel.setBorder(BorderFactory.createTitledBorder("Equipment Database"));

        JPanel panRemoveBtns = new JPanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        loadoutPanel.add(panRemoveBtns, gbc);
        panRemoveBtns.add(removeButton);
        removeButton.setActionCommand(REMOVE_COMMAND);
        removeButton.addActionListener(this);
        panRemoveBtns.add(removeAllButton);
        removeAllButton.setActionCommand(REMOVEALL_COMMAND);
        removeAllButton.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.VERTICAL;
        loadoutPanel.add(equipmentScroll, gbc);

        Dimension spinnerSize = new Dimension(55, 25);

        spnArmorTonnage = new JSpinner(new SpinnerNumberModel(0.0, 0.0, null, 0.5));
        ((JSpinner.DefaultEditor) spnArmorTonnage.getEditor()).setSize(spinnerSize);
        ((JSpinner.DefaultEditor) spnArmorTonnage.getEditor())
                .setMaximumSize(spinnerSize);
        ((JSpinner.DefaultEditor) spnArmorTonnage.getEditor())
                .setPreferredSize(spinnerSize);
        ((JSpinner.DefaultEditor) spnArmorTonnage.getEditor())
                .setMinimumSize(spinnerSize);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        loadoutPanel.add(new JLabel("Armor Tonnage:"), gbc);
        gbc.gridx = 1;
        loadoutPanel.add(spnArmorTonnage, gbc);
        gbc.gridx = 2;
        gbc.weightx = 1.0;
        loadoutPanel.add(lblArmorPoints, gbc);
        spnArmorTonnage.addChangeListener(this);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        loadoutPanel.add(createLabel("Heat Sinks:", labelSize), gbc);
        gbc.gridx = 1;
        loadoutPanel.add(txtHeatSinks, gbc);
                
        leftPanel.add(loadoutPanel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(2,2,2,2);
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.WEST;
        databasePanel.add(addButton, gbc);
        addButton.setActionCommand(ADD_COMMAND);
        addButton.addActionListener(this);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        databasePanel.add(choiceType, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        databasePanel.add(txtFilter, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        databasePanel.add(viewPanel, gbc);

        gbc.insets = new Insets(2,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.fill = java.awt.GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        databasePanel.add(masterEquipmentScroll, gbc);
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
        filterEquipment();
        updateEquipment();
        
        spnArmorTonnage.setValue(getHandheld().getLabArmorTonnage());
        lblArmorPoints.setText("(" + getHandheld().getLabTotalArmorPoints() + " Points)");
        txtHeatSinks.setText(Integer.toString(getHandheld().getNumHeatSinks()));
        
    	handlersActive = true;
        fireTableRefresh();
    }

    public void addRefreshedListener(RefreshListener l) {
        refresh = l;
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
        if (e.getActionCommand().equals(ADD_COMMAND)) {
            int view = masterEquipmentTable.getSelectedRow();
            if(view < 0) {
                //selection got filtered away
                return;
            }
            int selected = masterEquipmentTable.convertRowIndexToModel(view);
            EquipmentType equip = masterEquipmentList.getType(selected);
            addEquipment(equip);
        } else if (e.getActionCommand().equals(REMOVE_COMMAND)) {
            int selectedRows[] = equipmentTable.getSelectedRows();
            for (Integer row : selectedRows){
                equipmentList.removeMounted(row);
            }
            equipmentList.removeCrits(selectedRows);
            addButton.setEnabled(getHandheld().getEmptyCriticals(HandheldWeapon.LOC_GUNS) > 0);
        } else if (e.getActionCommand().equals(REMOVEALL_COMMAND)) {
            removeAllEquipment();
            addButton.setEnabled(getHandheld().getEmptyCriticals(HandheldWeapon.LOC_GUNS) > 0);
        }
        getHandheld().setNumHeatSinks(UnitUtil.getHandheldHeat(getHandheld()));
        fireTableRefresh();
        refresh.refreshAll();
    }

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (!handlersActive) {
			return;
		}
        if (e.getSource().equals(era)) {
            try {
                int year = Integer.parseInt(era.getText());
                if (year < 1950) {
                    return;
                }
                getTank().setYear(Integer.parseInt(era.getText()));
            } catch (Exception ex) {
                getHandheld().setYear(3145);
            }
            refresh.refreshEquipment();
        } else if (e.getSource().equals(source)) {
            getTank().setSource(source.getText());
        } else if (e.getSource().equals(chassis)) {
            getTank().setChassis(chassis.getText().trim());
            refresh.refreshPreview();
            refresh.refreshHeader();
        } else if (e.getSource().equals(model)) {
            getTank().setModel(model.getText().trim());
            refresh.refreshPreview();
            refresh.refreshHeader();
        }
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (!handlersActive) {
			return;
		}
		if (e.getSource().equals(spnArmorTonnage)) {
			double tonnage = (Double)spnArmorTonnage.getValue();
			getHandheld().setArmorTonnage(tonnage);
			getHandheld().initializeArmor((int)Math.floor(tonnage * 16), HandheldWeapon.LOC_GUNS);
			refresh.refreshAll();
		}
	}

	private void loadEquipmentTable() {

        for (Mounted mount : getHandheld().getWeaponList()) {
            equipmentList.addCrit(mount);
        }

        for (Mounted mount : getHandheld().getAmmo()) {
            equipmentList.addCrit(mount);
        }

        for (Mounted mount : getHandheld().getMisc()) {
            equipmentList.addCrit(mount);
        }
    }

    private void addEquipment(EquipmentType equip) {
        boolean success = false;
        Mounted mount = null;
        try {
            mount = new Mounted(getHandheld(), equip);
            int loc = Entity.LOC_NONE;
            if (equip instanceof WeaponType) {
            	loc = HandheldWeapon.LOC_GUNS;
            }
            getHandheld().addEquipment(mount, loc, false);
            success = true;
            addButton.setEnabled(getHandheld().getEmptyCriticals(HandheldWeapon.LOC_GUNS) > 0);
        } catch (LocationFullException lfe) {
        	//should not happen, since we disable the add button when full.
        }
        if (success) {
            equipmentList.addCrit(mount);
        }
    }

    public void updateEquipment() {
        equipmentList.removeAllCrits();
        loadEquipmentTable();
    }

    public void removeAllEquipment() {
        for (int count = 0; count < equipmentList.getRowCount(); count++) {
            equipmentList.removeMounted(count);
        }
        equipmentList.removeAllCrits();
    }

    private void fireTableRefresh() {
        equipmentList.updateUnit(getHandheld());
        equipmentList.refreshModel();
    }

    public CriticalTableModel getEquipmentList() {
        return equipmentList;
    }

    private void filterEquipment() {
        RowFilter<EquipmentTableModel, Integer> equipmentTypeFilter = null;
        final int nType = choiceType.getSelectedIndex();
        equipmentTypeFilter = new RowFilter<EquipmentTableModel,Integer>() {
            @Override
            public boolean include(Entry<? extends EquipmentTableModel, ? extends Integer> entry) {
            	HandheldWeapon handheld = getHandheld();
                EquipmentTableModel equipModel = entry.getModel();
                EquipmentType etype = equipModel.getType(entry.getIdentifier());
                WeaponType wtype = null;
                if (etype instanceof WeaponType) {
                    wtype = (WeaponType)etype;
                }
                AmmoType atype = null;
                if (etype instanceof AmmoType) {
                    atype = (AmmoType)etype;
                }
                if (!UnitUtil.isLegal(handheld, etype.getTechLevel(handheld.getTechLevelYear()))) {
                    return false;
                }
                if (((nType == T_OTHER) && UnitUtil.isHandheldEquipment(etype, handheld))
                        || (((nType == T_WEAPON) && UnitUtil.isHandheldWeapon(etype, handheld)))
                        || ((nType == T_ENERGY) && UnitUtil.isHandheldWeapon(etype, handheld)
                            && (wtype != null) && (wtype.hasFlag(WeaponType.F_ENERGY)
                            || (wtype.hasFlag(WeaponType.F_PLASMA) && (wtype.getAmmoType() == AmmoType.T_PLASMA))))
                        || ((nType == T_BALLISTIC) && UnitUtil.isHandheldWeapon(etype, handheld)
                            && (wtype != null) && (wtype.hasFlag(WeaponType.F_BALLISTIC)
                                    && (wtype.getAmmoType() != AmmoType.T_NA)))
                        || ((nType == T_MISSILE) && UnitUtil.isHandheldWeapon(etype, handheld)
                            && (wtype != null) && ((wtype.hasFlag(WeaponType.F_MISSILE)
                                    && (wtype.getAmmoType() != AmmoType.T_NA)) || (wtype.getAmmoType() == AmmoType.T_C3_REMOTE_SENSOR)))
                        || ((nType == T_ARTILLERY) && UnitUtil.isHandheldWeapon(etype, handheld)
                            && (wtype != null) && (wtype instanceof ArtilleryWeapon))
                        || (((nType == T_AMMO) & (atype != null)) && UnitUtil.canUseAmmo(handheld, atype))) {
                    if (txtFilter.getText().length() > 0) {
                        String text = txtFilter.getText();
                        return etype.getName().toLowerCase().contains(text.toLowerCase());
                    } else {
                        return true;
                    }
                }
                return false;
            }
        };
        equipmentSorter.setRowFilter(equipmentTypeFilter);
    }

    public void setEquipmentView() {
        XTableColumnModel columnModel = (XTableColumnModel)masterEquipmentTable.getColumnModel();
        if(rbtnStats.isSelected()) {
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_NAME), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_DAMAGE), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_DIVISOR), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_SPECIAL), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_HEAT), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_MRANGE), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_RANGE), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_SHOTS), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_TECH), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_TRATING), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_AVSL), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_AVSW), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_AVCL), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_DINTRO), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_DEXTINCT), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_DREINTRO), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_COST), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_CREW), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_BV), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_TON), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_CRIT), true);
        } else {
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_NAME), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_DAMAGE), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_DIVISOR), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_SPECIAL), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_HEAT), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_MRANGE), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_RANGE), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_SHOTS), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_TECH), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_TRATING), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_AVSL), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_AVSW), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_AVCL), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_DINTRO), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_DEXTINCT), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_DREINTRO), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_COST), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_CREW), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_BV), false);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_TON), true);
            columnModel.setColumnVisible(columnModel.getColumnByModelIndex(EquipmentTableModel.COL_CRIT), true);
        }
    }

    private class EnterAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 8247993757008802162L;

        @Override
        public void actionPerformed(ActionEvent e) {
            int view = masterEquipmentTable.getSelectedRow();
            if(view < 0) {
                //selection got filtered away
                return;
            }
            int selected = masterEquipmentTable.convertRowIndexToModel(view);
            EquipmentType equip = masterEquipmentList.getType(selected);
            addEquipment(equip);
            fireTableRefresh();
        }
    }

    /**
     * A comparator for integers written as strings with "-" sorted to the bottom always
     * @author Jay Lawson
     *
     */
    public class WeaponIntegerSorter implements Comparator<String> {

        @Override
        public int compare(String s0, String s1) {
            if(s0.equals("-") && s1.equals("-")) {
                return 0;
            } else if(s0.equals("-")) {
                return 1;
            } else if(s1.equals("-")) {
                return -1;
            } else {
                //get the numbers associated with each string
                int r0 = Integer.parseInt(s0);
                int r1 = Integer.parseInt(s1);
                return ((Comparable<Integer>)r1).compareTo(r0);
            }
        }
    }

    /**
     * A comparator for integers written as strings with "-" sorted to the bottom always
     * @author Jay Lawson
     *
     */
    public class WeaponRangeSorter implements Comparator<String> {

        @Override
        public int compare(String s0, String s1) {
            if(s0.equals("-") && s1.equals("-")) {
                return 0;
            } else if(s0.equals("-")) {
                return 1;
            } else if(s1.equals("-")) {
                return -1;
            } else {
                //get the numbers associated with each string
                int short0 = Integer.parseInt(s0.split("/")[0]);
                int short1 = Integer.parseInt(s1.split("/")[0]);
                int med0 = Integer.parseInt(s0.split("/")[1]);
                int med1 = Integer.parseInt(s1.split("/")[1]);
                int long0 = Integer.parseInt(s0.split("/")[2]);
                int long1 = Integer.parseInt(s1.split("/")[2]);
                int compare = ((Comparable<Integer>)short1).compareTo(short0);
                if(compare != 0) {
                    return compare;
                }
                compare = ((Comparable<Integer>)med1).compareTo(med0);
                if(compare != 0) {
                    return compare;
                }
                return ((Comparable<Integer>)long1).compareTo(long0);
            }
        }
    }

    public class WeaponDamageSorter implements Comparator<String> {

        @Override
        public int compare(String s0, String s1) {
            if(s0.equals("-") && s1.equals("-")) {
                return 0;
            } else if(s0.equals("-")) {
                return 1;
            } else if(s1.equals("-")) {
                return -1;
            } else if(s0.equals("Cluster") && s1.equals("-")) {
                return 1;
            } else if(s0.equals("-") && s1.equals("Cluster")) {
                return -1;
            } else if(s0.equals("Cluster") && s1.equals("Special")) {
                return 1;
            } else if(s0.equals("Special") && s1.equals("Cluster")) {
                return -1;
            } else if(s0.equals("Special") && s1.equals("-")) {
                return 1;
            } else if(s0.equals("-") && s1.equals("Special")) {
                return -1;
            } else if(s0.equals("Cluster") && s1.equals("Cluster")) {
                return 0;
            } else if(s0.equals("Cluster")) {
                return 1;
            } else if(s1.equals("Cluster")) {
                return -1;
            } else if(s0.equals("Special") && s1.equals("Special")) {
                return 0;
            } else if(s0.equals("Special")) {
                return 1;
            } else if(s1.equals("Special")) {
                return -1;
            } else {
                //get the numbers associated with each string
                int r1 = parseDamage(s1);
                int r0 = parseDamage(s0);
                return ((Comparable<Integer>)r1).compareTo(r0);
            }
        }

        private int parseDamage(String s) {
            int damage = 0;
            if(s.contains("/")) {
                damage = Integer.parseInt(s.split("/")[0]);
            } else {
                damage = Integer.parseInt(s);
            }
            return damage;
        }
    }

    /**
     * A comparator for numbers that have been formatted with DecimalFormat
     * @author Jay Lawson
     *
     */
    public class FormattedNumberSorter implements Comparator<String> {

        @Override
        public int compare(String s0, String s1) {
            //lets find the weight class integer for each name
            DecimalFormat format = new DecimalFormat();
            int l0 = 0;
            try {
                l0 = format.parse(s0).intValue();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            int l1 = 0;
            try {
                l1 = format.parse(s1).intValue();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            return ((Comparable<Integer>)l0).compareTo(l1);
        }
    }
}
