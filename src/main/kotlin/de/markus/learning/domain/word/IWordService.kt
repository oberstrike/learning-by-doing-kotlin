package de.markus.learning.domain.word

import de.markus.learning.domain.util.AbstractService
import de.markus.learning.domain.util.IService
import de.markus.learning.domain.util.WordValidator
import io.quarkus.panache.common.Page
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

interface IWordService : IService<WordDTO>

fun WordService.findByQuery(
    id: String = "",
    text: String = "",
    index: Int = 0,
    size: Int = 10
): List<WordDTO> {
    val page = Page.of(index, size)
    return if (id == "" && text == "")
        repository
                .findAll()
                .page<Word>(page)
                .list<Word>()
                .map(mapper::convertModelToDTO)
    else
        repository
                .findByQuery(id = id, text = text)
                .page<Word>(page)
                .list<Word>()
                .map(mapper::convertModelToDTO)

}

@ApplicationScoped
class WordService : AbstractService<Word, WordDTO>(), IWordService {

    @Inject
    override lateinit var mapper: WordMapper

    @Inject
    override lateinit var repository: IWordRepository

    @Inject
    override lateinit var validator: WordValidator
}
