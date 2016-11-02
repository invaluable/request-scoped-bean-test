package com.chengsoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by tcheng on 11/2/16.
 */
@Component
public class FooToBarViewConverter implements Converter<Foo, BarView> {

    @Autowired RequestScopedBean requestScopedBean;

    @Override
    public BarView convert(Foo house) {
        return BarView.builder()
                .barId(house.getId())
                .loadSaleStats(requestScopedBean.getLoadSaleStats())
                .build();
    }
}
