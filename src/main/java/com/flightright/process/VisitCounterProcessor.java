package com.flightright.process;

import com.flightright.dto.VisitorDto;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class VisitCounterProcessor implements ItemProcessor<VisitorDto, VisitorDto> {

    @Override
    public VisitorDto process(VisitorDto item) {

        if (StringUtils.hasLength(item.getEmail())
                && StringUtils.hasLength(item.getPhone())
                && StringUtils.hasLength(item.getSource())) {
            return item;
        }
        return null;
    }
}
