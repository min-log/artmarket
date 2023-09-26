package llustmarket.artmarket.web.service.alert;

import llustmarket.artmarket.domain.alert.AlertType;
import llustmarket.artmarket.web.dto.alert.AlertDTO;
import llustmarket.artmarket.web.dto.alert.Alrams;

public interface AlertService {
    void registerAlert(long memberId, long alertPath, AlertType alertType);
    void updateStatus(long memberId);
    void updateDate(AlertDTO dto);

    Alrams searchOneAlert(long memberId);
    AlertDTO searchOnePath(long memberId ,AlertType alertType);

    int searchOneAlertNumber(long alramTotalID);
}
