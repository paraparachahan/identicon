package com.example.identicon;

import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.Icon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class Main {
    private Main() {
    }

    public static void saveImage(boolean[][] icon, Color iconColor, String imageName) throws IOException {
        int cellSize = 40;
        int imageSize = 9 * cellSize;

        BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imageSize, imageSize);
        g.setColor(iconColor);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (icon[i][j]) {
                    g.fillRect((j + 1) * cellSize, (i + 1) * cellSize, cellSize, cellSize);
                }
            }
        }
        g.dispose();

        ImageIO.write(image, "png", new File(imageName));
    }

    public static boolean[][] mirrorIcon(boolean[][] icon) {
        for (int i = 0; i < ICON_SIZE; i++) {
            for (int j = 0; j < (ICON_SIZE / 2); j++) {
                icon[i][j] = icon[i][ICON_SIZE - 1 - j];
            }
        }

        return icon;
    }

    public static boolean[][] generateIcon(int panel, int pattern) {
        boolean[][] icon = new boolean[7][7];
        int panelBit;
        int shiftPanel = panel;
        if (pattern == 1) {
            for (int i = icon.length - 1; 3 <= i; i--) {
                for (int j = icon[0].length - 1; 0 <= j; j--) {
                    panelBit = shiftPanel & 1;
                    if (panelBit == 1) {
                        icon[j][i] = true;
                    }
                    shiftPanel = shiftPanel >>> 1;
                }
            }
        } else {
            for (int i = icon.length - 1; 0 <= i; i--) {
                for (int j = icon[0].length - 1; 3 <= j; j--) {
                    panelBit = shiftPanel & 1;
                    if (panelBit == 1) {
                        icon[i][j] = true;
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

        String familyName = scanner.nextLine();
        String givenName = scanner.nextLine();

        String yourName = familyName.toUpperCase() + ":" + givenName.toUpperCase();
        System.out.println(yourName);

        int hash = yourName.hashCode();

        int color = hash & 0xF;

        int panel = hash >>> 4;

        boolean[][] iconA = generateIcon(panel, 1);
        boolean[][] iconB = generateIcon(panel, 2);

        Color[] colors = {
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

        Color iconColor = colors[color];

        saveImage(iconA, iconColor, "iconA.png");
        saveImage(iconB, iconColor, "iconB.png");
    }
}
