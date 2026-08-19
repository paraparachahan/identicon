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

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        String familyName = scanner.nextLine();
        String givenName = scanner.nextLine();

        String yourName = familyName.toUpperCase() + ":" + givenName.toUpperCase();
        System.out.println(yourName);

        int hash = yourName.hashCode();
        System.out.println(hash);

        String binary = Integer.toBinaryString(hash);
        System.out.println(binary);

        int color = hash & 0xF;
        System.out.println(color);
        String colorBinary = Integer.toBinaryString(color);
        System.out.println(colorBinary);

        int panel = hash >>> 4;
        System.out.println(panel);
        String panelBinary = Integer.toBinaryString(panel);
        System.out.println(panelBinary);

        boolean iconA[][] = new boolean[7][7];
        boolean iconB[][] = new boolean[7][7];

        int panelCountA;
        int shiftPanelA = panel;
        for (int i = iconA.length - 1; 3 <= i; i--) {
            for (int j = iconA[0].length - 1; 0 <= j; j--) {
                panelCountA = shiftPanelA & 1;
                if (panelCountA == 1) {
                    iconA[j][i] = true;
                }
                shiftPanelA = shiftPanelA >>> 1;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                iconA[j][i] = iconA[j][6 - i];
            }
        }
        int panelCountB;
        int shiftPanelB = panel;
        for (int i = iconB.length - 1; 0 <= i; i--) {
            for (int j = iconB[0].length - 1; 3 <= j; j--) {
                panelCountB = shiftPanelB & 1;
                if (panelCountB == 1) {
                    iconB[i][j] = true;
                }
                shiftPanelB = shiftPanelB >>> 1;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                iconB[j][i] = iconB[j][6 - i];
            }
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (iconA[i][j]) {
                    System.out.print(1);
                } else {
                    System.out.print(0);
                }
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (iconB[i][j]) {
                    System.out.print(1);
                } else {
                    System.out.print(0);
                }
            }
            System.out.println();
        }

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

        int cellSize = 40;
        int imageSIze = 9 * cellSize;

        BufferedImage imageA = new BufferedImage(imageSIze, imageSIze, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = imageA.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imageSIze, imageSIze);
        g.setColor(iconColor);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (iconA[i][j]) {
                    g.fillRect((j + 1) * cellSize, (i + 1) * cellSize, cellSize, cellSize);
                }
            }
        }
        g.dispose();

        ImageIO.write(imageA, "png", new File("iconA.png"));

        BufferedImage imageB = new BufferedImage(imageSIze, imageSIze, BufferedImage.TYPE_INT_ARGB);
        Graphics2D h = imageB.createGraphics();
        h.setColor(Color.WHITE);
        h.fillRect(0, 0, imageSIze, imageSIze);
        h.setColor(iconColor);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (iconB[i][j]) {
                    h.fillRect((j + 1) * cellSize, (i + 1) * cellSize, cellSize, cellSize);
                }
            }
        }
        h.dispose();

        ImageIO.write(imageB, "png", new File("iconB.png"));
    }
}
