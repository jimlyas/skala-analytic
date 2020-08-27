package id.udata.analytics.repository

import id.udata.analytics.api.AnalyticsApiClient

/**
 * @author Jimly A.
 * @since 25-Aug-20.
 * @param api Api Client for id.udata.skala.analytics
 */

class AnalyticsRepository(private val api: AnalyticsApiClient) {

    /**
     * @param endpoint endpoint for id.udata.skala.analytics
     * @param params contains all the parameter for id.udata.skala.analytics
     * @return Any value
     */
    suspend fun logEvent(endpoint: String, params: HashMap<String, String>): Any =
        api.logEventAsync(endpoint, params).await()
}