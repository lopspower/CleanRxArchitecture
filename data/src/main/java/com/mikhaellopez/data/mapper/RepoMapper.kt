package com.mikhaellopez.data.mapper

import com.mikhaellopez.data.net.dto.RepoDTO
import com.mikhaellopez.data.persistence.entity.RepoEntity
import com.mikhaellopez.domain.model.Repo
import javax.inject.Inject

/**
 * Mapper class used to transform [RepoDTO] or [RepoEntity] (in the data layer) to [Repo]
 * in the domain layer and vice versa.
 */
class RepoMapper
@Inject constructor() {

    //region DTO to MODEL
    /**
     * Transform a [RepoDTO] into an [Repo].
     * @param dto  Object to be transformed.
     * @param userName Foreign key
     * @return [Repo] if valid [RepoDTO]
     */
    fun transform(dto: RepoDTO, userName: String): Repo =
        Repo(dto.id, dto.name, dto.description, dto.url, false, userName)

    /**
     * Transform a Collection of [RepoDTO] into a List of [Repo].
     * @param dtoCollection Object Collection to be transformed.
     * @param userName          Foreign key
     * @return list of [Repo]
     */
    fun transform(dtoCollection: Collection<RepoDTO>, userName: String): List<Repo> =
        dtoCollection.map { transform(it, userName) }
    //endregion

    //region ENTITY to MODEL
    /**
     * Transform a [RepoEntity] into an [Repo].
     * @param entity Object to be transformed.
     * @return [Repo] if valid [RepoEntity] otherwise null.
     */
    fun transform(entity: RepoEntity): Repo =
        Repo(
            entity.id,
            entity.name,
            entity.description,
            entity.url,
            entity.isFavorite,
            entity.userName
        )

    /**
     * Transform a Collection of [RepoEntity] into a List of [Repo].
     * @param entityCollection Object Collection to be transformed.
     * @return list of [Repo]
     */
    fun transform(entityCollection: Collection<RepoEntity>): List<Repo> =
        entityCollection.map { transform(it) }
    //endregion

    //region MODEL to ENTITY
    /**
     * Transform a [Repo] into an [RepoEntity].
     * @param model Object to be transformed.
     * @return [Repo] if valid [RepoEntity] otherwise null.
     */
    fun transformToEntity(model: Repo): RepoEntity =
        RepoEntity(
            model.id,
            model.name,
            model.description,
            model.url,
            model.isFavorite,
            model.userName
        )

    /**
     * Transform a Collection of [Repo] into a List of [RepoEntity].
     * @param modelCollection Object Collection to be transformed.
     * @return list of [RepoEntity]
     */
    fun transformToEntity(modelCollection: Collection<Repo>): List<RepoEntity> =
        modelCollection.map { transformToEntity(it) }
    //endregion

}