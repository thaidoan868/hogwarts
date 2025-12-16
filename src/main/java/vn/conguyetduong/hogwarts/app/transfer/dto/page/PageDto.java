package vn.conguyetduong.hogwarts.app.transfer.dto.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PageDto<T> {
    private List<T> content;

    @Schema( defaultValue = "18", description = "The total number of pages")
    private int totalPages;

    @Schema( defaultValue = "3", description = "The current page")
    private int number;

    @Schema( defaultValue = "20", description = "Number of elements per page")
    private int size;

    @Schema( defaultValue = "3", description = "The total number of available results")
    private long totalElements;

    @Schema( defaultValue = "20", description = "Total number of elements on this page, usually equal to size but the last page can change this number")
    private int numberOfElements;
}
