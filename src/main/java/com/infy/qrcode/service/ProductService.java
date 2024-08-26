package com.infy.qrcode.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.infy.qrcode.exception.CustomException;
import com.infy.qrcode.repo.ProductRepo;
import com.infy.qrcode.entity.Product;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.infy.qrcode.utils.ImageUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public Product getProduct(Long id) throws CustomException {
        return productRepo.findById(id).orElseThrow(() -> new CustomException("Product with id = "+id+" not found",ThreadContext.get("Transaction_Id")));
    }

    public String addProduct(MultipartFile file, Product product) throws IOException, WriterException {

        String path = "demo.jpeg";
        String charset = "UTF-8";

        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        Product savedProduct = productRepo.save(Product.builder()
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .image(ImageUtils.compressImage(file.getBytes()))
                .build());
        String data = "product/" + savedProduct.getId();
        savedProduct.setQrCode(ImageUtils.compressImage(createQR(data, path, charset, hashMap, 200, 200)));
        productRepo.save(savedProduct);
        return savedProduct.getId().toString();
    }


    public byte[] getProductImage(Long id) throws DataFormatException, IOException, CustomException {
        Product product = productRepo.findById(id).orElseThrow(() -> new CustomException("Product Image with id = "+id+" not found",ThreadContext.get("Transaction_Id")));
        return ImageUtils.decompressImage(product.getImage());
    }


    public static byte[] createQR(String data, String path, String charset, Map hashMap, int height, int width)
            throws WriterException, IOException {

        BitMatrix matrix = new MultiFormatWriter()
                .encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);
        MatrixToImageWriter.writeToFile(
                matrix,
                path.substring(path.lastIndexOf('.') + 1),
                new File(path));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(matrix,"JPEG",outputStream);
        return outputStream.toByteArray();
    }

    public byte[] getQrCode(Long id) throws CustomException {
        Product product = productRepo.findById(id).orElseThrow(() -> new CustomException("Product QrCode with id = "+id+" not found",ThreadContext.get("Transaction_Id")));;
        return ImageUtils.decompressImage(product.getQrCode());
    }
}
