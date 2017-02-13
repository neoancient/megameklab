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
package megameklab.com.ui.handheld.printing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.PrintQuality;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.Text;
import com.kitfox.svg.Tspan;
import com.kitfox.svg.animation.AnimationElement;

import megamek.common.AmmoType;
import megamek.common.EquipmentType;
import megamek.common.HandheldWeapon;
import megamek.common.MiscType;
import megamek.common.Mounted;
import megamek.common.WeaponType;
import megameklab.com.util.ImageHelper;
import megameklab.com.util.StringUtils;

/**
 * @author Neoancient
 *
 */
public class PrintHandheld implements Printable {
	
	/* Id tags of elements in the SVG file */
//	private final static String ID_FLUFF_IMAGE = "imageFluff";
	private final static String ID_WEAPON_NAME = "tspanWeaponName";
	private final static String ID_WEAPON_QTY = "tspanWpnQty";
	private final static String ID_WEAPON_TYPE = "tspanWpnType";
	private final static String ID_WEAPON_DMG = "tspanWpnDmg";
	private final static String ID_WEAPON_MIN = "tspanWpnMin";
	private final static String ID_WEAPON_SHORT = "tspanWpnSht";
	private final static String ID_WEAPON_MED = "tspanWpnMed";
	private final static String ID_WEAPON_LONG = "tspanWpnLng";
	private final static String ID_ARMOR = "tspanArmor";
	private final static String ID_AMMO_TYPE = "tspanAmmoType";
	private final static String ID_BV = "tspanBV";

	private final static int AMMO_LINE 			= 0;
	private final static int AMMO_LINE_SPARSE 	= 1;
	private final static int AMMO_LINE_DENSE 	= 2;
	
	private HandheldWeapon handheld = null;
    private ArrayList<HandheldWeapon> handheldList;
    PrinterJob masterPrintJob;
    private int currentPosition;
    
