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
    protected lateinit var navController: NavController

    override fun bind(pojo: TPOJO, navController: NavController) {
        this.pojo = pojo
        model = pojo.toModel()
        this.navController = navController

        setupViews()
        setupOnClickListener()
    }

    abstract fun setupViews()
    abstract fun setupOnClickListener()
}