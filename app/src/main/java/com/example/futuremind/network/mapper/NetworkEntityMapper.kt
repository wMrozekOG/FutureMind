package com.example.futuremind.network.mapper

/**
 * Base mapper for network entities
 */
abstract class NetworkEntityMapper <Entity, Model>{
    /**
     * Maps single Entity to Model
     *
     * @param entity - entity to be parsed
     * @return model parsed from entity
     */
    abstract fun mapFromEntity(entity: Entity): Model

    /**
     * Maps list of Entity to list of Model
     *
     * @param entities - entities to be parsed
     * @return models parsed from entities
     */
    abstract fun mapFromEntityList(entities: List<Entity>): List<Model>

    abstract fun performValidation(entity: Entity): Boolean
}