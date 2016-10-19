package thread;

import Entity.Thisinh;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phucv on 10/3/2016.
 */
public class ThreadReadFile extends Thread {
    private File file;
    private ObjectMapper objectMapper;
    private List<Thisinh> thisinhList;

    public ThreadReadFile(File file) {
        this.file = file;
        this.objectMapper = new ObjectMapper();
        this.thisinhList = new ArrayList<Thisinh>();
    }
/*    public List<Thisinh> ReadListObjectIntoFiles() throws Exception {
        try {
            list= objectMapper.readValue(file, new TypeReference<List<Thisinh>>() {
            });
        }catch (JsonMappingException e){
            e.printStackTrace();
        }catch (JsonParseException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }*/

    @Override
    public void run() {
        try {
            thisinhList= objectMapper.readValue(file, new TypeReference<List<Thisinh>>() {
            });
        } catch (Exception e){
            e.printStackTrace();
        }
        ThisinhInfoSingleton.getInstance().insertWithLock(thisinhList);
        System.out.println("Doc xong "+file.getName());
    }

}