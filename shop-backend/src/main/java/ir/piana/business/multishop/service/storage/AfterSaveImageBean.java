package ir.piana.business.multishop.service.storage;


import ir.piana.business.multishop.dev.uploadrest.AfterSaveImage;
import ir.piana.business.multishop.dev.uploadrest.UploadController;
import ir.piana.business.multishop.service.sql.SqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Component("afterSaveImage")
public class AfterSaveImageBean extends UploadController.AfterSaveImageAction {
    @Autowired
    private SqlService sqlService;

    BiFunction<HttpServletRequest, String, ResponseEntity> deleteSampleSessionImageBusiness = (request, path) -> {
        Object id = AfterSaveImage.getValueObject(request.getHeader("id"));

        sqlService.updateByQueryName("delete-session-image",
                new Object[]{id});
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", id);
        return ResponseEntity.ok(map);
    };

    BiFunction<HttpServletRequest, String, ResponseEntity> replaceSampleSessionImageBusiness = (request, path) -> {
        Object id = AfterSaveImage.getValueObject(request.getHeader("id"));

        sqlService.updateByQueryName("replace-session-image",
                new Object[]{path, id});
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", id);
        map.put("image_src", path);
        return ResponseEntity.ok(map);
    };

    BiFunction<HttpServletRequest, String, ResponseEntity> insertSampleSessionImageBusiness = (request, path) -> {
        Object sessionId = AfterSaveImage.getValueObject(request.getHeader("sessionId"));
//        Object orders = getValueObject(request.getHeader("orders"));

        Long orders = sqlService.selectLong("select max(orders) + 1 from samples_session_image where samples_session_id = ?", new Object[] {sessionId});

        long id = sqlService.insertByQueryName("insert-session-image", "vavishka_seq",
                new Object[]{path, sessionId, orders});

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", id);
        map.put("session_id", sessionId);
        map.put("orders", orders);
        map.put("image_src", path);
        return ResponseEntity.ok(map);
    };
}
