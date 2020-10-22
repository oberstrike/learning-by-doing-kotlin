package de.markus.learning.domain.word

import com.ibm.asyncutil.util.Either
import de.markus.learning.domain.util.AbstractService
import de.markus.learning.domain.util.IMapper
import de.markus.learning.domain.util.IService
import io.quarkus.mongodb.panache.PanacheMongoRepository
import io.quarkus.panache.common.Page
import javax.annotation.Priority
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject


fun WordService.findByQuery(id: String,
                            text: String,
                            index: Int,
                            size: Int): Array<IWordDTO> {
    val page = Page.of(index, size)

    return if (id == "" && text == "")
        repository
                .findAll()
                .page<Word>(page)
                .list<Word>()
                .map(mapper::convertModelToDTO)
                .toTypedArray()
    else
        repository
                .findByQuery(id = id, text = text)
                .page<Word>(page)
                .list<Word>()
                .map(mapper::convertModelToDTO)
                .toTypedArray()

}

@ApplicationScoped
class WordService : AbstractService<Word, IWordDTO>() {

    @Inject
    override lateinit var mapper: IWordMapper

    @Inject
    override lateinit var repository: IWordRepository
}
