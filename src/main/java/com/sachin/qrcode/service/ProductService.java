package com.sachin.qrcode.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sachin.qrcode.entity.Product;
import com.sachin.qrcode.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.sachin.qrcode.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public Product getProduct(Long id) {
        return productRepo.findById(id).get();
    }

    public void addProduct(MultipartFile file, Product product) throws IOException, WriterException {


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
        String data = "http://192.168.29.15:8080/product/getProduct/" + savedProduct.getId();
        savedProduct.setQrCode(ImageUtils.compressImage(createQR(data, path, charset, hashMap, 200, 200)));
        productRepo.save(savedProduct);
    }


    public byte[] getProductImage(Long id) throws DataFormatException, IOException {
        Optional<Product> product = productRepo.findById(id);
        return ImageUtils.decompressImage(product.get().getImage());
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

    public byte[] getQrCode(Long id) {
        Optional<Product> product = productRepo.findById(id);
        return ImageUtils.decompressImage(product.get().getQrCode());
    }

    public String getQrData(String path, String charset,
                                Map hashMap)
            throws FileNotFoundException, IOException,
            NotFoundException
    {
        BinaryBitmap binaryBitmap
                = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                        ImageIO.read(
                                new FileInputStream(path)))));

        Result result
                = new MultiFormatReader().decode(binaryBitmap);

        return result.getText();
    }
}
