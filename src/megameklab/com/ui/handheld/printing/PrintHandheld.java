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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.PrintQuality;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.Text;
import com.kitfox.svg.Tspan;

import megamek.common.AmmoType;
import megamek.common.EquipmentType;
import megamek.common.HandheldWeapon;
import megamek.common.MiscType;
import megamek.common.Mounted;
import megamek.common.WeaponType;
import megameklab.com.util.ImageHelper;
import megameklab.com.util.StringUtils;
import megameklab.com.util.UnitUtil;

/**
 * @author Neoancient
 *
 */
public class PrintHandheld implements Printable {

	private final static int[] VERTICAL_MARGIN = {74, 111, 148};
	
	/* Id tags of elements in the SVG file */
	private final static String ID_WEAPON_NAME = "tspanWeaponName";
	private final static String ID_WEAPON_QTY = "tspanWpnQty";
	private final static String ID_WEAPON_TYPE = "tspanWpnType";
	private final static String ID_WEAPON_DMG = "tspanWpnDmg";
	private final static String ID_WEAPON_MIN = "tspanWpnMin";
	private final static String ID_WEAPON_SHORT = "tspanWpnSht";
	private final static String ID_WEAPON_MED = "tspanWpnMed";
	private final static String ID_WEAPON_LONG = "tspanWpnLng";
	private final static String ID_ARMOR = "tspanArmor";
	private final static String ID_BV = "tspanBV";

	private final static int AMMO_LINE_SPARSE 	= -1;
	private final static int AMMO_LINE_STANDARD = 0;
	private final static int AMMO_LINE_HEAVY	= 1;
	private final static int AMMO_LINE_DENSE 	= 2;
	private final int[] AMMO_LENGTH = {10, 16, 20};
	
	private final static int TEMPLATE_STANDARD = 0;
	private final static int TEMPLATE_LARGE    = 1;
	private final static int TEMPLATE_VLARGE   = 2;
	private final static int TEMPLATE_BLANK	   = 3;
	
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
        
        ArrayList<Integer> templateList = new ArrayList<>();
        double space = 0.0;
        while (space <= 8) {
        	if (currentPosition + templateList.size() < handheldList.size()) {
	        	int template = requiredTemplate(handheldList.get(currentPosition + templateList.size()));
	        	templateList.add(template);
	        	space += 1 + template * 0.5;
        	} else {
        		templateList.add(TEMPLATE_BLANK);
        		space++;
        	}
        }

		diagram = ImageHelper.loadSVGImage(new File("data/images/recordsheets/Handheld_Sheet.svg"));

