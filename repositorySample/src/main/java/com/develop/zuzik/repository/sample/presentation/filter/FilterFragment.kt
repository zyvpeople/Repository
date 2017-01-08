package com.develop.zuzik.repository.sample.presentation.filter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.develop.zuzik.repository.R
import com.develop.zuzik.repository.sample.application.app
import com.develop.zuzik.repository.sample.domain.filter.Filter
import com.jakewharton.rxbinding.widget.RxTextView
import kotlinx.android.synthetic.main.fragment_filter.*
import rx.Observable
import rx.internal.util.SubscriptionList

/**
 * User: zuzik
 * Date: 1/8/17
 */
class FilterFragment : Fragment() {

    private val subscriptionList = SubscriptionList()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filterModel = context.app.filterModel
        val nameObservable: Observable<String?> = RxTextView
                .textChanges(name)
                .map { it.toString() }
                .defaultIfEmpty("")
                .map { if (it.isEmpty()) null else it }
        val ageObservable: Observable<Int?> = RxTextView
                .textChanges(age)
                .map { it.toString() }
                .defaultIfEmpty("")
                .map {
                    try {
                        it.toInt()
                    } catch (e: NumberFormatException) {
                        null
                    }
                }

        val filterObservable = Observable
                .combineLatest(nameObservable, ageObservable, ::Filter)
        subscriptionList.add(filterModel
                .update(filterObservable))
        subscriptionList.add(filterModel
                .stateObservable
                .map { it.name ?: "" }
                .distinctUntilChanged()
                .filter { it != name.text.toString() }
                .subscribe(RxTextView.text(name)))
        subscriptionList.add(filterModel
                .stateObservable
                .map { it.age?.toString() ?: "" }
                .distinctUntilChanged()
                .filter { it != age.text.toString() }
                .subscribe(RxTextView.text(age)))
    }

    override fun onDestroyView() {
        subscriptionList.unsubscribe()
        super.onDestroyView()
    }
}