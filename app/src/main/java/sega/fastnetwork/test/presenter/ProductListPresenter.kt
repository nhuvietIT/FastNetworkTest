package sega.fastnetwork.test.presenter

import android.os.Looper
import android.util.Log
import com.androidnetworking.error.ANError
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import sega.fastnetwork.test.fragment.ProductListFragment
import sega.fastnetwork.test.model.ResponseListProduct
import sega.fastnetwork.test.util.Constants
import sega.fastnetwork.test.view.ProductListView


/**
 * Created by sega4 on 27/07/2017.
 */

class ProductListPresenter(productListFragment: ProductListFragment) {
    internal var mProductListView: ProductListView = productListFragment
    var userdetail = "USERDETAIL"

    fun getProductList() {

        Rx2AndroidNetworking.post(Constants.BASE_URL + "/allproduct")
                .build()
                .setAnalyticsListener { timeTakenInMillis, bytesSent, bytesReceived, isFromCache ->
                    Log.d(userdetail, " timeTakenInMillis : " + timeTakenInMillis)
                    Log.d(userdetail, " bytesSent : " + bytesSent)
                    Log.d(userdetail, " bytesReceived : " + bytesReceived)
                    Log.d(userdetail, " isFromCache : " + isFromCache)
                }
                .getObjectObservable(ResponseListProduct::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseListProduct> {
                    override fun onNext(response: ResponseListProduct?) {
                        Log.d(userdetail, "onResponse isMainThread : " + (Looper.myLooper() == Looper.getMainLooper()).toString())

                        mProductListView.getListProduct(response?.listproduct!!)
                    }


                    override fun onComplete() {


                    }

                    override fun onError(e: Throwable) {
                        if (e is ANError) {
                            val anError = e
                            if (anError.errorCode != 0) {
                                // received ANError from server
                                // error.getErrorCode() - the ANError code from server
                                // error.getErrorBody() - the ANError body from server
                                // error.getErrorDetail() - just a ANError detail
                                Log.d(userdetail, "onError errorCode : " + anError.errorCode)
                                Log.d(userdetail, "onError errorBody : " + anError.errorBody)
                                Log.d(userdetail, "onError errorDetail : " + anError.errorDetail)
                                mProductListView.setErrorMessage(anError.errorDetail)
                            } else {
                                // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                                Log.d(userdetail, "onError errorDetail : " + anError.errorDetail)
                                mProductListView.setErrorMessage(anError.errorDetail)
                            }
                        } else {
                            Log.d(userdetail, "onError errorMessage : " + e.message)
                            mProductListView.setErrorMessage(e.message!!)
                        }
                    }

                    override fun onSubscribe(d: Disposable) {

                    }


                })
    }

}