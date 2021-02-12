package com.ilnur.modimporter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

/*

abstract class UnifiedAdapter<TBinding : ViewDataBinding, TData> : RecyclerView.Adapter<ProstoHolder<TBinding>>() {

    abstract val viewModel: ProstoViewModel<TData>
    abstract val layoutId: Int

    private var dataSize: Int = 0

    open fun setData(data: List<TData>) {
        this.dataSize = data.size
        viewModel.data = data
        notifyDataSetChanged()
    }

    open var onBind: ((ProstoHolder<TBinding>) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProstoHolder<TBinding> =
        ProstoHolder.create(parent, layoutId)

    override fun getItemCount(): Int = dataSize

    override fun onBindViewHolder(holder: ProstoHolder<TBinding>, position: Int) {
        holder.bind(viewModel, position)
        onBind?.invoke(holder)
    }
}

abstract class ProstoViewModel<T>: ViewModel() {
    abstract var data: List<T>
}

open class ProstoHolder<TBinding : ViewDataBinding>(val binding: TBinding) : RecyclerView.ViewHolder(binding.root) {
    open fun <TData, TViewModel : ProstoViewModel<TData>> bind(viewModel: TViewModel, position: Int) {
        binding.setVariable(binding.viewModel, viewModel)
        binding.setVariable(BR.position, position)
        binding.executePendingBindings()
    }

    companion object {
        fun <TBinding : ViewDataBinding> create(parent: ViewGroup, layoutId: Int): ProstoHolder<TBinding> {
            val inflater = LayoutInflater.from(parent.context)
            val binding: TBinding = DataBindingUtil.inflate(inflater, layoutId, parent, false)
            return ProstoHolder(binding)
        }
    }
}*/
