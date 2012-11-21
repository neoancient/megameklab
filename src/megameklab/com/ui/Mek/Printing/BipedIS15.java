/*
 * MegaMekLab - Copyright (C) 2012
 *
 * Original author - jtighe (torren@users.sourceforge.net)
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

package megameklab.com.ui.Mek.Printing;

import java.awt.*;
import java.awt.geom.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class has been automatically generated using <a
 * href="http://englishjavadrinker.blogspot.com/search/label/SVGRoundTrip">SVGRoundTrip</a>.
 */
public class BipedIS15 {
    /**
     * Paints the transcoded SVG image on the specified graphics context. You
     * can install a custom transformation on the graphics context to scale the
     * image.
     * 
     * @param g 
     *            Graphics context.
     */
    public static void paint(Graphics2D g) {
        Shape shape = null;
        Paint paint = null;
        Stroke stroke = null;
        Area clip = null;
        
        float origAlpha = 1.0f;
        Composite origComposite = ((Graphics2D)g).getComposite();
        if (origComposite instanceof AlphaComposite) {
            AlphaComposite origAlphaComposite = 
                (AlphaComposite)origComposite;
            if (origAlphaComposite.getRule() == AlphaComposite.SRC_OVER) {
                origAlpha = origAlphaComposite.getAlpha();
            }
        }
        
        Shape clip_ = g.getClip();
AffineTransform defaultTransform_ = g.getTransform();
//  is CompositeGraphicsNode
float alpha__0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0 = g.getClip();
AffineTransform defaultTransform__0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -0.0f, -0.0f));
clip = new Area(g.getClip());
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,612.0,792.0)));
g.setClip(clip);
// _0 is CompositeGraphicsNode
float alpha__0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0 = g.getClip();
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0 is CompositeGraphicsNode
float alpha__0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(482.48, 423.47);
((GeneralPath)shape).curveTo(481.48502, 423.47, 480.659, 422.642, 480.659, 421.648);
((GeneralPath)shape).curveTo(480.659, 420.656, 481.485, 419.828, 482.48, 419.828);
((GeneralPath)shape).curveTo(483.47202, 419.828, 484.30103, 420.656, 484.30103, 421.648);
((GeneralPath)shape).curveTo(484.302, 422.642, 483.473, 423.47, 482.48, 423.47);
((GeneralPath)shape).lineTo(482.48, 423.47);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_0;
g.setTransform(defaultTransform__0_0_0_0);
g.setClip(clip__0_0_0_0);
float alpha__0_0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(478.181, 415.8);
((GeneralPath)shape).curveTo(477.185, 415.8, 476.356, 414.972, 476.356, 413.97998);
((GeneralPath)shape).curveTo(476.356, 412.986, 477.185, 412.158, 478.181, 412.158);
((GeneralPath)shape).curveTo(479.173, 412.158, 480.001, 412.986, 480.001, 413.97998);
((GeneralPath)shape).curveTo(480.001, 414.972, 479.173, 415.8, 478.181, 415.8);
((GeneralPath)shape).lineTo(478.181, 415.8);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_1;
g.setTransform(defaultTransform__0_0_0_1);
g.setClip(clip__0_0_0_1);
float alpha__0_0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_2 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(474.077, 423.47);
((GeneralPath)shape).curveTo(473.085, 423.47, 472.25598, 422.642, 472.25598, 421.648);
((GeneralPath)shape).curveTo(472.25598, 420.656, 473.085, 419.828, 474.077, 419.828);
((GeneralPath)shape).curveTo(475.072, 419.828, 475.897, 420.656, 475.897, 421.648);
((GeneralPath)shape).curveTo(475.897, 422.642, 475.072, 423.47, 474.077, 423.47);
((GeneralPath)shape).lineTo(474.077, 423.47);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_2;
g.setTransform(defaultTransform__0_0_0_2);
g.setClip(clip__0_0_0_2);
float alpha__0_0_0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_3 is CompositeGraphicsNode
float alpha__0_0_0_3_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_3_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_3_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_3_0 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(505.04, 530.964);
((GeneralPath)shape).curveTo(504.061, 531.152, 503.091, 530.498, 502.902, 529.524);
((GeneralPath)shape).curveTo(502.711, 528.548, 503.36502, 527.578, 504.34302, 527.388);
((GeneralPath)shape).curveTo(505.31903, 527.199, 506.286, 527.853, 506.47702, 528.827);
((GeneralPath)shape).curveTo(506.666, 529.804, 506.012, 530.774, 505.04, 530.964);
((GeneralPath)shape).lineTo(505.04, 530.964);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_3_0;
g.setTransform(defaultTransform__0_0_0_3_0);
g.setClip(clip__0_0_0_3_0);
float alpha__0_0_0_3_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_3_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_3_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_3_1 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(513.015, 572.006);
((GeneralPath)shape).curveTo(512.039, 572.198, 511.069, 571.544, 510.88, 570.568);
((GeneralPath)shape).curveTo(510.69, 569.592, 511.344, 568.622, 512.31903, 568.43097);
((GeneralPath)shape).curveTo(513.29706, 568.243, 514.263, 568.896, 514.45605, 569.87195);
((GeneralPath)shape).curveTo(514.646, 570.848, 513.989, 571.817, 513.015, 572.006);
((GeneralPath)shape).lineTo(513.015, 572.006);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_3_1;
g.setTransform(defaultTransform__0_0_0_3_1);
g.setClip(clip__0_0_0_3_1);
float alpha__0_0_0_3_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_3_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_3_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_3_2 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(497.06, 489.92);
((GeneralPath)shape).curveTo(496.08398, 490.11002, 495.11398, 489.45602, 494.923, 488.48102);
((GeneralPath)shape).curveTo(494.732, 487.50403, 495.389, 486.535, 496.364, 486.34503);
((GeneralPath)shape).curveTo(497.33902, 486.15503, 498.30902, 486.80902, 498.5, 487.78503);
((GeneralPath)shape).curveTo(498.689, 488.761, 498.035, 489.731, 497.06, 489.92);
((GeneralPath)shape).lineTo(497.06, 489.92);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_3_2;
g.setTransform(defaultTransform__0_0_0_3_2);
g.setClip(clip__0_0_0_3_2);
origAlpha = alpha__0_0_0_3;
g.setTransform(defaultTransform__0_0_0_3);
g.setClip(clip__0_0_0_3);
float alpha__0_0_0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_4 = g.getClip();
AffineTransform defaultTransform__0_0_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_4 is CompositeGraphicsNode
float alpha__0_0_0_4_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_4_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_4_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_4_0 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(442.505, 572.006);
((GeneralPath)shape).curveTo(443.48102, 572.198, 444.45102, 571.544, 444.64, 570.568);
((GeneralPath)shape).curveTo(444.83102, 569.592, 444.177, 568.622, 443.199, 568.43097);
((GeneralPath)shape).curveTo(442.223, 568.243, 441.25702, 568.896, 441.065, 569.87195);
((GeneralPath)shape).curveTo(440.875, 570.848, 441.53, 571.817, 442.505, 572.006);
((GeneralPath)shape).lineTo(442.505, 572.006);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_4_0;
g.setTransform(defaultTransform__0_0_0_4_0);
g.setClip(clip__0_0_0_4_0);
float alpha__0_0_0_4_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_4_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_4_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_4_1 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(450.482, 530.963);
((GeneralPath)shape).curveTo(451.458, 531.151, 452.428, 530.497, 452.62, 529.52203);
((GeneralPath)shape).curveTo(452.80798, 528.54706, 452.15298, 527.57904, 451.178, 527.38904);
((GeneralPath)shape).curveTo(450.203, 527.19806, 449.233, 527.85205, 449.04102, 528.827);
((GeneralPath)shape).curveTo(448.854, 529.803, 449.508, 530.772, 450.482, 530.963);
((GeneralPath)shape).lineTo(450.482, 530.963);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_4_1;
g.setTransform(defaultTransform__0_0_0_4_1);
g.setClip(clip__0_0_0_4_1);
float alpha__0_0_0_4_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_4_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_4_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_4_2 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(458.46, 489.92);
((GeneralPath)shape).curveTo(459.436, 490.11002, 460.406, 489.45602, 460.598, 488.48102);
((GeneralPath)shape).curveTo(460.788, 487.50403, 460.13098, 486.535, 459.156, 486.34503);
((GeneralPath)shape).curveTo(458.181, 486.15503, 457.211, 486.80902, 457.022, 487.78503);
((GeneralPath)shape).curveTo(456.831, 488.761, 457.485, 489.731, 458.46, 489.92);
((GeneralPath)shape).lineTo(458.46, 489.92);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_4_2;
g.setTransform(defaultTransform__0_0_0_4_2);
g.setClip(clip__0_0_0_4_2);
origAlpha = alpha__0_0_0_4;
g.setTransform(defaultTransform__0_0_0_4);
g.setClip(clip__0_0_0_4);
float alpha__0_0_0_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_5 = g.getClip();
AffineTransform defaultTransform__0_0_0_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_5 is CompositeGraphicsNode
float alpha__0_0_0_5_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_5_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_5_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_5_0 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(525.585, 434.635);
((GeneralPath)shape).curveTo(524.594, 434.72202, 523.697, 433.97, 523.61005, 432.98);
((GeneralPath)shape).curveTo(523.52203, 431.99002, 524.27606, 431.09302, 525.26807, 431.006);
((GeneralPath)shape).curveTo(526.25507, 430.919, 527.15106, 431.67102, 527.2391, 432.66);
((GeneralPath)shape).curveTo(527.326, 433.651, 526.573, 434.548, 525.585, 434.635);
((GeneralPath)shape).lineTo(525.585, 434.635);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_5_0;
g.setTransform(defaultTransform__0_0_0_5_0);
g.setClip(clip__0_0_0_5_0);
float alpha__0_0_0_5_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_5_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_5_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_5_1 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(530.469, 490.469);
((GeneralPath)shape).curveTo(529.47797, 490.556, 528.581, 489.804, 528.493, 488.814);
((GeneralPath)shape).curveTo(528.409, 487.824, 529.16, 486.927, 530.151, 486.84);
((GeneralPath)shape).curveTo(531.142, 486.75198, 532.038, 487.505, 532.125, 488.495);
((GeneralPath)shape).curveTo(532.21, 489.485, 531.46, 490.381, 530.469, 490.469);
((GeneralPath)shape).lineTo(530.469, 490.469);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_5_1;
g.setTransform(defaultTransform__0_0_0_5_1);
g.setClip(clip__0_0_0_5_1);
origAlpha = alpha__0_0_0_5;
g.setTransform(defaultTransform__0_0_0_5);
g.setClip(clip__0_0_0_5);
float alpha__0_0_0_6 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_6 = g.getClip();
AffineTransform defaultTransform__0_0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_6 is CompositeGraphicsNode
float alpha__0_0_0_6_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_6_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_6_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_6_0 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(431.106, 434.635);
((GeneralPath)shape).curveTo(432.097, 434.72202, 432.99698, 433.97, 433.08398, 432.98);
((GeneralPath)shape).curveTo(433.168, 431.99002, 432.417, 431.09302, 431.426, 431.006);
((GeneralPath)shape).curveTo(430.435, 430.919, 429.541, 431.67102, 429.452, 432.66);
((GeneralPath)shape).curveTo(429.367, 433.651, 430.118, 434.548, 431.106, 434.635);
((GeneralPath)shape).lineTo(431.106, 434.635);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_6_0;
g.setTransform(defaultTransform__0_0_0_6_0);
g.setClip(clip__0_0_0_6_0);
float alpha__0_0_0_6_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_6_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_6_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_6_1 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(426.223, 490.469);
((GeneralPath)shape).curveTo(427.214, 490.556, 428.111, 489.804, 428.198, 488.814);
((GeneralPath)shape).curveTo(428.286, 487.824, 427.532, 486.927, 426.542, 486.84);
((GeneralPath)shape).curveTo(425.55298, 486.75198, 424.65698, 487.505, 424.569, 488.495);
((GeneralPath)shape).curveTo(424.48102, 489.485, 425.234, 490.381, 426.223, 490.469);
((GeneralPath)shape).lineTo(426.223, 490.469);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_6_1;
g.setTransform(defaultTransform__0_0_0_6_1);
g.setClip(clip__0_0_0_6_1);
origAlpha = alpha__0_0_0_6;
g.setTransform(defaultTransform__0_0_0_6);
g.setClip(clip__0_0_0_6);
float alpha__0_0_0_7 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_7 = g.getClip();
AffineTransform defaultTransform__0_0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_7 is CompositeGraphicsNode
float alpha__0_0_0_7_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_7_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_7_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_7_0 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(474.492, 480.627);
((GeneralPath)shape).curveTo(473.496, 480.627, 472.668, 479.80002, 472.668, 478.806);
((GeneralPath)shape).curveTo(472.668, 477.813, 473.496, 476.985, 474.492, 476.985);
((GeneralPath)shape).curveTo(475.484, 476.985, 476.312, 477.813, 476.312, 478.806);
((GeneralPath)shape).curveTo(476.313, 479.8, 475.484, 480.627, 474.492, 480.627);
((GeneralPath)shape).lineTo(474.492, 480.627);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_7_0;
g.setTransform(defaultTransform__0_0_0_7_0);
g.setClip(clip__0_0_0_7_0);
float alpha__0_0_0_7_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_7_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_7_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_7_1 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(482.01, 480.627);
((GeneralPath)shape).curveTo(481.014, 480.627, 480.186, 479.80002, 480.186, 478.806);
((GeneralPath)shape).curveTo(480.186, 477.813, 481.014, 476.985, 482.01, 476.985);
((GeneralPath)shape).curveTo(483.002, 476.985, 483.83002, 477.813, 483.83002, 478.806);
((GeneralPath)shape).curveTo(483.83, 479.8, 483.002, 480.627, 482.01, 480.627);
((GeneralPath)shape).lineTo(482.01, 480.627);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_7_1;
g.setTransform(defaultTransform__0_0_0_7_1);
g.setClip(clip__0_0_0_7_1);
origAlpha = alpha__0_0_0_7;
g.setTransform(defaultTransform__0_0_0_7);
g.setClip(clip__0_0_0_7);
float alpha__0_0_0_8 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_8 = g.getClip();
AffineTransform defaultTransform__0_0_0_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_8 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(478.251, 459.552);
((GeneralPath)shape).curveTo(477.255, 459.552, 476.427, 458.725, 476.427, 457.731);
((GeneralPath)shape).curveTo(476.427, 456.73798, 477.255, 455.90997, 478.251, 455.90997);
((GeneralPath)shape).curveTo(479.243, 455.90997, 480.071, 456.73798, 480.071, 457.731);
((GeneralPath)shape).curveTo(480.071, 458.725, 479.243, 459.552, 478.251, 459.552);
((GeneralPath)shape).lineTo(478.251, 459.552);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_8;
g.setTransform(defaultTransform__0_0_0_8);
g.setClip(clip__0_0_0_8);
float alpha__0_0_0_9 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_9 = g.getClip();
AffineTransform defaultTransform__0_0_0_9 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_9 is CompositeGraphicsNode
float alpha__0_0_0_9_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_9_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_9_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_9_0 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(474.492, 438.477);
((GeneralPath)shape).curveTo(473.496, 438.477, 472.668, 437.65, 472.668, 436.65598);
((GeneralPath)shape).curveTo(472.668, 435.66296, 473.496, 434.83496, 474.492, 434.83496);
((GeneralPath)shape).curveTo(475.484, 434.83496, 476.312, 435.66296, 476.312, 436.65598);
((GeneralPath)shape).curveTo(476.313, 437.649, 475.484, 438.477, 474.492, 438.477);
((GeneralPath)shape).lineTo(474.492, 438.477);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_9_0;
g.setTransform(defaultTransform__0_0_0_9_0);
g.setClip(clip__0_0_0_9_0);
float alpha__0_0_0_9_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_9_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_9_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_9_1 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(482.01, 438.477);
((GeneralPath)shape).curveTo(481.014, 438.477, 480.186, 437.65, 480.186, 436.65598);
((GeneralPath)shape).curveTo(480.186, 435.66296, 481.014, 434.83496, 482.01, 434.83496);
((GeneralPath)shape).curveTo(483.002, 434.83496, 483.83002, 435.66296, 483.83002, 436.65598);
((GeneralPath)shape).curveTo(483.83, 437.649, 483.002, 438.477, 482.01, 438.477);
((GeneralPath)shape).lineTo(482.01, 438.477);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_9_1;
g.setTransform(defaultTransform__0_0_0_9_1);
g.setClip(clip__0_0_0_9_1);
origAlpha = alpha__0_0_0_9;
g.setTransform(defaultTransform__0_0_0_9);
g.setClip(clip__0_0_0_9);
float alpha__0_0_0_10 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_10 = g.getClip();
AffineTransform defaultTransform__0_0_0_10 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_10 is CompositeGraphicsNode
float alpha__0_0_0_10_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_10_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_10_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_10_0 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(506.989, 438.477);
((GeneralPath)shape).curveTo(507.98502, 438.477, 508.81403, 437.649, 508.81403, 436.655);
((GeneralPath)shape).curveTo(508.81403, 435.663, 507.98502, 434.83398, 506.989, 434.83398);
((GeneralPath)shape).curveTo(505.997, 434.83398, 505.169, 435.663, 505.169, 436.655);
((GeneralPath)shape).curveTo(505.169, 437.648, 505.997, 438.477, 506.989, 438.477);
((GeneralPath)shape).lineTo(506.989, 438.477);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_10_0;
g.setTransform(defaultTransform__0_0_0_10_0);
g.setClip(clip__0_0_0_10_0);
float alpha__0_0_0_10_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_10_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_10_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_10_1 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(495.485, 438.477);
((GeneralPath)shape).curveTo(496.47998, 438.477, 497.306, 437.649, 497.306, 436.655);
((GeneralPath)shape).curveTo(497.306, 435.663, 496.48, 434.83398, 495.485, 434.83398);
((GeneralPath)shape).curveTo(494.49298, 434.83398, 493.66397, 435.663, 493.66397, 436.655);
((GeneralPath)shape).curveTo(493.664, 437.648, 494.493, 438.477, 495.485, 438.477);
((GeneralPath)shape).lineTo(495.485, 438.477);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_10_1;
g.setTransform(defaultTransform__0_0_0_10_1);
g.setClip(clip__0_0_0_10_1);
origAlpha = alpha__0_0_0_10;
g.setTransform(defaultTransform__0_0_0_10);
g.setClip(clip__0_0_0_10);
float alpha__0_0_0_11 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_11 = g.getClip();
AffineTransform defaultTransform__0_0_0_11 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_11 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(493.905, 459.552);
((GeneralPath)shape).curveTo(494.898, 459.552, 495.726, 458.725, 495.726, 457.731);
((GeneralPath)shape).curveTo(495.726, 456.73798, 494.898, 455.90997, 493.905, 455.90997);
((GeneralPath)shape).curveTo(492.91, 455.90997, 492.08398, 456.73798, 492.08398, 457.731);
((GeneralPath)shape).curveTo(492.084, 458.725, 492.91, 459.552, 493.905, 459.552);
((GeneralPath)shape).lineTo(493.905, 459.552);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_11;
g.setTransform(defaultTransform__0_0_0_11);
g.setClip(clip__0_0_0_11);
float alpha__0_0_0_12 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_12 = g.getClip();
AffineTransform defaultTransform__0_0_0_12 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_12 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(500.614, 480.408);
((GeneralPath)shape).curveTo(501.61002, 480.408, 502.43802, 479.58, 502.43802, 478.586);
((GeneralPath)shape).curveTo(502.43802, 477.593, 501.61002, 476.76498, 500.614, 476.76498);
((GeneralPath)shape).curveTo(499.622, 476.76498, 498.793, 477.593, 498.793, 478.586);
((GeneralPath)shape).curveTo(498.793, 479.58, 499.622, 480.408, 500.614, 480.408);
((GeneralPath)shape).lineTo(500.614, 480.408);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_12;
g.setTransform(defaultTransform__0_0_0_12);
g.setClip(clip__0_0_0_12);
float alpha__0_0_0_13 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_13 = g.getClip();
AffineTransform defaultTransform__0_0_0_13 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_13 is CompositeGraphicsNode
float alpha__0_0_0_13_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_13_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_13_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_13_0 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(449.567, 438.477);
((GeneralPath)shape).curveTo(448.572, 438.477, 447.74298, 437.649, 447.74298, 436.655);
((GeneralPath)shape).curveTo(447.74298, 435.663, 448.572, 434.83398, 449.567, 434.83398);
((GeneralPath)shape).curveTo(450.559, 434.83398, 451.38498, 435.663, 451.38498, 436.655);
((GeneralPath)shape).curveTo(451.386, 437.648, 450.56, 438.477, 449.567, 438.477);
((GeneralPath)shape).lineTo(449.567, 438.477);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_13_0;
g.setTransform(defaultTransform__0_0_0_13_0);
g.setClip(clip__0_0_0_13_0);
float alpha__0_0_0_13_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_13_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_13_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_13_1 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(461.072, 438.477);
((GeneralPath)shape).curveTo(460.076, 438.477, 459.248, 437.649, 459.248, 436.655);
((GeneralPath)shape).curveTo(459.248, 435.663, 460.076, 434.83398, 461.072, 434.83398);
((GeneralPath)shape).curveTo(462.064, 434.83398, 462.893, 435.663, 462.893, 436.655);
((GeneralPath)shape).curveTo(462.894, 437.648, 462.064, 438.477, 461.072, 438.477);
((GeneralPath)shape).lineTo(461.072, 438.477);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_13_1;
g.setTransform(defaultTransform__0_0_0_13_1);
g.setClip(clip__0_0_0_13_1);
origAlpha = alpha__0_0_0_13;
g.setTransform(defaultTransform__0_0_0_13);
g.setClip(clip__0_0_0_13);
float alpha__0_0_0_14 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_14 = g.getClip();
AffineTransform defaultTransform__0_0_0_14 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_14 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(462.652, 459.552);
((GeneralPath)shape).curveTo(461.659, 459.552, 460.831, 458.725, 460.831, 457.731);
((GeneralPath)shape).curveTo(460.831, 456.73798, 461.659, 455.90997, 462.652, 455.90997);
((GeneralPath)shape).curveTo(463.647, 455.90997, 464.47202, 456.73798, 464.47202, 457.731);
((GeneralPath)shape).curveTo(464.473, 458.725, 463.647, 459.552, 462.652, 459.552);
((GeneralPath)shape).lineTo(462.652, 459.552);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_14;
g.setTransform(defaultTransform__0_0_0_14);
g.setClip(clip__0_0_0_14);
float alpha__0_0_0_15 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_15 = g.getClip();
AffineTransform defaultTransform__0_0_0_15 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_15 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(0.504f,0,0,2.4142f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(455.547, 480.481);
((GeneralPath)shape).curveTo(454.552, 480.481, 453.723, 479.654, 453.723, 478.65997);
((GeneralPath)shape).curveTo(453.723, 477.66595, 454.552, 476.83896, 455.547, 476.83896);
((GeneralPath)shape).curveTo(456.539, 476.83896, 457.367, 477.66595, 457.367, 478.65997);
((GeneralPath)shape).curveTo(457.367, 479.654, 456.539, 480.481, 455.547, 480.481);
((GeneralPath)shape).lineTo(455.547, 480.481);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0_15;
g.setTransform(defaultTransform__0_0_0_15);
g.setClip(clip__0_0_0_15);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
origAlpha = alpha__0_0;
g.setTransform(defaultTransform__0_0);
g.setClip(clip__0_0);
origAlpha = alpha__0;
g.setTransform(defaultTransform__0);
g.setClip(clip__0);
g.setTransform(defaultTransform_);
g.setClip(clip_);

    }

    /**
     * Returns the X of the bounding box of the original SVG image.
     * 
     * @return The X of the bounding box of the original SVG image.
     */
    public static int getOrigX() {
        return 425;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 412;
    }

    /**
     * Returns the width of the bounding box of the original SVG image.
     * 
     * @return The width of the bounding box of the original SVG image.
     */
    public static int getOrigWidth() {
        return 612;
    }

    /**
     * Returns the height of the bounding box of the original SVG image.
     * 
     * @return The height of the bounding box of the original SVG image.
     */
    public static int getOrigHeight() {
        return 792;
    }
}

