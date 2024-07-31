package com.sachin.qrcode.controller;
import com.google.zxing.EncodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sachin.qrcode.entity.Product;
import com.sachin.qrcode.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;


@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    public ProductService productService;

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Integer id) {
        Product product = productService.getProduct(Long.valueOf(id));
        return ResponseEntity.status(HttpStatus.OK)
                .body(product);
    }

    @PostMapping("/addProduct")
    public void addProduct(@RequestPart("file") MultipartFile file, @RequestPart("product") Product product) throws IOException, WriterException {
        productService.addProduct(file, product);
    }

    @GetMapping("/getProductImage")
    public ResponseEntity<?> getProductImage(@RequestParam Integer id) throws DataFormatException, IOException {
        byte[] imageData = productService.getProductImage(Long.valueOf(id));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE))
                .body(imageData);
    }

    @GetMapping("/getQrCode")
    public ResponseEntity<?> getQrCode(@RequestParam Integer id) throws DataFormatException, IOException {
        byte[] imageData = productService.getQrCode(Long.valueOf(id));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE))
                .body(imageData);
    }

    @GetMapping("/getQrData")
    public ResponseEntity<?> getQrData(@RequestParam Integer id) throws DataFormatException, IOException, NotFoundException {
        // Path where the QR code is saved
        String filePath = "E:\\Projects\\Infosys\\qrcode\\demo.jpeg";
        String charset = "UTF-8";

        Map<EncodeHintType, ErrorCorrectionLevel> hashMap
                = new HashMap<EncodeHintType,
                                ErrorCorrectionLevel>();

        hashMap.put(EncodeHintType.ERROR_CORRECTION,
                ErrorCorrectionLevel.L);
        String qrData = productService.getQrData(filePath, charset, hashMap);
        return ResponseEntity.status(HttpStatus.OK).body(qrData);
    }


}