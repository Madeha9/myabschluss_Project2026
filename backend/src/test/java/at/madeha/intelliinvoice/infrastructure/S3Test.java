package at.madeha.intelliinvoice.infrastructure;

import java.io.FileInputStream;
import java.io.InputStream;

public class S3Test {

    static void main(String[] args) throws Exception {

        CloudStorageService storage = new CloudStorageService();

        InputStream file = new FileInputStream("test.txt");

        String url = storage.uploadFile(file, "test.txt");

        System.out.println("Uploaded file URL:");
        System.out.println(url);
    }
}