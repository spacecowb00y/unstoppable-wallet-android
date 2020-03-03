package io.horizontalsystems.bankwallet.core.managers

import android.content.Context
import android.util.Log
import io.horizontalsystems.bankwallet.core.ILocalStorage
import io.horizontalsystems.bankwallet.core.INetManager
import io.horizontalsystems.netkit.NetKit
import io.horizontalsystems.tor.ConnectionStatus
import io.horizontalsystems.tor.Tor
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class NetManager(context: Context, val localStorage: ILocalStorage) : INetManager {

    interface Listener{
        fun onStatusChange(torStatus: TorStatus)
    }

    override val torObservable = BehaviorSubject.create<TorStatus>()
    private val disposables = CompositeDisposable()
    private var listener: Listener? = null
    private val kit: NetKit by lazy {
        NetKit(context)
    }

    init {
        if (localStorage.torEnabled) {
            start()
        }
    }

    override fun start() {
        disposables.add(kit.torInfoSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listener?.onStatusChange(getStatus(it))
                    torObservable.onNext(getStatus(it))
                }, {
                    Log.e("NetManager", "Tor exception", it)
                })
        )
        kit.startTor(false)
    }

    override fun stop(): Single<Boolean> {
        return kit.stopTor()
    }

    override fun enableTor() {
        localStorage.torEnabled = true
    }

    override fun disableTor() {
        localStorage.torEnabled = false
    }

    override fun setListener(listener: Listener) {
        this.listener = listener
    }

    override val isTorEnabled: Boolean
        get() = localStorage.torEnabled

    private fun getStatus(torinfo: Tor.Info): TorStatus {
        return when (torinfo.connection.status) {
            ConnectionStatus.CONNECTED ->TorStatus.Connected
            ConnectionStatus.CONNECTING ->TorStatus.Connecting
            ConnectionStatus.CLOSED ->TorStatus.Closed
            ConnectionStatus.FAILED ->TorStatus.Failed
        }
    }

}

enum class TorStatus(val value: String) {
    Connected("Connected"),
    Connecting("Connecting"),
    Closed("Closed"),
    Failed("Failed");
}
