package ru.klepov.cacheHelidon.api;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import ru.klepov.cacheHelidon.cache.CacheItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SnapshotServiceImpl implements SnapshotService {
    private String fileName;
    private Boolean isHealthy;

    public SnapshotServiceImpl(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getIsHealthy() {
        return isHealthy;
    }

    @Override
    public Optional<List<CacheItem<String, String>>> load() {
        try {
            var file = new File(fileName);
            if (!(file).exists()) {
                return Optional.of(new ArrayList<>());
            }
            GsonBuilder gsonBldr = new GsonBuilder();
            var reader = new JsonReader(new FileReader(fileName));
            CacheDumpPresentation res = gsonBldr.create().fromJson(reader, CacheDumpPresentation.class);
            if (res.getList() != null) {
                return Optional.of(res.getList());
            } else {
                isHealthy = false;
                return Optional.empty();
            }
        } catch (IOException ex) {
            isHealthy = false;
            return Optional.empty();
        }
    }

    @Override
    public void save(List<CacheItem<String, String>> data) {
        var toDes = new CacheDumpPresentation();
        toDes.setList(data);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try {
            var newFileName = fileName + "NEW";
            var newFile = new File(newFileName);
            FileWriter writer = new FileWriter(newFile);
            gson.toJson(toDes, writer);
            writer.flush();
            var oldFile = new File(fileName);
            if (oldFile.exists()) {
                oldFile.delete();
            }
            writer.close();
            Files.move(new File(newFileName), new File(fileName));
        } catch (Exception ex) {
            isHealthy = false;
        }
    }
}
