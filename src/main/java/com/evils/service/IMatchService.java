package com.evils.service;

import com.evils.entity.dto.PlayerDetailSingleMatchDTO;
import com.evils.entity.form.SearchParam;

/**
 * Title: evils
 * CreateDate:  2018/7/3
 *
 * @author houdengw
 * @version 1.0
 */
public interface IMatchService {

    ServiceListResult<PlayerDetailSingleMatchDTO> getMatches(SearchParam searchParam);
}
