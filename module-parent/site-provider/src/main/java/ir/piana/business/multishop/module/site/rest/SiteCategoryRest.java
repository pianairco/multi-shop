package ir.piana.business.multishop.module.site.rest;

import ir.piana.business.multishop.common.data.repository.SiteRepository;
import ir.piana.business.multishop.common.model.ResponseModel;
import ir.piana.business.multishop.module.site.service.SiteCategoryManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/modules/site/category")
public class SiteCategoryRest {

    @Autowired
    private SiteRepository repository;

    @Autowired
    private SiteCategoryManagerService siteCategoryManagerService;

    @GetMapping("root")
    public ResponseEntity<ResponseModel> getRoot(HttpSession session) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserModel userModel = (UserModel) authentication.getPrincipal();

//        Date lmod = (Date) session.getAttribute("lmod");
//        HttpHeaders responseHeaders = new HttpHeaders();
//        if( null == lmod ) {
//            lmod = new Date();
//            session.setAttribute("lmod", lmod);
//            responseHeaders.add("Cache-Control", "max-age=3600");
//            return ResponseEntity.ok().headers(responseHeaders).body(ResponseModel.builder().code(0)
//                    .data(siteCategoryManagerService.getRoot()).build());
//        }
//        responseHeaders.add("Last-Modified", lmod.toString());

//        String ifModifiedSince = request.getHeader("If-Modified-Since");
//        if( null != ifModifiedSince ) { // You may want to compare lmod and ifModifiedSince here, too
//            return new ResponseEntity( responseHeaders, HttpStatus.NOT_MODIFIED );
//        }

//        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).headers(responseHeaders).build();
        return ResponseEntity.ok(ResponseModel.builder().code(0)
                    .data(siteCategoryManagerService.getRoot()).build());
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseModel> getById(@PathVariable("id") long id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserModel userModel = (UserModel) authentication.getPrincipal();

        return ResponseEntity.ok(ResponseModel.builder().code(0)
                .data(siteCategoryManagerService.getById(id)).build());
    }

    @GetMapping("children/{id}")
    public ResponseEntity<ResponseModel> getChildren(@PathVariable("id") long id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserModel userModel = (UserModel) authentication.getPrincipal();

        return ResponseEntity.ok(ResponseModel.builder().code(0)
                .data(siteCategoryManagerService.getChildren(id)).build());
    }
}
