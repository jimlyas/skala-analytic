package id.udata.analytics.api

import kotlinx.coroutines.Deferred
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * @author Jimly A.
 * @since 25-Aug-20.
 */
interface AnalyticsApiClient {

    /**
     * @param url endpoint for Analytics API
     * @param params all the parameter for id.udata.skala.analytics
     * @return Deferred any for coroutine
     */
    @FormUrlEncoded
    @POST
    fun logEventAsync(@Url url: String, @FieldMap params: HashMap<String, String>): Deferred<Any>
}