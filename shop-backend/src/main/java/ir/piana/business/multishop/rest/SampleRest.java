package ir.piana.business.multishop.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.piana.business.multishop.common.dev.uploadrest.StorageService;
import ir.piana.business.multishop.common.dev.service.sql.SqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SampleRest {
    @Autowired
    @Qualifier("databaseStorageService")
    private StorageService storageService;

    @Autowired
    private SqlService sqlService;

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper mapper;

    @PostMapping(path = "sample/add", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity addSample(
            @RequestBody Map<String, Object> sampleItem,
            @RequestHeader("file-group") String group){
        String imageSrc = storageService.store((String) sampleItem.get("image"), group, 0);
//        sqlService.update(group,
//                new Object[]{sampleItem.get("title"), sampleItem.get("description"), imageSrc});
        long id = sqlService.insert(group, "vavishka_seq",
                new Object[]{sampleItem.get("title"), sampleItem.get("description"), imageSrc});
        Map map = new LinkedHashMap();
        map.put("id", id);
        map.put("title", (String)sampleItem.get("title"));
        map.put("description", (String)sampleItem.get("description"));
        map.put("image_src", imageSrc);
        return ResponseEntity.ok(map);
    }

    @PostMapping(path = "sample/edit", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity editSample(
            @RequestBody Map<String, Object> sampleItem,
            @RequestHeader("file-group") String group){
        String imageSrc = null;
        if(sampleItem.get("image") != null) {
            imageSrc = storageService.store((String) sampleItem.get("image"), group, 0);
        } else {
            imageSrc = (String)sampleItem.get("imageSrc");
        }

        sqlService.update(group,
                new Object[]{sampleItem.get("title"), sampleItem.get("description"), imageSrc, sampleItem.get("id")});
        Map map = new LinkedHashMap();
        map.put("id", sampleItem.get("id"));
        map.put("title", (String)sampleItem.get("title"));
        map.put("description", (String)sampleItem.get("description"));
        map.put("image_src", imageSrc);
        return ResponseEntity.ok(map);
    }

    @PostMapping(path = "sample/delete", consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity deleteSample(
            @RequestBody Map<String, Object> sampleItem,
            @RequestHeader("file-group") String group) {
        sqlService.delete(group, new Object[]{sampleItem.get("id")});
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "samples",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity samples() {
        List<Map<String, Object>> samples = sqlService.list("sample", null);
        return ResponseEntity.ok(samples);
    }

        @GetMapping(path = "sample/id",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity sampleById(@PathParam("id") long id) {
        Map<String, Object> sample = sqlService.select("get-sample-by-id", new Object[]{id});
        return ResponseEntity.ok(sample);
    }
}
