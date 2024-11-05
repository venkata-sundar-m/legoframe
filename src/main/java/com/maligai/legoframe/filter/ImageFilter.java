package com.maligai.legoframe.filter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class ImageFilter {
static ArrayList<Color> pallet1 = new ArrayList<>(){{
        add(Color.WHITE);
        add(Color.GRAY);
        add(Color.DARK_GRAY);
        add(Color.BLACK);
    }};

    static  ArrayList<Color> pallet2 = new ArrayList<>(){{
        add(Color.WHITE);
        add(new Color(208, 163, 132));
        add(Color.YELLOW);
        add(Color.ORANGE);       
        add(Color.BLUE.brighter());
        add(Color.GREEN);
        add(Color.GRAY);
        add(Color.DARK_GRAY);
        add(Color.BLUE.darker());
        add(Color.GREEN.darker());
        add(Color.RED);
        add(Color.BLACK);
    }};

    static ArrayList<Color> pallet3 = new ArrayList<>(){{
        add(Color.WHITE);
        add(Color.GRAY);
        add(Color.DARK_GRAY);
        add(new Color(208, 163, 132).darker());
        add(new Color(208, 163, 132));
        add(Color.BLACK);
    }};

    public static BufferedImage resize(BufferedImage img, int newHeight) {
        
        double scaleFactor = (double) newHeight/img.getHeight();
        int scaledWidth = (int)(scaleFactor*img.getWidth());
        System.out.println("  Scaling image. H: " + newHeight + " W: " + scaledWidth );
        BufferedImage scaledImage = new BufferedImage(scaledWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(img, 0, 0, scaledWidth, newHeight, null);
        g2d.dispose();
        return scaledImage;
    }

    public static BufferedImage legozised(BufferedImage img) {
        System.out.println("logo 1x1 blocks");
        int blockSize = 15;
        int height = blockSize*img.getHeight();
        int width = blockSize* img.getWidth();

        BufferedImage legoImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = legoImage.createGraphics();

        for(int row = 0 ; row < img.getHeight(); row++) {
            int col=0;
            for(col = 0; col < img.getWidth(); col++) {
                Color c = new Color( img.getRGB(col, row));
                Color closestColor = findClosestColor(c, pallet3);
                g2d.setColor(closestColor);
                g2d.fillRect(col *blockSize, row*blockSize, blockSize, blockSize);
                g2d.setColor(Color.BLACK);
                g2d.drawLine(col*blockSize, row, col*blockSize, row*blockSize);
            }
            g2d.drawLine(0,row*blockSize, col*blockSize,row*blockSize);
        }
        g2d.dispose();
        return legoImage;
    }

    private static Color findClosestColor(Color originalColor, ArrayList<Color> colors) {
        Color closestColor = colors.get(0);
        double minDistance = Double.MAX_VALUE;
        for (Color color : colors) {
            double distance = colorDistance(originalColor, color);
            if (distance < minDistance) {
                minDistance = distance;
                closestColor = color;
            }
        }

        return closestColor;
    }

    private static double colorDistance(Color c1, Color c2) {
        int rDiff = c1.getRed() - c2.getRed();
        int gDiff = c1.getGreen() - c2.getGreen();
        int bDiff = c1.getBlue() - c2.getBlue();
        return Math.sqrt(rDiff * rDiff + gDiff * gDiff + bDiff * bDiff);
    } 
}
