package com.infy.qrcode.controller;
import com.google.zxing.WriterException;
import com.infy.qrcode.exception.CustomException;
import com.infy.qrcode.entity.Product;
import com.infy.qrcode.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.DataFormatException;


@RestController
@RequestMapping("/product")
@Slf4j
@CrossOrigin
public class ProductController {

    @Autowired
    public ProductService productService;

    @GetMapping("/login")
    public String login() {
        return "logged In";
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Integer id) throws CustomException {
        Product product = productService.getProduct(Long.valueOf(id));
        return ResponseEntity.status(HttpStatus.OK)
                .body(product);
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestPart("file") MultipartFile file, @RequestPart("product") Product product) throws IOException, WriterException {
        return productService.addProduct(file, product);
    }

    @GetMapping("/getProductImage")
    public ResponseEntity<?> getProductImage(@RequestParam Integer id) throws DataFormatException, IOException, CustomException {
        byte[] imageData = productService.getProductImage(Long.valueOf(id));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE))
                .body(imageData);
    }

    @GetMapping("/getQrCode")
    public ResponseEntity<?> getQrCode(@RequestParam Integer id) throws DataFormatException, IOException, CustomException {
        byte[] imageData = productService.getQrCode(Long.valueOf(id));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE))
                .body(imageData);
    }


}