package com.Devex.Sevice;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Devex.Entity.ImageProduct;

@Service
public class FileManagerService {

	@Value("${myapp.file-storage-path}")
    private String fileStoragePath;

    @Autowired
    SessionService session;

    @Autowired
    ImageProductService imageProductService;

    private Path getPath(String shopname, String id, String filename) {
        File shop = Paths.get(fileStoragePath + "/product", shopname).toFile();
        if(!shop.exists()) {
            shop.mkdirs();
        }
        File dir = Paths.get(fileStoragePath + "/product", shopname, id).toFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return Paths.get(dir.getAbsolutePath(), filename);
    }

    public byte[] read(String shopname, String id, String filename) {
        Path path = this.getPath(shopname, id, filename);
        try {
            return Files.readAllBytes(path);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public List<String> save(String shopname, String id, MultipartFile[] files) {
        List<String> filenames = new ArrayList<String>();
        for (MultipartFile file : files) {
        	System.out.println("1");
            String name = System.currentTimeMillis() + file.getOriginalFilename();
            System.out.println("2");
            String filename = Integer.toHexString(name.hashCode()) + name.substring(name.lastIndexOf("."));
            System.out.println("3");
            System.out.println(shopname);
            System.out.println(id);
            System.out.println(filename);
            Path path = this.getPath(shopname, id, filename);
            System.out.println("4");
            try {
            	System.out.println("5");
                file.transferTo(path);
                System.out.println("6");
                filenames.add(filename);
                System.out.println("7");
                imageProductService.insertImageProduct(id, filename, id);
                System.out.println("7");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        session.set("listimg", filenames);
        return filenames;
    }

    public void delete(String shopname, String id, String filename) {
        Path path = this.getPath(shopname, id, filename);
        path.toFile().delete();
    }

    public List<String> list(String id, String shopname) {
        List<ImageProduct> listImageProduct = imageProductService.findAllImageProductByProductId(id);
        System.out.println(listImageProduct.size());
        List<String> filenames = new ArrayList<String>();
        File dir = Paths.get(fileStoragePath + "/product", shopname, id).toFile();
        File shop = Paths.get(fileStoragePath + "/product", shopname).toFile();
        System.out.println(dir.getAbsolutePath());
        if(!shop.exists()) {
            shop.mkdirs();

            if(!dir.exists()){
                dir.mkdirs();
                File[] files = dir.listFiles();
                for (File file : files) {
                    filenames.add("/img/product/"+file.getName());
                }
            }
        }else {
            if (dir.exists() && listImageProduct.size() > 0) {
                System.out.println(listImageProduct.size());
                System.out.println(id+"check2");
                File[] files = dir.listFiles();
                for (File file : files) {
                    filenames.add("/img/product/"+file.getName());
                }
            } else if (dir.exists() && listImageProduct.size() == 0) {
                System.out.println(id+"check1");
                File file = Paths.get(fileStoragePath + "/product", shopname).toFile();
                Path path = Paths.get(file.getAbsolutePath(), id);
                path.toFile().delete();
            }else if(!dir.exists()) {
                System.out.println(id+"check");
                dir.mkdirs();
                File[] files = dir.listFiles();
                for (File file : files) {
                    filenames.add("/img/product/"+file.getName());
                }
            }
        }
        return filenames;
    }

    public void changeImage(String shopname, String id) {
        Path source = Paths.get(fileStoragePath + "/product", "default.webp");
        Path target = this.getPath(shopname, id, "default.webp");
        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
