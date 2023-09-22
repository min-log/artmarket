package llustmarket.artmarket.web.mapper.file;

import llustmarket.artmarket.domain.file.FileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    void insertOne(FileVO vo);

    FileVO selectOnePathAndId(FileVO vo);

    FileVO selectOnefileName(String fileName);

    void deleteFile(FileVO vo);
}
