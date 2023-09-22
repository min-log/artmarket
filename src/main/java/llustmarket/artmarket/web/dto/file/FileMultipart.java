package llustmarket.artmarket.web.dto.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;


public class FileMultipart implements MultipartFile {

    // websocket 에서 전달 받는 바이트 배열의 파일 객체
    private final byte[] bytes;
    private final String name;
    private final String originalFilename;
    private final String contentType;
    private final long size;

    public FileMultipart(byte[] fileData, String fileName, String fileContentType, long fileSize) {
        this.bytes = fileData;
        this.name = fileName;
        this.originalFilename = fileName;
        this.contentType = fileContentType;
        this.size = fileSize;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOriginalFilename() {
        return this.originalFilename;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isEmpty() {
        return this.bytes == null || this.bytes.length == 0;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return this.bytes;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.bytes);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        if (dest.exists() && !dest.delete()) {
            throw new IllegalStateException("Destination file already exists and cannot be deleted.");
        }
        try (OutputStream os = new FileOutputStream(dest)) {
            os.write(this.bytes);
        }
    }
}
