/*
 * MegaMekLab - Copyright (C) 2008
 *
 * Original author - jtighe (torren@users.sourceforge.net)
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */

package megameklab.com.ui.Aero.Printing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;

import megamek.common.Aero;
import megameklab.com.util.ImageHelper;
import megameklab.com.util.UnitUtil;

public class PrintAero implements Printable {

    private Aero aero = null;
    private ArrayList<Aero> aeroList;

    public PrintAero(ArrayList<Aero> list) {
        aeroList = list;

    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex >= 1) {
            return Printable.NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        // f.setPaper(this.paper);
        printImage(g2d, pageFormat);
        return Printable.PAGE_EXISTS;
    }

    public void printImage(Graphics2D g2d, PageFormat pageFormat) {
        if (g2d == null) {
            return;
        }

        System.gc();

        g2d.drawImage(ImageHelper.getRecordSheet(aero), 18, 18, 558, 738, Color.BLACK, null);
        printAeroImage(g2d, ImageHelper.getFluffImage(aero, "aero"));

        printAeroData(g2d);
        printArmor(g2d);
        printWeaponsNEquipment(g2d);
        printHeatSinks(g2d);

        // Armor Pips
        printFrontArmor(g2d, aero.getOArmor(Aero.LOC_NOSE));
        printLeftArmor(g2d, aero.getOArmor(Aero.LOC_LWING));
        printRightArmor(g2d, aero.getOArmor(Aero.LOC_RWING));
        printRearArmor(g2d, aero.getOArmor(Aero.LOC_AFT));

        // Internal Pips
        printStruct(g2d, aero.get0SI());

        g2d.scale(pageFormat.getImageableWidth(), pageFormat.getImageableHeight());

    }

    private void printAeroData(Graphics2D g2d) {
        String aeroName = aero.getChassis().toUpperCase() + " " + aero.getModel().toUpperCase();

        g2d.setFont(UnitUtil.getNewFont(g2d, aeroName, true, 180, 10.0f));
        g2d.drawString(aeroName, 49, 118);

        Font font = UnitUtil.deriveFont(8.0f);
        g2d.setFont(font);

        g2d.drawString(Integer.toString(aero.getWalkMP()), 99, 143);
        g2d.drawString(Integer.toString(aero.getRunMP()), 99, 154);


        int tonnage = (int) Math.ceil(aero.getWeight());

        if (tonnage % 5 != 0) {
            tonnage += 5 - (tonnage % 5);
        }

        g2d.drawString(Integer.toString(tonnage), 177, 132.5f);

        String techBase = "Inner Sphere";
        if (aero.isClan()) {
            techBase = "Clan";
        }
        g2d.drawString(techBase, 177, 143.5f);

        g2d.drawString(Integer.toString(aero.getYear()), 188, 155);

        // Cost/BV
        g2d.drawString(String.format("%1$,d", aero.calculateBattleValue(true, true)), 150, 346.2f);

        // myFormatter = new DecimalFormat("#,###.##");
        g2d.drawString(String.format("%1$,.0f C-bills", aero.getCost()), 52, 346.2f);

        font = new Font("Arial", Font.BOLD, 7);
        g2d.setFont(font);
        g2d.drawString("2009", 105f, 745f);
    }

    private void printArmor(Graphics2D g2d) {

        // Armor
        Font font = UnitUtil.deriveFont(true, 9.0f);
        g2d.setFont(font);

        ImageHelper.printCenterString(g2d, String.format("%1$S (%2$s)", aero.getThresh(Aero.LOC_NOSE), aero.getArmor(Aero.LOC_NOSE)), g2d.getFont(), 300, 139);

        ImageHelper.printCenterString(g2d, String.format("%1$S (%2$s)", aero.getThresh(Aero.LOC_RWING), aero.getArmor(Aero.LOC_RWING)), g2d.getFont(), 495, 310);

        ImageHelper.printCenterString(g2d, String.format("%1$S (%2$s)", aero.getThresh(Aero.LOC_LWING), aero.getArmor(Aero.LOC_LWING)), g2d.getFont(), 290, 310);

        ImageHelper.printCenterString(g2d, String.format("%1$S (%2$s)", aero.getThresh(Aero.LOC_AFT), aero.getArmor(Aero.LOC_AFT)), g2d.getFont(), 398, 487);

    }

