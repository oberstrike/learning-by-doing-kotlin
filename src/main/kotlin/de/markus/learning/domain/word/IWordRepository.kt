package de.markus.learning.domain.word

import io.quarkus.mongodb.panache.PanacheMongoRepository
import io.quarkus.mongodb.panache.PanacheQuery
import javax.enterprise.context.ApplicationScoped

interface IWordRepository : PanacheMongoRepository<Word>{
    fun findByQuery(id: String = "", text: String = ""): PanacheQuery<Word>
}

@ApplicationScoped
class WordRepository : IWordRepository {

    override fun findByQuery(id: String, text: String): PanacheQuery<Word> {
        return find("id = ?1 or text like ?2", id, text)
    }

}