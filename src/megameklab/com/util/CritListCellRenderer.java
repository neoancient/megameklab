/*
 * MegaMekLab - Copyright (C) 2009
 *
 * Original author - jtighe (torren@users.sourceforge.net)
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */

/*
 * Thanks to Lost in space of the Solaris Sunk Works Project for the code snippet and idea.
 */

package megameklab.com.util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import megamek.common.AmmoType;
import megamek.common.CriticalSlot;
import megamek.common.Entity;
import megamek.common.Mech;
import megamek.common.Mounted;
import megamek.common.WeaponType;

public class CritListCellRenderer extends DefaultListCellRenderer {

    private JList list = null;
    private int index = -1;
    private Entity unit = null;


    /**
     *
     */
    private static final long serialVersionUID = 1599368063832366744L;

    public CritListCellRenderer(Entity unit) {
        this.unit = unit;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
        JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
        this.list = list;
        this.index = index;

        CriticalSlot cs = getCrit();

        if (cs != null) {

            if (cs.getType() == CriticalSlot.TYPE_SYSTEM) {
                label.setForeground(Color.GREEN);
            } else if (cs.getMount() != null) {

                Mounted mount = cs.getMount();

                if (mount.getType() instanceof WeaponType) {
                    label.setForeground(Color.RED);
                } else if (mount.getType() instanceof AmmoType) {
                    label.setForeground(Color.BLUE);
                }

                int size = UnitUtil.getCritsUsed((Mech) unit, mount.getType());
                if (unit instanceof Mech && size > 1) {
                    label.setText("(" + size + ") " + label.getText());
                }
            }
        }

        return label;
    }

    private CriticalSlot getCrit() {
        int slot = index;
        int location = getCritLocation();
        CriticalSlot crit = null;
        if (slot >= 0 && slot < unit.getNumberOfCriticals(location)) {
            crit = unit.getCritical(location, slot);
        }

        return crit;
    }

    private int getCritLocation() {
        return Integer.parseInt(list.getName());
    }

}