package Controller;


import Entity.Thisinh;
import Model.ThisinhModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by phucv on 10/3/2016.
 */
public class ThisinhController {
    private ThisinhModel thisinhModel;

    public ThisinhController() {
        this.thisinhModel= new ThisinhModel();
    }

    public List<Thisinh> execOfGetThisinh(String path) throws Exception {
        return thisinhModel.getThisinh(path);
    }

   /* public boolean execOfInsertThisinh(Thisinh thisinh) throws Exception {
        return false;
    }*/

    public boolean execOfUpdateThisinh(Thisinh thisinh) throws Exception {
        return thisinhModel.updateThisinh(thisinh);
    }

    public boolean execOfDeleteThisinh(Thisinh thisinh) throws Exception {
        return thisinhModel.deleteThisinh(thisinh);
    }

    public List<Thisinh> execOfSearchThisinh(Thisinh thisinh, int option) {
        return thisinhModel.searchThisinh(thisinh, option);
    }

    public void execOfStatistic() {
        thisinhModel.statistic();
    }

    public List<Thisinh> execOfSortThisinh(int option) {
        return thisinhModel.sortThisinh(option);
    }

    public boolean execOfWriteResult(List<Thisinh> list) throws Exception {
        return thisinhModel.writeResult(list);
    }

    public List<Thisinh> execOfGetLastestData() throws Exception {
        return thisinhModel.getLastestData();
    }

}
