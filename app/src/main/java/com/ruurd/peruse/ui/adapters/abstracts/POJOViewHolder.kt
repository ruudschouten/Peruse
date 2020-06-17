package com.ruurd.peruse.ui.adapters.abstracts

import android.view.View
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.ruurd.peruse.data.pojo.PojoToModel
import com.ruurd.peruse.models.ModelToPojo
import com.ruurd.peruse.ui.adapters.interfaces.IPOJOViewHolder

abstract class POJOViewHolder<
        TPOJO : PojoToModel<TModel>,
        TModel : ModelToPojo<TPOJO>
        >
    (var view: View) :
    RecyclerView.ViewHolder(view),
    IPOJOViewHolder<TPOJO> {

    protected lateinit var model: TModel
    protected lateinit var pojo: TPOJO

    override fun bind(pojo: TPOJO) {
        this.pojo = pojo
        model = pojo.toModel()

        setupViews()
        setupOnClickListener()
    }

    abstract fun setupViews()
    abstract fun setupOnClickListener()
}