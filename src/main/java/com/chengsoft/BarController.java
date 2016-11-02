package com.chengsoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tcheng on 11/2/16.
 */
@RestController
public class BarController {

    @Autowired ConversionService conversionService;
    @Autowired RequestScopedBean requestScopedBean;

    @RequestMapping("/bar/{id}")
    public BarView getGallery(@PathVariable("id") Integer id,
                              @RequestParam(value="loadSaleStats", required = false, defaultValue = "false") Boolean loadSaleStats) {
        requestScopedBean.setLoadSaleStats(loadSaleStats);
        return conversionService.convert(Foo.builder()
                .id(id)
                .build(), BarView.class);
    }
}
