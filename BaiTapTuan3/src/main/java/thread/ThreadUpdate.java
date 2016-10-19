package thread;

import Entity.Thisinh;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by phucv on 10/4/2016.
 */
public class ThreadUpdate extends Thread{
    private ObjectMapper objectMapper;
    private List<Thisinh> listUpdate;
    private boolean checkUpdate;
    private String srcFolder;

    public ThreadUpdate(String srcFolder) {
        this.objectMapper = new ObjectMapper();
        this.listUpdate = new ArrayList<Thisinh>();
        this.checkUpdate = true;
        this.srcFolder = srcFolder;
    }

    @Override
    public void run() {
        File folder = new File(srcFolder);
        File[] files = folder.listFiles();
        HashMap<String , Long> fileState = new HashMap<String, Long>();
        List<Thisinh> list = new ArrayList<Thisinh>();
        for (File file :
                files) {
            fileState.put(file.getName(), file.lastModified());
        }
        while (true){
            files = folder.listFiles();
            try {
                Thread.sleep(30000);
                checkUpdate = false;
                System.out.println("Updating.....");
                listUpdate.clear();
                int index;
                for (int i = 0; i < files.length; i++) {
                    list.clear();
                    if(fileState.containsKey(files[i].getName())){

                        if(files[i].lastModified() != fileState.get(files[i].getName())){
                            list = objectMapper.readValue(files[i], new TypeReference<List<Thisinh>>() {});
                            for (Thisinh std:
                                    list) {
                                if(std.isStateChange()) {
                                    listUpdate.add(std);
                                    index = list.indexOf(std);
                                    std.setStateChange(false);
                                    list.add(i, std);
                                    list.remove(index+1);
                                    objectMapper.writeValue(files[i], list);
                                }
                            }
                            fileState.put(files[i].getName(), files[i].lastModified());
                        }
                    }else{
                        list = objectMapper.readValue(files[i], new TypeReference<List<Thisinh>>() {});
                        fileState.put(files[i].getName(), files[i].lastModified());
                        listUpdate.addAll(list);
                    }

                }
                if(!listUpdate.isEmpty()){
                    ThisinhInfoSingleton.getInstance().insertWithLock(listUpdate);
                }
                System.out.println("Update!!!");
                checkUpdate = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isCheckUpdate() {
        return checkUpdate;
    }
}
