package com.example.identicon;

import java.util.Scanner;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class Main {
    private Main() {
    }

    private static final Color[] COLORS = {
            new Color(0xD64545),
            new Color(0xE06B35),
            new Color(0xD49A28),
            new Color(0xA6A832),
            new Color(0x5C9E45),
            new Color(0x3A9B72),
            new Color(0x329C9C),
            new Color(0x3D8DBD),
            new Color(0x4B72C2),
            new Color(0x6258B5),
            new Color(0x7A53B8),
            new Color(0x9950A5),
            new Color(0xB84F82),
            new Color(0xC95D68),
            new Color(0x6F7D8C),
            new Color(0x5F6B62)
    };

    private static final int ICON_SIZE = 7;
    private static final int CELL_SIZE = 40;
    private static final int PADDING = 1;

    public static void saveImage(boolean[][] icon, Color iconColor, String imageName) throws IOException {

        int imageSize = (ICON_SIZE + 2 * PADDING) * CELL_SIZE;

        BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imageSize, imageSize);
        g.setColor(iconColor);
        for (int row = 0; row < ICON_SIZE; row++) {
            for (int column = 0; column < ICON_SIZE; column++) {
                if (icon[row][column]) {
                    g.fillRect((column + 1) * CELL_SIZE, (row + 1) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
        g.dispose();

        ImageIO.write(image, "png", new File(imageName));
    }

    public static void mirrorIcon(boolean[][] icon) {
        for (int row = 0; row < ICON_SIZE; row++) {
            for (int column = 0; column < (ICON_SIZE / 2); column++) {
                icon[row][column] = icon[row][ICON_SIZE - 1 - column];
            }
        }
    }

    public static boolean[][] generateIcon(int panel, int pattern) {
        boolean[][] icon = new boolean[ICON_SIZE][ICON_SIZE];
        int panelBit;
        int shiftPanel = panel;
        if (pattern == 1) {
            for (int column = icon.length - 1; (ICON_SIZE / 2) <= column; column--) {
                for (int row = icon[0].length - 1; 0 <= row; row--) {
                    panelBit = shiftPanel & 1;
                    if (panelBit == 1) {
                        icon[row][column] = true;
                    }
                    shiftPanel = shiftPanel >>> 1;
                }
            }
        } else {
            for (int row = icon.length - 1; 0 <= row; row--) {
                for (int column = icon[0].length - 1; (ICON_SIZE / 2) <= column; column--) {
                    panelBit = shiftPanel & 1;
                    if (panelBit == 1) {
                        icon[row][column] = true;
                    }
                    shiftPanel = shiftPanel >>> 1;
                }
            }
        }

        mirrorIcon(icon);

        return icon;
    }

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Last  Name : ");
        String lastName = scanner.nextLine();
        System.out.print("First Name : ");
        String firstName = scanner.nextLine();

        String yourName = lastName.toUpperCase() + ":" + firstName.toUpperCase();
        System.out.println("Use this name : " + yourName);

        int hash = yourName.hashCode();

        int colorIndex = hash & 0xF;

        int designPattern = hash >>> 4;

        boolean[][] iconA = generateIcon(designPattern, 1);
        boolean[][] iconB = generateIcon(designPattern, 2);

        Color iconColor = COLORS[colorIndex];

        saveImage(iconA, iconColor, "docs/images/iconA.png");
        saveImage(iconB, iconColor, "docs/images/iconB.png");

        System.out.println("Made it your name Icon!");
    }
}
