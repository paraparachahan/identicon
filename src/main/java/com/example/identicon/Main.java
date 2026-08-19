package com.example.identicon;

import java.util.Scanner;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {

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

    }
}
