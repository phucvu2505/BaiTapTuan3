package Model;

import Entity.Thisinh;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import thread.ThisinhInfoSingleton;
import thread.ThreadReadFile;
import thread.ThreadUpdate;

import java.io.*;
import java.util.*;

/**
 * Created by phucv on 10/3/2016.
 */
public class ThisinhModel {
    private List<Thisinh> listThisinh = new ArrayList<Thisinh>();
    private ObjectMapper objectMapper = new ObjectMapper();
    public List<Thisinh> getThisinh(String path) throws Exception {
        File folder  = new File(path);
        File[] listFiles = folder.listFiles();
        ThreadReadFile[] threadReadFile = new ThreadReadFile[listFiles.length];
        for (int i = 0; i < threadReadFile.length; i++) {
            threadReadFile[i] = new ThreadReadFile(listFiles[i]);
            threadReadFile[i].start();
        }
        for (int i = 0; i < threadReadFile.length; i++) {
            threadReadFile[i].join();
        }
        listThisinh = ThisinhInfoSingleton.getInstance().getThisinhList();
        ThreadUpdate threadUpdate = new ThreadUpdate(path);
        threadUpdate.setDaemon(true);
        threadUpdate.start();
        return listThisinh;
    }

   /* public boolean insertThisinh(Thisinh thisinh) throws Exception {
        return false;
    }*/

    public boolean updateThisinh(Thisinh thisinh) throws Exception {
        boolean result = false;
        File file = new File("DIEM_THI_2016/"+thisinh.getSrcFile());
        List<Thisinh> list = objectMapper.readValue(file, new TypeReference<List<Thisinh>>() {});
        for (int i = 0; i < list.size(); i++) {
            if(thisinh.getRollNo().equals(list.get(i).getRollNo())){
                list.add(i, thisinh);
                list.remove(i+1);
                break;
            }
        }
        try {
            objectMapper.writeValue(file, list);
            result = true;
        }catch (Exception e){
            throw e;
        }
        return result;
    }

    public boolean deleteThisinh(Thisinh thisinh) throws Exception {
        boolean result = false;
        File file = new File("DIEM_THI_2016/" + thisinh.getSrcFile());
        List<Thisinh> list = objectMapper.readValue(file, new TypeReference<List<Thisinh>>() {});
        for (int i = 0; i < listThisinh.size(); i++) {
            if(list.get(i).getRollNo().equals(thisinh.getRollNo())){
                list.remove(i);
                ThisinhInfoSingleton.getInstance().deleteThisinhList(thisinh);
                break;
            }
        }

        try {
            objectMapper.writeValue(file, list);
            result = true;
        } catch (Exception e) {
            throw e;
        }

        return result;
    }

    public List<Thisinh> searchThisinh(Thisinh thisinh, int option) {
        List<Thisinh> resultList = new ArrayList<Thisinh>();
        listThisinh = ThisinhInfoSingleton.getInstance().getThisinhList();
        if (option == 1) {
            for (Thisinh ths : listThisinh) {
                if (ths.getTotalMark() == thisinh.getTotalMark()) {
                    resultList.add(ths);
                }
            }
        } else if (option == 2) {
            for (Thisinh ths : listThisinh) {
                if (ths.getRollNo().equals(thisinh.getRollNo())) {
                    resultList.add(ths);
                }
            }
        } else if (option == 3) {
            for (Thisinh ths : listThisinh) {
                if (ths.getName().equals(thisinh.getName())) {
                    resultList.add(ths);

                }
            }
        } else if (option == 4) {
            for (Thisinh ths : listThisinh) {
                for (double cMark : ths.getMark()) {
                    if (thisinh.getMark()[0] == cMark) {
                        resultList.add(ths);
                        break;
                    }
                }
            }
        }
        return resultList;
    }

    public List<Thisinh> sortThisinh(int option) {
        listThisinh = ThisinhInfoSingleton.getInstance().getThisinhList();
        if (option == 1) {
            Collections.sort(listThisinh, new Comparator<Thisinh>() {
                public int compare(Thisinh o1, Thisinh o2) {
                    return o1.getRollNo().compareTo(o2.getRollNo());
                }
            });
        } else if (option == 2) {
            Collections.sort(listThisinh, new Comparator<Thisinh>() {
                public int compare(Thisinh o1, Thisinh o2) {
                    return o1.reverName().compareTo(o2.reverName());
                }
            });
        } else {
            Collections.sort(listThisinh, new Comparator<Thisinh>() {
                public int compare(Thisinh o1,Thisinh o2) {
                    if (o1.getTotalMark() < o2.getTotalMark())
                        return 1;
                    else if (o1.getTotalMark() == o2.getTotalMark())
                        return 0;
                    else
                        return -1;
                }
            });
        }
        return listThisinh;
    }

    public void statistic() {
        int[] statistic = new int[]{0, 0, 0, 0};
        listThisinh = ThisinhInfoSingleton.getInstance().getThisinhList();
        System.out.println("Tong so thi sinh du thi: " + listThisinh.size());
        for (Thisinh ths : listThisinh) {
            if (ths.getTotalMark() < 15)
                statistic[0]++;
            else if (ths.getTotalMark() >= 15 && ths.getTotalMark() < 20)
                statistic[1]++;
            else if (ths.getTotalMark() >= 20 && ths.getTotalMark() < 25)
                statistic[2]++;
            else
                statistic[3]++;
        }
        int mucDiem = 0;
        for (int tongSV : statistic) {
            if (mucDiem == 0) {
                System.out.println("Muc diem tu 0 den 15: " + tongSV + " thi sinh");
                mucDiem += 15;
            } else {
                System.out.println("Muc diem tu " + mucDiem + " den " + (mucDiem + 5) + ": " + tongSV + "thi sinh");
                mucDiem += 5;
            }
        }
    }

    public boolean writeResult(List<Thisinh> list) throws Exception {
        File file = new File("KetQua.json");
        boolean check = false;
        try {
            objectMapper.writeValue(file, list);
            check = true;
        }catch (Exception e){
            throw e;
        }
        return check;
    }

    public List<Thisinh> getLastestData() throws Exception {
        listThisinh = ThisinhInfoSingleton.getInstance().getThisinhList();
        return listThisinh;
    }

}
