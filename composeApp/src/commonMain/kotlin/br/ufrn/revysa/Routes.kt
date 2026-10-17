package br.ufrn.revysa

import kotlinx.serialization.Serializable

@Serializable internal data object Home

@Serializable internal data object LibraryGraph

@Serializable internal data object Library

@Serializable internal data object Subjects

@Serializable internal data class Material(val id: String)

@Serializable internal data class Reader(val id: String, val chapter: Int)

@Serializable internal data class Notes(val id: String, val chapter: Int)

@Serializable internal data class CreateCard(val id: String, val chapter: Int)

@Serializable internal data class Quiz(val id: String)

@Serializable internal data object Review

@Serializable internal data object Progress

@Serializable internal data object Profile

@Serializable internal data object Login

@Serializable internal data object Register
