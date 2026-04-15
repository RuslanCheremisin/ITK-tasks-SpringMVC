package rus.cheremisin.itktasksspringmvc.service;

import org.springframework.web.multipart.MultipartFile;

public interface RequestMapper<T> {

    T toEntity (MultipartFile file);
    byte[] toJsonFile(T t);

}