        try {
        	Tspan tspan = (Tspan)diagram.getElement("tspanCopyright");
        	tspan.setText(String.format(tspan.getText(), Calendar.getInstance().get(Calendar.YEAR)));
        	((Text)tspan.getParent()).rebuild();
        	
            diagram.render(g2d);

            if (handheldList.get(currentPosition).getFluff().getMMLImagePath() != null) {
            	printFluffImage(g2d);
            }
        	
            for (int pos = 0; pos < templateList.size(); pos++) {
            	if (templateList.get(pos) == TEMPLATE_BLANK) {
                	diagram = ImageHelper.loadSVGImage(new File("data/images/recordsheets/Handheld_Weapon_Blank.svg"));
                	diagram.render(g2d);
                	g2d.translate(0, VERTICAL_MARGIN[0]);
                	continue;
            	}
        		handheld = handheldList.get(pos + currentPosition);
            	if (templateList.get(pos) == TEMPLATE_VLARGE) {
                	diagram = ImageHelper.loadSVGImage(new File("data/images/recordsheets/Handheld_Weapon_VLarge.svg"));
            	} else if (templateList.get(pos) == TEMPLATE_LARGE) {
                	diagram = ImageHelper.loadSVGImage(new File("data/images/recordsheets/Handheld_Weapon_Large.svg"));            		
            	} else {
            		diagram = ImageHelper.loadSVGImage(new File("data/images/recordsheets/Handheld_Weapon.svg"));
            	}
        		tspan = (Tspan)diagram.getElement(ID_WEAPON_NAME);
        		tspan.setText(handheld.getShortName() + " Weapon ("
        				+ new DecimalFormat("0.#").format(handheld.getWeight()) + " tons)");
        		((Text)tspan.getParent()).rebuild();
        		
        		tspan = (Tspan)diagram.getElement(ID_ARMOR);
        		tspan.setText(Integer.toString(handheld.getTotalOArmor()));
        		
        		Map<String,Integer> weaponCount = new HashMap<>();
        		Map<String,EquipmentType> weaponType = new HashMap<>();
        		for (Mounted m : handheld.getEquipment()) {
        			if (m.getType() instanceof WeaponType
        					|| (m.getType() instanceof MiscType
        							&& m.getType().hasFlag(MiscType.F_VEHICLE_MINE_DISPENSER))) {
        				String eqName = m.getType().getShortName();
        				if (m.getLinked() != null) {
        					eqName += "+" + m.getLinked().getType().getShortName();
        				}
        				weaponCount.merge(eqName, 1, Integer::sum);
        				weaponType.put(eqName, m.getType());
        			}
        		}

        		Map<String,List<Mounted>> weapons = handheld.getEquipment().stream()
        				.filter(m -> m.getType() instanceof WeaponType
        						|| (m.getType() instanceof MiscType
        								&& m.getType().hasFlag(MiscType.F_VEHICLE_MINE_DISPENSER)))
        				.collect(Collectors.groupingBy(m -> getEqDisplayName(m)));
        		int line = 0;
        		for (String name : weapons.keySet()) {
        			final EquipmentType eq = weapons.get(name).get(0).getType();
					tspan = (Tspan)diagram.getElement(ID_WEAPON_QTY + "_" + line);
					tspan.setText(Integer.toString(weapons.get(name).size()));
					((Text)tspan.getParent()).rebuild();
					
					String[] lines = name.split("\n");
					tspan = (Tspan)diagram.getElement(ID_WEAPON_TYPE + "_" + line);
					tspan.setText(lines[0]);
					if (lines.length > 1) {
						tspan = (Tspan)diagram.getElement(ID_WEAPON_TYPE + "_" + (line + 1));
						tspan.setText(lines[1]);
					}
					((Text)tspan.getParent()).rebuild();
					
					if (eq instanceof WeaponType) {
						WeaponType wtype = (WeaponType)eq;
						if (wtype.getAmmoType() == AmmoType.T_MML) {
							tspan = (Tspan)diagram.getElement(ID_WEAPON_DMG + "_" + line);
							tspan.setText("1/Msl [M,C,S]");
							((Text)tspan.getParent()).rebuild();
							tspan = (Tspan)diagram.getElement(ID_WEAPON_DMG + "_" + (line + 1));
							tspan.setText("2/Msl [M,C,S]");
							((Text)tspan.getParent()).rebuild();
							
							tspan = (Tspan)diagram.getElement(ID_WEAPON_MIN + "_" + line);
							tspan.setText("6");
							((Text)tspan.getParent()).rebuild();
							tspan = (Tspan)diagram.getElement(ID_WEAPON_MIN + "_" + (line + 1));
							tspan.setText("—");
							((Text)tspan.getParent()).rebuild();

							tspan = (Tspan)diagram.getElement(ID_WEAPON_SHORT + "_" + line);
							tspan.setText("7");
							((Text)tspan.getParent()).rebuild();
							tspan = (Tspan)diagram.getElement(ID_WEAPON_SHORT + "_" + (line + 1));
							tspan.setText("3");
							((Text)tspan.getParent()).rebuild();

							tspan = (Tspan)diagram.getElement(ID_WEAPON_MED + "_" + line);
							tspan.setText("14");
							((Text)tspan.getParent()).rebuild();
							tspan = (Tspan)diagram.getElement(ID_WEAPON_MED + "_" + (line + 1));
							tspan.setText("6");
							((Text)tspan.getParent()).rebuild();

							tspan = (Tspan)diagram.getElement(ID_WEAPON_LONG + "_" + line);
							tspan.setText("21");
							((Text)tspan.getParent()).rebuild();
							tspan = (Tspan)diagram.getElement(ID_WEAPON_LONG + "_" + (line + 1));
							tspan.setText("9");
							((Text)tspan.getParent()).rebuild();
						} else {
							tspan = (Tspan)diagram.getElement(ID_WEAPON_DMG + "_" + line);
							tspan.setText(StringUtils.getEquipmentInfo(handheld, weapons.get(name).get(0)));
							((Text)tspan.getParent()).rebuild();
							
							tspan = (Tspan)diagram.getElement(ID_WEAPON_MIN + "_" + line);
							if (((WeaponType)eq).getMinimumRange() > 0) {
								tspan.setText(Integer.toString(wtype.getMinimumRange()));
							} else {
								tspan.setText("—");
							}
							((Text)tspan.getParent()).rebuild();
	
							tspan = (Tspan)diagram.getElement(ID_WEAPON_SHORT + "_" + line);
							tspan.setText(Integer.toString(wtype.getShortRange()));
							((Text)tspan.getParent()).rebuild();
	
							tspan = (Tspan)diagram.getElement(ID_WEAPON_MED + "_" + line);
							if (wtype.getMediumRange() > wtype.getShortRange()) {
								tspan.setText(Integer.toString(((WeaponType)eq).getMediumRange()));
							} else {
								tspan.setText("—");
							}
							((Text)tspan.getParent()).rebuild();
	
							tspan = (Tspan)diagram.getElement(ID_WEAPON_LONG + "_" + line);
							if (wtype.getLongRange() > wtype.getMediumRange()) {
								tspan.setText(Integer.toString(((WeaponType)eq).getLongRange()));
							} else {
								tspan.setText("—");
							}
							((Text)tspan.getParent()).rebuild();
						}
					}
					line++;
					if (name.contains("\n")) {
						line++;
					}
        		}
        		
        		tspan = (Tspan)diagram.getElement(ID_BV);
        		tspan.setText(Integer.toString(handheld.calculateBattleValue()));
        		((Text)tspan.getParent()).rebuild();
        		
        		tspan = (Tspan)diagram.getElement(ID_ARMOR);
        		tspan.setText(Integer.toString(handheld.getTotalOArmor()));
        		((Text)tspan.getParent()).rebuild();

        		diagram.updateTime(0);
        		
            	diagram.render(g2d);
            	
            	printArmor(g2d, handheld.getTotalOArmor());
            	
        		//Collect all ammo by type, sorting with the type shortest name first (since
        		//it has the least space to print).
        		Map<String,Integer> ammoByType = handheld.getAmmo().stream()
        				.collect(Collectors.groupingBy(m -> m.getType().getShortName(),
        						Collectors.summingInt(Mounted::getBaseShotsLeft)));
        		int totalShots = ammoByType.values().stream().mapToInt(Integer::intValue).sum();

        		float yPos = 106;
        		g2d.setFont(UnitUtil.deriveFont(7));
        		g2d.setPaint(Color.BLACK);
        		g2d.setStroke(new BasicStroke(0.9f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        		if (ammoByType.isEmpty()) {
        			printAmmoLabel(g2d, "None", yPos);
        		} else {
        			int lineType = AMMO_LINE_SPARSE;
        			if (ammoByType.size() > 1 || totalShots > 10) {
        				for (int i = 0; i < AMMO_LENGTH.length; i++) {
        					lineType = i;
        					int lines = 0;
        					for (Integer shots : ammoByType.values()) {
        						lines++;
        						lines += shots / AMMO_LENGTH[lineType];
        						if (shots % AMMO_LENGTH[lineType] > 0) {
        							lines++;
        						}
        					}
        					if (lines <= 5 + 4 * (templateList.get(pos))) {
        						break;
        					}
        				}
        			}
	        		for (String at : ammoByType.keySet()) {
	        			yPos = printAmmoLabel(g2d, at + " (" + ammoByType.get(at) + ")", yPos);
	        			int shots = ammoByType.get(at);
	        			if (lineType == AMMO_LINE_SPARSE) {
	        				yPos = printAmmoLine(g2d, AMMO_LINE_SPARSE, shots, yPos, false);
	        			} else {
	        				boolean indent = false;
	        				for (int i = 0; i < shots / AMMO_LENGTH[lineType]; i++) {
	            				yPos = printAmmoLine(g2d, lineType, AMMO_LENGTH[lineType], yPos, indent);
	            				indent = !indent;
	        				}
	        				if (shots % AMMO_LENGTH[lineType] > 0) {
	            				yPos = printAmmoLine(g2d, lineType, shots % AMMO_LENGTH[lineType], yPos, indent);
	        				}
	        			}
	        		}
        		}

        		g2d.translate(0, VERTICAL_MARGIN[templateList.get(pos)]);
            }
        } catch (SVGException ex) {
        	ex.printStackTrace();
        }
        
        g2d.scale(pageFormat.getImageableWidth(), pageFormat.getImageableHeight());
    }
	
    private int requiredTemplate(HandheldWeapon hhw) {
    	int template = TEMPLATE_STANDARD;
    	if (hhw.getTotalOArmor() > 128) {
    		return TEMPLATE_VLARGE;
    	}
    	if (hhw.getTotalOArmor() > 64) {
    		template = TEMPLATE_LARGE;
    	}

    	Set<String> wpnRows = hhw.getEquipment().stream()
    			.filter(m -> m.getType() instanceof WeaponType
    					|| (m.getType() instanceof MiscType
    							&& m.getType().hasFlag(MiscType.F_VEHICLE_MINE_DISPENSER)))
    			.map(this::getEqDisplayName)
    			.collect(Collectors.toSet());
    	int wpnRowCount = wpnRows.stream().mapToInt(r -> r.contains("\n")? 2 : 1).sum();
    	if (wpnRowCount > 7) {
    		return TEMPLATE_VLARGE;
    	}
    	if (wpnRowCount > 3) {
    		template = TEMPLATE_LARGE;
    	}

    	Map<EquipmentType,Integer> ammoByType = new HashMap<>();
    	for (Mounted m : hhw.getAmmo()) {
    		ammoByType.merge(m.getType(), m.getBaseShotsLeft(), Integer::sum);
    	}
    	int ammoLines = 0;
    	for (int shots : ammoByType.values()) {
    		ammoLines += 2 + shots / 20;
    		if (shots % 20 > 0) {
    			ammoLines++;
    		}
    	}
    	if (ammoLines > 15) {
    		return TEMPLATE_VLARGE;
    	}
    	if (ammoLines > 7) {
    		return TEMPLATE_LARGE;
    	}
    	return template;
    }
    
	private String getEqDisplayName(Mounted m) {
		if (m.getLinkedBy() != null) {
			return m.getType().getShortName() + "\nw/"
					+ m.getLinkedBy().getType().getShortName();
		} else {
			if (m.getType() instanceof WeaponType
					&& ((WeaponType)m.getType()).getAmmoType() == AmmoType.T_MML) {
				return m.getType().getName() + " (LRM)\n"
						+ m.getType().getName() + " (SRM)";
			}
			return m.getType().getName();
		}
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
	
	private float printAmmoLine(Graphics2D g2d, int lineType, int ammo, float yPos, boolean indent) {
		float xPos = 459;
		float offsetX;
		float offsetY;
		float size;
		
		switch (lineType) {
		case AMMO_LINE_SPARSE:
			size = 7.394f;
			offsetX = 10.61f;
			if (ammo > 1) {
				offsetX *= 9f / (ammo - 1);
			}
			offsetY = 8.0928f;
			break;
		case AMMO_LINE_HEAVY:
			size = 4.621f;
			offsetX = 6.366f;
			offsetY = 5.058f;
			break;
		case AMMO_LINE_DENSE:
			size = 3.698f;
			offsetX = 5.124f;
			offsetY = 4.6724f;
			break;
		case AMMO_LINE_STANDARD:
		default:
			size = 7.394f;
			offsetX = 10.61f;
			offsetY = 8.0928f;
			break;
		}
		if (indent) {
			xPos += offsetX * 0.5f;
		}
		for (int i = 0; i < ammo; i++) {
			g2d.draw(new Ellipse2D.Double(xPos, yPos, size, size));
			xPos += offsetX;
		}
		return yPos + offsetY;
	}
	
	private float printAmmoLabel(Graphics2D g2d, String label, float y) {
		g2d.drawString(label, 460, y + 6);
		return y + 8;
	}
	
    private void printFluffImage(Graphics2D g2d) {
        Image img = ImageHelper.getFluffImage(handheld, ImageHelper.imageHandheld);
        int width = img.getWidth(null);
        int height = img.getHeight(null);
        double scale = Math.min(48.0 / width, 48.0 / height);
        int	drawingX = 440 - (int)Math.round(width * scale / 2);
        int drawingY = 42 - (int)Math.round(height * scale / 2);
        AffineTransform xform = new AffineTransform(scale, 0, 0, scale, drawingX, drawingY);
        g2d.drawImage(img, xform, null);
    }

    public void print(HashPrintRequestAttributeSet aset) {

        try {
        	while (currentPosition < handheldList.size()) {
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
                double space = 0;
                while (space <= 8 && currentPosition < handheldList.size()) {
                	space += 1 + requiredTemplate(handheldList.get(currentPosition)) * 0.5;
                	currentPosition++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
