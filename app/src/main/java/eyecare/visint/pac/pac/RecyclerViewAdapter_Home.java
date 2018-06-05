package eyecare.visint.pac.pac;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class RecyclerViewAdapter_Home extends RecyclerView.Adapter<RecyclerViewHolders_Home>
{

    private List<ItemObjectHome> itemList;
    private Context context;

    public RecyclerViewAdapter_Home(Context context, List<ItemObjectHome> itemList)
    {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders_Home onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_home_screen, null);
        RecyclerViewHolders_Home rcv1 = new RecyclerViewHolders_Home(layoutView);
        return rcv1;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders_Home holder, int position)
    {
        holder.countryName.setText(itemList.get(position).getName());
        animate(holder);
    }

    public void animate(RecyclerView.ViewHolder viewHolder)
    {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.trans);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    @Override
    public int getItemCount()
    {
        return this.itemList.size();
    }
}