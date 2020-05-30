package com.maxsch.symmetricguide.ui.material.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maxsch.symmetricguide.R
import com.maxsch.symmetricguide.entity.material.Material
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_material.view.*

class MaterialsAdapter(
    private val materialClickListener: (Int) -> Unit,
    private val materialLongClickListener: (Material) -> Unit
) : RecyclerView.Adapter<MaterialsAdapter.ViewHolder>() {

    var items = mutableListOf<Material>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_material, parent, false),
            materialClickListener,
            materialLongClickListener
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    inner class ViewHolder(
        override val containerView: View,
        private val materialClickListener: (Int) -> Unit,
        private val materialLongClickListener: (Material) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(material: Material) {
            containerView.apply {
                setOnClickListener {
                    materialClickListener(material.id)
                }
                setOnLongClickListener {
                    materialLongClickListener(material)
                    true
                }
                materialTitle.text = material.title
                materialShortText.text = material.description
            }
        }
    }
}