package dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@Accessors(chain = true)
public class DashboardReqDTO {

    private String startTime;

    private String endTime;

    private List<Long> platforms;

    private List<Long> projectIds;

    private List<String> emails;




    public static DashboardReqDTO init(DashboardReqDTO  dashboardReqDTO) {
        DashboardReqDTO reqDTO = new DashboardReqDTO();
        BeanUtils.copyProperties(dashboardReqDTO, reqDTO);
        return reqDTO;
    }
}
