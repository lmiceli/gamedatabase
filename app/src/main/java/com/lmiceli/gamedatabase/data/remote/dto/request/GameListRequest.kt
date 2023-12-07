package com.lmiceli.gamedatabase.data.remote.dto.request

data class GameListRequest(

    val fields: String = "*",
    val where: String? = null,
    val sort: String? = null,
    val limit: Int = GAME_REQUEST_SIZE,
    val offset: Int = 0,
) {
    // todo later this should be made less manual
    fun toRawPostBody(): String {

        return """fields $fields;
            |where $where;
            |sort $sort;
            |limit $limit;
            |offset $offset;
        """.trimMargin()
    }

    fun getPageRequest(pageNumber: Int): GameListRequest {
        val newOffset = (pageNumber - 1) * limit
        return this.copy(offset = newOffset)
    }

    companion object {
        const val GAME_REQUEST_SIZE = 20
        private val POPULAR_GAME_REQUEST = GameListRequest(
            fields = "name, summary, cover.url, total_rating_count, aggregated_rating, genres.name, " +
                    "involved_companies.company.name, first_release_date, videos.video_id, platforms.name",
            where = "total_rating_count != null & cover != null & aggregated_rating != null & platforms != null",
            sort = "total_rating_count desc",
            limit = 20,
            offset = 0
        )

        /**
         * first page is page 1
         */
        fun getPopularGamesBody(pageNumber: Int) =
            POPULAR_GAME_REQUEST
                .getPageRequest(pageNumber)
                .toRawPostBody()
    }

}


