package id.udata.analytics

import android.content.Context
import id.udata.analytics.repository.AnalyticsRepository
import id.udata.analytics.utils.DeviceSpecsUtils
import id.udata.analytics.utils.NetworkUtils
import id.udata.analytics.utils.getCurrentDate
import id.udata.analytics.utils.provideClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @author Jimly A.
 * @since 25-Aug-20.
 * @param baseUrl Base Url for Skala Analytics
 * @param endpoint endpoint for logging event
 * @param ctx context for getting default params
 *
 * @property deviceUtils utility to get device info
 * @property networkUtils utility to get network info
 * @property listJobs list of job, used for cancelling coroutine's job
 * @property defaultParams list of default parameters for each event
 */
class SkalaAnalytic(private val ctx: Context, private val endpoint: String, baseUrl: String) {

    private val deviceUtils = DeviceSpecsUtils(ctx)
    private val networkUtils = NetworkUtils(ctx)

    private val listJobs = ArrayList<Job>()
    private val repo = AnalyticsRepository(provideClient(baseUrl))
    private val defaultParams = hashMapOf(
        Pair("device_name", deviceUtils.getDeviceName()),
        Pair("ram", deviceUtils.getTotalRAM()),
        Pair("storage", deviceUtils.getStorage()),
        Pair("cpu", deviceUtils.cPU),
        Pair("fw", deviceUtils.firmware),
        Pair("ip_address", networkUtils.getIPAddress()),
        Pair("carrier", networkUtils.getCarrier()),
        Pair("network_type", networkUtils.getNetworkType()),
        Pair("platform", "Android")
    )

    /**
     * call this method to log user's event
     * @param params All params with key needed for specific event
     */
    fun logEvent(params: HashMap<String, String>) {
        params.putAll(defaultParams)
        params["date_time"] = getCurrentDate()

        GlobalScope.launch {
            try {
                repo.logEvent(endpoint, params)
            } catch (t: Throwable) {

            }
        }.let(listJobs::add)
    }

    /**
     * call this method to cancel any coroutine job from this class
     */
    fun cancel() {
        listJobs.forEach { it.cancel() }
    }
}