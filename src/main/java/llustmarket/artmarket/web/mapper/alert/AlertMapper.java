package llustmarket.artmarket.web.mapper.alert;

import llustmarket.artmarket.domain.alert.Alert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlertMapper {
    void insertOne(Alert vo);
    List<Alert> selectOne(long memberId);
}
