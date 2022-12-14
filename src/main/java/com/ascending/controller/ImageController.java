package com.ascending.controller;

import com.ascending.model.Image;
import com.ascending.service.FileService;
import com.ascending.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URL;

@RestController
@RequestMapping(value = "/files")
public class ImageController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private FileService fileService;

    @Autowired
    private ImageService imageService;

    /**
     * POST /files?bucket=value1&file=value2
     * @param bucket
     * @param file
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity uploadFiles(@RequestParam(name="bucket",required=false,defaultValue = "training-dan-1") String bucket, @RequestParam("file") MultipartFile file){

        String msg = String.format("Invalid parameters bucket=%s and file=%s.", bucket,file);
        if(bucket == null || file == null) return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(msg);

        try {
            logger.info(">>>>>>>>>>>>>>>>>Loading ..." + file.getName());
            Image image = imageService.saveUuidImage(file);
//            msg = String.format("The file=%s was upload to bucket=%s.", file.getOriginalFilename(), bucket);
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(image);
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    /**
     * GET /files?bucket=value1&key=value2
     * @param bucket
     * @param key
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getPublicUrl(@RequestParam("bucket") String bucket, @RequestParam("key") String key){
        String msg = String.format("Invalid parameters bucket=%s and key=%s.", bucket, key);
        if(bucket == null || key == null) return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(msg);

        //check null check empty
        //not able to find key throw exception
        try {
            logger.info(String.format(">>>>>>>>>>>>>>>>>Getting url of %s ...", key));
            URL url = fileService.getObjectPublicUrl(bucket, key);
            msg = String.format("The url of the file with key=%s in bucket=%s is: %s.",
                    key, bucket, url);
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(url);
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_NOT_ACCEPTABLE).body(e.getMessage());
        }
    }
}
