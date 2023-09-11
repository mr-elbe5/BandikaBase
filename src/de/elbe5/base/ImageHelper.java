/*
 Bandika CMS - A Java based modular Content Management System
 Copyright (C) 2009-2018 Michael Roennau

 This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.elbe5.base;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ImageHelper {

    public static BufferedImage createImage(byte[] bytes, String contentType) throws IOException {
        Iterator<ImageReader> readers = ImageIO.getImageReadersByMIMEType(contentType);
        if (readers.hasNext() && bytes != null) {
            ImageReader reader = readers.next();
            BufferedImage image;
            boolean singleImage = !contentType.endsWith("gif");
            ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(bytes));
            reader.setInput(iis, singleImage);
            image = reader.read(0);
            return image;
        }
        return null;
    }

    public static BufferedImage createResizedImage(byte[] bytes, String contentType, int maxWidth, int maxHeight, boolean expand) throws IOException {
        BufferedImage source = createImage(bytes, contentType);
        if (source == null) {
            return null;
        }
        float factor = getResizeFactor(source, maxWidth, maxHeight, expand);
        if (factor == 1) {
            return source;
        }
        return copyImage(source, factor);
    }

    public static BufferedImage createScaledImage(byte[] bytes, String contentType, int scalePercent) throws IOException {
        BufferedImage source = createImage(bytes, contentType);
        if (source == null) {
            return null;
        }
        float factor = ((float)scalePercent)/100;
        if (factor == 1) {
            return source;
        }
        return copyImage(source, factor);
    }

    public static float getResizeFactor(BufferedImage source, int maxWidth, int maxHeight, boolean expand) {
        if (source == null) {
            return 1;
        }
        float wfactor = maxWidth == 0 ? 0 : ((float) maxWidth) / source.getWidth();
        float hfactor = maxHeight == 0 ? 0 : ((float) maxHeight) / source.getHeight();
        float factor = 1;
        if (wfactor != 0)
            factor=wfactor;
        if (hfactor != 0)
            factor=Math.min(factor, hfactor);
        if (factor>1 && !expand)
            factor = 1;
        return factor;
    }

    public static BufferedImage copyImage(BufferedImage source, float factor) {
        BufferedImage bi = new BufferedImage((int) (source.getWidth() * factor), (int) (source.getHeight() * factor), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        AffineTransform at = AffineTransform.getScaleInstance(factor, factor);
        g.drawRenderedImage(source, at);
        return bi;
    }

    public static BufferedImage rotateImage(BufferedImage src, Double angle){
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage output = new BufferedImage(height, width, src.getType());
        Graphics2D g2d = (Graphics2D)output.getGraphics();
        g2d.translate(height/2., width/2.);
        g2d.rotate(angle);
        g2d.translate(-width/2., -height/2.);
        g2d.drawImage(src, 0,0,width, height,null);
        return output;
    }

    public static AffineTransform getExifTransformation(int orientation, int width, int height) {
        AffineTransform t = new AffineTransform();
        switch (orientation) {
            case 1:
                break;
            case 2:
                t.scale(-1.0, 1.0);
                t.translate(-width, 0);
                break;
            case 3:
                t.translate(width, height);
                t.rotate(Math.PI);
                break;
            case 4:
                t.scale(1.0, -1.0);
                t.translate(0, -height);
                break;
            case 5:
                t.rotate(-Math.PI / 2);
                t.scale(-1.0, 1.0);
                break;
            case 6:
                t.translate(height, 0);
                t.rotate(Math.PI / 2);
                break;
            case 7:
                t.scale(-1.0, 1.0);
                t.translate(-height, 0);
                t.translate(0, width);
                t.rotate(  3 * Math.PI / 2);
                break;
            case 8:
                t.translate(0, width);
                t.rotate(  3 * Math.PI / 2);
                break;
        }
        return t;
    }

    public static BufferedImage transformImage(BufferedImage image, AffineTransform transform) throws IOException {
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
        BufferedImage destinationImage = op.createCompatibleDestImage(image, (image.getType() == BufferedImage.TYPE_BYTE_GRAY) ? image.getColorModel() : null );
        Graphics2D g = destinationImage.createGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, destinationImage.getWidth(), destinationImage.getHeight());
        destinationImage = op.filter(image, destinationImage);
        return destinationImage;
    }

    public static byte[] writeImage(ImageWriter writer, BufferedImage image) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ImageOutputStream ios = ImageIO.createImageOutputStream(bout);
        writer.setOutput(ios);
        writer.write(image);
        bout.flush();
        bout.close();
        return bout.toByteArray();
    }
}
