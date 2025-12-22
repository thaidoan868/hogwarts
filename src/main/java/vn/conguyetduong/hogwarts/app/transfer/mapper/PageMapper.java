package vn.conguyetduong.hogwarts.app.transfer.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.conguyetduong.hogwarts.app.transfer.dto.page.PageDto;

@Component
public class PageMapper {
    public <T> PageDto<T>  toPageDto(Page<T> page) {
        if (page == null) {return null;}
        return new PageDto<T>(
                page.getContent(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getNumberOfElements()
        );
    }
}