    public PrintHandheld(ArrayList<HandheldWeapon> list, PrinterJob masterPrintJob) {
    	handheldList = list;
        this.masterPrintJob = masterPrintJob;
    }

    
	/* (non-Javadoc)
	 * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
	 */
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex != 0) {
            return Printable.NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        printImage(g2d, pageFormat);
        return Printable.PAGE_EXISTS;
	}

	public void printImage(Graphics2D g2d, PageFormat pageFormat) {
        if (g2d == null) {
            return;
        }
        
        SVGDiagram diagram;
        
        int stop = Math.min(9, handheldList.size() - currentPosition);
		diagram = ImageHelper.loadSVGImage(new File("data/images/recordsheets/handheld.svg"));

        try {
        	Tspan tspan = (Tspan)diagram.getElement("tspanCopyright");
        	tspan.setText(String.format(tspan.getText(), Calendar.getInstance().get(Calendar.YEAR)));
        	((Text)tspan.getParent()).rebuild();
            diagram.render(g2d);

            for (int pos = 0; pos < stop; pos++) {
        		handheld = handheldList.get(pos + currentPosition);
        		tspan = (Tspan)diagram.getElement(ID_WEAPON_NAME + "_" + pos);
        		tspan.setText(handheld.getShortName());
        		((Text)tspan.getParent()).rebuild();
        		
        		tspan = (Tspan)diagram.getElement(ID_ARMOR + "_" + pos);
        		tspan.setText(Integer.toString(handheld.getTotalOArmor()));

        		Map<String,List<Mounted>> weapons = handheld.getEquipment().stream()
        				.filter(m -> m.getType() instanceof WeaponType
        						|| (m.getType() instanceof MiscType
        								&& m.getType().hasFlag(MiscType.F_VEHICLE_MINE_DISPENSER)))
        				.collect(Collectors.groupingBy(m -> m.getType().getInternalName()));
        		int line = 0;
        		for (String name : weapons.keySet()) {
        			EquipmentType eq = EquipmentType.get(name);
					tspan = (Tspan)diagram.getElement(ID_WEAPON_QTY + "_" + pos + "_" + line);
					tspan.setText(Integer.toString(weapons.get(name).size()));
					((Text)tspan.getParent()).rebuild();
					
					tspan = (Tspan)diagram.getElement(ID_WEAPON_TYPE + "_" + pos + "_" + line);
					tspan.setText(eq.getName());
					((Text)tspan.getParent()).rebuild();
					
					if (eq instanceof WeaponType) {
						tspan = (Tspan)diagram.getElement(ID_WEAPON_DMG + "_" + pos + "_" + line);
						tspan.setText(StringUtils.getEquipmentInfo(handheld, weapons.get(name).get(0)));
						((Text)tspan.getParent()).rebuild();
						
						tspan = (Tspan)diagram.getElement(ID_WEAPON_MIN + "_" + pos + "_" + line);
						if (((WeaponType)eq).getMinimumRange() > 0) {
							tspan.setText(Integer.toString(((WeaponType)eq).getMinimumRange()));
						} else {
							tspan.setText("—");
						}
						((Text)tspan.getParent()).rebuild();

						tspan = (Tspan)diagram.getElement(ID_WEAPON_SHORT + "_" + pos + "_" + line);
						tspan.setText(Integer.toString(((WeaponType)eq).getShortRange()));
						((Text)tspan.getParent()).rebuild();

						tspan = (Tspan)diagram.getElement(ID_WEAPON_MED + "_" + pos + "_" + line);
						tspan.setText(Integer.toString(((WeaponType)eq).getMediumRange()));
						((Text)tspan.getParent()).rebuild();

						tspan = (Tspan)diagram.getElement(ID_WEAPON_LONG + "_" + pos + "_" + line);
						tspan.setText(Integer.toString(((WeaponType)eq).getLongRange()));
						((Text)tspan.getParent()).rebuild();
					}
					line++;
        		}
        		
        		tspan = (Tspan)diagram.getElement(ID_BV + "_" + pos);
        		tspan.setText(Integer.toString(handheld.calculateBattleValue()));
        		((Text)tspan.getParent()).rebuild();
        		
        		tspan = (Tspan)diagram.getElement(ID_ARMOR + "_" + pos);
        		tspan.setText(Integer.toString(handheld.getTotalOArmor()));
        		((Text)tspan.getParent()).rebuild();

        		//Collect all ammo by type, sorting with the type shortest name first (since
        		//it has the least space to print).
        		Map<AmmoType,Integer> ammoByType = new TreeMap<>((i1, i2) ->
        			i1.getShortName().length() - i2.getShortName().length());
        		for (Mounted m : handheld.getAmmo()) {
        			ammoByType.merge((AmmoType)m.getType(),
        					m.getBaseShotsLeft(), Integer::sum);
        		}
        		int totalShots = ammoByType.values().stream().mapToInt(Integer::intValue).sum();
        		
        		if (ammoByType.size() > 1) {
        			line = 0;
        			for (AmmoType at : ammoByType.keySet()) {
        				tspan = (Tspan)diagram.getElement(ID_AMMO_TYPE + "_" + pos + "_" + line);
        	            	tspan.setText(at.getShortName());
        	            if (tspan.getParent().hasAttribute("display", AnimationElement.AT_XML)) {
        	            	tspan.getParent().removeAttribute("display", AnimationElement.AT_XML);
        	            }
        	            ((Text)tspan.getParent()).rebuild();
        	            if (totalShots > 30) {
        	            	line += (int)Math.ceil(Math.min(100, ammoByType.get(at)) / 20.0) + 1;
        	            } else {
        	            	line += (int)Math.ceil(ammoByType.get(at) / 10.0) * 2 + 1;
        	            }
        			}
        		}

        		diagram.updateTime(0);
        		
            	diagram.render(g2d);
            	
            	printArmor(g2d, handheld.getTotalOArmor());

        		line = 0;
        		for (AmmoType at : ammoByType.keySet()) {
        			int shots = Math.min(100, ammoByType.get(at));
        			if (shots <= 10 && ammoByType.size() == 1) {
        				printAmmoLine(g2d, AMMO_LINE_SPARSE, ammoByType.get(at), line);
        				line += 2;
        			} else if (totalShots <= 40) {
        				for (int i = 0; i < shots / 10; i++) {
            				printAmmoLine(g2d, AMMO_LINE, 10, line);
            				line += 2;
        				}
        				if (shots % 10 > 0) {
            				printAmmoLine(g2d, AMMO_LINE, shots % 10, line);
            				line += 2;
        				}
        			} else {
        				for (int i = 0; i < shots / 20; i++) {
            				printAmmoLine(g2d, AMMO_LINE_DENSE, 20, line);
            				line += 1;
        				}
        				if (shots % 20 > 0) {
            				printAmmoLine(g2d, AMMO_LINE_DENSE, shots % 20, line);
            				line += 1;
        				}
        			}
        			line += 2;
        		}

            }
        } catch (SVGException ex) {
        	ex.printStackTrace();
        }

        g2d.scale(pageFormat.getImageableWidth(), pageFormat.getImageableHeight());
    }
	
	private void printArmor(Graphics2D g2d, int armor) {
		double offsetX = 20;
		double offsetY = 10;
		double startX = 280;
		double startY = 110;
		double xEnd = startX + offsetX * 8;
		
		g2d.setPaint(Color.BLACK);
		
		if (armor > 32) {
			offsetX = 10;
			offsetY = 8.66;
		}
		
		double x = startX;
		double y = startY;
		boolean indent = false;
		for (int a = 0; a < Math.min(64, armor); a++) {
			g2d.draw(new Ellipse2D.Double(x, y, 7.394, 7.394));
			x += offsetX;
			if (x >= xEnd) {
				y += offsetY;
				x = startX;
				indent = !indent;
				if (indent) {
					x += offsetX * 0.5;
				}
			}
		}
	}
	
	private void printAmmoLine(Graphics2D g2d, int lineType, int ammo, int line) {
		double startX = 458;
		double startY = 110;
		double size = 7.394;
		
		double offsetX = 10.832;
		double offsetY = 4.6724;
		
		if (lineType == AMMO_LINE_SPARSE) {
			offsetX = 24.372;
		} else if (lineType == AMMO_LINE_DENSE) {
			offsetX = 5.5;
			size = 3.698;
		}
		
		double x = startX;
		double y = startY + offsetY * line;
		g2d.setPaint(Color.BLACK);
		for (int i = 0; i < Math.min(50, ammo); i++) {
			g2d.draw(new Ellipse2D.Double(x, y, size, size));
			x += offsetX;
		}
	}
	
	public void print(HashPrintRequestAttributeSet aset) {

        try {
            for (; currentPosition < handheldList.size(); currentPosition += 8) {
                PrinterJob pj = PrinterJob.getPrinterJob();
                pj.setPrintService(masterPrintJob.getPrintService());

                aset.add(PrintQuality.HIGH);

                PageFormat pageFormat = new PageFormat();
                pageFormat = pj.getPageFormat(null);

                Paper p = pageFormat.getPaper();
                p.setImageableArea(0, 0, p.getWidth(), p.getHeight());
                pageFormat.setPaper(p);

                pj.setPrintable(this, pageFormat);

                handheld = handheldList.get(currentPosition);
                pj.setJobName(handheld.getShortName());

                try {
                    pj.print(aset);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.gc();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
