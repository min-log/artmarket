package llustmarket.artmarket.web.mapper.file;

import llustmarket.artmarket.domain.chat.Chat;
import llustmarket.artmarket.domain.file.File;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    void insertOne(File vo);
    File selectOnePathAndId(File vo);
}
