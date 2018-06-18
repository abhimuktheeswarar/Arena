package com.abhi.koinarc

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.architecture.ext.sharedViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class MainWithFragmentActivityFragment : Fragment() {

    // Inject SimpleViewModel
    val viewModel: SimpleViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_with_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()
        Log.i(this.javaClass.simpleName, "Hello from ViewModel = ${viewModel.sayHello()} with count = ${viewModel.count}")
    }
}
