package de.markus.learning.domain.word

import de.markus.learning.domain.util.IMapper
import org.bson.types.ObjectId
import javax.enterprise.context.ApplicationScoped

interface IWordMapper: IMapper<Word, IWordDTO>

@ApplicationScoped
class WordMapper : IWordMapper {

    override fun convertModelToDTO(model: Word): IWordDTO {
        return WordDTO(
                id = model.id?.toString(),
                text = model.text,
                type = model.type,
                translations = model.translations.toTypedArray()
        )
    }

    override fun convertDTOToModel(dto: IWordDTO): Word {
        return Word(
                text = dto.text,
                type = dto.type,
                translations = dto.translations.toList()
        ).apply {
            if (dto.id != null) id = ObjectId(dto.id)
        }
    }

}

