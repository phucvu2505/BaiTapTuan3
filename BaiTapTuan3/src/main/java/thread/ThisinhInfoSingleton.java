package thread;

import Entity.Thisinh;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by phucv on 10/16/2016.
 */
public class ThisinhInfoSingleton {
    private HashMap<String, Thisinh> thisinhList;
    private ReentrantLock lock;

    public ThisinhInfoSingleton() {
        this.thisinhList = new HashMap<String, Thisinh>();
        this.lock = new ReentrantLock();
    }

    public List<Thisinh> getThisinhList() {
        Collection<Thisinh> thisinhtCollection = thisinhList.values();
        List<Thisinh> list = new ArrayList<Thisinh>();
        list.addAll(thisinhtCollection);
        return list;
    }

    public void deleteThisinhList(Thisinh thisinh){
        thisinhList.remove(thisinh.getRollNo());
    }

    private static class StudentInfoSingletonHolder {
        private static final ThisinhInfoSingleton INSTANCE = new ThisinhInfoSingleton();
    }

    public static ThisinhInfoSingleton getInstance() {
        return StudentInfoSingletonHolder.INSTANCE;
    }

    public void insertWithLock(List<Thisinh> list){
        lock.lock();
        for (Thisinh thisinh :
                list) {
            thisinhList.put(thisinh.getRollNo(), thisinh);
        }
        lock.unlock();
    }
}