    private void printHeatSinks(Graphics2D g2d) {
        Font font = UnitUtil.deriveFont(true, 8.0f);
        g2d.setFont(font);

        // Heat Sinks
        if (aero.getHeatType() == Aero.HEAT_DOUBLE) {
            g2d.drawString(String.format("%1$s (%2$s)", aero.getHeatSinks(), aero.getHeatSinks() * 2), 502, 535);
            g2d.drawString("Double", 502, 543);
        } else {
            g2d.drawString(String.format("%1$s (%1$s)", aero.getHeatSinks()), 502, 535);
            g2d.drawString("Single", 502, 543);
        }

        Dimension column = new Dimension(504, 552);
        Dimension pipShift = new Dimension(9, 9);

        for (int pos = 1; pos <= aero.getHeatSinks(); pos++) {
            ImageHelper.drawHeatSinkPip(g2d, column.width, column.height);
            column.height += pipShift.height;

            if (pos % 10 == 0) {
                column.height -= pipShift.height * 10;
                column.width += pipShift.width;
            }

        }

    }

    private void printWeaponsNEquipment(Graphics2D g2d) {

        ImageHelper.printAeroWeaponsNEquipment(aero, g2d);

    }

    public void print() {

        try {
            PrinterJob pj = PrinterJob.getPrinterJob();

            if (pj.printDialog()) {
                // Paper paper = new Paper();
                PageFormat pageFormat = new PageFormat();
                pageFormat = pj.getPageFormat(null);

                Paper p = pageFormat.getPaper();
                p.setImageableArea(0, 0, p.getWidth(), p.getHeight());
                pageFormat.setPaper(p);

                pj.setPrintable(this, pageFormat);

                for (int pos = 0; pos < aeroList.size(); pos++) {

                    aero = aeroList.get(pos);
                    pj.setJobName(aero.getChassis() + " " + aero.getModel());

                    try {
                        pj.print();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    System.gc();
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void printFrontArmor(Graphics2D g2d, int totalArmor) {
        float[] topColumn = { 302, 170 };
        float[] pipShift = { 8, 7 };

        float maxRows = 7;
        float maxColumns = 23;
        float shift = (float) Math.ceil(totalArmor / maxColumns);
        pipShift[1] = (maxRows / shift) * 7;

        for (int pos = 1; pos <= totalArmor; pos++) {
            ImageHelper.drawAeroArmorPip(g2d, topColumn[0], topColumn[1]);
            topColumn[0] += pipShift[0];
            if (pos % maxColumns == 0) {
                topColumn[1] += pipShift[1];
                pipShift[0] *= -1;
                if (pos > totalArmor - maxColumns) {
                    topColumn[0] += pipShift[0] * ((maxColumns - (totalArmor - pos)) / 2);
                } else {
                    topColumn[0] += pipShift[0] / 2;
                }
            }
        }
    }

    private void printRearArmor(Graphics2D g2d, int totalArmor) {
        float[] topColumn = new float[] { 360, 310 };
        float[] pipShift = new float[] { 8, 7 };
        float maxRows = 21;
        float maxColumns = 8;

        float shift = (float) Math.ceil(totalArmor / maxColumns);
        pipShift[1] = (maxRows / shift) * 7;

        for (int pos = 1; pos <= totalArmor; pos++) {
            ImageHelper.drawAeroArmorPip(g2d, topColumn[0], topColumn[1]);
            topColumn[0] += pipShift[0];
            if (pos % maxColumns == 0) {
                topColumn[1] += pipShift[1];
                pipShift[0] *= -1;
                if (pos > totalArmor - maxColumns) {
                    topColumn[0] += pipShift[0] * ((maxColumns - (totalArmor - pos)) / 2);
                } else {
                    topColumn[0] += pipShift[0] / 2;
                }
            }
        }

    }

    private void printLeftArmor(Graphics2D g2d, int totalArmor) {
        float[] topColumn = new float[] { 336.5f, 325 };
        float[] pipShift = new float[] { 7, 8 };

        int numberPerRow = 3;
        int curretNumber = 0;
        pipShift[1] += 2 * Math.max(0, 7 - (totalArmor / 8));
        for (int pos = 1; pos < totalArmor; pos++) {
            ImageHelper.drawAeroArmorPip(g2d, topColumn[0], topColumn[1]);
            topColumn[0] += pipShift[0];

            if (++curretNumber == numberPerRow) {
                topColumn[1] += pipShift[1];
                pipShift[0] *= -1;
                if (pos > totalArmor - numberPerRow) {
                    topColumn[0] += pipShift[0] * ((numberPerRow - (totalArmor - pos)) / 2);
                } else {
                    topColumn[0] += pipShift[0] / 2;
                }

                curretNumber = 0;
                if (pos == 79) {
                    numberPerRow = 13;
                    topColumn[0] -= Math.abs(pipShift[0] * .8);
                } else if (pos == 92) {
                   topColumn[0] += Math.abs(pipShift[0] * .4);
                } else if (numberPerRow < 14) {
                    if (numberPerRow == 7) {
                        numberPerRow = 12;
                    } else if( numberPerRow == 12){
                        numberPerRow = 14;
                        topColumn[0] -= Math.abs(pipShift[0] * 1.5);
                    }
                    else {
                        numberPerRow++;
                    }

                    topColumn[0] -= Math.abs(pipShift[0] * .8);
                }

            }
        }
    }

    private void printRightArmor(Graphics2D g2d, int totalArmor) {
        float[] topColumn = new float[] { 429, 325 };
        float[] pipShift = new float[] { 7, 8 };

        int numberPerRow = 3;
        int curretNumber = 0;
        pipShift[1] += 2 * Math.max(0, 7 - (totalArmor / 8));
        for (int pos = 1; pos < totalArmor; pos++) {
            ImageHelper.drawAeroArmorPip(g2d, topColumn[0], topColumn[1]);
            topColumn[0] += pipShift[0];

            if (++curretNumber == numberPerRow) {
                topColumn[1] += pipShift[1];
                pipShift[0] *= -1;
                if (pos > totalArmor - numberPerRow) {
                    topColumn[0] += pipShift[0] * ((numberPerRow - (totalArmor - pos)) / 2);
                } else {
                    topColumn[0] += pipShift[0] / 2;
                }

                curretNumber = 0;
                if (pos == 65) {
                    topColumn[0] += Math.abs(pipShift[0] * .6);
                } else if (pos == 79) {
                    numberPerRow = 13;
                    topColumn[0] -= Math.abs(pipShift[0] * 1.2);
                } else if (pos == 92) {
                    topColumn[0] += Math.abs(pipShift[0] * .65);
                } else if (numberPerRow < 14) {
                    if (numberPerRow == 7) {
                        numberPerRow = 12;
                        topColumn[0] += Math.abs(pipShift[0] * 4);
                    } else if (numberPerRow == 12) {
                        numberPerRow = 14;
                        // topColumn[0] += Math.abs(pipShift[0] * 2);
                    } else {
                        numberPerRow++;
                    }

                    topColumn[0] += Math.abs(pipShift[0] * .8);
                }

            }
        }
    }

    private void printStruct(Graphics2D g2d, int totalArmor) {
        int[] topColumn = new int[] { 366, 265 };
        int[] pipShift = new int[] { 7, 9 };

        if (totalArmor < 8) {
            topColumn[0] += pipShift[0] * ((8 - (totalArmor)) / 2);
        }
        for (int pos = 1; pos <= totalArmor; pos++) {
            ImageHelper.drawAeroISPip(g2d, topColumn[0], topColumn[1]);
            topColumn[0] += pipShift[0];
            if (pos % 8 == 0) {
                topColumn[1] += pipShift[1];
                pipShift[0] *= -1;
                if (pos > totalArmor - 8) {
                    topColumn[0] += pipShift[0] * ((8 - (totalArmor - pos)) / 2);
                } else {
                    topColumn[0] += pipShift[0];
                }
            }
        }

    }

    private void printAeroImage(Graphics2D g2d, Image img) {

        int width = Math.min(220, img.getWidth(null));
        int height = Math.min(130, img.getHeight(null));
        int drawingX = 18 + ((220 - width) / 2);
        int drawingY = 365 + ((130 - height) / 2);
        g2d.drawImage(img, drawingX, drawingY, width, height, Color.BLACK, null);

    }

}