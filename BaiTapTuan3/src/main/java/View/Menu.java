package View;

import Controller.ThisinhController;
import Entity.Thisinh;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by phucv on 10/12/2016.
 */
public class Menu {
    private Scanner nhap;
    private ThisinhController thisinhController;

    public Menu(Scanner nhap) {
        this.nhap = nhap;
        thisinhController = new ThisinhController();
    }

    public void menuView() {
        System.out.println("------------------------");
        System.out.println("1. Lấy dữ liệu sinh viên");
        System.out.println("2. Cập nhật, xóa thông tin sinh viên");
        System.out.println("3. Tim Kiem sinh vien");
        System.out.println("4. Sap xep sinh vien");
        System.out.println("5. Thong ke");
        System.out.println("6. Hien thi danh sach");
        System.out.println("7. Thoat");
        System.out.println("------------------------");
    }

    public int menuUpdate() {
        System.out.println("---------------------");
        System.out.println("1. Cập nhật sinh viên");
        System.out.println("2. Xóa sinh viên");
        System.out.println("3. Quay lại");
        System.out.println("---------------------");
        int chon = 0;
        System.out.print("Chon thao tac: ");
        chon = nhap.nextInt();
        return chon;
    }

    public int menuSearch() {
        System.out.println("------------------");
        System.out.println("1. Tong so diem");
        System.out.println("2. So bao danh");
        System.out.println("3. Ho va ten");
        System.out.println("4. Diem thanh phan");
        System.out.println("5. Quay lại");
        System.out.println("------------------");
        int chon = 0;
        System.out.print("Chon thao tac: ");
        chon = nhap.nextInt();
        nhap = new Scanner(System.in);
        return chon;
    }

    public int menuSort() {
        System.out.println("-------------------------");
        System.out.println("1. Sap xep so bao danh");
        System.out.println("2. Sap xep theo ten");
        System.out.println("3. Sap xep theo tong diem");
        System.out.println("4. Quay lại");
        System.out.println("-------------------------");
        int chon = 0;
        System.out.print("Chon thao tac: ");
        chon = nhap.nextInt();
        return chon;
    }

    public void optionAdd() {
        try {
            thisinhController.execOfGetThisinh("DIEM_THI_2016/");
        } catch (Exception e) {
            System.out.println("Lay du lieu bi loi!");
        }
        execOfDisplay();
    }

    public void optionUpdate() {
        int chon = 0;
        do {
            List<Thisinh> thisinhList = new ArrayList<Thisinh>();
            Thisinh thisinh = new Thisinh();
            chon = menuUpdate();
            switch (chon) {
                case 1: {
                    System.out.println("Nhập sô báo danh cần sửa thông tin: ");
                    String soBaoDanh = nhap.next();
                    thisinh.setRollNo(soBaoDanh);
                    thisinhList = thisinhController.execOfSearchThisinh(thisinh, 2);
                    if (thisinhList.isEmpty()) {
                        System.out.println("Khong ton tai sinh vien nay");
                    } else {
                        boolean check = true;
                        thisinh = updateInfo(thisinhList.get(0));
                        try {
                            thisinhController.execOfUpdateThisinh(thisinh);
                        } catch (Exception e) {
                            check = false;
                            e.printStackTrace();
                        }
                        if (check)
                            System.out.println("Sua thông tin thành công!!!");
                    }
                    break;
                }
                case 2: {
                    System.out.println("Nhập sô báo danh cần xóa: ");
                    thisinh.setRollNo(nhap.next());
                    thisinhList = thisinhController.execOfSearchThisinh(thisinh, 2);
                    if (thisinhList.isEmpty())
                        System.out.println("Khong ton tai sinh vien nay");
                    else {
                        boolean check = true;
                        try {
                            thisinhController.execOfDeleteThisinh(thisinhList.get(0));
                        } catch (Exception e) {
                            check = false;
                            e.printStackTrace();
                        }
                        if (check)
                            System.out.println("Xoa thông tin thành công!!!");
                    }

                    break;
                }
            }
        } while (chon < 3);
    }

