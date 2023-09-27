package llustmarket.artmarket.web.mapper.alert;

import llustmarket.artmarket.domain.alert.Alert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlertMapper {
    void insertOne(Alert vo);
    List<Alert> selectOne(long memberId);
    Alert selectOnePathId(Alert vo);
    void updateStatus(long memberId);
    void updateOneStatus(Alert vo);
    void updateDate (Alert vo);
    int selectTotalNumber(long alramTotalID);

}
