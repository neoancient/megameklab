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

import java.awt.BorderLayout;
import java.awt.Color;

import megamek.client.ui.swing.MechViewPanel;
import megamek.common.MechView;
import megameklab.com.ui.EntitySource;
import megameklab.com.util.ITab;

/**
 * Preview tab for handheld weapons
 * 
 * @author Neoancient
 *
 */
public class PreviewTab extends ITab {

	private static final long serialVersionUID = 3768698270044435462L;

	private MechViewPanel panelMekView;

    public PreviewTab(EntitySource eSource) {
        super(eSource);
        this.setLayout(new BorderLayout());
        panelMekView = new MechViewPanel(350, 500);
        add(panelMekView, BorderLayout.CENTER);
        setBackground(Color.WHITE);
        refresh();
    }
    
    public void refresh() {
        boolean populateTextFields = true;
        MechView mechView = null;
        try {
            mechView = new MechView(getHandheld(), false);
        } catch (Exception e) {
            e.printStackTrace();
            // error unit didn't load right. this is bad news.
            populateTextFields = false;
        }
        if (populateTextFields && (mechView != null)) {
            panelMekView.setMech(getHandheld());
        } else {
            panelMekView.reset();
        }
    }
}
