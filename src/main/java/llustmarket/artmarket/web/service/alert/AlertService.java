package llustmarket.artmarket.web.service.alert;

import llustmarket.artmarket.domain.alert.AlertType;
import llustmarket.artmarket.web.dto.alert.AlertDTO;
import llustmarket.artmarket.web.dto.alert.Alrams;

public interface AlertService {
    void registerAlert(long memberId, long alertPath, AlertType alertType);
    Alrams searchOneAlert(long memberId);
}
