package vn.conguyetduong.hogwarts.app.transfer.mapper;

import org.mapstruct.Mapper;
import vn.conguyetduong.hogwarts.app.transfer.dto.file.FileResponse;
import vn.conguyetduong.hogwarts.infra.model.File;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileResponse toFileResponse(File file);
    List<FileResponse> toFileResponseList(List<File> files);
}
