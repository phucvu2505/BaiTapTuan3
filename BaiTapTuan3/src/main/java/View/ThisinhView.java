package View;

import java.util.Scanner;

/**
 * Created by phucv on 10/3/2016.
 */
public class ThisinhView {
    public static void main(String[] args) {
        Scanner nhap = new Scanner(System.in);
        Menu menu = new Menu(nhap);
        int chon = 0;
        do {
            menu.menuView();
            System.out.print("Chon chuc nang: ");
            chon = nhap.nextInt();
            switch (chon) {
                case 1: {
                    menu.optionAdd();
                    break;
                }
                case 2: {
                    menu.optionUpdate();
                    break;
                }
                case 3: {
                    menu.optionSearch();
                    break;
                }
                case 4: {
                    menu.optionSort();
                    break;
                }
                case 5: {
                    menu.optionStatistic();
                    break;
                }
                case 6: {
                    menu.execOfDisplay();
                }
            }
        } while (chon < 7);
    }
}

