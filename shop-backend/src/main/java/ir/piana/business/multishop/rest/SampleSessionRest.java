package ir.piana.business.multishop.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.piana.business.multishop.dev.uploadrest.StorageService;
import ir.piana.business.multishop.service.sql.SqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.*;

@RestController
@RequestMapping("/api")
public class SampleSessionRest {
    @Autowired
    @Qualifier("databaseStorageService")
    private StorageService storageService;

    @Autowired
    private SqlService sqlService;

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper mapper;

//    @Autowired
//    private InsertSampleSessionImageBusiness insertSampleSessionImageBusiness;

//    @GetMapping(path = "sample/sessions/id", consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Transactional
//    public ResponseEntity getSessions(@PathParam("id") long sampleId) {
//        List<Map<String, Object>> mapList = sqlService.listByName("sample-session",
//                new Object[]{sampleId});
//        return ResponseEntity.ok(mapList);
//    }

    @PostMapping(path = "sample/session/add", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity addSession(
            @RequestBody Map<String, Object> sampleItem,
            @RequestHeader("file-group") String group){
        String iconSrc = storageService.store((String) sampleItem.get("icon"), group, 0);
//        sqlService.update(group,
//                new Object[]{sampleItem.get("title"), sampleItem.get("description"), imageSrc});
        long id = sqlService.insert(group, "vavishka_seq",
                new Object[]{sampleItem.get("samples_id"),
                        sampleItem.get("title"),
                        sampleItem.get("description"),
                        iconSrc,
                        sampleItem.get("orders")});
        Map map = new LinkedHashMap();
        map.put("id", id);
        map.put("samples_id", sampleItem.get("samples_id"));
        map.put("title", (String)sampleItem.get("title"));
        map.put("description", (String)sampleItem.get("description"));
        map.put("orders", sampleItem.get("orders"));
        map.put("icon_src", iconSrc);

        long id2 = sqlService.insertByQueryName("insert-session-image", "vavishka_seq",
                new Object[]{iconSrc, id, 1});

        return ResponseEntity.ok(map);
    }

    @PostMapping(path = "sample/session/edit", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity editSession(
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

    @PostMapping(path = "sample/session/delete", consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity deleteSession(
            @RequestBody Map<String, Object> sampleItem,
            @RequestHeader("file-group") String group) {
        sqlService.delete(group, new Object[]{sampleItem.get("id")});
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "sample/session/image/add", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity addSessionImage(
            @RequestBody Map<String, Object> sampleItem,
            @RequestHeader("file-group") String group){
        String iconSrc = storageService.store((String) sampleItem.get("icon"), group, 0);
//        sqlService.update(group,
//                new Object[]{sampleItem.get("title"), sampleItem.get("description"), imageSrc});
        long id = sqlService.insert(group, "vavishka_seq",
                new Object[]{sampleItem.get("samples_id"), sampleItem.get("title"),
                        sampleItem.get("description"),
                        iconSrc, sampleItem.get("orders")});
        Map map = new LinkedHashMap();
        map.put("id", id);
        map.put("samples_id", sampleItem.get("samples_id"));
        map.put("title", (String)sampleItem.get("title"));
        map.put("description", (String)sampleItem.get("description"));
        map.put("orders", sampleItem.get("orders"));
        map.put("iconSrc", iconSrc);
        return ResponseEntity.ok(map);
    }

    @PostMapping(path = "sample/session/image/delete", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity deleteSessionImage(
            @RequestBody Map<String, Object> sampleItem,
            @RequestHeader("business") String queryName){
        Integer id = (Integer)sampleItem.get("id");
        sqlService.deleteByQueryName(queryName, new Object[]{id});
        Map map = new LinkedHashMap();
        map.put("id", id);
        return ResponseEntity.ok(map);
    }

    @PostMapping(path = "sample/session/images", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity getSessionImages(@RequestBody Map<String, Object> sampleItem) {
        List<Map<String, Object>> mapList = sqlService.listByName("get-session-images",
                new Object[]{sampleItem.get("id")});
        SortedMap<String, Map<String, Object>> map  = new TreeMap<>();
        for (Map m : mapList) {
            map.put(m.get("ID").toString(), m);
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping(path = "sample/session/images",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity getSessionImages2(@RequestParam("id") int id) {
        List<Map<String, Object>> mapList = sqlService.listByName("get-session-images",
                new Object[]{id});
        SortedMap<String, Map<String, Object>> map  = new TreeMap<>();
        for (Map m : mapList) {
            map.put(m.get("ID").toString(), m);
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping(path = "sample/session/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity sessionsById(@PathVariable("id") Long id) {
        List<Map<String, Object>> list = sqlService.list("session", new Object[]{id});
        return ResponseEntity.ok(list);
    }

    @GetMapping(path = "sample/session/map/id", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity sessionMapById(@PathParam("id") long id) {
        List<Map<String, Object>> list = sqlService.list("sample-session", new Object[]{id});
        SortedMap<String, Map<String, Object>> map  = new TreeMap<>();
        for (Map m : list) {
            map.put(m.get("ID").toString(), m);
        }
        return ResponseEntity.ok(map);
    }
}
