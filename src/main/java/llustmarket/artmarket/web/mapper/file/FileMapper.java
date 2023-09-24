package llustmarket.artmarket.web.mapper.file;

import llustmarket.artmarket.domain.file.FileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileMapper {
    void insertOne(FileVO vo);

    FileVO selectOnePathAndId(FileVO vo);

    FileVO selectOnefileName(String fileName);

    void deleteFile(FileVO vo);

    List<FileVO> getFilesByTypeAndId(@Param("filePath") String filePath, @Param("fileTypeId") Long fileTypeId);
}
