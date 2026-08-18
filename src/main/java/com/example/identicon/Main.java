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

        boolean icon[][] = new boolean[7][7];

        int panelCount;
        int shiftPanel = panel;
        for (int i = icon.length - 1; 3 <= i; i--) {
            for (int j = icon[0].length - 1; 0 <= j; j--) {
                panelCount = shiftPanel & 1;
                if (panelCount == 1) {
                    icon[j][i] = true;
                }
                shiftPanel = shiftPanel >>> 1;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                icon[j][i] = icon[j][6 - i];
            }
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (icon[i][j]) {
                    System.out.print(1);
                } else {
                    System.out.print(0);
                }
            }
            System.out.println();
        }

    }
}
