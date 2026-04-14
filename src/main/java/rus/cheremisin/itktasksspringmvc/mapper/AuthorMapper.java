package rus.cheremisin.itktasksspringmvc.mapper;

import org.mapstruct.Mapper;
import rus.cheremisin.itktasksspringmvc.DTO.AuthorDTO;
import rus.cheremisin.itktasksspringmvc.entity.Author;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO toDto(Author author);
    Author toAuthor(AuthorDTO dto);

    List<AuthorDTO> toDtoList(List<Author> list);
}