    public void optionSearch() {
        nhap = new Scanner(System.in);
        List<Thisinh> thisinhList = new ArrayList<Thisinh>();
        Thisinh thisinh = new Thisinh();
        int chon = 0;
        do {
            chon = menuSearch();
            switch (chon) {
                case 1: {
                    System.out.println("Nhap tong diem cua sinh vien: ");
                    double tongDiem = nhap.nextDouble();
                    thisinh.setTotalMark(tongDiem);
                    break;
                }
                case 2: {
                    System.out.println("Nhap so bao danh can tim: ");
                    String SBD = nhap.next();
                    thisinh.setRollNo(SBD);
                    break;
                }
                case 3: {
                    System.out.println("Nhap ho ten can tim: ");
                    String hoTen = nhap.nextLine();
                    thisinh.setName(hoTen);
                    break;
                }
                case 4: {
                    System.out.println("Nhap diem thanh phan can tim; ");
                    double diemTP = nhap.nextDouble();
                    thisinh.setMark(new double[]{diemTP, 0.0, 0.0});
                    break;
                }
            }
            thisinhList = thisinhController.execOfSearchThisinh(thisinh, chon);
            if (chon < 5) {
                System.out.println("Danh sach ket qua tim kiem: ");
                for (Thisinh ths :
                        thisinhList) {
                    displayInfo(ths);
                }
                System.out.println("Ban co muon ghi kết quả ra file ko: (y/n)");
                System.out.println("0. Co");
                System.out.println("1. Khong");
                int ghiFile;
                boolean check = true;
                do {
                    ghiFile = nhap.nextInt();
                    if (ghiFile == 0) {
                        try {
                            thisinhController.execOfWriteResult(thisinhList);
                        } catch (Exception e) {
                            check = false;
                            e.printStackTrace();
                        }
                    }
                } while (ghiFile > 1);
                if (check)
                    System.out.println("Ghi kết quả xuống file thành công");
            }
        } while (chon < 5);
    }

    public void optionSort() {
        int chon = 0;
        List<Thisinh> list = new ArrayList<Thisinh>();
        do {
            chon = menuSort();
            list = thisinhController.execOfSortThisinh(chon);
            System.out.println("Danh sach sinh vien sau khi sap xep");
            for (Thisinh ths :
                    list) {
                displayInfo(ths);
            }
        } while (chon < 4);
    }

    public void optionStatistic() {
        thisinhController.execOfStatistic();
    }

    public void execOfDisplay() {
        try {
            List<Thisinh> list = thisinhController.execOfGetLastestData();
            for (Thisinh ths :
                    list) {
                displayInfo(ths);
            }
            list.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayInfo(Thisinh ths) {
        System.out.print(" " + ths.getRollNo() + "       ");
        System.out.print(" " + ths.getName() + "      ");
        System.out.print(" " + ths.getAddress() + "       ");
        System.out.print(" " + (ths.isGender() ? "Nam" : "Nu") + "      ");
        double[] mark = ths.getMark();
        System.out.printf(" %f %f %f        ", mark[0], mark[1], mark[2]);
        System.out.println("");
    }

    public Thisinh inputInfo() {
        Thisinh ths = new Thisinh();
        System.out.print("Nhap so bao danh: ");
        ths.setRollNo(nhap.nextLine());
        System.out.print("Nhap ten thi sinh: ");
        ths.setName(nhap.nextLine());
        System.out.print("Nhap dia chi thi sinh: ");
        ths.setAddress(nhap.nextLine());
        System.out.println("Chon gioi tinh thi sinh: ");
        System.out.println("0. Nam\n1. Nu");
        int gioiTinh;
        do {
            System.out.print("Chon: ");
            gioiTinh = nhap.nextInt();
            if (gioiTinh != 0 && gioiTinh != 1) {
                System.out.println("Chon sai!!!");
            } else {
                ths.setGender(gioiTinh == 0 ? true : false);
            }
        } while (gioiTinh != 0 && gioiTinh != 1);

        System.out.println("Nhap diem Toan - Ly - Hoa: ");
        double[] diem = new double[3];
        for (int j = 0; j < 3; j++) {
            diem[j] = nhap.nextDouble();
        }
        ths.setMark(diem);
        return ths;
    }

    public Thisinh updateInfo(Thisinh thisinh) {
        System.out.println("Sua thong tin thi sinh: ");
        System.out.println("So bao danh: " + thisinh.getRollNo());
        System.out.print("Sua ten thi sinh " + thisinh.getName() + ": ");
        nhap = new Scanner(System.in);
        thisinh.setName(nhap.nextLine());
        System.out.print("Sua dia chi thi sinh: " + thisinh.getAddress() + ": ");
        thisinh.setAddress(nhap.nextLine());
        System.out.println("Sua gioi tinh thi sinh: ");
        System.out.println("0. Nam\n1. Nu");
        int gioiTinh;
        do {
            System.out.print("Chon: ");
            gioiTinh = nhap.nextInt();
            if (gioiTinh != 0 && gioiTinh != 1) {
                System.out.println("Chon sai!!!");
            } else {
                thisinh.setGender(gioiTinh == 0 ? true : false);
            }
        } while (gioiTinh != 0 && gioiTinh != 1);
        System.out.println("Sua diem Toan - Ly - Hoa: ");
        double[] diem = new double[3];
        for (int j = 0; j < 3; j++) {
            diem[j] = nhap.nextDouble();
        }
        thisinh.setMark(diem);
        thisinh.setStateChange(true);
        return thisinh;
    }
}
