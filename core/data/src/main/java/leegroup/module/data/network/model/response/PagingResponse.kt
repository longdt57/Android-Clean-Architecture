package leegroup.module.data.network.model.response

interface PagingResponse<T> {
    val total: Int
    val pagingItems: List<T>
}
