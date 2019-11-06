package com.teoryul.currencyconverter.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.teoryul.currencyconverter.BR
import com.teoryul.currencyconverter.R
import com.teoryul.currencyconverter.event.NavigationAction
import com.teoryul.currencyconverter.ui.extensions.showDialog
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment<V : BaseVM, B : ViewDataBinding> : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: V
    protected abstract val viewModelClass: KClass<V>
    private lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass.java)
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initBinding(binding)
        observeNavigationEvents()
        observeErrorEvents()

        return binding.root
    }

    override fun onResume() {
        setActionBarTitle()
        setActionBarVisibility()
        super.onResume()
    }

    protected abstract fun getLayoutResId(): Int

    protected open fun initBinding(binding: B) = binding.setVariable(BR.viewModel, viewModel)

    private fun observeNavigationEvents() =
        viewModel.newDestinationEvent.observe(this, Observer { event ->
            if (event != null) {
                event.getContent()?.let { actionId ->
                    if (actionId == NavigationAction.POP_BACK_STACK.value) {
                        findNavController().popBackStack()
                    } else {
                        navigateTo(actionId, event.bundle)
                    }
                }
            }
        })

    private fun navigateTo(actionId: Int, bundle: Bundle?) = findNavController().navigate(actionId, bundle)

    private fun observeErrorEvents() =
        viewModel.errorEvent.observe(this, Observer { event ->
            if (event != null) {
                event.getContent()?.let { messageId ->
                    if (isVisible) {
                        showDialog {
                            setTitle(R.string.title_dialog_error)
                            setMessage(messageId)
                            setPositiveButton(R.string.btn_ok) { dialog, which -> dialog.cancel() }
                        }
                    }
                }
            }
        })

    protected open fun getTitle() = R.string.empty

    protected open fun shouldHideActionBar() = false

    private fun setActionBarTitle() = (activity as AppCompatActivity).supportActionBar?.setTitle(getTitle())

    private fun setActionBarVisibility() {
        (activity as AppCompatActivity).supportActionBar?.setShowHideAnimationEnabled(false)
        if (shouldHideActionBar()) {
            (activity as AppCompatActivity).supportActionBar?.hide()
        } else {
            (activity as AppCompatActivity).supportActionBar?.show()
        }
    }
}