package com.chengsoft;

import lombok.Builder;
import lombok.Data;

/**
 * Created by tcheng on 11/2/16.
 */
@Data
@Builder
public class BarView {
    private Integer barId;
    private Boolean loadSaleStats;
}
